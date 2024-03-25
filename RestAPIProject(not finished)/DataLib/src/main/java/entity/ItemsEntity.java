package entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "items", schema = "crm", catalog = "")
public class ItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    private int itemId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "articul")
    private String articul;
    @Basic
    @Column(name = "price")
    private Integer price;
    @Basic
    @Column(name = "taxes")
    private Integer taxes;
    @Basic
    @Column(name = "measurement")
    private String measurement;
    @OneToMany(mappedBy = "itemsByItemId")
    private Collection<PaymentsEntity> paymentsByItemId;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticul() {
        return articul;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTaxes() {
        return taxes;
    }

    public void setTaxes(Integer taxes) {
        this.taxes = taxes;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemsEntity that = (ItemsEntity) o;

        if (itemId != that.itemId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (articul != null ? !articul.equals(that.articul) : that.articul != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (taxes != null ? !taxes.equals(that.taxes) : that.taxes != null) return false;
        if (measurement != null ? !measurement.equals(that.measurement) : that.measurement != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (articul != null ? articul.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (taxes != null ? taxes.hashCode() : 0);
        result = 31 * result + (measurement != null ? measurement.hashCode() : 0);
        return result;
    }

    public Collection<PaymentsEntity> getPaymentsByItemId() {
        return paymentsByItemId;
    }

    public void setPaymentsByItemId(Collection<PaymentsEntity> paymentsByItemId) {
        this.paymentsByItemId = paymentsByItemId;
    }
}
