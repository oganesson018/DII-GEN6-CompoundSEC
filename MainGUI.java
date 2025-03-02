import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    String username;
    String password;
    String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}

public class MainGUI extends JFrame {
    private Map<String, User> users;
    private List<String> loggedInUsers;
    private List<String> adminIds;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainGUI() {
        this.users = new HashMap<>();
        this.loggedInUsers = new ArrayList<>();
        this.adminIds = new ArrayList<>();
        this.adminIds.add("admin123"); // Add initial admin

        setTitle("Security System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        JPanel loginPanel = createLoginPanel();
        JPanel createAccountPanel = createCreateAccountPanel();
        JPanel adminPanel = createAdminPanel();

        // Add panels to main panel
        mainPanel.add(loginPanel, "login");
        mainPanel.add(createAccountPanel, "createAccount");
        mainPanel.add(adminPanel, "admin");

        add(mainPanel);
        cardLayout.show(mainPanel, "login"); // Show login panel initially
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String result = login(username, password);
                JOptionPane.showMessageDialog(MainGUI.this, result);
                if (isAdmin(username)) {
                    cardLayout.show(mainPanel, "admin");
                }
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "createAccount");
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(createAccountButton);

        return panel;
    }

    private JPanel createCreateAccountPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JButton createButton = new JButton("Create");
        JButton backButton = new JButton("Back");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String result = createAccount(username, password, email);
                JOptionPane.showMessageDialog(MainGUI.this, result);
                cardLayout.show(mainPanel, "login");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "login");
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(createButton);
        panel.add(backButton);

        return panel;
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JButton viewUsersButton = new JButton("View Logged-in Users");
        JButton addUserButton = new JButton("Add User");
        JButton removeUserButton = new JButton("Remove User");
        JButton logoutButton = new JButton("Logout");

        viewUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> users = viewLoggedInUsers("admin123"); // Assuming admin ID is "admin123"
                if (users != null) {
                    JOptionPane.showMessageDialog(MainGUI.this, users.toString());
                } else {
                    JOptionPane.showMessageDialog(MainGUI.this, "Access denied.");
                }
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(MainGUI.this, "Username:");
                String password = JOptionPane.showInputDialog(MainGUI.this, "Password:");
                String email = JOptionPane.showInputDialog(MainGUI.this, "Email:");
                String result = addUser("admin123", username, password, email);
                JOptionPane.showMessageDialog(MainGUI.this, result);
            }
        });

        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(MainGUI.this, "Username to remove:");
                String result = removeUser("admin123", username);
                JOptionPane.showMessageDialog(MainGUI.this, result);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "login");
            }
        });

        panel.add(viewUsersButton);
        panel.add(addUserButton);
        panel.add(removeUserButton);
        panel.add(logoutButton);

        return panel;
    }

    public String createAccount(String username, String password, String email) {
        if (users.containsKey(username)) {
            return "Username already exists.";
        }
        users.put(username, new User(username, password, email));
        return "Account created successfully.";
    }

    public String login(String username, String password) {
        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            loggedInUsers.add(username);
            return "Login successful.";
        }
        return "Invalid username or password.";
    }

    public boolean isAdmin(String adminId) {
        return adminIds.contains(adminId);
    }

    public List<String> viewLoggedInUsers(String adminId) {
        if (!isAdmin(adminId)) {
            return null; // Access denied
        }
        return loggedInUsers;
    }

    public String addUser(String adminId, String username, String password, String email) {
        if (!isAdmin(adminId)) {
            return "Access denied. Admin only.";
        }
        if (users.containsKey(username)) {
            return "Username already exists.";
        }
        users.put(username, new User(username, password, email));
        return "User added successfully.";
    }

    public String removeUser(String adminId, String username) {
        if (!isAdmin(adminId)) {
            return "Access denied. Admin only.";
        }
        if (!users.containsKey(username)) {
            return "User not found.";
        }
        users.remove(username);
        loggedInUsers.remove(username);
        return "User removed successfully.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }
}