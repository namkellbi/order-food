package vn.fpt.orderfood.service;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import vn.fpt.orderfood.entity.Category;
import vn.fpt.orderfood.entity.Food;

@Dao
public interface FoodService {
    @Query("SELECT * FROM Food where status = 1")
    List<Food> loadStatus();

    @Query("Select * From Food where foodId = :foodId")
    Food loadById(int foodId);

    @Query("Select * From Food where categoryId = :categoryId")
    List<Food> loadCategory(int categoryId);
}
