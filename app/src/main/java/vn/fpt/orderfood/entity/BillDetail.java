package vn.fpt.orderfood.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BillDetail {
    @PrimaryKey
    private int billId;

    @ColumnInfo
    private int foodId;

    @ColumnInfo
    private int quantity;

    @ColumnInfo
    private double price;

    public BillDetail(){}

    public BillDetail(int billId, int foodId, int quantity, double price) {
        this.billId = billId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
