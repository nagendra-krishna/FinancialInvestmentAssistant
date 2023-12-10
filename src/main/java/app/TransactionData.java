package main.java.app;
import javafx.beans.property.*;

public class TransactionData {
    private final IntegerProperty serialNo;
    private final StringProperty transactionType;
    private final StringProperty transactionId;
    private final DoubleProperty amount;
    private final ObjectProperty<java.util.Date> date;
    private final ObjectProperty<java.sql.Time> time;

    public TransactionData(int serialNo, String transactionType, String transactionId,
                           double amount, java.util.Date date, java.sql.Time time) {
        this.serialNo = new SimpleIntegerProperty(serialNo);
        this.transactionType = new SimpleStringProperty(transactionType);
        this.transactionId = new SimpleStringProperty(transactionId);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
    }

    // Getters for properties

    public IntegerProperty serialNoProperty() {
        return serialNo;
    }

    public StringProperty transactionTypeProperty() {
        return transactionType;
    }

    public StringProperty transactionIdProperty() {
        return transactionId;
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public ObjectProperty<java.util.Date> dateProperty() {
        return date;
    }

    public ObjectProperty<java.sql.Time> timeProperty() {
        return time;
    }

    // Getters for values

    public int getSerialNo() {
        return serialNo.get();
    }

    public String getTransactionType() {
        return transactionType.get();
    }

    public String getTransactionId() {
        return transactionId.get();
    }

    public double getAmount() {
        return amount.get();
    }

    public java.util.Date getDate() {
        return date.get();
    }

    public java.sql.Time getTime() {
        return time.get();
    }
}
