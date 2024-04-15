package DTO;

import jakarta.persistence.*;

import java.util.ArrayList;

public class ChatDTO {

    public int chatsId;

    public String description;

    public String name;

    public String imageName;

    public ArrayList<String> membersList;

    public ChatDTO() {
        membersList = new ArrayList<>();
    }

    public ArrayList<String> getMembersList() {
        return membersList;
    }

    public void setMembersList(ArrayList<String> membersList) {
        this.membersList = membersList;
    }

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
}
