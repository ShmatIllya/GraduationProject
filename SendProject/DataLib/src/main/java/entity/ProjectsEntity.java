package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "projects", schema = "crm", catalog = "")
public class ProjectsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "projects_id")
    private int projectsId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "responsable_id")
    private Integer responsableId;
    @Basic
    @Column(name = "deadline")
    private Date deadline;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "creation_date")
    private Date creationDate;
    @Basic
    @Column(name = "trudozatraty")
    private String trudozatraty;
    @Basic
    @Column(name = "start_control")
    private String startControl;
    @Basic
    @Column(name = "end_control")
    private String end_control;
    @Basic
    @Column(name = "plan_control")
    private String plan_control;
    @Basic
    @Column(name = "izm")
    private String izm;
    @Basic
    @Column(name = "checker_id")
    private Integer checkerId;
    @OneToMany(mappedBy = "projectsByProjectId")
    private Collection<JournalsEntity> journalsByProjectsId;
    @OneToMany(mappedBy = "projectsByProjectId")
    private Collection<ProcessesEntity> processesByProjectsId;
    @OneToMany(mappedBy = "projectsByProjectId")
    private Collection<ProjectMembersEntity> projectMembersByProjectsId;
    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByResponsableId;
    @ManyToOne
    @JoinColumn(name = "checker_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByCheckerId;
    @OneToMany(mappedBy = "projectsByProjectId")
    private Collection<TasksEntity> tasksByProjectsId;
    @OneToMany(mappedBy = "projectsByProjectId")
    private Collection<BusinessEntity> businessByProjectsId;

    public int getProjectsId() {
        return projectsId;
    }

    public void setProjectsId(int projectsId) {
        this.projectsId = projectsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Integer responsableId) {
        this.responsableId = responsableId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTrudozatraty() {
        return trudozatraty;
    }

    public void setTrudozatraty(String trudozatraty) {
        this.trudozatraty = trudozatraty;
    }

    public String getStartControl() {
        return startControl;
    }

    public void setStartControl(String startControl) {
        this.startControl = startControl;
    }

    public String getEnd_control() {
        return end_control;
    }

    public void setEnd_control(String end_control) {
        this.end_control = end_control;
    }

    public Integer getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Integer checkerId) {
        this.checkerId = checkerId;
    }

    public String getPlan_control() {
        return plan_control;
    }

    public void setPlan_control(String plan_control) {
        this.plan_control = plan_control;
    }

    public String getIzm() {
        return izm;
    }

    public void setIzm(String izm) {
        this.izm = izm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectsEntity that = (ProjectsEntity) o;

        if (projectsId != that.projectsId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (responsableId != null ? !responsableId.equals(that.responsableId) : that.responsableId != null)
            return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (trudozatraty != null ? !trudozatraty.equals(that.trudozatraty) : that.trudozatraty != null) return false;
        if (startControl != null ? !startControl.equals(that.startControl) : that.startControl != null) return false;
        if (end_control != null ? !end_control.equals(that.end_control) : that.end_control != null) return false;
        if (plan_control != null ? !plan_control.equals(that.plan_control) : that.plan_control != null) return false;
        if (izm != null ? !izm.equals(that.izm) : that.izm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectsId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (responsableId != null ? responsableId.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (trudozatraty != null ? trudozatraty.hashCode() : 0);
        result = 31 * result + (startControl != null ? startControl.hashCode() : 0);
        result = 31 * result + (end_control != null ? end_control.hashCode() : 0);
        result = 31 * result + (plan_control != null ? plan_control.hashCode() : 0);
        result = 31 * result + (izm != null ? izm.hashCode() : 0);
        return result;
    }

    public Collection<JournalsEntity> getJournalsByProjectsId() {
        return journalsByProjectsId;
    }

    public void setJournalsByProjectsId(Collection<JournalsEntity> journalsByProjectsId) {
        this.journalsByProjectsId = journalsByProjectsId;
    }

    public Collection<ProcessesEntity> getProcessesByProjectsId() {
        return processesByProjectsId;
    }

    public void setProcessesByProjectsId(Collection<ProcessesEntity> processesByProjectsId) {
        this.processesByProjectsId = processesByProjectsId;
    }

    public Collection<ProjectMembersEntity> getProjectMembersByProjectsId() {
        return projectMembersByProjectsId;
    }

    public void setProjectMembersByProjectsId(Collection<ProjectMembersEntity> projectMembersByProjectsId) {
        this.projectMembersByProjectsId = projectMembersByProjectsId;
    }

    public PersonalEntity getPersonalByResponsableId() {
        return personalByResponsableId;
    }

    public void setPersonalByResponsableId(PersonalEntity personalByResponsableId) {
        this.personalByResponsableId = personalByResponsableId;
    }

    public PersonalEntity getPersonalByCheckerId() {
        return personalByCheckerId;
    }

    public void setPersonalByCheckerId(PersonalEntity personalByCheckerId) {
        this.personalByCheckerId = personalByCheckerId;
    }

    public Collection<TasksEntity> getTasksByProjectsId() {
        return tasksByProjectsId;
    }

    public void setTasksByProjectsId(Collection<TasksEntity> tasksByProjectsId) {
        this.tasksByProjectsId = tasksByProjectsId;
    }

    public Collection<BusinessEntity> getBusinessByProjectsId() {
        return businessByProjectsId;
    }

    public void setBusinessByProjectsId(Collection<BusinessEntity> businessByProjectsId) {
        this.businessByProjectsId = businessByProjectsId;
    }
}
