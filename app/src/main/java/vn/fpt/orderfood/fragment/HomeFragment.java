package vn.fpt.orderfood.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.slider.Slider;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import vn.fpt.orderfood.Helper.ApiConfig;
import vn.fpt.orderfood.Helper.Session;
import vn.fpt.orderfood.MainActivity;
import vn.fpt.orderfood.R;
import vn.fpt.orderfood.adapter.CategoryAdapter;
import vn.fpt.orderfood.adapter.SectionAdapter;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.config.AppDatabase;
import vn.fpt.orderfood.entity.Category;
import vn.fpt.orderfood.entity.Food;
import vn.fpt.orderfood.service.CategoryService;
import vn.fpt.orderfood.service.FoodService;
import vn.fpt.orderfood.service.UserService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static ArrayList<Category> categoryArrayList;
    public static ArrayList<Food> foods,sectionList;
    public Session session;
    ArrayList<Slider> sliderArrayList;
    Activity activity;
    NestedScrollView nestedScrollView;
    SwipeRefreshLayout swipeLayout;
    View root;
    int timerDelay = 0, timerWaiting = 0;
    EditText searchView;
    RecyclerView categoryRecyclerView, sectionView, offerView;
    TabLayout tabLayout;
    ViewPager mPager, viewPager;
    LinearLayout mMarkersLayout;
    int size;
    Timer swipeTimer;
    Handler handler;
    Runnable Update;
    int currentPage = 0;
    LinearLayout lytCategory, lytSearchView;
    Menu menu;
    TextView tvMore, tvMoreFlashSale;
    boolean searchVisible = false;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView tvNoData;
    Toolbar toolbarLayout;
    AppDatabase db;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        tvNoData = root.findViewById(R.id.tvNoData);
        swipeLayout = root.findViewById(R.id.swipeLayout);
        categoryRecyclerView = root.findViewById(R.id.categoryRecyclerView);

        sectionView = root.findViewById(R.id.sectionView);
        sectionView.setLayoutManager(new LinearLayoutManager(activity));
        sectionView.setNestedScrollingEnabled(false);

        offerView = root.findViewById(R.id.offerView);
        offerView.setLayoutManager(new LinearLayoutManager(activity));
        offerView.setNestedScrollingEnabled(false);

        tabLayout = root.findViewById(R.id.tabLayout);
        viewPager = root.findViewById(R.id.viewPager);

        nestedScrollView = root.findViewById(R.id.nestedScrollView);
        mMarkersLayout = root.findViewById(R.id.layout_markers);
        lytCategory = root.findViewById(R.id.lytCategory);
        lytSearchView = root.findViewById(R.id.lytSearchView);
        tvMoreFlashSale = root.findViewById(R.id.tvMoreFlashSale);
        tvMore = root.findViewById(R.id.tvMore);
        mShimmerViewContainer = root.findViewById(R.id.mShimmerViewContainer);

        searchView = root.findViewById(R.id.searchView);

        db = AppDatabase.getDbInstance(activity.getApplicationContext());


        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.categoryClicked) {
                    MainActivity.fm.beginTransaction().add(R.id.container, MainActivity.categoryFragment).show(MainActivity.categoryFragment).hide(MainActivity.active).commit();
                    MainActivity.categoryClicked = true;
                } else {
                    MainActivity.fm.beginTransaction().show(MainActivity.categoryFragment).hide(MainActivity.active).commit();
                }
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.navCategory);
                MainActivity.active = MainActivity.categoryFragment;
            }
        });

        tvMoreFlashSale.setOnClickListener(v -> {
            Fragment fragment = new ProductListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", "");
            bundle.putString("cat_id", "");
            bundle.putString(MessageConstants.FROM, "flash_sale_all");
            bundle.putString("name", activity.getString(R.string.flash_sales));
            fragment.setArguments(bundle);
            MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
        });

        searchView.setOnTouchListener((View v, MotionEvent event) -> {
            Fragment fragment = new ProductListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(MessageConstants.FROM, "search");
            bundle.putString(MessageConstants.NAME, activity.getString(R.string.search));
            bundle.putString(MessageConstants.ID, "");
            fragment.setArguments(bundle);
            MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
            return false;
        });

        lytSearchView.setOnClickListener(v -> {
            Fragment fragment = new ProductListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(MessageConstants.FROM, "search");
            bundle.putString(MessageConstants.NAME, activity.getString(R.string.search));
            bundle.putString(MessageConstants.ID, "");
            fragment.setArguments(bundle);
            MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
        });

        mPager = root.findViewById(R.id.pager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                ApiConfig.addMarkers(position, sliderArrayList, mMarkersLayout, activity);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        categoryArrayList = new ArrayList<>();
        foods = new ArrayList<>();

        swipeLayout.setColorSchemeColors(ContextCompat.getColor(activity,R.color.colorPrimary));

        swipeLayout.setOnRefreshListener(() -> {
            if (swipeTimer != null) {
                swipeTimer.cancel();
            }
//            if (ApiConfig.isConnected(getActivity())) {
//                ApiConfig.getWalletBalance(activity, new Session(activity));
//                GetHomeData();
//            }
            swipeLayout.setRefreshing(false);
        });

//        if (ApiConfig.isConnected(getActivity())) {
//            ApiConfig.getWalletBalance(activity, new Session(activity));
//            GetHomeData();
//        } else {
        GetHomeData(db);
            nestedScrollView.setVisibility(View.VISIBLE);
            mShimmerViewContainer.setVisibility(View.GONE);
            mShimmerViewContainer.stopShimmer();
       // }
        return root;
    }

    public void GetHomeData(AppDatabase db) {
        nestedScrollView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
//        if (session.getBoolean(MessageConstants.IS_USER_LOGIN)) {
//            params.put(MessageConstants.USER_ID, session.getData(MessageConstants.ID));
//        }
        GetCategory(db);
        GetProductById(db);

    }


    void GetCategory(AppDatabase db) {
        categoryArrayList = new ArrayList<>();
        CategoryService categoryService = db.categoryService();
        List<Category> categories = categoryService.getAll();
        if (categories.size() > 0) {
            for (Category category : categories) {
                categoryArrayList.add(category);
            }
            categoryRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            categoryRecyclerView.setAdapter(new CategoryAdapter(activity, categoryArrayList, R.layout.lyt_category_list, "category", 0));
            mShimmerViewContainer.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
        } else {
           // tvNoData.setVisibility(View.VISIBLE);
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.GONE);
        }
    }

    void GetProductById(AppDatabase db) {
        sectionList = new ArrayList<>();
        FoodService foodService = db.foodService();
        List<Food> foodStatus = foodService.loadStatus();
        if (foodStatus.size() != 0) {
            for (Food food : foodStatus) {
                Food section = new Food();
                section.setFoodId(food.getFoodId());
                section.setFoodImage(food.getFoodImage());
                section.setFoodName(food.getFoodName());
                sectionList.add(food);
            }
            sectionView.setVisibility(View.VISIBLE);
            SectionAdapter sectionAdapter = new SectionAdapter(activity, getActivity(), sectionList);
            sectionView.setAdapter(sectionAdapter);
        }
    }

    public void SectionProductRequest(AppDatabase db) {  //json request for product search
        categoryArrayList = new ArrayList<>();
        CategoryService categoryService = db.categoryService();
        List<Category> categories = categoryService.getAll();
        if(categories.size() > 0) {
            for (Category category : categories) {
                Category section = new Category();
                section.setCategoryId(category.getCategoryId());
                section.setCategoryName(category.getCategoryName());
                section.setCategoryImage(category.getCategoryImage());
            }
            sectionView.setVisibility(View.VISIBLE);
            SectionAdapter sectionAdapter = new SectionAdapter(activity, getActivity(), sectionList);
            sectionView.setAdapter(sectionAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.invalidateOptionsMenu();
        requireActivity().invalidateOptionsMenu();
        hideKeyboard();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(root.getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}