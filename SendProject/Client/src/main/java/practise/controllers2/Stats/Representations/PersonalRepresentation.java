package practise.controllers2.Stats.Representations;

import entity.*;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

public class PersonalRepresentation {

    private String nameSername;
    private String role;
    private String subrole;
    private String status;
    private int businessCount;
    private int clientsCount;
    private int projectsCount;
    private int tasksCount;

    public PersonalRepresentation(String nameSername, String role, String subrole, String status,
                                  int businessCount, int clientsCount, int projectsCount, int tasksCount) {
        this.nameSername = nameSername;
        this.role = role;
        this.subrole = subrole;
        this.status = status;
        this.businessCount = businessCount;
        this.clientsCount = clientsCount;
        this.projectsCount = projectsCount;
        this.tasksCount = tasksCount;
    }

    public String getNameSername() {
        return nameSername;
    }

    public void setNameSername(String nameSername) {
        this.nameSername = nameSername;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSubrole() {
        return subrole;
    }

    public void setSubrole(String subrole) {
        this.subrole = subrole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBusinessCount() {
        return businessCount;
    }

    public void setBusinessCount(int businessCount) {
        this.businessCount = businessCount;
    }

    public int getClientsCount() {
        return clientsCount;
    }

    public void setClientsCount(int clientsCount) {
        this.clientsCount = clientsCount;
    }

    public int getProjectsCount() {
        return projectsCount;
    }

    public void setProjectsCount(int projectsCount) {
        this.projectsCount = projectsCount;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }
}
