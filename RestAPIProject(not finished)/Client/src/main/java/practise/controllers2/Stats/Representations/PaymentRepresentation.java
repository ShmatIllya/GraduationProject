package practise.controllers2.Stats.Representations;

import entity.ClientsEntity;
import entity.ItemsEntity;
import jakarta.persistence.*;

import java.sql.Date;

public class PaymentRepresentation {

    private  int id;
    private int finalPrice;
    private String status;
    private String itemName;

    public PaymentRepresentation(int id, int finalPrice, String status, String itemName) {
        this.id = id;
        this.finalPrice = finalPrice;
        this.status = status;
        this.itemName = itemName;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
