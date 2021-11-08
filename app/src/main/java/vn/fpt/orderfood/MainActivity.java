package vn.fpt.orderfood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.fpt.orderfood.Helper.Session;
import vn.fpt.orderfood.common.MessageConstants;
import vn.fpt.orderfood.config.AppDatabase;
import vn.fpt.orderfood.entity.Category;
import vn.fpt.orderfood.fragment.CategoryFragment;
import vn.fpt.orderfood.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;

    static final String TAG = "MAIN ACTIVITY";
    @SuppressLint("StaticFieldLeak")
    public static Toolbar toolbar;
    public static BottomNavigationView bottomNavigationView;
    public static Fragment active;
    public static FragmentManager fm = null;
    public static Fragment homeFragment, categoryFragment, favoriteFragment, trackOrderFragment, drawerFragment;
    public static boolean homeClicked = false, categoryClicked = false, favoriteClicked = false, drawerClicked = false;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    public Session session;
    boolean doubleBackToExitPressedOnce = false;
    Menu menu;
    //DatabaseHelper databaseHelper;
    String from;
    TextView toolbarTitle;
    ImageView imageMenu, imageHome;
    CardView cardViewHamburger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        toolbarTitle = findViewById(R.id.toolbarTitle);
        imageMenu = findViewById(R.id.imageMenu);
        imageHome = findViewById(R.id.imageHome);
        cardViewHamburger = findViewById(R.id.cardViewHamburger);

        activity = MainActivity.this;
        session = new Session(activity);
        db = AppDatabase.getDbInstance(this.getApplicationContext());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        from = getIntent().getStringExtra(MessageConstants.FROM);

        fm = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        //favoriteFragment = new FavoriteFragment();
        //trackOrderFragment = new TrackOrderFragment();
        //drawerFragment = new DrawerFragment();
        importData();



        Bundle bundle = new Bundle();
        bottomNavigationView.setSelectedItemId(R.id.navMain);
        active = homeFragment;
        homeClicked = true;
        drawerClicked = false;
        favoriteClicked = false;
        categoryClicked = false;
        try {
            if (!getIntent().getStringExtra("json").isEmpty()) {
                bundle.putString("json", getIntent().getStringExtra("json"));
            }
            homeFragment.setArguments(bundle);
            fm.beginTransaction().add(R.id.container, homeFragment).commit();
        } catch (Exception e) {
            fm.beginTransaction().add(R.id.container, homeFragment).commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            {
                switch (item.getItemId()) {
                    case R.id.navMain:
                        if (active != homeFragment) {
                            bottomNavigationView.getMenu().findItem(item.getItemId()).setChecked(true);
                            if (!homeClicked) {
                                fm.beginTransaction().add(R.id.container, homeFragment).show(homeFragment).hide(active).commit();
                                homeClicked = true;
                            } else {
                                fm.beginTransaction().show(homeFragment).hide(active).commit();
                            }
                            active = homeFragment;
                        }
                        break;
                    case R.id.navCategory:
                        if (active != categoryFragment) {
                            bottomNavigationView.getMenu().findItem(item.getItemId()).setChecked(true);
                            if (!categoryClicked) {
                                fm.beginTransaction().add(R.id.container, categoryFragment).show(categoryFragment).hide(active).commit();
                                categoryClicked = true;
                            } else {
                                fm.beginTransaction().show(categoryFragment).hide(active).commit();
                            }
                            active = categoryFragment;
                        }
                        break;
                    case R.id.navWishList:
                        if (active != favoriteFragment) {
                            bottomNavigationView.getMenu().findItem(item.getItemId()).setChecked(true);
                            if (!favoriteClicked) {
                                fm.beginTransaction().add(R.id.container, favoriteFragment).show(favoriteFragment).hide(active).commit();
                                favoriteClicked = true;
                            } else {
                                fm.beginTransaction().show(favoriteFragment).hide(active).commit();
                            }
                            active = favoriteFragment;
                        }
                        break;
                    case R.id.navProfile:
                        if (active != drawerFragment) {
                            bottomNavigationView.getMenu().findItem(item.getItemId()).setChecked(true);
                            if (!drawerClicked) {
                                fm.beginTransaction().add(R.id.container, drawerFragment).show(drawerFragment).hide(active).commit();
                                drawerClicked = true;
                            } else {
                                fm.beginTransaction().show(drawerFragment).hide(active).commit();
                            }
                            active = drawerFragment;
                        }
                        break;
                }
                return false;
            }
        });



        fm.addOnBackStackChangedListener(() -> {
            toolbar.setVisibility(View.VISIBLE);
            Fragment currentFragment = fm.findFragmentById(R.id.container);
            assert currentFragment != null;
            currentFragment.onResume();
        });


    }
    public void importData(){
        List<Category> categories = new ArrayList<>();
        Category category = new Category(1, "Rice", "/sdcard/Download/rice.png");
        Category category2 = new Category(2, "Noodles", "/sdcard/Download/noodles.png");
        Category category3 = new Category(3, "Drink", "/sdcard/Download/drink.png");
        Category category4 = new Category(4, "Specialties", "/sdcard/Download/specialties.png");
        Category category5 = new Category(5, "FastFood", "/sdcard/Download/fastfood.png");
        Category category6 = new Category(6, "Snacks", "/sdcard/Download/snacks.png");
        Category category7 = new Category(7, "Healthy", "/sdcard/Download/healthy.png");
        Category category8 = new Category(8, "Bread", "/sdcard/Download/bread.png");

        categories.add(category);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);
        categories.add(category6);
        categories.add(category7);
        categories.add(category8);
        if(db.categoryService().getAll().size() == 0) {
            db.categoryService().insertAll(categories);
        }
    }
}