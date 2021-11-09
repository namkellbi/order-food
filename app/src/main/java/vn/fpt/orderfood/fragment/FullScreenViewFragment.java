package vn.fpt.orderfood.fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import vn.fpt.orderfood.R;
import vn.fpt.orderfood.adapter.SliderAdapter;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.entity.Slider;

public class FullScreenViewFragment extends Fragment {
    View root;
    int pos;
    String imageList;
    LinearLayout mMarkersLayout;
    ViewPager viewPager;
    Activity activity;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_full_screen_view, container, false);

        mMarkersLayout = root.findViewById(R.id.layout_markers);
        viewPager = root.findViewById(R.id.pager);

        activity = getActivity();
        context = activity;

        setHasOptionsMenu(true);

        //imageList = new ArrayList<>();
        imageList = ProductDetailFragment.sliderArrayList.get(0);
        assert getArguments() != null;
        pos = getArguments().getInt("pos", 0);

        viewPager.setAdapter(new SliderAdapter(imageList, activity, R.layout.lyt_fullscreenimg, "fullscreen"));


        viewPager.setCurrentItem(pos);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        MessageConstants.TOOLBAR_TITLE = getString(R.string.app_name);
        activity.invalidateOptionsMenu();
        hideKeyboard();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(root.getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
