package vn.fpt.orderfood.entity;

public class Statistic {
    private int statisticId;
    private String date;
    private double netIncome;

    public Statistic(){}

    public Statistic(int statisticId, String date, double netIncome) {
        this.statisticId = statisticId;
        this.date = date;
        this.netIncome = netIncome;
    }

    public int getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(int statisticId) {
        this.statisticId = statisticId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }
}
