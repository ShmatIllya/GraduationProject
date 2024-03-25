package practise.controllers2.Stats.Representations;

import entity.*;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

public class ClientRepresentation {

    private String name;
    private String type;
    private String work_type;
    private int businessCount;
    private int paymentCount;
    private int tasksCount;

    public ClientRepresentation(String name, String type, String work_type, int businessCount,
                                int paymentCount, int tasksCount) {
        this.name = name;
        this.type = type;
        this.work_type = work_type;
        this.businessCount = businessCount;
        this.paymentCount = paymentCount;
        this.tasksCount = tasksCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public int getBusinessCount() {
        return businessCount;
    }

    public void setBusinessCount(int businessCount) {
        this.businessCount = businessCount;
    }

    public int getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(int paymentCount) {
        this.paymentCount = paymentCount;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }
}
