package vn.fpt.orderfood.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.fpt.orderfood.R;
import vn.fpt.orderfood.adapter.FlashSaleAdapter;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.config.AppDatabase;
import vn.fpt.orderfood.entity.Food;
import vn.fpt.orderfood.service.FoodService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FlashSaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlashSaleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    Activity activity;
    List<Food> productArrayList;
    View root;
    AppDatabase db;

    public FlashSaleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FlashSaleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FlashSaleFragment newInstance(String param1, String param2) {
        FlashSaleFragment fragment = new FlashSaleFragment();
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
        root = inflater.inflate(R.layout.fragment_flash_sale, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        db = AppDatabase.getDbInstance(activity.getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        activity = getActivity();
        productArrayList = GetProductById(db);
        FlashSaleAdapter flashSaleAdapter = new FlashSaleAdapter(activity, productArrayList, "home");
        recyclerView.setAdapter(flashSaleAdapter);

        return inflater.inflate(R.layout.fragment_flash_sale, container, false);
    }

    public List<Food> GetProductById(AppDatabase db) {
       List<Food> foods = new ArrayList<>();
        FoodService foodService = db.foodService();
        List<Food> foodStatus = foodService.loadStatus();
        if (foodStatus.size() != 0) {
            for (Food food : foodStatus) {
                foods.add(food);
            }

        }
        return foods;
    }
    public static FlashSaleFragment AddFragment(AppDatabase db) {
        FlashSaleFragment fragment = new FlashSaleFragment();
        FoodService foodService = db.foodService();
        List<Food> list = foodService.loadStatus();
        for (Food food: list) {
            Bundle args = new Bundle();
            args.putString(MessageConstants.IMAGE, food.getFoodImage());
            args.putString(MessageConstants.NAME, food.getFoodName());
            fragment.setArguments(args);
            return fragment;
        }
        return null;
    }
}