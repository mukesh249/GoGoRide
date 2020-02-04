package in.wingstud.gogoride.bean;

/**
 * Created by hemant on 24-08-2019.
 */

public class TodayPayBean {

    private String orderId;
    private String mobileNo;
    private double price;

    public TodayPayBean(String orderId, String mobileNo, double price) {
        this.orderId = orderId;
        this.mobileNo = mobileNo;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
