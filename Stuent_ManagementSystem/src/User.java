import java.util.ArrayList;

public class User {
    private String user_name;
    private String user_password;
    private String idnumber;
    private String phonenumber;

    public User() {
    }

    public User(String user_name, String user_password, String idnumber, String phonenumber) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.idnumber = idnumber;
        this.phonenumber = phonenumber;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
