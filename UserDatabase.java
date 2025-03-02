import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private List<User> users;

    public UserDatabase() {
        this.users = new ArrayList<>();
    }

    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return false; // Username ซ้ำ
            }
        }
        users.add(user);
        return true;
    }

    public User getUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }
}
