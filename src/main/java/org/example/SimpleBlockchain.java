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

// Define a Block class
class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private Student student;

    public Block(int index, String previousHash, Student student) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.student = student;
        this.hash = calculateHash();
    }


public String calculateHash() {

    try {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        int nonce = 0;
        String input;
        while (true) {
            input = index + timestamp + previousHash + student.getName() + student.getGpa() + student.getCredits() + nonce;
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
//    while (true) {
//        input = index + timestamp + previousHash + student.getName() + student.getGpa() + student.getCredits() + nonce;
//        byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
//        StringBuilder hexString =
//
//    func (b*Block) calculateHash(diff string) string {
//        nonce := 0
//        for {
//            record :=fmt.Sprintf("#{b.Index}-#{b.Timestamp-#{b.Data}#{b.PrevHash)-#{nonce}")
//              h := sha256.New()
//              h.Write([]byte(record))
//              hashed := h.Sum(nil)
//              shash := fmt.Sprintf("#{hashed}")
//              if string.HasPrefix(shash, diff): fmt.Sprintf("%X", hashed)
//    nonce++
//        }
//    }

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
}

// Define a Blockchain class
class Blockchain {
    private List<Block> chain;

    // Constructor
    public Blockchain() {
        chain = new ArrayList<Block>();
        // Create the genesis block (the first block in the chain)
        chain.add(new Block(0, "0", new Student("Genesis Block", 0.0, 0)));
    }

    // Add a new block to the blockchain
    public void addStudent(Student student) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getIndex() + 1, previousBlock.getHash(), student);
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

        blockchain.addStudent(student1);
        blockchain.addStudent(student2);
        blockchain.addStudent(student3);

        // Print the blockchain
        blockchain.printBlockchain();




        dbManager.addStudent(student1);
        dbManager.addStudent(student2);
        dbManager.addStudent(student3);



        dbManager.close();
    }
}