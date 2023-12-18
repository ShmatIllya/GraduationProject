package practise.controllers2.Stats.Representations;

import entity.*;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

public class ProjectRepresentation {

    private String name;
    private String status;
    private String trudozatraty;
    private int membersCount;
    private int tasksCount;

    public ProjectRepresentation(String name, String status,
                                 String trudozatraty, int membersCount, int tasksCount) {
        this.name = name;
        this.status = status;
        this.trudozatraty = trudozatraty;
        this.membersCount = membersCount;
        this.tasksCount = tasksCount;
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

    public String getTrudozatraty() {
        return trudozatraty;
    }

    public void setTrudozatraty(String trudozatraty) {
        this.trudozatraty = trudozatraty;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }
}
