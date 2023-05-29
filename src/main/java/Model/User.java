package Model;

public class User {
    private String name;
    private String Lastname;
    private String username;
    private String password;
    private String Email;
    private String code;
    private int id;
    public User (String name, String familyName, String username, String password,String Email) {
        this.name = name;
        this.Lastname = familyName;
        this.username = username;
        this.password = password;
        this.Email =    Email;
    }
    public User(int id,String name , String family, String username , String password,String email){
        this.id=id;
        this.name=name;
        this.Lastname=family;
        this.username = username;
        this.Email = email;
        this.password = password;
    }

    public String getName ( ) {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }
    public String getFamilyName ( ) {
        return Lastname;
    }
    public void setFamilyName (String familyName) {
        this.Lastname = familyName;
    }
    public String getUsername ( ) {
        return username;
    }
    public void setUsername (String username) {
        this.username = username;
    }
    public String getPassword ( ) {
        return password;
    }
    public void setPassword (String password) {
        this.password = password;
    }
    public String getEmail() {return Email;}
    public void setEmail(String email) {this.Email = email;}
    public String getCode() {return code;}
    public void setCode(String code) {this.code = code;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
}