package Model;

import java.io.*;
import java.util.ArrayList;

public class FileForFriendsMassages {
    private FileForUsers fileForUsers;
    public void addMessage(String message , String sender , String receiver , String dateTime){
        File file = new File("messageFriends.txt");
        try (BufferedWriter writer = new BufferedWriter( new FileWriter(file,true))){

            writer.newLine();
            writer.write(sender + "-" + receiver + "-" + message + "-" + dateTime);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void clearHistory(User user , User friend){
        ArrayList <Massages> allMessage= new ArrayList<>();
        allMessage.addAll(getAllMessages());
        ArrayList<Massages> allMessageAfterClearHistory = new ArrayList<>();
        for(int i = 0 ; i < allMessage.size() ; i++){
            String[] message = {allMessage.get(i).getSenderUsername() , allMessage.get(i).getReceiverUsername() , allMessage.get(i).getMessage(),allMessage.get(i).getDateTime()};

            if(!((message[0].equals(user.getUsername()) && message[1].equals(friend.getUsername())) || (message[0].equals(friend.getUsername()) && message[1].equals(user.getUsername()))) ){
                allMessageAfterClearHistory.add(new Massages(message[0],message[1],message[2],message[3]));
            }
        }
        writeInFileAfterEditing(allMessageAfterClearHistory);
    }

    public void deleteMessage(Massages selectedMessage){
        ArrayList <Massages> allMessage = new ArrayList<>();
        allMessage.addAll(getAllMessages());
        ArrayList<Massages> allMessageAfterDeleting = new ArrayList<>();
        for(int i=0 ; i < allMessage.size() ; i++){
            String[] message = {allMessage.get(i).getSenderUsername() , allMessage.get(i).getReceiverUsername() , allMessage.get(i).getMessage(),allMessage.get(i).getDateTime()};
            if(!(message[0].equals(selectedMessage.getSenderUsername()) && message[1].equals(selectedMessage.getReceiverUsername()) && message[2].equals(selectedMessage.getMessage())
                    && message[3].equals(selectedMessage.getDateTime()))){
                allMessageAfterDeleting.add(new Massages(message[0],message[1],message[2],message[3]));
            }
        }
        writeInFileAfterEditing(allMessageAfterDeleting);
    }

    public int getFileSize(){
        int size=0;
        File file = new File("messageFriends.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            while (reader.readLine() != null){
                size++;
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return size;
    }

    public ArrayList<Massages> getAllMessages(){
        int size = getFileSize();
        ArrayList<Massages> messages= new ArrayList<>();
        fileForUsers= new FileForUsers();
        File file = new File("messageFriends.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i = 0 ; i < size ; i++){
                String line = reader.readLine();
                String[] lineContent = line.split("-");
                messages.add(new Massages( lineContent[0],lineContent[1] ,lineContent[2],lineContent[3]));
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public ArrayList<Massages> getUserMessages(int senderId , int receiverId){
        ArrayList<Massages> allMessage =new ArrayList<>();
        allMessage.addAll(getAllMessages());
        ArrayList<Massages> userMessages = new ArrayList<>();
        for(int i=0 ; i < allMessage.size() ; i++){
            String[] message= {allMessage.get(i).getSenderUsername() , allMessage.get(i).getReceiverUsername(),
                    allMessage.get(i).getMessage(), allMessage.get(i).getDateTime()};
            if(fileForUsers.getUserId(message[0]) == senderId && fileForUsers.getUserId(message[1]) == receiverId){userMessages.add(new Massages(message[0],message[1],message[2] ,message[3]));
            }
            else if( fileForUsers.getUserId(message[0]) == receiverId && fileForUsers.getUserId(message[1]) == senderId){
                userMessages.add(new Massages(message[0],message[1],message[2],message[3]));
            }
        }
        return userMessages;
    }

    public void editMessage(Massages selectedMessage, Massages editedMessage){
        ArrayList<Massages> allMessage= new ArrayList<>();
        allMessage.addAll(getAllMessages());
        ArrayList<Massages> allMessageAfterEditing= new ArrayList<>();
        for(int i=0 ; i < allMessage.size() ; i++){
            String[] messages= {allMessage.get(i).getSenderUsername() , allMessage.get(i).getReceiverUsername() , allMessage.get(i).getMessage() , allMessage.get(i).getDateTime()};
            if(messages[0].equals(selectedMessage.getSenderUsername()) && messages[1].equals(selectedMessage.getReceiverUsername())
                    && messages[2].equals(selectedMessage.getMessage())){
                allMessageAfterEditing.add(new Massages(editedMessage.getSenderUsername()
                        ,editedMessage.getReceiverUsername() , editedMessage.getMessage() ,editedMessage.getDateTime()));
            }
            else {
                allMessageAfterEditing.add(new Massages(messages[0] , messages[1] , messages[2] , messages[3]));
            }
        }
        writeInFileAfterEditing(allMessageAfterEditing);

    }

    public void writeInFileAfterEditing(ArrayList<Massages> editedMessages){
        File file= new File("messageFriends.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

            for(int i=0 ; i < editedMessages.size() ; i++){
                writer.newLine();
                writer.write(editedMessages.get(i).getSenderUsername()+"-"+
                        editedMessages.get(i).getReceiverUsername()+"-"+editedMessages.get(i).getMessage()+"-"
                        +editedMessages.get(i).getDateTime());
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
