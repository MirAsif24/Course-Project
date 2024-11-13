
package cse215project;

public class User {
    
    private String username;
    private String password;
    private String usertype;
    private int userID;

    public User(String username, String password, String usertype, int userID) {
        this.username = username;
        this.password = password;
        this.usertype = usertype.toLowerCase();
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsertype() {
        return usertype;
    }

    public int getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nPassword: " + password +
               "\nUserType: " + usertype + "\nID: " + userID + "\n";
    }
}
    

