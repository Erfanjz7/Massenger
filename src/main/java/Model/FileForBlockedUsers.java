package Model;

import java.io.*;
import java.util.ArrayList;

public class FileForBlockedUsers {

    public int getFileSize(){
        int size = 0;
        File file = new File("blockUsers.txt");
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

    public void blockFriend(User user , User friend){
        File file= new File("blockUsers.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))){
            writer.newLine();
            writer.write(user.getId() + "-" + friend.getId());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isBlocked(User user , User friend){
        int size= getFileSize();
        File file = new File("blockUsers.txt");
        try ( BufferedReader reader = new BufferedReader(new FileReader(file))){
            reader.readLine();
            for(int i = 0 ; i < size ; i++){
                String line = reader.readLine();
                String[] lineContent = line.split("-");
                if(user.getId() == Integer.parseInt(lineContent[0]) && friend.getId() == Integer.parseInt(lineContent[1])){
                    return true;
                }
                else if(user.getId() == Integer.parseInt(lineContent[1]) && friend.getId() == Integer.parseInt
                        (lineContent[0])){
                    return true;
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<String>getAllBlockedUsers(){
        int size = getFileSize();
        ArrayList <String> blockUsers = new ArrayList<>();
        File file=new File("blockUsers.txt");
        try(BufferedReader reader=new BufferedReader(new FileReader(file))){
            reader.readLine();
            for(int i=0;i<size;i++){
                String [] line=reader.readLine().split("-");
                blockUsers.add(line[0]+"-"+line[1]);
            }
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return blockUsers;
    }
    public void unblockuser(User user,User select){
        File file =new File("blockUsers.txt");
        ArrayList <String> blockedUsers = getAllBlockedUsers();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(int i = 0 ; i < blockedUsers.size() ; i++){
                String[]line = blockedUsers.get(i).split("-");
                if(!(line[0].equals(String.valueOf(user.getId())) && line[1].equals(String.valueOf(select.getId())))){
                    writer.newLine();
                    writer.write(line[0] + "-" + line[1]);
                }
            }
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
