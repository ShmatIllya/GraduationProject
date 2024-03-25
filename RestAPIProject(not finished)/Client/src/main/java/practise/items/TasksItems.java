package practise.items;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TasksItems extends RecursiveTreeObject<TasksItems> {
    public SimpleStringProperty name, businesses, creationDate, deadline, responsable, checker, status;
    public SimpleIntegerProperty id;
    public TasksItems (String name, String businesses, String creationDate, String deadline, String responsable,
                          String checker, String status, int id) {
        this.name = new SimpleStringProperty(name);
        this.businesses = new SimpleStringProperty(businesses);
        this.creationDate = new SimpleStringProperty(creationDate);
        this.deadline = new SimpleStringProperty(deadline);
        this.responsable = new SimpleStringProperty(responsable);
        this.checker = new SimpleStringProperty(checker);
        this.status = new SimpleStringProperty(status);
        this.id = new SimpleIntegerProperty(id);
    }
}
