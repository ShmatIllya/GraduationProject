package entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "payments", schema = "crm", catalog = "")
public class PaymentsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "payment_id")
    private int paymentId;
    @Basic
    @Column(name = "creation_date")
    private Date creationDate;
    @Basic
    @Column(name = "deadline")
    private Date deadline;
    @Basic
    @Column(name = "sub_info")
    private String subInfo;
    @Basic
    @Column(name = "paymenter_id")
    private Integer paymenterId;
    @Basic
    @Column(name = "reciever_id")
    private Integer recieverId;
    @Basic
    @Column(name = "item_id")
    private Integer itemId;
    @Basic
    @Column(name = "amount")
    private Integer amount;
    @Basic
    @Column(name = "final_price")
    private Integer finalPrice;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "payment_image_name")
    private String paymentImageName;
    @ManyToOne
    @JoinColumn(name = "paymenter_id", referencedColumnName = "clients_id", insertable = false, updatable = false)
    private ClientsEntity clientsByPaymenterId;
    @ManyToOne
    @JoinColumn(name = "reciever_id", referencedColumnName = "clients_id", insertable = false, updatable = false)
    private ClientsEntity clientsByRecieverId;
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id", insertable = false, updatable = false)
    private ItemsEntity itemsByItemId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentsEntity that = (PaymentsEntity) o;

        if (paymentId != that.paymentId) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;
        if (subInfo != null ? !subInfo.equals(that.subInfo) : that.subInfo != null) return false;
        if (paymenterId != null ? !paymenterId.equals(that.paymenterId) : that.paymenterId != null) return false;
        if (recieverId != null ? !recieverId.equals(that.recieverId) : that.recieverId != null) return false;
        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (finalPrice != null ? !finalPrice.equals(that.finalPrice) : that.finalPrice != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (paymentImageName != null ? !paymentImageName.equals(that.status) : that.paymentImageName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paymentId;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (subInfo != null ? subInfo.hashCode() : 0);
        result = 31 * result + (paymenterId != null ? paymenterId.hashCode() : 0);
        result = 31 * result + (recieverId != null ? recieverId.hashCode() : 0);
        result = 31 * result + (itemId != null ? itemId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (finalPrice != null ? finalPrice.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (paymentImageName != null ? paymentImageName.hashCode() : 0);
        return result;
    }

    public ClientsEntity getClientsByPaymenterId() {
        return clientsByPaymenterId;
    }

    public void setClientsByPaymenterId(ClientsEntity clientsByPaymenterId) {
        this.clientsByPaymenterId = clientsByPaymenterId;
    }

    public ClientsEntity getClientsByRecieverId() {
        return clientsByRecieverId;
    }

    public void setClientsByRecieverId(ClientsEntity clientsByRecieverId) {
        this.clientsByRecieverId = clientsByRecieverId;
    }

    public ItemsEntity getItemsByItemId() {
        return itemsByItemId;
    }

    public void setItemsByItemId(ItemsEntity itemsByItemId) {
        this.itemsByItemId = itemsByItemId;
    }
}
