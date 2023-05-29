package Model;

import java.util.regex.Pattern;

public class Regexes {

    public boolean nameRegex( String name){
        return ( Pattern.matches("[a-zA-Z ]+" , name));
    }

    public boolean numberRegex(String number){
        return(number.length()>=8 && Pattern.matches("[a-zA-Z0-9 -]+",number));
    }

    public boolean UserNameRegex(String user){
        return (Pattern.matches("[a-zA-Z]+[0-9_.a-zA-Z]+" , user));
    }

    public boolean EmailRegex(String email){
        return (Pattern.matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$",email ));
    }

    public boolean groupNameRegex(String groupName){
        return (Pattern.matches("^[a-zA-Z0-9]+[a-zA-Z0-9 ]+",groupName));
    }

    public boolean linkRegex(String link){
        return (Pattern.matches("^[@]+[a-zA-Z0-9_.]+",link));
    }


}
