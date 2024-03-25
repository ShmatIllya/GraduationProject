package practise.controllers2.Stats.Representations;

import entity.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class BusinessRepresentation {

    private String name;
    private String status;

    public BusinessRepresentation(String name, String status) {
        this.name = name;
        this.status = status;
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
}
