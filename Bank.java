import java.util.*;
public class Bank{
    public static void main(String[] args){
        System.out.println("PLease Select an option:");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Get Balance");
        System.out.println("6. Get the top 10 Bank Transactions");
        System.out.println("7. Find the debit and credit transactions of a particular account");
        System.out.println("8. Find the net profit or loss of the particular account");
        System.out.println("9. Exit");
        HashMap<String, Account> accounts=new HashMap<>(); // This will store account number and account object pairs
        TreeSet<Transaction> transactions=new TreeSet<>(); // This will store all transactions
        Scanner scanner=new Scanner(System.in);
        while(true){
            int option=scanner.nextInt();
            scanner.nextLine();
            switch(option){
                case 1:
                    System.out.println("Please enter your name:");
                    String name=scanner.nextLine();
                    Account newAccount=new Account(name);
                    accounts.put(newAccount.getAccountNumber(), newAccount);
                    System.out.println("Account created successfully! Your account number is: " + newAccount.getAccountNumber());
                    break;
            case 2:
                System.out.println("Please enter your account number:");
                String accountNumber=scanner.nextLine();
                if(accounts.containsKey(accountNumber)){
                    System.out.println("Please enter the amount to deposit:");
                    double amount=scanner.nextDouble();
                    accounts.get(accountNumber).deposit(amount);
                }
                else{
                    System.out.println("Account not found.");
                }
                break;
            case 3:
                System.out.println("Please enter your account number:");
                accountNumber=scanner.nextLine();
                if(accounts.containsKey(accountNumber)){
                    System.out.println("Please enter the amount to withdraw:");
                    double amount=scanner.nextDouble();
                    accounts.get(accountNumber).withdraw(amount);
                }
                else{
                    System.out.println("Account not found.");
                } 
                break;
            case 4:
                System.out.println("Please enter your account number:");
                String fromAccount=scanner.nextLine();
                if(accounts.containsKey(fromAccount)){
                    System.out.println("Please enter the recipient's account number:");
                    String toAccount=scanner.nextLine();
                    if(accounts.containsKey(toAccount)){
                        System.out.println("Please enter the amount to transfer:");
                        double amount=scanner.nextDouble();
                        if(accounts.get(fromAccount).getBalance()>=amount){
                            accounts.get(fromAccount).withdraw(amount);
                            accounts.get(toAccount).deposit(amount);
                            Transaction transaction=new Transaction(fromAccount, toAccount, amount);
                            transactions.add(transaction);
                            System.out.println("Transfer successful! Transaction ID: " + transaction.getTransactionID());
                        }else{
                            System.out.println("Insufficient funds.");
                        }
                    }else{
                        System.out.println("Recipient account not found.");
                    }
                }
                else{
                    System.out.println("Sender account not found.");
                }
            case 5:
                System.out.println("Please enter your account number:");
                accountNumber=scanner.nextLine();
                if(accounts.containsKey(accountNumber)){
                    System.out.println("Current balance: " + accounts.get(accountNumber).getBalance());
                }
                else{
                    System.out.println("Account not found.");
                }
                break;
            case 6:
                System.out.println("Top 10 Bank Transactions:");
                transactions.stream().sorted(Comparator.comparing(Transaction::getTimeStamp).reversed()).limit(10).forEach(transaction -> {
                    System.out.println("Transaction ID: " + transaction.getTransactionID());
                    System.out.println("From Account: " + transaction.getFromAccount());
                    System.out.println("To Account: " + transaction.getToAccount());
                    System.out.println("Amount Transferred: " + transaction.getAmountToBeTransferred());
                    System.out.println("Timestamp: " + transaction.getTimeStamp());
                    System.out.println("-----------------------------------");
                });

            case 7:
                System.out.println("Please enter your account number:");
                System.out.println("The debit transactions are:");
                accountNumber=scanner.nextLine();
                if(accounts.containsKey(accountNumber)){
                    transactions.stream().filter(transaction-> transaction.getFromAccount().equals(accountNumber)).forEach(transaction -> {
                        System.out.println("Transaction ID: " + transaction.getTransactionID());
                        System.out.println("From Account: " + transaction.getFromAccount());
                        System.out.println("To Account: " + transaction.getToAccount());
                        System.out.println("Amount Transferred: " + transaction.getAmountToBeTransferred());
                        System.out.println("Timestamp: " + transaction.getTimeStamp());
                        System.out.println("-----------------------------------");
                    });
                } else {
                    System.out.println("Account not found.");
                }
                System.out.println("The credit transactions are:");
                if(accounts.containsKey(accountNumber)){
                    transactions.stream().filter(transaction-> transaction.getToAccount().equals(accountNumber)).forEach(transaction -> {
                        System.out.println("Transaction ID: " + transaction.getTransactionID());
                        System.out.println("From Account: " + transaction.getFromAccount());
                        System.out.println("To Account: " + transaction.getToAccount());
                        System.out.println("Amount Transferred: " + transaction.getAmountToBeTransferred());
                        System.out.println("Timestamp: " + transaction.getTimeStamp());
                        System.out.println("-----------------------------------");
                    });
                } else {
                    System.out.println("Account not found.");
                }

            case 8:
                System.out.println("Please enter your account number:");
                String account8=scanner.nextLine();
                if(accounts.containsKey(account8)){
                    double totalDeposits=transactions.stream().filter(transaction-> transaction.getToAccount().equals(account8)).mapToDouble(Transaction::getAmountToBeTransferred).sum();
                    double totalWithdrawals=transactions.stream().filter(transaction-> transaction.getFromAccount().equals(account8)).
                    mapToDouble(Transaction::getAmountToBeTransferred).sum();
                    double netProfitOrLoss=totalDeposits-totalWithdrawals;
                    System.out.println("Net Profit or Loss for Account " + account8 + ": " + netProfitOrLoss);
                } else {
                    System.out.println("Account not found.");
                }
            case 9:
                System.out.println("Thank you for using the Banking System. Goodbye!");
                break;
            default:
                System.out.println("Invalid option. Please try again.");    
    }
        }
} 
}