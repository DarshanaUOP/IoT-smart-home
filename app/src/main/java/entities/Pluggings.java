package entities;

/**
 * Created by acer on 09-Jun-18.
 */
public class Pluggings {
    private String productName;
    private int status_image;
    private boolean on_off;

    public Pluggings(String productName, int status_image, boolean on_off) {
        this.productName = productName;
        this.status_image = status_image;
        this.on_off = on_off;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStatus_image() {
        return status_image;
    }

    public void setStatus_image(int status_image) {
        this.status_image = status_image;
    }

    public boolean isOn_off() {
        return on_off;
    }

    public void setOn_off(boolean on_off) {
        this.on_off = on_off;
    }
}
