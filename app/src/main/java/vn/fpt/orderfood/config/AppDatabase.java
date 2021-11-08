package vn.fpt.orderfood.config;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vn.fpt.orderfood.entity.Bill;
import vn.fpt.orderfood.entity.BillDetail;
import vn.fpt.orderfood.entity.Category;
import vn.fpt.orderfood.entity.Food;
import vn.fpt.orderfood.entity.RestaurantInformation;
import vn.fpt.orderfood.entity.Role;
import vn.fpt.orderfood.entity.Statistic;
import vn.fpt.orderfood.entity.Table;
import vn.fpt.orderfood.entity.User;
import vn.fpt.orderfood.service.BillService;
import vn.fpt.orderfood.service.CategoryService;
import vn.fpt.orderfood.service.FoodService;
import vn.fpt.orderfood.service.InformationService;
import vn.fpt.orderfood.service.RoleService;
import vn.fpt.orderfood.service.StatisticService;
import vn.fpt.orderfood.service.TableService;
import vn.fpt.orderfood.service.UserService;

@Database(entities = {User.class, Bill.class, BillDetail.class, Category.class, Food.class,
            RestaurantInformation.class, Role.class, Statistic.class, Table.class},  version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserService userService();

    public abstract BillService billService();

    public abstract CategoryService categoryService();

    public abstract FoodService foodService();

    public abstract InformationService informationService();

    public abstract RoleService roleService();

    public abstract StatisticService statisticService();

    public abstract TableService tableService();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    vn.fpt.orderfood.config.AppDatabase.class, "foodOrder").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
