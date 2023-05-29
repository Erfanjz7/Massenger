package Model;

public class Massages {

    private User sender;
    private String message;
    private String senderUsername;
    private FileForUsers fileForUsers;
    private String receiverUsername;
    private String dateTime;
    private int groupId;

    public Massages(String senderUsername, String receiverUsername , String message , String dateTime) {
        fileForUsers= new FileForUsers();
        this.sender = fileForUsers.getUser(senderUsername);
        this.message = message;
        this.dateTime=dateTime;
        this.receiverUsername=receiverUsername;
        this.senderUsername=senderUsername;
    }

    public Massages(String senderUsername , int groupId , String message , String dateTime){
        fileForUsers=new FileForUsers();
        this.senderUsername=senderUsername;
        this.sender=fileForUsers.getUser(senderUsername);
        this.groupId=groupId;
        this.message=message;
        this.dateTime=dateTime;
    }

    public int getGroupId(){
        return groupId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
