package DTO;

import jakarta.persistence.Id;

import java.util.Date;

public class ClientDTO {

    public int clientsId;

    public String name;

    public Integer responsableId;

    public String phone;

    public String email;

    public String adress;

    public String description;

    public String type;

    public String work_type;

    public Date reg_date;

    public String responsible_name;

    public String old_name;

    public String old_email;

    public String getOld_name() {
        return old_name;
    }

    public void setOld_name(String old_name) {
        this.old_name = old_name;
    }

    public String getOld_email() {
        return old_email;
    }

    public void setOld_email(String old_email) {
        this.old_email = old_email;
    }

    public String getResponsible_name() {
        return responsible_name;
    }

    public void setResponsible_name(String responsible_name) {
        this.responsible_name = responsible_name;
    }

    public int getClientsId() {
        return clientsId;
    }

    public void setClientsId(int clientsId) {
        this.clientsId = clientsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Integer responsableId) {
        this.responsableId = responsableId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }
}
