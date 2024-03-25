package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "tasks", schema = "crm", catalog = "")
public class TasksEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tasks_id")
    private int tasksId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "responsable_id")
    private Integer responsableId;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "checker_id")
    private Integer checkerId;
    @Basic
    @Column(name = "deadline")
    private Date deadline;
    @Basic
    @Column(name = "project_id")
    private Integer projectId;
    @Basic
    @Column(name = "process_id")
    private Integer processId;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "client_id")
    private Integer clientId;
    @Basic
    @Column(name = "creation_date")
    private Date creationDate;
    @Basic
    @Column(name = "priority")
    private String priority;
    @OneToMany(mappedBy = "tasksByTaskId")
    private Collection<JournalsEntity> journalsByTasksId;
    @OneToMany(mappedBy = "tasksByTaskId")
    private Collection<BusinessEntity> businessesByTasksId;
    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByResponsableId;
    @ManyToOne
    @JoinColumn(name = "checker_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByCheckerId;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "projects_id", insertable = false, updatable = false)
    private ProjectsEntity projectsByProjectId;
    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "processes_id", insertable = false, updatable = false)
    private ProcessesEntity processesByProcessId;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clients_id", insertable = false, updatable = false)
    private ClientsEntity clientsByClientId;

    public int getTasksId() {
        return tasksId;
    }

    public void setTasksId(int tasksId) {
        this.tasksId = tasksId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Integer checkerId) {
        this.checkerId = checkerId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TasksEntity that = (TasksEntity) o;

        if (tasksId != that.tasksId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (responsableId != null ? !responsableId.equals(that.responsableId) : that.responsableId != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (checkerId != null ? !checkerId.equals(that.checkerId) : that.checkerId != null) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (processId != null ? !processId.equals(that.processId) : that.processId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (priority != null ? !priority.equals(that.priority) : that.priority != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tasksId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (responsableId != null ? responsableId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (checkerId != null ? checkerId.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (processId != null ? processId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        return result;
    }

    public Collection<JournalsEntity> getJournalsByTasksId() {
        return journalsByTasksId;
    }

    public void setJournalsByTasksId(Collection<JournalsEntity> journalsByTasksId) {
        this.journalsByTasksId = journalsByTasksId;
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

    public ProjectsEntity getProjectsByProjectId() {
        return projectsByProjectId;
    }

    public void setProjectsByProjectId(ProjectsEntity projectsByProjectId) {
        this.projectsByProjectId = projectsByProjectId;
    }

    public ProcessesEntity getProcessesByProcessId() {
        return processesByProcessId;
    }

    public void setProcessesByProcessId(ProcessesEntity processesByProcessId) {
        this.processesByProcessId = processesByProcessId;
    }

    public ClientsEntity getClientsByClientId() {
        return clientsByClientId;
    }

    public void setClientsByClientId(ClientsEntity clientsByClientId) {
        this.clientsByClientId = clientsByClientId;
    }

    public Collection<BusinessEntity> getBusinessesByTasksId() {
        return businessesByTasksId;
    }

    public void setBusinessesByTasksId(Collection<BusinessEntity> businessesByTasksId) {
        this.businessesByTasksId = businessesByTasksId;
    }



}
