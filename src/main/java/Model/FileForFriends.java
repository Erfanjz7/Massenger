package Model;

import java.io.*;
import java.util.ArrayList;

public class FileForFriends {

    private File file;
    private FileForUsers fileForUsers;

    public void addFriend(User user , User userFriend){
        file= new File("friendsFile.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))){

            writer.newLine();
            writer.write(user.getId() + "-" +userFriend.getId());

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public ArrayList <User> findFriends(User user)  {
        ArrayList <User> users = new ArrayList<>();
        fileForUsers = new FileForUsers();
        int size= getFileSize();
        file = new File("friendsFile.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for (int i = 0 ; i < size ; i++){
                String line = reader.readLine();
                String[] friendsId = line.split("-");
                if(friendsId[0].equals(String.valueOf(user.getId()))){
                    users.add(fileForUsers.getUser(Integer.parseInt(friendsId[1])));
                }
                else if(friendsId[1].equals(String.valueOf(user.getId()))){
                    users.add(fileForUsers.getUser(Integer.parseInt(friendsId[0])));
                }
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return users;
    }
    public int getFileSize(){
        file = new File("friendsFile.txt");
        int size=0;
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

    public boolean isFriend(String username , String friendUsername){
        fileForUsers = new FileForUsers();
        int userId = fileForUsers.getUserId(username);
        ArrayList <User> friendsArray= new ArrayList<>();
        friendsArray.addAll(getFriends(userId));
        for(int i = 0 ; i < friendsArray.size() ; i++ ){
            if(friendUsername.equals(friendsArray.get(i).getUsername())){
                return true;
            }
        }
        return false;
    }

    public ArrayList <User> getFriends(int userId){
        fileForUsers = new FileForUsers();
        int size = getFileSize();
        ArrayList <Integer> friendsId = new ArrayList<>();
        file = new File("friendsFile.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            for(int i = 0 ; i < size ; i++){
                String line = reader.readLine();
                String[] ids = line.split("-");
                if( ids[0].equals(String.valueOf(userId))){
                    friendsId.add(Integer.valueOf(ids[1]));

                }else if( ids[1].equals(String.valueOf(userId))){
                    friendsId.add(Integer.valueOf(ids[0]));
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        ArrayList <User> friends = new ArrayList<>();
        for(int c = 0 ; c < friendsId.size() ; c++){
            friends.add(fileForUsers.getUser(friendsId.get(c)));
        }
        return friends;
    }
}
