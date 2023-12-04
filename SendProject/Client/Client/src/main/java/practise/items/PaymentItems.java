package practise.items;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PaymentItems extends RecursiveTreeObject<PaymentItems> {
    public SimpleIntegerProperty id, money;
    public SimpleStringProperty date, paymenter, status;
    public PaymentItems(int id, String date, int money, String paymenter, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.money = new SimpleIntegerProperty(money);
        this.paymenter = new SimpleStringProperty(paymenter);
        this.status = new SimpleStringProperty(status);
    }
}
