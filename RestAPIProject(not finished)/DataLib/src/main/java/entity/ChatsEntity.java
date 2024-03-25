package entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "chats", schema = "crm", catalog = "")
public class ChatsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "chats_id")
    private int chatsId;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "imageName")
    private String imageName;
    @OneToMany(mappedBy = "chatsByChatId")
    private Collection<ChatMembersEntity> chatMembersByChatsId;
    @OneToMany(mappedBy = "chatsByChatId")
    private Collection<MessagesEntity> messagesByChatsId;

    public int getChatsId() {
        return chatsId;
    }

    public void setChatsId(int chatsId) {
        this.chatsId = chatsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatsEntity that = (ChatsEntity) o;

        if (chatsId != that.chatsId) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chatsId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Collection<ChatMembersEntity> getChatMembersByChatsId() {
        return chatMembersByChatsId;
    }

    public void setChatMembersByChatsId(Collection<ChatMembersEntity> chatMembersByChatsId) {
        this.chatMembersByChatsId = chatMembersByChatsId;
    }

    public Collection<MessagesEntity> getMessagesByChatsId() {
        return messagesByChatsId;
    }

    public void setMessagesByChatsId(Collection<MessagesEntity> messagesByChatsId) {
        this.messagesByChatsId = messagesByChatsId;
    }
}
