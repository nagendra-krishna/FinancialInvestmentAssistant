/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.app.model;

// Investment.java
public class Investment {
    private int id;
    private String name;
    private double amount;
    private String sector;

    public Investment() {}
    //this constructor is used to make the and Empty object to hold on to

    
    public Investment(String name, double amount, String sector) {
        this.name = name;
        this.amount = amount;
        this.sector = sector;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}

