package flowershop;

/**
 * This class represents a bundle of flowers with their price.
 *
 * @author nmenego 2016/11/28
 */
public class Bundle {
    /**
     * Flowers per bundle.
     */
    private int size;
    /**
     * Price per bundle.
     */
    private double price;

    /**
     * Constructor
     *
     * @param size
     * @param price
     */
    public Bundle(int size, double price) {
        this.size = size;
        this.price = price;
    }

    // getters and setters

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
