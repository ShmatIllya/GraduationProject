package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "personal", schema = "crm", catalog = "")
public class PersonalEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "personal_id")
    private int personalId;
    @Basic
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "nameSername")
    private String nameSername;
    @Basic
    @Column(name = "contacts")
    private String contacts;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "role")
    private String role;
    @Basic
    @Column(name = "subrole")
    private String subrole;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "regDate")
    private Date regDate;
    @Basic
    @Column(name = "imageName")
    private String imageName;
    @OneToMany(mappedBy = "personalByResponsableId")
    private Collection<BusinessEntity> businessesByPersonalId;
    @OneToMany(mappedBy = "personalByPersonalId")
    private Collection<ChatMembersEntity> chatMembersByPersonalId;
    @OneToMany(mappedBy = "personalByResponsableId")
    private Collection<ClientsEntity> clientsByPersonalId;
    @OneToMany(mappedBy = "personalBySenderId")
    private Collection<CommentsEntity> commentsByPersonalId;
    @OneToMany(mappedBy = "personalBySenderId")
    private Collection<MessagesEntity> messagesByPersonalId;
    @OneToMany(mappedBy = "personalByPersonalId")
    private Collection<ProcessMembersEntity> processMembersByPersonalId;
    @OneToMany(mappedBy = "personalByResponsableId")
    private Collection<ProcessesEntity> processesByPersonalId;
    @OneToMany(mappedBy = "personalByCheckerId")
    private Collection<ProcessesEntity> processesByPersonalId_0;
    @OneToMany(mappedBy = "personalByPersonalId")
    private Collection<ProjectMembersEntity> projectMembersByPersonalId;
    @OneToMany(mappedBy = "personalByResponsableId")
    private Collection<ProjectsEntity> projectsByPersonalId;
    @OneToMany(mappedBy = "personalByResponsableId")
    private Collection<TasksEntity> tasksByPersonalId;
    @OneToMany(mappedBy = "personalByCheckerId")
    private Collection<TasksEntity> tasksByPersonalId_0;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalEntity personal = (PersonalEntity) o;

        if (personalId != personal.personalId) return false;
        if (login != null ? !login.equals(personal.login) : personal.login != null) return false;
        if (password != null ? !password.equals(personal.password) : personal.password != null) return false;
        if (nameSername != null ? !nameSername.equals(personal.nameSername) : personal.nameSername != null)
            return false;
        if (contacts != null ? !contacts.equals(personal.contacts) : personal.contacts != null) return false;
        if (email != null ? !email.equals(personal.email) : personal.email != null) return false;
        if (role != null ? !role.equals(personal.role) : personal.role != null) return false;
        if (subrole != null ? !subrole.equals(personal.subrole) : personal.subrole != null) return false;
        if (status != null ? !status.equals(personal.status) : personal.status != null) return false;
        if (description != null ? !description.equals(personal.description) : personal.description != null)
            return false;
        if (regDate != null ? !regDate.equals(personal.regDate) : personal.regDate != null) return false;
        if (imageName != null ? !imageName.equals(personal.imageName) : personal.imageName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = personalId;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (nameSername != null ? nameSername.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (subrole != null ? subrole.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (regDate != null ? regDate.hashCode() : 0);
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        return result;
    }

    public Collection<BusinessEntity> getBusinessesByPersonalId() {
        return businessesByPersonalId;
    }

    public void setBusinessesByPersonalId(Collection<BusinessEntity> businessesByPersonalId) {
        this.businessesByPersonalId = businessesByPersonalId;
    }

    public Collection<ChatMembersEntity> getChatMembersByPersonalId() {
        return chatMembersByPersonalId;
    }

    public void setChatMembersByPersonalId(Collection<ChatMembersEntity> chatMembersByPersonalId) {
        this.chatMembersByPersonalId = chatMembersByPersonalId;
    }

    public Collection<ClientsEntity> getClientsByPersonalId() {
        return clientsByPersonalId;
    }

    public void setClientsByPersonalId(Collection<ClientsEntity> clientsByPersonalId) {
        this.clientsByPersonalId = clientsByPersonalId;
    }

    public Collection<CommentsEntity> getCommentsByPersonalId() {
        return commentsByPersonalId;
    }

    public void setCommentsByPersonalId(Collection<CommentsEntity> commentsByPersonalId) {
        this.commentsByPersonalId = commentsByPersonalId;
    }

    public Collection<MessagesEntity> getMessagesByPersonalId() {
        return messagesByPersonalId;
    }

    public void setMessagesByPersonalId(Collection<MessagesEntity> messagesByPersonalId) {
        this.messagesByPersonalId = messagesByPersonalId;
    }

    public Collection<ProcessMembersEntity> getProcessMembersByPersonalId() {
        return processMembersByPersonalId;
    }

    public void setProcessMembersByPersonalId(Collection<ProcessMembersEntity> processMembersByPersonalId) {
        this.processMembersByPersonalId = processMembersByPersonalId;
    }

    public Collection<ProcessesEntity> getProcessesByPersonalId() {
        return processesByPersonalId;
    }

    public void setProcessesByPersonalId(Collection<ProcessesEntity> processesByPersonalId) {
        this.processesByPersonalId = processesByPersonalId;
    }

    public Collection<ProcessesEntity> getProcessesByPersonalId_0() {
        return processesByPersonalId_0;
    }

    public void setProcessesByPersonalId_0(Collection<ProcessesEntity> processesByPersonalId_0) {
        this.processesByPersonalId_0 = processesByPersonalId_0;
    }

    public Collection<ProjectMembersEntity> getProjectMembersByPersonalId() {
        return projectMembersByPersonalId;
    }

    public void setProjectMembersByPersonalId(Collection<ProjectMembersEntity> projectMembersByPersonalId) {
        this.projectMembersByPersonalId = projectMembersByPersonalId;
    }

    public Collection<ProjectsEntity> getProjectsByPersonalId() {
        return projectsByPersonalId;
    }

    public void setProjectsByPersonalId(Collection<ProjectsEntity> projectsByPersonalId) {
        this.projectsByPersonalId = projectsByPersonalId;
    }

    public Collection<TasksEntity> getTasksByPersonalId() {
        return tasksByPersonalId;
    }

    public void setTasksByPersonalId(Collection<TasksEntity> tasksByPersonalId) {
        this.tasksByPersonalId = tasksByPersonalId;
    }

    public Collection<TasksEntity> getTasksByPersonalId_0() {
        return tasksByPersonalId_0;
    }

    public void setTasksByPersonalId_0(Collection<TasksEntity> tasksByPersonalId_0) {
        this.tasksByPersonalId_0 = tasksByPersonalId_0;
    }
}
