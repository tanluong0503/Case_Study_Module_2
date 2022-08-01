package model;

import java.time.Instant;

public class Order {
    private Long id;
    private String fullName;
    private String phone;
    private String address;
    private Double grandTotal;
    private Instant timeNow;

    OrderItem orderItem = new OrderItem();

    public Order() {

    }

    public Order(long id, String fullName, String mobile, String address) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public static Order parse(String record) {
        Order order = new Order();
        String[] field = record.split(",");
        order.id = Long.parseLong(field[0]);
        order.fullName = field[1];
        order.phone = field[2];
        order.address = field[3];
        return order;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return phone;
    }

    public void setMobile(String mobile) {
        this.phone = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return address;
    }

    public void setEmail(String address) {
        this.address = address;
    }

    public Double getGrandTotal() {

        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Instant getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(Instant timeNow) {
        this.timeNow = timeNow;
    }

    @Override
    public String toString() {
        return id + "," + fullName + "," + phone + "," + address;
    }

}
