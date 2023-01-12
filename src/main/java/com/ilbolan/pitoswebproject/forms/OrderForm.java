package com.ilbolan.pitoswebproject.forms;

import com.ilbolan.pitoswebproject.forms.annotations.EmailConstrains;
import com.ilbolan.pitoswebproject.forms.annotations.OrderAmountConstrains;
import com.ilbolan.pitoswebproject.forms.annotations.OrderTimeConstrains;
import com.ilbolan.pitoswebproject.forms.annotations.TelephoneConstrains;
import com.ilbolan.pitoswebproject.models.beans.Order;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The Form for ordering
 * It encapsulates data & performs check at field constrains
 * at runtime through annotations
 */
public class OrderForm {

    @NotNull(message = "Πρέπει να εισαχθεί όνομα")
    @NotEmpty
    private String fullName;

    @NotNull(message = "Πρέπει να εισαχθεί διεύθυνση")
    @NotEmpty
    private String address;

    @NotNull
    private Integer areaId;

    @NotNull(message = "Πρέπει να εισαχθεί email")
    @EmailConstrains
    private String email;

    @NotNull(message = "Πρέπει να εισαχθεί τηλέφωνο")
    @TelephoneConstrains
    private String tel;

    private String comments;

    @OrderAmountConstrains
    private List<Order> orderItems;

    @NotNull
    private boolean offer;

    @NotNull(message = "Πρέπει να επιλεγεί τρόπος πληρωμής")
    private String payment;

    @OrderTimeConstrains
    private LocalDateTime timestamp;

    public OrderForm(String fullName, String address, Integer areaId, String email, String tel, String comments,
                     List<Order> orderItems, boolean offer, String payment, LocalDateTime timestamp) {
        this.fullName = fullName;
        this.address = address;
        this.areaId = areaId;
        this.email = email;
        this.tel = tel;
        this.comments = comments;
        this.orderItems = orderItems;
        this.offer = offer;
        this.payment = payment;
        this.timestamp = timestamp;
    }

    public OrderForm(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Order> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Order> orderItems) {
        this.orderItems = orderItems;
    }

    public boolean getOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FormOrder{" +
                "\nfullname='" + fullName + '\'' +
                ", \naddress='" + address + '\'' +
                ", \nareaId=" + areaId +
                ", \nemail='" + email + '\'' +
                ", \ntel='" + tel + '\'' +
                ", \ncomments='" + comments + '\'' +
                ", \norderItems=" + orderItems +
                ", \noffer=" + offer +
                ", \npayment='" + payment + '\'' +
                ", \ntimestamp=" + timestamp +
                '}';
    }
}
