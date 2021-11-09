package vn.fpt.orderfood.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import vn.fpt.orderfood.MainActivity;
import vn.fpt.orderfood.R;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.entity.Category;
import vn.fpt.orderfood.entity.Food;
import vn.fpt.orderfood.fragment.ProductDetailFragment;
import vn.fpt.orderfood.fragment.ProductListFragment;


public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionHolder> {

    public final ArrayList<Food> sectionList;
    public final Activity activity;
    final Context context;

    public SectionAdapter(Context context, Activity activity, ArrayList<Food> sectionList) {
        this.context = context;
        this.activity = activity;
        this.sectionList = sectionList;
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    @Override
    public void onBindViewHolder(SectionHolder holder1, final int position) {
        final Food section;
        section = sectionList.get(position);
        holder1.tvTitle.setText(section.getFoodName());
        holder1.tvSubTitle.setText(section.getFoodDescription());
        holder1.tvPrice.setText(String.valueOf(section.getFoodPrice()));
        if(section.getFoodImage() != null){
        File imgFile = new  File(section.getFoodImage());
        if(imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            holder1.imgView.setImageBitmap(myBitmap);
        }
        }

        holder1.tvMore.setOnClickListener(view -> {

            Fragment fragment = new ProductDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(MessageConstants.FROM, "section");
            bundle.putString(MessageConstants.NAME, section.getFoodName());
            bundle.putInt(MessageConstants.ID, section.getFoodId());
            fragment.setArguments(bundle);

            MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
        });
    }

    @NonNull
    @Override
    public SectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_layout, parent, false);
        return new SectionHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class SectionHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvSubTitle;
        final TextView tvMore;
        final TextView tvPrice;
        final ImageView imgView;

        public SectionHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            tvMore = itemView.findViewById(R.id.tvMore);
            tvPrice = itemView.findViewById(R.id.tvSectionPrice);
            imgView = itemView.findViewById(R.id.imgFood);

        }
    }


}
