import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        UserDatabase userDB = new UserDatabase();
        AuthService authService = new AuthService(userDB);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Email: ");
                String email = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                if (authService.register(username, email, password)) {
                    System.out.println("Account created successfully!");
                } else {
                    System.out.println("Username already exists!");
                }

            } else if (option == 2) {
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                if (authService.login(username, password)) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid username or password.");
                }

            } else if (option == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}
