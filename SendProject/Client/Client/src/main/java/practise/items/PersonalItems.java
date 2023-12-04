package practise.items;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class PersonalItems extends RecursiveTreeObject<PersonalItems> {
    public SimpleStringProperty nameSername, contacts, email, subrole, status, login, password;
    public PersonalItems (String nameSername, String contacts, String email, String subrole, String status, String login,
                          String password) {
        this.nameSername = new SimpleStringProperty(nameSername);
        this.contacts = new SimpleStringProperty(contacts);
        this.email = new SimpleStringProperty(email);
        this.subrole = new SimpleStringProperty(subrole);
        this.status = new SimpleStringProperty(status);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
    }
}
