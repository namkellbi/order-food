package vn.fpt.orderfood.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import java.io.File;
import java.util.List;

import vn.fpt.orderfood.R;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.entity.Food;
import vn.fpt.orderfood.fragment.ProductDetailFragment;

public class AdapterStyle1 extends RecyclerView.Adapter<AdapterStyle1.VideoHolder> {

    public final List<Food> productList;
    public final Activity activity;
    public final int itemResource;
    final Context context;

    public AdapterStyle1(Context context, Activity activity, List<Food> productList, int itemResource) {
        this.context = context;
        this.activity = activity;
        this.productList = productList;
        this.itemResource = itemResource;

    }

    @Override
    public int getItemCount() {
        return Math.min(productList.size(), 4);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, final int position) {
        final Food product = productList.get(position);
        if(product.getFoodImage() != null){
            File imgFile = new  File(product.getFoodImage());
            if(imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                holder.thumbnail.setImageBitmap(myBitmap);
            }
            }
            holder.tvDPrice.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(String.valueOf(product.getFoodPrice()));

            holder.tvTitle.setText(product.getFoodName());

        holder.relativeLayout.setOnClickListener(view -> {

            AppCompatActivity activity1 = (AppCompatActivity) context;
            Fragment fragment = new ProductDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(MessageConstants.FROM, "section");
            bundle.putInt("variantPosition", 0);
            fragment.setArguments(bundle);
            activity1.getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
        });
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(itemResource, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class VideoHolder extends RecyclerView.ViewHolder {

        public final ImageView thumbnail;
        public final TextView tvTitle;
        public final TextView tvPrice;
        public final TextView tvDPrice;
        public final RelativeLayout relativeLayout;

        public VideoHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDPrice = itemView.findViewById(R.id.tvDPrice);
            relativeLayout = itemView.findViewById(R.id.play_layout);

        }


    }
}
