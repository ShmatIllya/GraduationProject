package practise.items;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClientsItems extends RecursiveTreeObject<ClientsItems> {
    public SimpleStringProperty name, type, responsable;
    public SimpleIntegerProperty id;
    public ClientsItems (String name, String type, String responsable, Integer id) {
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.responsable = new SimpleStringProperty(responsable);
        this.id = new SimpleIntegerProperty(id);
    }
}
