package vn.fpt.orderfood.entity;

public class Table {
    private int tableId;
    private int status;

    public Table(){}

    public Table(int tableId, int status) {
        this.tableId = tableId;
        this.status = status;
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
}
