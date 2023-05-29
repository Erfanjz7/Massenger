package Model;

import java.io.*;
import java.util.ArrayList;

public class FileForGroupMassages {

    private FileForUsers fileForUsers;

    public void addMessage(String message , String sender , int groupId , String dateTime){
        File file = new File("messageGroups.txt");
        try (BufferedWriter writer = new BufferedWriter( new FileWriter(file,true))){

            writer.newLine();
            writer.write(sender + "-" + groupId + "-" + message + "-" + dateTime);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public int getFileSize(){
        int size = 0;
        File file=new File("messageGroups.txt");
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
        ArrayList<Massages> messages = new ArrayList<>();
        fileForUsers = new FileForUsers();
        File file = new File("messageGroups.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i = 0 ; i < size ; i++){
                String line = reader.readLine();
                String[] lineContent = line.split("-");
                messages.add(new Massages( lineContent[0] ,Integer.parseInt(lineContent[1]) , lineContent[2] , lineContent[3]));
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public ArrayList<Massages> getGroupMessages( int groupId){
        ArrayList<Massages> allMessage =new ArrayList<>();
        allMessage.addAll(getAllMessages());
        ArrayList<Massages> userMessages= new ArrayList<>();
        for(int i = 0 ; i < allMessage.size() ; i++){
            if(allMessage.get(i).getGroupId() == groupId){
                userMessages.add(new Massages(allMessage.get(i).getSender().getUsername() , groupId , allMessage.get(i).getMessage() , allMessage.get(i).getDateTime()));
            }
        }
        return userMessages;
    }

    public void clearHistory(Groups group){
        ArrayList<Massages> allMessage = getAllMessages();
        ArrayList<Massages> messagesAfterClearHistory = new ArrayList<>();
        for(int i = 0 ; i < allMessage.size() ; i++){
            if(allMessage.get(i).getGroupId() != group.getId()){
                messagesAfterClearHistory.add(allMessage.get(i));
            }
        }
        writeInFileAfterChange(messagesAfterClearHistory);
    }

    public void writeInFileAfterChange(ArrayList<Massages> messages){
        File file = new File("messageGroups.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

            for(int i=0 ; i < messages.size() ; i++){
                writer.newLine();
                writer.write(messages.get(i).getSenderUsername() + "-" + messages.get(i).getGroupId()+ "-"
                        +messages.get(i).getMessage()+ "-" +messages.get(i).getDateTime());
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void editMessage(Massages selectedMessage , Massages editedMessage){
        ArrayList<Massages> allMessages = new ArrayList<>();
        allMessages.addAll(getAllMessages());
        ArrayList<Massages> messagesAfterEditing= new ArrayList<>();
        for(int i = 0 ; i < allMessages.size() ; i++){
            boolean condition = (allMessages.get(i).getMessage().equals(selectedMessage.getMessage()) &&
                    allMessages.get(i).getSender().getId() == selectedMessage.getSender().getId() &&
                    allMessages.get(i).getDateTime().equals(selectedMessage.getDateTime()));
            if(condition){
                messagesAfterEditing.add(editedMessage);
            }
            else
                messagesAfterEditing.add(allMessages.get(i));
        }

        writeInFileAfterChange(messagesAfterEditing);
    }

    public void deleteMessage(Massages selectedMessage){
        ArrayList<Massages> allMessages = new ArrayList<>();
        allMessages.addAll(getAllMessages());
        ArrayList<Massages> messagesAfterDeleting = new ArrayList<>();
        for(int i=0 ; i < allMessages.size() ; i++){
            boolean condition = (allMessages.get(i).getMessage().equals(selectedMessage.getMessage()) &&
                    allMessages.get(i).getSender().getId()==selectedMessage.getSender().getId() &&
                    allMessages.get(i).getDateTime().equals(selectedMessage.getDateTime()));
            if(!condition){
                messagesAfterDeleting.add(allMessages.get(i));
            }
        }

        writeInFileAfterChange(messagesAfterDeleting);
    }
}
