package com.example.geektrust;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Borrower {
    private String bankName;
    private String borrowerName;
    private int amount;
    private int totalEMIs;
    private int emiAmount;

    public void setEmiAmount(int emiAmount) {
        this.emiAmount = emiAmount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalEMIs() {
        return totalEMIs;
    }

    public void setTotalEMIs(int totalEMIs) {
        this.totalEMIs = totalEMIs;
    }

    Map<Integer, Integer> amountPaidPerEachEmi = new HashMap<>();

    public int calculateAmountPaidUptoParticularEmi(int emiNo) {
        int emiNoIndicator = amountPaidPerEachEmi.containsKey(0) ? 0 : 1;
        if (emiNo != 0) {
            int MIN_EMI_SIZE = 1;
            if (!amountPaidPerEachEmi.containsKey(MIN_EMI_SIZE)) {
                logAmountForEachEmi(MIN_EMI_SIZE, emiNo);
            } else {
                int maxEmiNoToStartUpdate = Collections.max(amountPaidPerEachEmi.keySet()) + 1;
                logAmountForEachEmi(maxEmiNoToStartUpdate, emiNo);
            }
            return sumPaidAmount(emiNoIndicator, emiNo);

        }
        return amountPaidPerEachEmi.getOrDefault(0, 0);

    }

    public void calculateAmountPaidAfterLumpSumPayment(int emiNo, int lumpSum) {
        if (emiNo == 0)
            amountPaidPerEachEmi.put(0, lumpSum);
        else {
            int tempAmount = amountPaidPerEachEmi.get(emiNo);
            tempAmount += lumpSum;
            amountPaidPerEachEmi.put(emiNo, tempAmount);
        }

    }

    public int remainingEMIs(int emiNo) {
        int tempTotalAmount = amount;
        int emiNoIndicator = amountPaidPerEachEmi.containsKey(0) ? 0 : 1;
        int sumAmountPaid = sumPaidAmount(emiNoIndicator, emiNo);
        return (int) Math.ceil(Double.valueOf((tempTotalAmount) - Double.valueOf(sumAmountPaid)) / emiAmount);

    }

    public int sumPaidAmount(int emiNoIndicator, int emiNo) {
        int tempAmount = 0;
        for (int emiIterator = emiNoIndicator; emiIterator <= emiNo; emiIterator++) {
            tempAmount += amountPaidPerEachEmi.get(emiIterator);
        }
        return tempAmount;
    }

    public void logAmountForEachEmi(int emiNo, int maxEmi) {
        for (int emiIterator = emiNo; emiIterator <= maxEmi; emiIterator++) {
            amountPaidPerEachEmi.put(emiIterator, emiAmount);
        }
    }
}
