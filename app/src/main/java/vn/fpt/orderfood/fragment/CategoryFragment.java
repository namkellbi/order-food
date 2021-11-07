package vn.fpt.orderfood.fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.fpt.orderfood.R;
import vn.fpt.orderfood.adapter.CategoryAdapter;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.config.AppDatabase;
import vn.fpt.orderfood.entity.Category;
import vn.fpt.orderfood.service.CategoryService;
import vn.fpt.orderfood.service.UserService;

public class CategoryFragment extends Fragment {
    public static ArrayList<Category> categoryArrayList;
    TextView tvNoData;
    RecyclerView categoryRecyclerView;
    SwipeRefreshLayout swipeLayout;
    View root;
    Activity activity;
    private ShimmerFrameLayout mShimmerViewContainer;
    AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_category, container, false);
        mShimmerViewContainer = root.findViewById(R.id.mShimmerViewContainer);
        db = Room.databaseBuilder(activity.getApplicationContext(),
                AppDatabase.class, "category").allowMainThreadQueries().build();
        activity = getActivity();

        setHasOptionsMenu(true);


        tvNoData = root.findViewById(R.id.tvNoData);
        swipeLayout = root.findViewById(R.id.swipeLayout);
        categoryRecyclerView = root.findViewById(R.id.categoryRecyclerView);

        categoryRecyclerView.setLayoutManager(new GridLayoutManager(activity, MessageConstants.GRID_COLUMN));
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(activity,R.color.colorPrimary));


        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(false);
            //if (ApiConfig.isConnected(activity)) {
                categoryRecyclerView.setVisibility(View.GONE);
                mShimmerViewContainer.setVisibility(View.VISIBLE);
                mShimmerViewContainer.startShimmer();
                //ApiConfig.getWalletBalance(activity, new Session(activity));
                GetCategory();
            //}
        });

        //if (ApiConfig.isConnected(activity)) {
            categoryRecyclerView.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mShimmerViewContainer.startShimmer();
            //ApiConfig.getWalletBalance(activity, new Session(activity));
            GetCategory();
        //}

        return root;
    }


    void GetCategory() {
        categoryArrayList = new ArrayList<>();
        CategoryService categoryService = db.categoryService();
        List<Category> categories = categoryService.getAll();
        if (categories.size() > 0) {
            for (Category category : categories) {
                categoryArrayList.add(category);
            }
            categoryRecyclerView.setAdapter(new CategoryAdapter(activity, categoryArrayList, R.layout.lyt_subcategory, "category", 0));
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                categoryRecyclerView.setVisibility(View.GONE);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        MessageConstants.TOOLBAR_TITLE = getString(R.string.title_category);
        requireActivity().invalidateOptionsMenu();
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

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.toolbar_layout).setVisible(false);
        menu.findItem(R.id.toolbar_cart).setVisible(true);
        menu.findItem(R.id.toolbar_sort).setVisible(false);
        menu.findItem(R.id.toolbar_search).setVisible(true);
    }
}
