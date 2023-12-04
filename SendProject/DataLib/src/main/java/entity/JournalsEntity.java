package entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "journals", schema = "crm", catalog = "")
public class JournalsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "journals_id")
    private int journalsId;
    @Basic
    @Column(name = "process_id")
    private Integer processId;
    @Basic
    @Column(name = "task_id")
    private Integer taskId;
    @Basic
    @Column(name = "project_id")
    private Integer projectId;
    @Basic
    @Column(name = "client_id")
    private Integer clientId;
    @OneToMany(mappedBy = "journalsByJournalId")
    private Collection<CommentsEntity> commentsByJournalsId;
    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "processes_id", insertable = false, updatable = false)
    private ProcessesEntity processesByProcessId;
    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "tasks_id", insertable = false, updatable = false)
    private TasksEntity tasksByTaskId;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "projects_id", insertable = false, updatable = false)
    private ProjectsEntity projectsByProjectId;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clients_id", insertable = false, updatable = false)
    private ClientsEntity clientsByClientId;

    public int getJournalsId() {
        return journalsId;
    }

    public void setJournalsId(int journalsId) {
        this.journalsId = journalsId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JournalsEntity that = (JournalsEntity) o;

        if (journalsId != that.journalsId) return false;
        if (processId != null ? !processId.equals(that.processId) : that.processId != null) return false;
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = journalsId;
        result = 31 * result + (processId != null ? processId.hashCode() : 0);
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        return result;
    }

    public Collection<CommentsEntity> getCommentsByJournalsId() {
        return commentsByJournalsId;
    }

    public void setCommentsByJournalsId(Collection<CommentsEntity> commentsByJournalsId) {
        this.commentsByJournalsId = commentsByJournalsId;
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

    public ClientsEntity getClientsByClientId() {
        return clientsByClientId;
    }

    public void setClientsByClientId(ClientsEntity clientsByClientId) {
        this.clientsByClientId = clientsByClientId;
    }
}
