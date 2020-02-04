package in.wingstud.gogoride.bean;

/**
 * Created by hemant on 19-10-2019.
 */

public class CarListBean {

    private String title;
    private String type;
    private String price;
    private int image;

    public CarListBean(String title, String type, String price, int image) {
        this.title = title;
        this.type = type;
        this.price = price;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
