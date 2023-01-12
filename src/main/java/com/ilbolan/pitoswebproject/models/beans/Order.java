package com.ilbolan.pitoswebproject.models.beans;

/**
 * Order bean needed for inserting order into Database
 */
public class Order {

    private Integer id;
    private Integer pieId;
    private Integer orderId;
    private Integer quantity;

    public Order() {}

    public Order(Integer id, Integer pieId, Integer orderId, Integer quantity) {
        this.id = id;
        this.pieId = pieId;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPieId() {
        return pieId;
    }

    public void setPieId(Integer pieId) {
        this.pieId = pieId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", pieId=" + pieId +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                '}' +'\n';
    }
}
