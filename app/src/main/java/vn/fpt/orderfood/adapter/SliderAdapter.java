package vn.fpt.orderfood.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import vn.fpt.orderfood.MainActivity;
import vn.fpt.orderfood.R;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.entity.Slider;
import vn.fpt.orderfood.fragment.FullScreenViewFragment;
import vn.fpt.orderfood.fragment.ProductDetailFragment;
import vn.fpt.orderfood.fragment.SubCategoryFragment;

public class SliderAdapter extends PagerAdapter {

    final String dataList;
    final Activity activity;
    final int layout;
    final String from;

    public SliderAdapter(String dataList, Activity activity, int layout, String from) {
        this.dataList = dataList;
        this.activity = activity;
        this.layout = layout;
        this.from = from;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, final int position) {
        View imageLayout = LayoutInflater.from(activity).inflate(layout, view, false);

        assert imageLayout != null;
        ImageView imgSlider = imageLayout.findViewById(R.id.imgSlider);
        CardView lytMain = imageLayout.findViewById(R.id.lytMain);

        File imgFile = new  File(String.valueOf(dataList));
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgSlider.setImageBitmap(myBitmap);

        }
        view.addView(imageLayout, 0);

        //final Slider singleItem = dataList.get(position);
//
//
//        Picasso.get()
//                .load(singleItem.getImage())
//                .fit()
//                .centerInside()
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.placeholder)
//                .into(imgSlider);
//        view.addView(imageLayout, 0);

//        lytMain.setOnClickListener(v -> {
//            if (from.equalsIgnoreCase("detail")) {
//
//                Fragment fragment = new FullScreenViewFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("pos", position);
//                fragment.setArguments(bundle);
//
//                MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
//
//            } else {
//
//                if (singleItem.getType().equals("category")) {
//
//                    Fragment fragment = new SubCategoryFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString(MessageConstants.ID, singleItem.getType_id());
//                    bundle.putString(MessageConstants.NAME, singleItem.getName());
//                    bundle.putString(MessageConstants.FROM, "category");
//                    fragment.setArguments(bundle);
//
//                    MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
//
//
//                } else if (singleItem.getType().equals("product")) {
//
//                    Fragment fragment = new ProductDetailFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString(MessageConstants.ID, singleItem.getType_id());
//                    bundle.putString(MessageConstants.FROM, "slider");
//                    bundle.putInt("variantsPosition", 0);
//                    fragment.setArguments(bundle);
//
//                    MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
//
//                }
//
//            }
//        });

        return imageLayout;
    }


    @Override
    public int getCount() {
        return (null != dataList ? 1 : 0);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}

