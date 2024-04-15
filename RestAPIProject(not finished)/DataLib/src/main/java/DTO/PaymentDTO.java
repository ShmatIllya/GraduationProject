package DTO;

import java.sql.Date;

public class PaymentDTO {

    public int paymentId;

    public Date creationDate;

    public Date deadline;

    public String subInfo;

    public Integer paymenterId;

    public Integer recieverId;

    public Integer itemId;

    public Integer amount;

    public Integer finalPrice;

    public String status;

    public String paymentImageName;

    public String paymenterName;

    public String receiverName;

    public String localID;

    public String getLocalID() {
        return localID;
    }

    public void setLocalID(String localID) {
        this.localID = localID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPaymenterName() {
        return paymenterName;
    }

    public void setPaymenterName(String paymenterName) {
        this.paymenterName = paymenterName;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getSubInfo() {
        return subInfo;
    }

    public void setSubInfo(String subInfo) {
        this.subInfo = subInfo;
    }

    public Integer getPaymenterId() {
        return paymenterId;
    }

    public void setPaymenterId(Integer paymenterId) {
        this.paymenterId = paymenterId;
    }

    public Integer getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(Integer recieverId) {
        this.recieverId = recieverId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentImageName() {
        return paymentImageName;
    }

    public void setPaymentImageName(String paymentImageName) {
        this.paymentImageName = paymentImageName;
    }
}
