package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chat_members", schema = "crm", catalog = "")
public class ChatMembersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "chat_members_id")
    private int chatMembersId;
    @Basic
    @Column(name = "personal_id")
    private Integer personalId;
    @Basic
    @Column(name = "chat_id")
    private Integer chatId;
    @ManyToOne
    @JoinColumn(name = "personal_id", referencedColumnName = "personal_id", insertable = false, updatable = false)
    private PersonalEntity personalByPersonalId;
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chats_id", insertable = false, updatable = false)
    private ChatsEntity chatsByChatId;

    public int getChatMembersId() {
        return chatMembersId;
    }

    public void setChatMembersId(int chatMembersId) {
        this.chatMembersId = chatMembersId;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMembersEntity that = (ChatMembersEntity) o;

        if (chatMembersId != that.chatMembersId) return false;
        if (personalId != null ? !personalId.equals(that.personalId) : that.personalId != null) return false;
        if (chatId != null ? !chatId.equals(that.chatId) : that.chatId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chatMembersId;
        result = 31 * result + (personalId != null ? personalId.hashCode() : 0);
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        return result;
    }

    public PersonalEntity getPersonalByPersonalId() {
        return personalByPersonalId;
    }

    public void setPersonalByPersonalId(PersonalEntity personalByPersonalId) {
        this.personalByPersonalId = personalByPersonalId;
    }

    public ChatsEntity getChatsByChatId() {
        return chatsByChatId;
    }

    public void setChatsByChatId(ChatsEntity chatsByChatId) {
        this.chatsByChatId = chatsByChatId;
    }
}
