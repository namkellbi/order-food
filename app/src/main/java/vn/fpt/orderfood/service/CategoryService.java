package vn.fpt.orderfood.service;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import vn.fpt.orderfood.entity.Category;

@Dao
public interface CategoryService {
    @Query("SELECT * FROM category")
    List<Category> getAll();
}
