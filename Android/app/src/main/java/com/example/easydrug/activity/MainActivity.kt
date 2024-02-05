package com.example.easydrug.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.easydrug.Configs
import com.example.easydrug.R
import com.example.easydrug.Utils.FileUtil
import com.example.easydrug.Utils.FinishActivityEvent
import com.example.easydrug.Utils.UIUtils
import com.example.easydrug.Utils.UpdateProfileEvent
import com.githang.statusbar.StatusBarCompat
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : FragmentActivity() {

    private val TAG = "MainActivity"
    private var userAvatar: ImageView? = null
    private var scanCl: ConstraintLayout? = null
    private var foodCl: ConstraintLayout? = null
    private var exploreCl: ConstraintLayout? = null
    private var drugListCl: ConstraintLayout? = null
    private var learnCl:ConstraintLayout? = null
    private var editInfo: TextView? = null
    private var helloText: TextView? = null
    private var explore: ConstraintLayout? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarCompat.setStatusBarColor(this, this.resources.getColor(R.color.bg_color))
        EventBus.getDefault().register(this);
        userAvatar = findViewById(R.id.user_avatar)
        editInfo = findViewById(R.id.edit_info)
        helloText = findViewById(R.id.hello_text)
        helloText?.text = "Hello, " + FileUtil.getSPString(this, Configs.userNameKey) + "!"
        scanCl = findViewById(R.id.scan_drug)
        setFeatureWidth(scanCl)
        foodCl = findViewById(R.id.check_food)
        setFeatureWidth(foodCl)
        exploreCl = findViewById(R.id.explore)
        setFeatureWidth(exploreCl)
        drugListCl = findViewById(R.id.drug_list)
        setFeatureWidth(drugListCl)
        learnCl = findViewById(R.id.learn_cl)



        learnCl?.setOnClickListener {
            startActivity(Intent(this, FeedbackActivity::class.java))
        }

        scanCl?.setOnClickListener {
            startActivity(Intent(this, ScanDrugActivity::class.java))
        }

        foodCl?.setOnClickListener {
            startActivity(Intent(this, SpeechFoodActivity::class.java))
        }

        exploreCl?.setOnClickListener {
            startActivity(Intent(this, ExploreActivity::class.java))
        }

        drugListCl?.setOnClickListener {
            startActivity(Intent(this, DrugListActivity::class.java))
        }

        editInfo?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        userAvatar?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }

    private fun setFeatureWidth(view: View?) {
        val width = (UIUtils.getWidth(this) - UIUtils.dp2px(this, 64F)) / 2
        val params = view?.layoutParams
        params?.width = width
        view?.layoutParams = params
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: FinishActivityEvent) {
        if (event.scene == FinishActivityEvent.HOME) {
            finish()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: UpdateProfileEvent) {
        helloText?.text = "Hello, " + FileUtil.getSPString(this, Configs.userNameKey) + "!"
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }
}