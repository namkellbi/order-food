package vn.fpt.orderfood.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.fpt.orderfood.R;
import vn.fpt.orderfood.entity.Food;

public class FlashSaleAdapter extends RecyclerView.Adapter<FlashSaleAdapter.HolderItems> {

    public final List<Food> productList;
    public final Activity activity;
    final String from;

    public FlashSaleAdapter(Activity activity, List<Food> productList, String from) {
        this.activity = activity;
        this.productList = productList;
        this.from = from;
    }

    @Override
    public int getItemCount() {
        return Math.min(productList.size(), from.equals("home") ? 6 : productList.size());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderItems holder, final int position) {
        try {
            final Food product = productList.get(position);
            holder.setIsRecyclable(false);
            if (from.equals("home")) {
                if (position == 5) {
                    holder.tvViewAll.setVisibility(View.VISIBLE);
                    holder.lytMain_.setVisibility(View.INVISIBLE);
                } else {
                    holder.tvViewAll.setVisibility(View.GONE);
                    holder.lytMain_.setVisibility(View.VISIBLE);
                }
            }

            File imgFile = new  File(product.getFoodImage());
            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                holder.imgThumb.setImageBitmap(myBitmap);

            }

            holder.setIsRecyclable(false);
            holder.productName.setText(product.getFoodName());

//            holder.tvViewAll.setOnClickListener(view -> {
//                Fragment fragment = new ProductListFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.FROM, "flash_sale");
//                bundle.putString(Constant.NAME, activity.getString(R.string.flash_sale));
//                bundle.putString(Constant.ID, product.getVariants().get(0).getFlash_sales().get(0).getFlash_sales_id());
//                fragment.setArguments(bundle);
//                MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public HolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_flash_item_grid, parent, false);
        return new HolderItems(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class HolderItems extends RecyclerView.ViewHolder {
        final RelativeLayout lytMain_;
        final TextView showDiscount;
        final TextView productName;
        final TextView tvOriginalPrice;
        final TextView tvPrice;
        final TextView tvTimer;
        final TextView tvTimerTitle;
        final TextView tvViewAll;
        final ImageView imgThumb;

        public HolderItems(View itemView) {
            super(itemView);
            lytMain_ = itemView.findViewById(R.id.lytMain_);
            showDiscount = itemView.findViewById(R.id.showDiscount);
            productName = itemView.findViewById(R.id.productName);
            tvOriginalPrice = itemView.findViewById(R.id.tvOriginalPrice);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTimer = itemView.findViewById(R.id.tvTimer);
            tvTimerTitle = itemView.findViewById(R.id.tvTimerTitle);
            imgThumb = itemView.findViewById(R.id.imgThumb);
            tvViewAll = itemView.findViewById(R.id.tvViewAll);
        }
    }

}
