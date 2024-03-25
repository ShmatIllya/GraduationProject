package entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "clients", schema = "crm", catalog = "")
public class ClientsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "clients_id")
    private int clientsId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "responsable_id")
    private Integer responsableId;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "adress")
    private String adress;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "work_type")
    private String work_type;
    @Basic
    @Column(name = "reg_date")
    private Date reg_date;
    @OneToMany(mappedBy = "clientsByClientId")
    private Collection<BusinessEntity> businessesByClientsId;
    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByResponsableId;
    @OneToMany(mappedBy = "clientsByClientId")
    private Collection<JournalsEntity> journalsByClientsId;
    @OneToMany(mappedBy = "clientsByPaymenterId")
    private Collection<PaymentsEntity> paymentsByClientsId;
    @OneToMany(mappedBy = "clientsByRecieverId")
    private Collection<PaymentsEntity> paymentsByClientsId_0;
    @OneToMany(mappedBy = "clientsByClientId")
    private Collection<ProcessesEntity> processesByClientsId;
    @OneToMany(mappedBy = "clientsByClientId")
    private Collection<TasksEntity> tasksByClientsId;

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

    public String getWork_type() { return work_type; }

    public void setWork_type(String work_type) { this.work_type = work_type; }

    public Date getReg_date() { return reg_date; }

    public void setReg_date(Date reg_date) { this.reg_date = reg_date; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientsEntity that = (ClientsEntity) o;

        if (clientsId != that.clientsId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (responsableId != null ? !responsableId.equals(that.responsableId) : that.responsableId != null)
            return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (adress != null ? !adress.equals(that.adress) : that.adress != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (work_type != null ? !work_type.equals(that.work_type) : that.work_type != null) return false;
        if (reg_date != null ? !reg_date.equals(that.reg_date) : that.reg_date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clientsId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (responsableId != null ? responsableId.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (adress != null ? adress.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (work_type != null ? work_type.hashCode() : 0);
        result = 31 * result + (reg_date != null ? reg_date.hashCode() : 0);
        return result;
    }

    public Collection<BusinessEntity> getBusinessesByClientsId() {
        return businessesByClientsId;
    }

    public void setBusinessesByClientsId(Collection<BusinessEntity> businessesByClientsId) {
        this.businessesByClientsId = businessesByClientsId;
    }

    public PersonalEntity getPersonalByResponsableId() {
        return personalByResponsableId;
    }

    public void setPersonalByResponsableId(PersonalEntity personalByResponsableId) {
        this.personalByResponsableId = personalByResponsableId;
    }

    public Collection<JournalsEntity> getJournalsByClientsId() {
        return journalsByClientsId;
    }

    public void setJournalsByClientsId(Collection<JournalsEntity> journalsByClientsId) {
        this.journalsByClientsId = journalsByClientsId;
    }

    public Collection<PaymentsEntity> getPaymentsByClientsId() {
        return paymentsByClientsId;
    }

    public void setPaymentsByClientsId(Collection<PaymentsEntity> paymentsByClientsId) {
        this.paymentsByClientsId = paymentsByClientsId;
    }

    public Collection<PaymentsEntity> getPaymentsByClientsId_0() {
        return paymentsByClientsId_0;
    }

    public void setPaymentsByClientsId_0(Collection<PaymentsEntity> paymentsByClientsId_0) {
        this.paymentsByClientsId_0 = paymentsByClientsId_0;
    }

    public Collection<ProcessesEntity> getProcessesByClientsId() {
        return processesByClientsId;
    }

    public void setProcessesByClientsId(Collection<ProcessesEntity> processesByClientsId) {
        this.processesByClientsId = processesByClientsId;
    }

    public Collection<TasksEntity> getTasksByClientsId() {
        return tasksByClientsId;
    }

    public void setTasksByClientsId(Collection<TasksEntity> tasksByClientsId) {
        this.tasksByClientsId = tasksByClientsId;
    }
}
