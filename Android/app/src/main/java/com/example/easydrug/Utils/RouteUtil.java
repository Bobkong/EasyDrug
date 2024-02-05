package com.example.easydrug.Utils;

import android.content.Context;
import android.content.Intent;

import com.example.easydrug.Configs;
import com.example.easydrug.activity.DrugDetailActivity;

public class RouteUtil {

    public static int fromOnBoarding = 0;
    public static int fromDrugList = 1;
    public static int fromOther = 2;
    public static void gotoDrugDetailScreen(Context context, int fromScene, String drugName, String drugDescription, String drugImageUrl, String upc) {
        if (context != null && upc != null &&  !upc.isEmpty()) {
            Intent intent = new Intent(context, DrugDetailActivity.class);
            intent.putExtra("drugName", drugName);
            intent.putExtra("drugDescription", drugDescription);
            intent.putExtra("drugImage", drugImageUrl);
            intent.putExtra("upc", upc);
            intent.putExtra(Configs.drugDetailFromScene, fromScene);
            context.startActivity(intent);
        }

    }
}
