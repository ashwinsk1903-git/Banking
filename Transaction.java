import java.util.*;
import java.time.LocalDateTime; // Assuming you have a timeStamp class to handle timestamps for transactions
class Transaction implements Comparable<Transaction>{
    String TransactionID;
    String FromAccount;
    String ToAccount;
    double AmountToBeTransferred;
    LocalDateTime timestamp;
    public Transaction(String FromAccount, String ToAccount, double AmountToBeTransferred){
        this.TransactionID=java.util.UUID.randomUUID().toString();
        this.FromAccount=FromAccount;
        this.ToAccount=ToAccount;
        this.AmountToBeTransferred=AmountToBeTransferred;
        this.timestamp = LocalDateTime.now();
    }
    public String getTransactionID(){
        return TransactionID;
    }
    public String getFromAccount(){
        return FromAccount;
    }
    public String getToAccount(){
        return ToAccount;
    }
    public double getAmountToBeTransferred(){
        return AmountToBeTransferred;
    }
    public LocalDateTime getTimeStamp(){
        return timestamp;
    }

    @Override
    public int compareTo(Transaction other) {
        if (other == null) {
            return 1;
        }
        return this.timestamp.compareTo(other.timestamp);
    }

}