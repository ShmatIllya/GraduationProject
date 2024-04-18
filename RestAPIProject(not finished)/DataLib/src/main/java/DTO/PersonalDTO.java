package DTO;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.sql.Date;

public class PersonalDTO {

    public int personalId;

    public String login;

    public String password;

    public String nameSername;

    public String contacts;

    public String email;

    public String role;

    public String subrole;

    public String status;

    public String description;

    public Date regDate;

    public String imageName;

    public ByteArrayInputStream avatarImage;

    public ByteArrayInputStream getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(ByteArrayInputStream avatarImage) {
        this.avatarImage = avatarImage;
    }

    public int getPersonalId() {
        return personalId;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameSername() {
        return nameSername;
    }

    public void setNameSername(String nameSername) {
        this.nameSername = nameSername;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
