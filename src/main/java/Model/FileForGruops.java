package Model;

import java.io.*;
import java.util.ArrayList;

public class FileForGruops {

    private FileForUsers fileForUsers;
    private int groupId=0;

    public int getFileSize(){
        int size=0;
        File file = new File("GroupsFile.txt");
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

    public ArrayList<Groups> getAllGroups(){
        // id-admin-groupname-link-members(ali_sara)
        fileForUsers= new FileForUsers();
        int size= getFileSize();
        ArrayList<Groups> allGroups=new ArrayList<>();
        File file = new File("GroupsFile.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size ; i++) {
                String line = reader.readLine();
                String[] group = line.split("-");
                if (group.length > 4) {
                    String[] members = group[4].split("/");
                    ArrayList<User> membersArrayList = new ArrayList<>();
                    for (int j = 0; j < members.length; j++) {
                        membersArrayList.add(fileForUsers.getUser(members[j]));
                    }
                    allGroups.add(new Groups(Integer.parseInt(group[0]) ,fileForUsers.getUser(group[1]) ,group[2] ,group[3] ,
                            membersArrayList));
                }
                else{
                    allGroups.add(new Groups(Integer.parseInt(group[0]), fileForUsers.getUser(group[1]), group[2], group[3]));
                }
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        return allGroups;
    }


    public ArrayList<Groups> getUserAllGroups(User user){
        int size= getFileSize();
        ArrayList<Groups> allGroup = new ArrayList<>();
        allGroup.addAll(getAllGroups());
        ArrayList<Groups> userGroups = new ArrayList<>();
        for(int i=0 ; i < size ; i++){
            if(allGroup.get(i).getAdmin().getUsername().equals(user.getUsername())){
                userGroups.add(allGroup.get(i));
            }
            else {
                for(int j=0 ; j < allGroup.get(i).getMembers().size() ; j++){
                    if(allGroup.get(i).getMembers().size() > 0 ){
                        User member = allGroup.get(i).getMembers().get(j);
                        if(member.getUsername().equals(user.getUsername())){
                            userGroups.add(allGroup.get(i));
                            break;
                        }
                    }

                }

            }
        }

        return userGroups;

    }

    public void addNewGroup(User admin , String groupName ,String link){
        this.groupId=getGroupId();
        File file = new File("GroupsFile.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))){

            writer.newLine();
            writer.write(groupId+"-"+admin.getUsername()+"-"+groupName+"-"+link+"-");

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public int getGroupId(){
        int size= getFileSize();
        int id=0;
        for (int counter=0 ;  counter < size ; counter++){
            id++;
        }
        return (id+1);
    }

    public boolean linkNotExist(String link){
        int size = getFileSize();
        File file = new File("GroupsFile.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size ; i++){
                String line = reader.readLine();
                String[] group = line.split("-");
                if(link.equals(group[3])){
                    return false;
                }
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    public Groups getGroup(String link){
        ArrayList<Groups> groups = getAllGroups();
        for(int i=0 ; i < groups.size() ; i++){
            if(groups.get(i).getLink().equals(link)){
                return groups.get(i);
            }
        }
        return null;
    }

    public boolean isAdmin(Groups selectedGroup , User user){
        ArrayList<Groups> groups = getUserAllGroups(user);
        for (int i=0 ; i < groups.size() ; i++){
            if(groups.get(i).getId() == selectedGroup.getId()){
                if(user.getUsername().equals(selectedGroup.getAdmin().getUsername())){
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<User> getGroupMembers(int groupId){
        ArrayList<Groups> allGroups =new ArrayList<>();
        allGroups.addAll(getAllGroups());
        int i;
        for( i=0 ; i < allGroups.size() ; i++){
            if(allGroups.get(i).getId() == groupId){
                break;
            }
        }
        return ( allGroups.get(i).getMembers());
    }

    public Groups kickMember(User selectedMember , Groups group){
        ArrayList<User> members= new ArrayList<>();
        int i;
        Groups groupAfterKickMember ;
        if(group.getMembers().size() > 1){
            for(i=0 ; i < group.getMembers().size() ; i++){
                if( group.getMembers().get(i).getId() != selectedMember.getId() ){
                    members.add(group.getMembers().get(i));
                }
            }
            groupAfterKickMember = new Groups(group.getId(),group.getAdmin(),group.getGroupName(),group.getLink(),
                    members);
        }
        else
            groupAfterKickMember= new Groups(group.getId(),group.getAdmin(),group.getGroupName(),group.getLink());


        ArrayList<Groups> allGroups = new ArrayList<>();
        allGroups.addAll(getAllGroups());
        ArrayList<Groups> allGroupAfterKickMember = new ArrayList<>();
        for(int j = 0; j < allGroups.size() ; j++){
            if(allGroups.get(j).getId() == groupAfterKickMember.getId()){
                allGroupAfterKickMember.add(groupAfterKickMember);
            }
            else {
                allGroupAfterKickMember.add(allGroups.get(j));
            }
        }
        writeInFileAfterEditing(allGroupAfterKickMember);
        return groupAfterKickMember;
    }


    public void writeInFileAfterEditing(ArrayList<Groups> allGroupsAfterEditing){


        File file= new File("GroupsFile.txt");
        try( BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

            for(int i=0 ; i < allGroupsAfterEditing.size() ; i++){
                writer.newLine();
                writer.write(allGroupsAfterEditing.get(i).getId()+"-"+
                        allGroupsAfterEditing.get(i).getAdmin().getUsername()+"-"+
                        allGroupsAfterEditing.get(i).getGroupName()+"-"+allGroupsAfterEditing.get(i).getLink()+"-");
                if(allGroupsAfterEditing.get(i).getMembers().size() > 0){
                    for(int j=0 ; j < allGroupsAfterEditing.get(i).getMembers().size() ; j++){
                        if(j+1 == allGroupsAfterEditing.get(i).getMembers().size()){
                            writer.write(allGroupsAfterEditing.get(i).getMembers().get(j).getUsername());
                        }
                        else {
                            writer.write(allGroupsAfterEditing.get(i).getMembers().get(j).getUsername()+"/");
                        }

                    }
                }

            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void addMemberToGroup(String linkAddress , User newMember){
        Groups group = getGroup(linkAddress);
        ArrayList<User> member= new ArrayList<>();
        member.add(newMember);
        if( group.getMembers() != null){
            group.setMembers(newMember);

        }
        else
            group.setMembers(member);


        ArrayList<Groups> groups = new ArrayList<>();
        groups.addAll(getAllGroups());
        for(int i=0 ; i < groups.size() ; i++){
            if(groups.get(i).getId() == group.getId()){
                groups.remove(i);
                groups.add(group);
            }
        }
        writeInFileAfterEditing(groups);
    }

    public void removeGroup(Groups group){
        ArrayList<Groups> allGroups = new ArrayList<>();
        allGroups.addAll(getAllGroups());
        ArrayList<Groups> allGroupsAfterRemoving= new ArrayList<>();
        File file = new File("GroupsFile.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

            groupId=0;
            for(int i = 0 ; i< allGroups.size() ; i++){
                if(allGroups.get(i).getId() != group.getId()){
                    groupId++;
                    writer.newLine();
                    writer.write(groupId + "-" + allGroups.get(i).getAdmin().getUsername()+"-"+
                            allGroups.get(i).getGroupName()+"-"+allGroups.get(i).getLink()+"-");
                    if(allGroups.get(i).getMembers().size() > 0){

                        for(int j = 0 ; j < allGroups.get(i).getMembers().size() ; j++){
                            if(j == allGroups.get(i).getMembers().size()-1){
                                writer.write(allGroups.get(i).getMembers().get(j).getUsername());
                            }
                            else
                                writer.write(allGroups.get(i).getMembers().get(j).getUsername()+"/");
                        }
                    }
                }

            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

}
