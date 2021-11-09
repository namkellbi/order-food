package vn.fpt.orderfood.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.fpt.orderfood.MainActivity;
import vn.fpt.orderfood.R;
import vn.fpt.orderfood.adapter.AdapterStyle1;
import vn.fpt.orderfood.adapter.SliderAdapter;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.config.AppDatabase;
import vn.fpt.orderfood.entity.Food;
import vn.fpt.orderfood.entity.Slider;
import vn.fpt.orderfood.service.FoodService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {
    static ArrayList<Slider> sliderArrayList;
    TextView showDiscount, tvMfg, tvMadeIn, tvProductName, tvQuantity, tvPrice, tvOriginalPrice, tvMeasurement, tvStatus, tvTitleMadeIn, tvTitleMfg, tvTimer, tvTimerTitle;
    WebView webDescription;
    ViewPager viewPager;
    Spinner spinner;
    LinearLayout lytSpinner;
    ImageView imgIndicator;
    LinearLayout mMarkersLayout, lytMfg, lytMadeIn;
    RelativeLayout lytMainPrice, lytQuantity;
    ScrollView scrollView;
    boolean isFavorite;
    ImageView imgFav;
    ImageButton imgAdd, imgMinus;
    LinearLayout lytShare, lytSave, lytSimilar;
    int count;
    View root;
    int variantPosition;
    String from;
    int id;
    boolean isLogin;
    Food food;
    int position = 0;
    Button btnCart;
    Activity activity;
    RecyclerView recyclerView, recyclerViewReview;
    RelativeLayout relativeLayout, lytTimer;
    TextView tvMore;
    ImageView imgReturnable, imgCancellable;
    TextView tvReturnable, tvCancellable;
    String taxPercentage;
    LottieAnimationView lottieAnimationView;
    ShimmerFrameLayout mShimmerViewContainer;
    Button btnAddToCart;
    RatingBar ratingProduct_, ratingProduct;
    TextView tvRatingProductCount, tvRatingCount, tvMoreReview, tvReviewDetail;
    LinearLayout lytProductRatings;
    RelativeLayout lytReview;

    AppDatabase db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailFragment newInstance(String param1, String param2) {
        ProductDetailFragment fragment = new ProductDetailFragment();
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
        root = inflater.inflate(R.layout.fragment_product_detail, container, false);

        setHasOptionsMenu(true);
        activity = getActivity();

        MessageConstants.CartValues = new HashMap<>();

        assert getArguments() != null;
        from = getArguments().getString(MessageConstants.FROM);
        id = getArguments().getInt(MessageConstants.ID);

        taxPercentage = "0";

        if (from.equals("favorite") || from.equals("fragment") || from.equals("sub_cate") || from.equals("product") || from.equals("search") || from.equals("flash_sale")) {
            position = getArguments().getInt("position");
        }
        db = AppDatabase.getDbInstance(activity.getApplicationContext());

        lytQuantity = root.findViewById(R.id.lytQuantity);
        scrollView = root.findViewById(R.id.scrollView);
        mMarkersLayout = root.findViewById(R.id.layout_markers);
        sliderArrayList = new ArrayList<>();
        viewPager = root.findViewById(R.id.viewPager);
        tvProductName = root.findViewById(R.id.tvProductName);
        tvOriginalPrice = root.findViewById(R.id.tvOriginalPrice);
        webDescription = root.findViewById(R.id.txtDescription);
        tvPrice = root.findViewById(R.id.tvPrice);
        tvMeasurement = root.findViewById(R.id.tvMeasurement);
        imgFav = root.findViewById(R.id.imgFav);
        lytMainPrice = root.findViewById(R.id.lytMainPrice);
        tvQuantity = root.findViewById(R.id.tvQuantity);
        tvStatus = root.findViewById(R.id.tvStatus);
        imgAdd = root.findViewById(R.id.btnAddQty);
        imgMinus = root.findViewById(R.id.btnMinusQty);
        spinner = root.findViewById(R.id.spinner);
        lytSpinner = root.findViewById(R.id.lytSpinner);
        imgIndicator = root.findViewById(R.id.imgIndicator);
        showDiscount = root.findViewById(R.id.showDiscount);
        lytShare = root.findViewById(R.id.lytShare);
        lytSave = root.findViewById(R.id.lytSave);
        lytSimilar = root.findViewById(R.id.lytSimilar);
        tvMadeIn = root.findViewById(R.id.tvMadeIn);
        tvTitleMadeIn = root.findViewById(R.id.tvTitleMadeIn);
        tvMfg = root.findViewById(R.id.tvMfg);
        tvTitleMfg = root.findViewById(R.id.tvTitleMfg);
        tvTimer = root.findViewById(R.id.tvTimer);
        tvTimerTitle = root.findViewById(R.id.tvTimerTitle);
        lytMfg = root.findViewById(R.id.lytMfg);
        lytMadeIn = root.findViewById(R.id.lytMadeIn);
        btnCart = root.findViewById(R.id.btnCart);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerViewReview = root.findViewById(R.id.recyclerViewReview);
        relativeLayout = root.findViewById(R.id.relativeLayout);
        lytTimer = root.findViewById(R.id.lytTimer);
        tvMore = root.findViewById(R.id.tvMore);

        ratingProduct_ = root.findViewById(R.id.ratingProduct_);
        ratingProduct = root.findViewById(R.id.ratingProduct);
        tvRatingProductCount = root.findViewById(R.id.tvRatingProductCount);
        tvRatingCount = root.findViewById(R.id.tvRatingCount);
        tvReviewDetail = root.findViewById(R.id.tvReviewDetail);
        tvMoreReview = root.findViewById(R.id.tvMoreReview);
        lytProductRatings = root.findViewById(R.id.lytProductRatings);
        lytReview = root.findViewById(R.id.lytReview);

        tvReturnable = root.findViewById(R.id.tvReturnable);
        tvCancellable = root.findViewById(R.id.tvCancellable);
        imgReturnable = root.findViewById(R.id.imgReturnable);
        imgCancellable = root.findViewById(R.id.imgCancellable);
        btnAddToCart = root.findViewById(R.id.btnAddToCart);

//        lottieAnimationView = root.findViewById(R.id.lottieAnimationView);
//        lottieAnimationView.setAnimation("add_to_wish_list.json");

        mShimmerViewContainer = root.findViewById(R.id.mShimmerViewContainer);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(activity));

        lytProductRatings.setVisibility(View.GONE);
        lytReview.setVisibility(View.GONE);

        GetProductDetail(id);

        lytMainPrice.setOnClickListener(view -> spinner.performClick());

        tvMore.setOnClickListener(v -> ShowSimilar());

//        tvMoreReview.setOnClickListener(v -> {
//            Fragment fragment = new ReviewFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString(MessageConstants.FROM, from);
//            bundle.putString(MessageConstants.ID, id);
//            fragment.setArguments(bundle);
//            MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
//        });

        lytSimilar.setOnClickListener(view -> ShowSimilar());

        //btnCart.setOnClickListener(v -> MainActivity.fm.beginTransaction().add(R.id.container, new CartFragment()).addToBackStack(null).commit());

        lytShare.setOnClickListener(view -> {
            // String message = MessageMessageConstantss.WebsiteUrl + "itemdetail/" + food.get();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            //sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_via));
            startActivity(shareIntent);
        });
//
//        lytSave.setOnClickListener(view -> {
//                    if (isFavorite) {
//                        isFavorite = false;
//                        lottieAnimationView.setVisibility(View.GONE);
//                        fo.setIs_favorite(false);
//                        imgFav.setImageResource(R.drawable.ic_is_not_favorite);
//                    } else {
//                        isFavorite = true;
//                        product.setIs_favorite(true);
//                        lottieAnimationView.setVisibility(View.VISIBLE);
//                        lottieAnimationView.playAnimation();
//                    }
//                    AddOrRemoveFavorite(activity, session, product.getVariants().get(0).getProduct_id(), isFavorite);
//                }
//            } else {
//                isFavorite = databaseHelper.getFavoriteById(product.getId());
//                if (isFavorite) {
//                    isFavorite = false;
//                    lottieAnimationView.setVisibility(View.GONE);
//                    imgFav.setImageResource(R.drawable.ic_is_not_favorite);
//                } else {
//                    isFavorite = true;
//                    lottieAnimationView.setVisibility(View.VISIBLE);
//                    lottieAnimationView.playAnimation();
//                }
//                databaseHelper.AddOrRemoveFavorite(product.getVariants().get(0).getProduct_id(), isFavorite);
//            }
//            switch (from) {
//                case "fragment":
//                case "sub_cate":
//                case "search":
//                    ProductListFragment.productArrayList.get(position).setIs_favorite(isFavorite);
//                    ProductListFragment.productLoadMoreAdapter.notifyDataSetChanged();
//                    break;
//                case "favorite":
//                    product.setIs_favorite(isFavorite);
//                    if (isFavorite) {
//                        FavoriteFragment.productArrayList.add(product);
//                    } else {
//                        FavoriteFragment.productArrayList.remove(position);
//                    }
//                    FavoriteFragment.productLoadMoreAdapter.notifyDataSetChanged();
//                    break;
//            }
//        });

        return root;
    }
    public void ShowSimilar() {
        Fragment fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", food.getFoodId());
        bundle.putInt("cat_id", food.getCategoryId());
        bundle.putString(MessageConstants.FROM, "similar");
        bundle.putString("name", "Similar Products");
        fragment.setArguments(bundle);
        MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
    }


    void GetSimilarData(int categoryId) {
        List<Food> productArrayList = new ArrayList<>();
        FoodService foodService = db.foodService();
        productArrayList = foodService.loadCategory(categoryId);

        AdapterStyle1 adapter = new AdapterStyle1(activity, activity, productArrayList, R.layout.offer_layout);
        recyclerView.setAdapter(adapter);
        relativeLayout.setVisibility(View.VISIBLE);
    }
    void GetProductDetail(final int productId) {
        scrollView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        Map<String, String> params = new HashMap<>();
        if (from.equals("share")) {
            params.put(MessageConstants.SLUG, String.valueOf(productId));
        } else {
            params.put(MessageConstants.PRODUCT_ID, String.valueOf(productId));
        }
        FoodService foodService = db.foodService();
        Food product = foodService.loadById(productId);
        SetProductDetails(product);
        GetSimilarData(product.getCategoryId());


        scrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmer();
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    void SetProductDetails(final Food product) {
        tvProductName.setText(product.getFoodName());

        ratingProduct_ = root.findViewById(R.id.ratingProduct_);
        ratingProduct = root.findViewById(R.id.ratingProduct);
        tvRatingProductCount = root.findViewById(R.id.tvRatingProductCount);
        tvRatingCount = root.findViewById(R.id.tvRatingCount);
        tvReviewDetail = root.findViewById(R.id.tvReviewDetail);
        tvMoreReview = root.findViewById(R.id.tvMoreReview);
        List<String> arrayList = Arrays.asList(product.getFoodImage());
        sliderArrayList.add(new Slider(product.getFoodImage()));

       // viewPager.setAdapter(new SliderAdapter(sliderArrayList, activity, R.layout.lyt_detail_slider, "detail"));

        for (int i = 0; i < arrayList.size(); i++) {
            sliderArrayList.add(new Slider(arrayList.get(i)));
        }


        imgIndicator.setVisibility(View.VISIBLE);
        imgIndicator.setImageResource(R.drawable.ic_veg_icon);


        webDescription.setVerticalScrollBarEnabled(true);
        webDescription.loadDataWithBaseURL("", product.getFoodDescription(), "text/html", "UTF-8", "");
        webDescription.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
        tvProductName.setText(product.getFoodName());

        spinner.setSelection(variantPosition);



        scrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmer();

    }

}