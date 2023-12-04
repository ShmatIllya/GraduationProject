package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "message_status", schema = "crm", catalog = "")
public class MessageStatusEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "personal_id")
    private Integer personalId;
    @Basic
    @Column(name = "message_id")
    private Integer messageId;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "chat_id")
    private int chatId;
    @ManyToOne
    @JoinColumn(name = "personal_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByPersonalId;
    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "messages_id", insertable = false, updatable = false)
    private MessagesEntity messageByMessageId;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chats_id", insertable = false, updatable = false)
    private ChatsEntity chatByChatId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public PersonalEntity getPersonalByPersonalId() {
        return personalByPersonalId;
    }

    public void setPersonalByPersonalId(PersonalEntity personalByPersonalId) {
        this.personalByPersonalId = personalByPersonalId;
    }

    public MessagesEntity getMessageByMessageId() {
        return messageByMessageId;
    }

    public void setMessageByMessageId(MessagesEntity messageByMessageId) {
        this.messageByMessageId = messageByMessageId;
    }

    public ChatsEntity getChatByChatId() {
        return chatByChatId;
    }

    public void setChatByChatId(ChatsEntity chatByChatId) {
        this.chatByChatId = chatByChatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageStatusEntity that = (MessageStatusEntity) o;

        if (id != that.id) return false;
        if (personalId != null ? !personalId.equals(that.personalId) : that.personalId != null) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (personalId != null ? personalId.hashCode() : 0);
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
