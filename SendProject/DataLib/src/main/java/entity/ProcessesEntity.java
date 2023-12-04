package entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "processes", schema = "crm", catalog = "")
public class ProcessesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "processes_id")
    private int processesId;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "client_id")
    private Integer clientId;
    @Basic
    @Column(name = "responsable_id")
    private Integer responsableId;
    @Basic
    @Column(name = "checker_id")
    private Integer checkerId;
    @Basic
    @Column(name = "payment")
    private String payment;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "project_id")
    private Integer projectId;
    @OneToMany(mappedBy = "processesByProcessId")
    private Collection<BusinessEntity> businessesByProcessesId;
    @OneToMany(mappedBy = "processesByProcessId")
    private Collection<JournalsEntity> journalsByProcessesId;
    @OneToMany(mappedBy = "processesByProcessId")
    private Collection<ProcessMembersEntity> processMembersByProcessesId;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clients_id", insertable = false, updatable = false)
    private ClientsEntity clientsByClientId;
    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByResponsableId;
    @ManyToOne
    @JoinColumn(name = "checker_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByCheckerId;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "projects_id", insertable = false, updatable = false)
    private ProjectsEntity projectsByProjectId;
    @OneToMany(mappedBy = "processesByProcessId")
    private Collection<TasksEntity> tasksByProcessesId;

    public int getProcessesId() {
        return processesId;
    }

    public void setProcessesId(int processesId) {
        this.processesId = processesId;
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

    public Integer getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Integer responsableId) {
        this.responsableId = responsableId;
    }

    public Integer getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Integer checkerId) {
        this.checkerId = checkerId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessesEntity that = (ProcessesEntity) o;

        if (processesId != that.processesId) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (responsableId != null ? !responsableId.equals(that.responsableId) : that.responsableId != null)
            return false;
        if (checkerId != null ? !checkerId.equals(that.checkerId) : that.checkerId != null) return false;
        if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = processesId;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (responsableId != null ? responsableId.hashCode() : 0);
        result = 31 * result + (checkerId != null ? checkerId.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        return result;
    }

    public Collection<BusinessEntity> getBusinessesByProcessesId() {
        return businessesByProcessesId;
    }

    public void setBusinessesByProcessesId(Collection<BusinessEntity> businessesByProcessesId) {
        this.businessesByProcessesId = businessesByProcessesId;
    }

    public Collection<JournalsEntity> getJournalsByProcessesId() {
        return journalsByProcessesId;
    }

    public void setJournalsByProcessesId(Collection<JournalsEntity> journalsByProcessesId) {
        this.journalsByProcessesId = journalsByProcessesId;
    }

    public Collection<ProcessMembersEntity> getProcessMembersByProcessesId() {
        return processMembersByProcessesId;
    }

    public void setProcessMembersByProcessesId(Collection<ProcessMembersEntity> processMembersByProcessesId) {
        this.processMembersByProcessesId = processMembersByProcessesId;
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

    public Collection<TasksEntity> getTasksByProcessesId() {
        return tasksByProcessesId;
    }

    public void setTasksByProcessesId(Collection<TasksEntity> tasksByProcessesId) {
        this.tasksByProcessesId = tasksByProcessesId;
    }
}
