package main.java.app;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Investment {
    private Integer serial_no;
    private String transaction_type;
    private String transaction_amount;
    private Date transaction_date;
    private int units;

    public Investment(int serial_no, String transaction_type, String transaction_amount, Date transaction_date) {
        this.serial_no = serial_no;
        this.transaction_type = transaction_type;
        this.transaction_amount = transaction_amount;
        this.transaction_date = transaction_date;
    }

    public int getSerialNo() {
        return serial_no;
    }
    
    public int getUnits() {
        return units;
    }

    public String getTransactionType() {
        return transaction_type;
    }

    public String getTransactionAmount() {
        return transaction_amount;
    }

    public Date getTransactionDate() {
        return transaction_date;
    }

    // New method for calculating present value
    public double calculatePresentValue() {
        double presentValue = 0;

        switch (transaction_type.toLowerCase()) {
            case "stocks":
                // Assuming stocks grow at a random rate (e.g., 8% annually)
                double stockGrowthRate = 8;
                int investmentPeriodInMonths = (int) Math.floor(ChronoUnit.MONTHS.between(transaction_date.toLocalDate(), LocalDate.now()));
                presentValue = Double.parseDouble(transaction_amount) * Math.pow(1 + stockGrowthRate / 12, investmentPeriodInMonths);
                break;
            case "bonds":
                // Assuming bonds have a fixed interest rate (e.g., 5% annually)
                double bondInterestRate = 0.05;
                int bondInvestmentPeriodInMonths = (int) Math.floor(ChronoUnit.MONTHS.between(transaction_date.toLocalDate(), LocalDate.now()));
                presentValue = Double.parseDouble(transaction_amount) * Math.pow(1 + bondInterestRate / 12, bondInvestmentPeriodInMonths);
                break;
            case "real-estate":
                // Assuming real estate appreciates at a random rate (e.g., 3% annually)
                double realEstateAppreciationRate = 0.03;
                int realEstateInvestmentPeriodInMonths = (int) Math.floor(ChronoUnit.MONTHS.between(transaction_date.toLocalDate(), LocalDate.now()));
                presentValue = Double.parseDouble(transaction_amount) * Math.pow(1 + realEstateAppreciationRate / 12, realEstateInvestmentPeriodInMonths);
                break;
            case "cash":
                // Assuming cash has no growth or interest
                presentValue = Double.parseDouble(transaction_amount);
                break;
            case "mutual-funds":
                // Assuming mutual funds have a random growth rate (e.g., 10% annually)
                double mutualFundGrowthRate = 0.10;
                int mutualFundInvestmentPeriodInMonths = (int) Math.floor(ChronoUnit.MONTHS.between(transaction_date.toLocalDate(), LocalDate.now()));
                presentValue = Double.parseDouble(transaction_amount) * Math.pow(1 + mutualFundGrowthRate / 12, mutualFundInvestmentPeriodInMonths);
                break;
            case "fixed-deposit":
                // Assuming fixed deposits have a fixed interest rate (e.g., 4% annually)
                double fixedDepositInterestRate = 0.04;
                int fixedDepositInvestmentPeriodInMonths = (int) Math.floor(ChronoUnit.MONTHS.between(transaction_date.toLocalDate(), LocalDate.now()));
                presentValue = Double.parseDouble(transaction_amount) * Math.pow(1 + fixedDepositInterestRate / 12, fixedDepositInvestmentPeriodInMonths);
                break;
            case "commodities":
                // Assuming commodities have a random growth rate (e.g., 6% annually)
                double commoditiesGrowthRate = 0.06;
                int commoditiesInvestmentPeriodInMonths = (int) Math.floor(ChronoUnit.MONTHS.between(transaction_date.toLocalDate(), LocalDate.now()));
                presentValue = Double.parseDouble(transaction_amount) * Math.pow(1 + commoditiesGrowthRate / 12, commoditiesInvestmentPeriodInMonths);
                break;
            default:
                // Handle unknown investment types or return 0 if no calculation is applicable
                break;
        }

        return presentValue;
    }
}

