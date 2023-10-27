package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Define a Student class
class Student {
    private String name;
    private double gpa;
    private int credits;

    public Student(String name, double gpa, int credits) {
        this.name = name;
        this.gpa = gpa;
        this.credits = credits;
    }


    // Getters for student properties
    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public int getCredits() {
        return credits;
    }
}
class CreditCard {
    private String cardname;
    private String place;
    private double amountspent;
    private String date;

    public CreditCard(String cardname, String place, double amountspent, String date) {
        this.cardname = cardname;
        this.place = place;
        this.amountspent = amountspent;
        this.date = date;
    }


    // Getters for student properties
    public String getCardname() {
        return cardname;
    }

    public String getplace() {
        return place;
    }

    public double getAmountspent() {
        return amountspent;
    }
    public String getDate() {
        return date;
    }
}
class BankingInfo {
    private String bankName;
    private String cardNumber;
    private double amount;
    private String ccv;

    private String exp;

    public BankingInfo(String bankName, String cardNumber, double amount, String ccv, String exp) {
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.ccv = ccv;
        this.exp = exp;
    }


    // Getters for student properties
    public String getBankName() {
        return bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getAmount() {
        return amount;
    }
    public String getCcv() {
        return ccv;
    }

    public String getExp(){return exp;}
}

// Define a Block class
class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private Student student;

    private CreditCard card;

    private BankingInfo bank;

    public Block(int index, String previousHash, Student student, CreditCard card, BankingInfo bank) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.student = student;
        this.card = card;
        this.bank = bank;
        this.hash = calculateHash();

    }


public String calculateHash() {

    try {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        int nonce = 0;
        String input;
        while (true) {
            input = index + timestamp + previousHash + student.getName() + student.getGpa() + student.getCredits() +
                    card.getCardname() + card.getplace() + card.getAmountspent() + card.getDate() + bank.getBankName()+
                    bank.getCardNumber()+bank.getAmount()+bank.getCcv()+bank.getExp()+ nonce;

            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String hash = hexString.toString();
            if (hash.startsWith("000")) {

                return hash;

            }
            nonce++;
        }
    } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
        throw new RuntimeException(e);
    }
}


    // Getters
    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public Student getStudent() {
        return student;
    }
    public CreditCard getCreditCard() {
        return card;
    }

    public BankingInfo getBankingInfo() {
        return bank;
    }
}


// Define a Blockchain class
class Blockchain {
    private List<Block> chain;

    // Constructor
    public Blockchain() {
        chain = new ArrayList<Block>();
        // Create the genesis block (the first block in the chain)
        chain.add(new Block(0, "0", new Student("Genesis Block", 0.0, 0), new CreditCard("Genesis Card", "Genesis", 0.0, "0"), new BankingInfo("Genesis Bank", "0000-0000-0000-0000", 0.0, "000", "0000-00")));
    }
    // Add a new block to the blockchain
    public void addStudent(Student student, CreditCard card, BankingInfo bank) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getIndex() + 1, previousBlock.getHash(), student, card,bank);
        chain.add(newBlock);
    }

    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Student Name: " + block.getStudent().getName());
            System.out.println("Student GPA: " + block.getStudent().getGpa());
            System.out.println("Student Credits: " + block.getStudent().getCredits());
            System.out.println("Credit Card Name: " + block.getCreditCard().getCardname());
            System.out.println("Transaction Place: " + block.getCreditCard().getplace());
            System.out.println("Transaction Amount: " + block.getCreditCard().getAmountspent());
            System.out.println("Transaction Date: " + block.getCreditCard().getDate());
            System.out.println("Bank Name: " + block.getBankingInfo().getBankName());
            System.out.println("Bank Card Number: " + block.getBankingInfo().getCardNumber());
            System.out.println("Bank Amount: " + block.getBankingInfo().getAmount());
            System.out.println("Bank CCV: " + block.getBankingInfo().getCcv());
            System.out.println("Bank Expiration: " + block.getBankingInfo().getExp());
            System.out.println();
            System.out.println();
        }
    }
}

public class SimpleBlockchain {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        DatabaseManager dbManager = new DatabaseManager();
        // Create student objects and add them to the blockchain
        Student student1 = new Student("Joe Oakes", 2.5, 104);
        Student student2 = new Student("Jane Oakes", 3.2, 89);
        Student student3 = new Student("James Oakes", 1.5, 104);
        CreditCard cardTransaction1 = new CreditCard("Joe Oakes Card", "Wendys", 100.0, "2023-10-26");
        CreditCard cardTransaction2 = new CreditCard("Jane Oakes Card", "Chic-Fil-A", 75.0, "2023-10-27");
        CreditCard cardTransaction3 = new CreditCard("James Oakes Card", "Panda Express", 50.0, "2023-10-28");
        BankingInfo bankInfo1 = new BankingInfo("Santander", "3456-5678-9052-3656", 5000.0, "433", "12/25");
        BankingInfo bankInfo2 = new BankingInfo("Citizens", "9876-5432-2109-8765", 3000.0, "426", "08/24");
        BankingInfo bankInfo3 = new BankingInfo("Discover", "2468-1357-8024-6813", 2000.0, "759", "05/27");

        blockchain.addStudent(student1,cardTransaction1,bankInfo1);
        blockchain.addStudent(student2,cardTransaction2,bankInfo2);
        blockchain.addStudent(student3, cardTransaction3,bankInfo3);

        // Print the blockchain
        blockchain.printBlockchain();




        dbManager.addStudent(student1);
        dbManager.addStudent(student2);
        dbManager.addStudent(student3);



        dbManager.close();
    }
}