package vn.fpt.orderfood.service;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import vn.fpt.orderfood.entity.Bill;

@Dao
public interface BillService {
    @Query("SELECT * FROM bill")
    List<Bill> getAll();
}
