package com.example.geektrust;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> input = new ArrayList<>();
        Map<String, Borrower> borrowers = new HashMap<>();
        String filePath = args[0];
        InputStreamReader reader = new FileReader(filePath);
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {
            String read = scanner.nextLine();
            if (read == null || read.isEmpty()) {
                break;
            }
            input.add(read);
        }
        for (String command : input) {
            String firstWord = command.split(" ")[0];
            String borrowerBankName = command.split(" ")[1];
            String borrowerName = command.split(" ")[2];

            if (firstWord.equals("LOAN")) {
                int principalAmount = Integer.parseInt(command.split(" ")[3]);
                int noOfYears = Integer.parseInt(command.split(" ")[4]);
                int rateOfInterest = Integer.parseInt(command.split(" ")[5]);
                final int MONTHS_IN_YEAR = 12;
                Borrower borrower = new Borrower();
                borrowers.put(borrowerName + " " + borrowerBankName, borrower);
                borrower.setBankName(borrowerBankName);
                borrower.setBorrowerName(borrowerName);
                int interest = (principalAmount * noOfYears * rateOfInterest) / 100;
                borrower.setAmount(interest + principalAmount);
                borrower.setTotalEMIs(noOfYears * MONTHS_IN_YEAR);
                borrower.setEmiAmount((int) (Math.ceil(Double.valueOf(borrower.getAmount()) / Double.valueOf(borrower.getTotalEMIs()))));
            }

            if (firstWord.equals("BALANCE")) {
                int emiNo = Integer.parseInt(command.split(" ")[3]);
                Borrower b = borrowers.get(borrowerName + " " + borrowerBankName);
                System.out.println(b.getBankName() + " " + b.getBorrowerName() + " " + b.calculateAmountPaidUptoParticularEmi(emiNo) + " " + b.remainingEMIs(emiNo));
            }

            if (firstWord.equals("PAYMENT")) {
                int emiNo = Integer.parseInt(command.split(" ")[3]);
                int amountToBePaid = Integer.parseInt(command.split(" ")[4]);
                Borrower b = borrowers.get(borrowerName + " " + borrowerBankName);
                b.calculateAmountPaidUptoParticularEmi(amountToBePaid);
                b.calculateAmountPaidAfterLumpSumPayment(amountToBePaid, emiNo);
                b.calculateAmountPaidUptoParticularEmi(amountToBePaid);
            }
        }
    }
}
