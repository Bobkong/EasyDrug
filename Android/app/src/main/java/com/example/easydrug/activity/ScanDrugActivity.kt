package com.example.easydrug.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Vibrator
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zbar.ZBarView
import com.example.easydrug.Configs
import com.example.easydrug.R
import com.example.easydrug.Utils.FileUtil
import com.example.easydrug.Utils.RouteUtil
import com.example.easydrug.model.DrugLookUpInfo
import com.example.easydrug.netservice.Api.DrugLookUpService
import com.githang.statusbar.StatusBarCompat
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ScanDrugActivity: Activity(), QRCodeView.Delegate {

    private val TAG = "ScanDrugActivity"
    private var mZBarView: ZBarView? = null
    private var mBack: ImageView? = null
    private var mChoosePic: LinearLayout? = null
    private var mHintLl: LinearLayout? = null
    private var mSuccessImage: ImageView? = null
    private var ifFromOnBoarding = RouteUtil.fromOther

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ifFromOnBoarding = intent.getIntExtra(Configs.drugDetailFromScene, RouteUtil.fromOther)
        Log.i(TAG, ifFromOnBoarding.toString())



        setContentView(R.layout.activity_scan_drug)
        StatusBarCompat.setStatusBarColor(this, this.resources.getColor(R.color.black))



        mHintLl= findViewById(R.id.hint_ll)
        mSuccessImage = findViewById(R.id.successfully_scan)
        mZBarView = findViewById(R.id.zbarview)
        mZBarView?.setDelegate(this)

        mBack = findViewById(R.id.back)
        mBack?.setOnClickListener {
            this.finish()
        }

        mChoosePic = findViewById(R.id.select_photo)
        mChoosePic?.setOnClickListener {

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                // request permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),0);
            } else {
                // start select photo
                startSelectPhoto()
            }


        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),1);
        }
    }

    private fun startSelectPhoto() {
        val intentFromGallery: Intent = Intent();
        // set file type
        intentFromGallery.type = "image/*";
        intentFromGallery.action = Intent.ACTION_PICK;
        startActivityForResult(intentFromGallery, 1);
    }

    override fun onStart() {
        super.onStart()
        mZBarView?.startCamera()
        mZBarView?.showScanRect()
        mZBarView?.startSpot()
    }

    override fun onStop() {
        mZBarView?.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        mZBarView?.onDestroy()
        super.onDestroy()
    }

    private fun showSuccessScan() {
        mHintLl?.visibility = View.GONE
        mChoosePic?.visibility = View.GONE
        mSuccessImage?.visibility = View.VISIBLE
        mZBarView?.hiddenScanRect()
        mZBarView?.stopSpot()
    }

    private fun hideSuccessScan() {
        mHintLl?.visibility = View.VISIBLE
        mChoosePic?.visibility = View.VISIBLE
        mSuccessImage?.visibility = View.GONE
        mZBarView?.showScanRect()
        mZBarView?.startSpot()
    }

    override fun onScanQRCodeSuccess(result: String?) {
        vibrate()

        showSuccessScan()
        if (result == null) {
            hideSuccessScan()
            Toast.makeText(this, "Please make sure the barcode is big enough and in the center of the picture.", Toast.LENGTH_SHORT).show();
        }
        result?.let {
            DrugLookUpService.getInstance().drugLookUp(it).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<DrugLookUpInfo> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(value: DrugLookUpInfo?) {
                        if (value?.items != null && value.items.size != 0) {
                            // ensure scanning a drug
                            if (value.items[0].category.startsWith("Health")) {
                                gotoDrugDetail(value, it)
                            } else {
                                hideSuccessScan()
                                Toast.makeText(this@ScanDrugActivity, "Please scan a drug", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            hideSuccessScan()
                            Toast.makeText(this@ScanDrugActivity, "No drug searched!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: Throwable?) {
                        hideSuccessScan()
                        Toast.makeText(this@ScanDrugActivity, e?.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {
                    }

               })
        }
    }

    private fun gotoDrugDetail(drug: DrugLookUpInfo, upc: String) {
        // go to drug detail screen
        var imageUrl: String? = null
        if (drug.items[0]?.images != null && drug.items[0]?.images?.isNotEmpty() == true) {
            imageUrl = drug.items[0]?.images?.get(0)
        }
        RouteUtil.gotoDrugDetailScreen(this@ScanDrugActivity, ifFromOnBoarding, drug.items[0]?.title, drug.items[0]?.description, imageUrl, upc)
        finish()
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {

    }

    override fun onScanQRCodeOpenCameraError() {
        //Toast.makeText(this, "scan barcode failed", Toast.LENGTH_SHORT).show()
    }


    private fun vibrate() {
        val vibrator: Vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mZBarView?.showScanRect()
        if (resultCode == RESULT_OK && requestCode == 1) {
            mZBarView?.decodeQRCode(FileUtil.getPath(this, data.data))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] !== PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        this@ScanDrugActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    Toast.makeText(this, "Permission Request is Rejected!", Toast.LENGTH_LONG).show()

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Permission Request is Rejected!", Toast.LENGTH_LONG).show()
                }
            } else {
                startSelectPhoto()
            }
        } else if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] !== PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        this@ScanDrugActivity,
                        Manifest.permission.CAMERA
                    )
                ) {
                    Toast.makeText(this, "Permission Request is Rejected!", Toast.LENGTH_LONG).show()

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Permission Request is Rejected!", Toast.LENGTH_LONG).show()
                    finish()
                }
            } else {
                mZBarView?.startCamera()
                mZBarView?.showScanRect()
                mZBarView?.startSpot()
            }
        }
    }


}