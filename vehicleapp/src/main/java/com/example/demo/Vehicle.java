package com.example.demo;

import javax.persistence.*;

/**
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 */
@Entity //(name = "inventory")
@Table(name = "inventory")
//@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String makeModel;
    private int year;
    private double retailPrice;

    public Vehicle() {

    }

    public Vehicle(int id, String makeModel, int year, double retailPrice){
        this.id = id;
        this.makeModel = makeModel;
        this.year = year;
        this.retailPrice = retailPrice;
    }

    public Vehicle(String makeModel, int year, double retailPrice){
        this.makeModel = makeModel;
        this.year = year;
        this.retailPrice = retailPrice;
    }

    public String toString() {
        return this.getId() + ", " + this.makeModel + ", Year: " + this.year + ", Price: $" + this.retailPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){ return id; }

    public String getMakeModel () { return makeModel;}

    public void setMakeModel (String makeModel) { this.makeModel = makeModel;}

    public int getYear() { return year; }

    public void setYear(int year) { this.year = year; }

    public double getRetailPrice() { return retailPrice; }

    public void setRetailPrice(double retailPrice) { this.retailPrice = retailPrice; }
}
