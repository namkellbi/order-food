package vn.fpt.orderfood.entity;

public class Bill {
    private int billId;
    private String dateCreated;
    private double totalPrice;
    private int tableId;
    private int status;
    private int userId;

    public Bill(){}

    public Bill(int billId, String dateCreated, double totalPrice, int tableId, int status, int userId) {
        this.billId = billId;
        this.dateCreated = dateCreated;
        this.totalPrice = totalPrice;
        this.tableId = tableId;
        this.status = status;
        this.userId = userId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
