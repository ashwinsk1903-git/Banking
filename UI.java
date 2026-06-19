import java.util.*;

public class UI{
    public static void main(String[] args){
        AuditLogger auditLogger = new AuditLogger("audit.log");
        Thread auditThread = new Thread(auditLogger, "AuditLoggerThread");
        auditThread.setDaemon(true);
        auditThread.start();

        auditLogger.log("Application started");
        System.out.println("Welcome to the Banking System!");
        System.out.println("Please select an option:");
        System.out.println("LOGIN");
        System.out.println("REGISTER");
        System.out.println("EXIT");
        Scanner scanner=new Scanner(System.in);
        HashMap<String, String> users=new HashMap<>(); // This will store username and password pairs
        while(true){
            String option=scanner.nextLine();
            auditLogger.log("User selected option: " + option);
            if(option.equalsIgnoreCase("LOGIN")){
                System.out.println("Please enter your username:");
                String username=scanner.nextLine();
                System.out.println("Please enter your password:");
                String password=scanner.nextLine();
                auditLogger.log("Login attempt for username: " + username);
                if(users.containsKey(username)){
                    if(users.get(username).equals(password)){
                        System.out.println("Login successful! Welcome, " + username + "!");
                        auditLogger.log("Login successful for username: " + username);
                        Bank.main(null); // Call the main method of the Bank class to proceed with banking operations
                    }else{
                        System.out.println("Invalid password.");
                        auditLogger.log("Invalid password for username: " + username);
                    }
                }
                else{
                    System.out.println("Username not found. Register your account first.");
                    auditLogger.log("Login failed, username not found: " + username);
                }
            }else if(option.equalsIgnoreCase("REGISTER")){
                System.out.println("Please enter your desired username:");
                String newUsername=scanner.nextLine();
                System.out.println("Please enter your desired password:");
                String newPassword=scanner.nextLine();
                users.put(newUsername, newPassword);
                System.out.println("Registration successful! You can now log in with your new credentials.");
                auditLogger.log("New user registered: " + newUsername);
            }else if(option.equalsIgnoreCase("EXIT")){
                System.out.println("Thank you for using the Banking System. Goodbye!");
                auditLogger.log("Application exiting");
                break;
            }else{
                System.out.println("Invalid option. Please try again.");
                auditLogger.log("Invalid option entered: " + option);
            }
        }

        auditLogger.stop();
        try {
            auditThread.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}