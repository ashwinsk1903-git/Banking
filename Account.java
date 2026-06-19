class Account{
    private String AccountNumber;
    private String name;
    private double balance;
    public Account(String name){
        this.AccountNumber=java.util.UUID.randomUUID().toString(); 
        this.name = name;
        this.balance = 0.0;
    }
    public String getName(){
        return name;
    }
    public double getBalance(){
        return balance;
    } 
    public String getAccountNumber(){
        return AccountNumber;
    }
    public void deposit(double amount){
        if(amount>0){
            balance+=amount;
            System.out.println("Deposit successful. Current balance: "+balance);
        }else{
            System.out.println("Invalid deposit amount.");
        }
    } 
    public void withdraw(double amount){
        if(amount>0 && amount<=balance){
            balance-=amount;
            System.out.println("Withdrawal successful. Current balance: "+balance);
        }else{
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    } 

}