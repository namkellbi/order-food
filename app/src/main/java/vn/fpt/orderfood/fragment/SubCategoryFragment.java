package vn.fpt.orderfood.fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import vn.fpt.orderfood.Helper.Session;
import vn.fpt.orderfood.R;
import vn.fpt.orderfood.adapter.CategoryAdapter;
import vn.fpt.orderfood.adapter.SubCategoryAdapter;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.config.AppDatabase;
import vn.fpt.orderfood.entity.Category;
import vn.fpt.orderfood.entity.Food;
import vn.fpt.orderfood.service.CategoryService;

public class SubCategoryFragment extends Fragment {
    public static ArrayList<Category> categoryArrayList;
    //public ProductLoadMoreAdapter productLoadMoreAdapter;
    View root;
    Session session;
    int total;
    NestedScrollView nestedScrollView;
    Activity activity;
    int offset = 0;
    String id, filterBy, from;
    RecyclerView recyclerView, subCategoryRecycleView;
    SwipeRefreshLayout swipeLayout;
    int filterIndex;
    boolean isSort = false, isLoadMore = false;
    boolean isGrid = false;
    int resource;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView tvAlert;
    AppDatabase db;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_sub_category, container, false);
        getAllWidgets(root);
        setHasOptionsMenu(true);
        offset = 0;
        activity = getActivity();
        session = new Session(activity);
        db = Room.databaseBuilder(activity.getApplicationContext(),
                AppDatabase.class, "category").allowMainThreadQueries().build();

        assert getArguments() != null;
        from = getArguments().getString(MessageConstants.FROM);
        id = getArguments().getString("id");

        if (session.getBoolean("grid")) {
            resource = R.layout.lyt_item_grid;
            isGrid = true;
            recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        } else {
            resource = R.layout.lyt_item_list;
            isGrid = false;
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        }

        filterIndex = -1;

//        if (ApiConfig.isConnected(activity)) {
//            GetSettings(activity);
//            GetCategory();
//            //GetProducts();
//        }
        GetCategory();

        swipeLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(false);
            GetCategory();
            //GetProducts();
        });

        return root;
    }


    public void getAllWidgets(View root) {
        tvAlert = root.findViewById(R.id.tvNoData);
        recyclerView = root.findViewById(R.id.recyclerView);
        swipeLayout = root.findViewById(R.id.swipeLayout);
        nestedScrollView = root.findViewById(R.id.nestedScrollView);
        mShimmerViewContainer = root.findViewById(R.id.mShimmerViewContainer);
        subCategoryRecycleView = root.findViewById(R.id.subCategoryRecycleView);
        subCategoryRecycleView.setLayoutManager(new GridLayoutManager(activity, MessageConstants.GRID_COLUMN));

    }

    public void stopShimmer() {
        nestedScrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmer();
    }

    public void startShimmer() {
        nestedScrollView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
    }

    void GetCategory() {
        startShimmer();
        Map<String, String> params = new HashMap<>();
        params.put(MessageConstants.CATEGORY_ID, id);

        categoryArrayList = new ArrayList<>();
        CategoryService categoryService = db.categoryService();
        List<Category> categories = categoryService.getAll();
        if (categories.size() > 0) {
            for (Category category : categories) {
                categoryArrayList.add(category);
            }
            subCategoryRecycleView.setAdapter(new SubCategoryAdapter(activity, categoryArrayList, R.layout.lyt_subcategory, "sub_cate"));
        }
        stopShimmer();
    }


    @SuppressLint({"UseCompatLoadingForDrawables", "NotifyDataSetChanged"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (isSort) {
            if (item.getItemId() == R.id.toolbar_sort) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(activity.getResources().getString(R.string.filter_by));
                builder.setSingleChoiceItems(MessageConstants.filterValues, filterIndex, (dialog, item1) -> {
                    filterIndex = item1;
                    switch (item1) {
                        case 0:
                            filterBy = MessageConstants.NEW;
                            break;
                        case 1:
                            filterBy = MessageConstants.OLD;
                            break;
                        case 2:
                            filterBy = MessageConstants.HIGH;
                            break;
                        case 3:
                            filterBy = MessageConstants.LOW;
                            break;
                    }
                    if (item1 != -1) {
                        GetCategory();
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else if (item.getItemId() == R.id.toolbar_layout) {
                if (isGrid) {

                    isGrid = false;
                    recyclerView.setAdapter(null);
                    resource = R.layout.lyt_item_list;
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                } else {

                    isGrid = true;
                    recyclerView.setAdapter(null);
                    resource = R.layout.lyt_item_grid;
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                }
                session.setBoolean("grid", isGrid);
                //productLoadMoreAdapter = new ProductLoadMoreAdapter(activity, productArrayList, resource, from);
                //recyclerView.setAdapter(productLoadMoreAdapter);
                //productLoadMoreAdapter.notifyDataSetChanged();
                activity.invalidateOptionsMenu();
            }
        }

        return false;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.toolbar_sort).setVisible(isSort);
        menu.findItem(R.id.toolbar_search).setVisible(true);
        menu.findItem(R.id.toolbar_cart).setIcon(buildCounterDrawable(MessageConstants.TOTAL_CART_ITEM, activity));

        menu.findItem(R.id.toolbar_layout).setVisible(true);

        Drawable myDrawable;
        if (isGrid) {

            myDrawable = ContextCompat.getDrawable(activity, R.drawable.ic_list_);   // The ID of your drawable
        } else {
            myDrawable = ContextCompat.getDrawable(activity, R.drawable.ic_grid_);    // The ID of your drawable.
        }
        menu.findItem(R.id.toolbar_layout).setIcon(myDrawable);

        super.onPrepareOptionsMenu(menu);
    }

    public static Drawable buildCounterDrawable(int count, Activity activity) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.counter_menuitem_layout, null);
        TextView textView = view.findViewById(R.id.tvCounter);
        RelativeLayout lytCount = view.findViewById(R.id.lytCount);
        if (count == 0) {
            lytCount.setVisibility(View.GONE);
        } else {
            lytCount.setVisibility(View.VISIBLE);
            textView.setText("" + count);
        }
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(activity.getResources(), bitmap);
    }

    @Override
    public void onResume() {
        super.onResume();
        assert getArguments() != null;
        MessageConstants.TOOLBAR_TITLE = getArguments().getString(MessageConstants.NAME);
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

    @Override
    public void onPause() {
        super.onPause();
        //ApiConfig.AddMultipleProductInCart(session, activity, Constant.CartValues);
    }
}