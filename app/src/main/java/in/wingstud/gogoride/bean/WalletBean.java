package in.wingstud.gogoride.bean;

/**
 * Created by hemant on 25-08-2019.
 */

public class WalletBean {

    private String orderId;

    public WalletBean(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
