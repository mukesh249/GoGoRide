package in.wingstud.gogoride.bean;

/**
 * Created by hemant on 25-08-2019.
 */

public class NotificationBean {

    private String title;
    private String time;
    private String desc;

    public NotificationBean(String title, String time, String desc) {
        this.title = title;
        this.time = time;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
