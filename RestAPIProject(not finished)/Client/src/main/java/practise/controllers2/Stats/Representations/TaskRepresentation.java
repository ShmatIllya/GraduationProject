package practise.controllers2.Stats.Representations;

import entity.*;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

public class TaskRepresentation {

    private String name;
    private String status;
    private String priority;

    public TaskRepresentation(String name, String status, String priority) {
        this.name = name;
        this.status = status;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
