package Model;

import java.util.ArrayList;

public class Groups {

    private String groupName;
    private ArrayList<User> members;
    private int id;
    private String link;
    private User admin;
    public Groups(int id , User admin , String groupName , String link , ArrayList<User> members){
        this.id=id;
        this.members=members;
        this.admin=admin;
        this.groupName=groupName;
        this.link=link;
    }

    public Groups(int id , User admin , String groupName , String link ){
        this.id=id;
        this.admin=admin;
        this.groupName=groupName;
        this.link=link;
        members=new ArrayList<>();
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public ArrayList<User> getMembers() {
        return members;
    }
    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public User getAdmin() {
        return admin;
    }
    public void setAdmin(User admin) {
        this.admin = admin;
    }
    public void setMembers(User member){
        members.add(member);
    }
}
