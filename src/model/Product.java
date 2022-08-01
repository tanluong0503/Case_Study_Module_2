package model;

import java.time.Instant;

public class Product {
    private long id;
    private String title;
    private int quantity;
    private double price;
    private Instant timeNow;


    private Instant timeUpdate;

    public Product() {

    }

    public Product(Long id, String title, Integer quantity, double price) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(Long id, String title, Integer quantity, Double price, Instant timeNow, Instant timeUpdate) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.timeNow = timeNow;
        this.timeUpdate = timeUpdate;
    }

    public static Product parse(String record) {
        String[] fields = record.split(",");
        long id = Long.parseLong(fields[0]);
        String title = fields[1];
        int quantity = Integer.parseInt(fields[2]);
        double price = Double.parseDouble(fields[3]);
        Instant timeNow = Instant.parse(fields[4]);
        String temp = fields[5];
        Instant timeUpdate = null;
        if (temp != null && !temp.equals("null")) timeUpdate = Instant.parse(temp);
        return new Product(id, title, quantity, price, timeNow, timeUpdate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Instant getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(Instant timeNow) {
        this.timeNow = timeNow;
    }

    public Instant getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Instant timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    @Override
    public String toString() {
        return id + "," + title + "," + quantity + "," + price + "," + timeNow + "," + timeUpdate;
    }
}
