package entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "comments", schema = "crm", catalog = "")
public class CommentsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "comments_id")
    private int commentsId;
    @Basic
    @Column(name = "text")
    private String text;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "sender_id")
    private Integer senderId;
    @Basic
    @Column(name = "journal_id")
    private Integer journalId;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalBySenderId;
    @ManyToOne
    @JoinColumn(name = "journal_id", referencedColumnName = "journals_id", insertable = false, updatable = false)
    private JournalsEntity journalsByJournalId;

    public int getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(int commentsId) {
        this.commentsId = commentsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getJournalId() {
        return journalId;
    }

    public void setJournalId(Integer journalId) {
        this.journalId = journalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentsEntity that = (CommentsEntity) o;

        if (commentsId != that.commentsId) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (senderId != null ? !senderId.equals(that.senderId) : that.senderId != null) return false;
        if (journalId != null ? !journalId.equals(that.journalId) : that.journalId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentsId;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
        result = 31 * result + (journalId != null ? journalId.hashCode() : 0);
        return result;
    }

    public PersonalEntity getPersonalBySenderId() {
        return personalBySenderId;
    }

    public void setPersonalBySenderId(PersonalEntity personalBySenderId) {
        this.personalBySenderId = personalBySenderId;
    }

    public JournalsEntity getJournalsByJournalId() {
        return journalsByJournalId;
    }

    public void setJournalsByJournalId(JournalsEntity journalsByJournalId) {
        this.journalsByJournalId = journalsByJournalId;
    }
}
