package vn.fpt.orderfood.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Food {
    @PrimaryKey
    private int foodId;

    @ColumnInfo
    private String foodName;

    @ColumnInfo
    private String foodDescription;

    @ColumnInfo
    private double foodPrice;

    @ColumnInfo
    private int status;

    @ColumnInfo
    private int quantity;

    @ColumnInfo
    private int categoryId;

    public Food(){}

    public Food(int foodId, String foodName, String foodDescription, double foodPrice, int status, int quantity, int categoryId) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.foodPrice = foodPrice;
        this.status = status;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}