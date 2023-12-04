package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "process_members", schema = "crm", catalog = "")
public class ProcessMembersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "process_members_id")
    private int processMembersId;
    @Basic
    @Column(name = "process_id")
    private Integer processId;
    @Basic
    @Column(name = "personal_id")
    private Integer personalId;
    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "processes_id", insertable = false, updatable = false)
    private ProcessesEntity processesByProcessId;
    @ManyToOne
    @JoinColumn(name = "personal_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByPersonalId;

    public int getProcessMembersId() {
        return processMembersId;
    }

    public void setProcessMembersId(int processMembersId) {
        this.processMembersId = processMembersId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessMembersEntity that = (ProcessMembersEntity) o;

        if (processMembersId != that.processMembersId) return false;
        if (processId != null ? !processId.equals(that.processId) : that.processId != null) return false;
        if (personalId != null ? !personalId.equals(that.personalId) : that.personalId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = processMembersId;
        result = 31 * result + (processId != null ? processId.hashCode() : 0);
        result = 31 * result + (personalId != null ? personalId.hashCode() : 0);
        return result;
    }

    public ProcessesEntity getProcessesByProcessId() {
        return processesByProcessId;
    }

    public void setProcessesByProcessId(ProcessesEntity processesByProcessId) {
        this.processesByProcessId = processesByProcessId;
    }

    public PersonalEntity getPersonalByPersonalId() {
        return personalByPersonalId;
    }

    public void setPersonalByPersonalId(PersonalEntity personalByPersonalId) {
        this.personalByPersonalId = personalByPersonalId;
    }
}
