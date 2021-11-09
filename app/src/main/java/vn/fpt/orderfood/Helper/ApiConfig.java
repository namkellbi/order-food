package vn.fpt.orderfood.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

import vn.fpt.orderfood.R;
import vn.fpt.orderfood.config.AppDatabase;
import vn.fpt.orderfood.entity.Food;
import vn.fpt.orderfood.service.FoodService;

public class ApiConfig {
    static boolean isDialogOpen = false;

    public static List<Food> foods;
    public static void addMarkers(int currentPage, ArrayList<Slider> imageList, LinearLayout mMarkersLayout, Context context) {

        if (context != null) {
            TextView[] markers = new TextView[imageList.size()];

            mMarkersLayout.removeAllViews();

            for (int i = 0; i < markers.length; i++) {
                markers[i] = new TextView(context);
                markers[i].setText(Html.fromHtml("&#8226;"));
                markers[i].setTextSize(35);
                markers[i].setTextColor(ContextCompat.getColor(context, R.color.gray));
                mMarkersLayout.addView(markers[i]);
            }
            if (markers.length > 0)
                markers[currentPage].setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }
    public static Boolean isConnected(final Activity activity) {
        boolean check = false;
        try {
            ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                check = true;
            } else {
                try {
                    if (!isDialogOpen) {
                        @SuppressLint("InflateParams") View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_no_internet, null);
                        ViewGroup parentViewGroup = (ViewGroup) sheetView.getParent();
                        if (parentViewGroup != null) {
                            parentViewGroup.removeAllViews();
                        }

                        final Dialog mBottomSheetDialog = new Dialog(activity);
                        mBottomSheetDialog.setContentView(sheetView);
                        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mBottomSheetDialog.show();
                        isDialogOpen = true;
                        Button btnRetry = sheetView.findViewById(R.id.btnRetry);
                        mBottomSheetDialog.setCancelable(false);

                        btnRetry.setOnClickListener(view -> {
                            if (isConnected(activity)) {
                                isDialogOpen = false;
                                mBottomSheetDialog.dismiss();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

       public List<Food> GetProductById(AppDatabase db) {
           foods = new ArrayList<>();
           FoodService foodService = db.foodService();
           List<Food> foodStatus = foodService.loadStatus();
           if (foodStatus.size() != 0) {
               for (Food food : foodStatus) {
                   foods.add(food);
               }

           }
           return foods;
       }
}
