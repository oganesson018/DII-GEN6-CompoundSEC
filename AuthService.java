public class AuthService {
    private UserDatabase userDB;

    public AuthService(UserDatabase userDB) {
        this.userDB = userDB;
    }

    public boolean register(String username, String email, String password) {
        User newUser = new User(username, email, password);
        return userDB.addUser(newUser);
    }

    public boolean login(String username, String password) {
        User user = userDB.getUser(username);
        return user != null && user.validatePassword(password);
    }
}
