package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "messages", schema = "crm", catalog = "")
public class MessagesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "messages_id")
    private int messagesId;
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
    @Column(name = "chat_id")
    private Integer chatId;
    @Basic
    @Column(name = "time")
    private Time time;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalBySenderId;
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chats_id", insertable = false, updatable = false)
    private ChatsEntity chatsByChatId;

    public int getMessagesId() {
        return messagesId;
    }

    public void setMessagesId(int messagesId) {
        this.messagesId = messagesId;
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

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessagesEntity that = (MessagesEntity) o;

        if (messagesId != that.messagesId) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (senderId != null ? !senderId.equals(that.senderId) : that.senderId != null) return false;
        if (chatId != null ? !chatId.equals(that.chatId) : that.chatId != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messagesId;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    public PersonalEntity getPersonalBySenderId() {
        return personalBySenderId;
    }

    public void setPersonalBySenderId(PersonalEntity personalBySenderId) {
        this.personalBySenderId = personalBySenderId;
    }

    public ChatsEntity getChatsByChatId() {
        return chatsByChatId;
    }

    public void setChatsByChatId(ChatsEntity chatsByChatId) {
        this.chatsByChatId = chatsByChatId;
    }
}
