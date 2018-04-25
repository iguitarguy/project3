package com.example.demo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Data Access Object - provide some specific data operations without exposing details of the database
 * Access data for the Vehicle entity.
 * Repository annotation allows Spring to find and configure the DAO.
 * Transactional annonation will cause Spring to call begin() and commit()
 * at the start/end of the method. If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class VehicleDao {
    private ArrayList<String> models = new ArrayList<>(Arrays.asList("Toyota Camry","Toyota Corolla",
            "Honda Civic","Honda Accord","Ford Mustang","Chevrolet Camaro","Dodge Charger","Dodge Challenger","BMW 3 Series","BMW 5 Series"));


    //PersistenceContext annotation used to specify there is a database source.
    //EntityManager is used to create and remove persistent entity instances,
    //to find entities by their primary key, and to query over entities.
    @PersistenceContext
    private EntityManager entityManager;

    public Vehicle createRandomVehicle() throws IOException {

        ArrayList<Vehicle> vehicleAL = new ArrayList<>();
        String makeModel = models.get(ThreadLocalRandom.current().nextInt(models.size()));
        int year = ThreadLocalRandom.current().nextInt(1986, 2017);
        double retailPrice = Math.round(ThreadLocalRandom.current().nextDouble(15000.0, 45000.0)*100.0)/100.0;
        System.out.println(new Vehicle(makeModel, year, retailPrice));
        return new Vehicle(makeModel, year, retailPrice);
    }

    //Insert greeting into the database.
    public void create(Vehicle vehicle) {
        if (entityManager.find(Vehicle.class, vehicle.getId()) == null) {
            entityManager.persist(vehicle);
            return;
        }
        else
        {
            throw new IllegalArgumentException("ID already exists");
        }

    }


    //Get Latest Vehicles
    public ArrayList<Vehicle> getLatest() {
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT * FROM inventory ORDER BY id DESC LIMIT 10) sub ORDER BY id ASC ;");
        ArrayList<Vehicle> vehicleAl = new ArrayList<>(query.getResultList());
        //System.out.println(vehicleAl.);
        return vehicleAl;
    }

    //Update greeting into the database;
    public Vehicle update(Vehicle vehicle) {
        if (entityManager.find(Vehicle.class, vehicle.getId()) != null) {
            Vehicle temp = entityManager.find(Vehicle.class, vehicle.getId());
            temp.setMakeModel(vehicle.getMakeModel());
            temp.setYear(vehicle.getYear());
            temp.setRetailPrice(vehicle.getRetailPrice());
            return entityManager.merge(temp);
        }
        else {
            System.out.println("Unknown ID for update: " + vehicle.getId() + ".");
            return vehicle;
        }
    }

    //Delete greeting from the database;
    public String delete(int id) {
        if (entityManager.find(Vehicle.class, id) != null) {
            Vehicle temp = entityManager.find(Vehicle.class, id);
            entityManager.remove(temp);
            return "Removed Vehicle " + id + " successfully.";
        }
        else {
            return "Delete method: cannot find" + id + ".";
            //throw new IllegalArgumentException("Unknown Vehicle id");
        }
    }

    //Return the greeting with the passed-in id.
    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    /**
     * Delete the user from the database.
     */
    /*
    public void delete(Vehicle greeting) {
        if (entityManager.contains(greeting))
            entityManager.remove(greeting);
        else
            entityManager.remove(entityManager.merge(greeting));
        return;
    }
    */

    /**
     * Return all the users stored in the database.
     */
    /*
    @SuppressWarnings("unchecked")
    public List<Vehicle> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }
    */


    /**
     * Return the user having the passed email.
     */
    /*
    public Vehicle getByEmail(String email) {
        return (Vehicle) entityManager.createQuery(
                "from User where email = :email")
                .setParameter("email", email)
                .getSingleResult();
    }
    */

    /**
     * Update the passed user in the database.
     */
    /*
    public void update(Vehicle student) {
        entityManager.merge(student);
        return;
    }
    */

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------


} // class UserDao
