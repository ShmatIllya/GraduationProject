package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "business", schema = "crm", catalog = "")
public class BusinessEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "business_id")
    private int businessId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "client_id")
    private Integer clientId;
    @Basic
    @Column(name = "date")
    private String date;
    @Basic
    @Column(name = "place")
    private String place;
    @Basic
    @Column(name = "responsable_id")
    private Integer responsableId;
    @Basic
    @Column(name = "process_id")
    private Integer processId;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "task_id")
    private Integer taskID;
    @Basic
    @Column(name = "project_id")
    private Integer projectID;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clients_id", insertable = false, updatable = false)
    private ClientsEntity clientsByClientId;
    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByResponsableId;
    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "processes_id", insertable = false, updatable = false)
    private ProcessesEntity processesByProcessId;
    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "tasks_id", insertable = false, updatable = false)
    private TasksEntity tasksByTaskId;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "projects_id", insertable = false, updatable = false)
    private ProjectsEntity projectsByProjectId;

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Integer responsableId) {
        this.responsableId = responsableId;
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

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessEntity that = (BusinessEntity) o;

        if (businessId != that.businessId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (responsableId != null ? !responsableId.equals(that.responsableId) : that.responsableId != null)
            return false;
        if (processId != null ? !processId.equals(that.processId) : that.processId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (taskID != null ? !taskID.equals(that.taskID) : that.taskID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = businessId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (responsableId != null ? responsableId.hashCode() : 0);
        result = 31 * result + (processId != null ? processId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (taskID != null ? taskID.hashCode() : 0);
        return result;
    }

    public ClientsEntity getClientsByClientId() {
        return clientsByClientId;
    }

    public void setClientsByClientId(ClientsEntity clientsByClientId) {
        this.clientsByClientId = clientsByClientId;
    }

    public PersonalEntity getPersonalByResponsableId() {
        return personalByResponsableId;
    }

    public void setPersonalByResponsableId(PersonalEntity personalByResponsableId) {
        this.personalByResponsableId = personalByResponsableId;
    }

    public ProcessesEntity getProcessesByProcessId() {
        return processesByProcessId;
    }

    public void setProcessesByProcessId(ProcessesEntity processesByProcessId) {
        this.processesByProcessId = processesByProcessId;
    }

    public TasksEntity getTasksByTaskId() {
        return tasksByTaskId;
    }

    public void setTasksByTaskId(TasksEntity tasksByTaskId) {
        this.tasksByTaskId = tasksByTaskId;
    }

    public ProjectsEntity getProjectsByProjectId() {
        return projectsByProjectId;
    }

    public void setProjectsByProjectId(ProjectsEntity projectsByProjectId) {
        this.projectsByProjectId = projectsByProjectId;
    }
}
