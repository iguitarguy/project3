package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;

//@Repository
//@Transactional
@Component
public class ScheduledTasks {


    @Autowired
    private VehicleDao VehicleDao;
    @PersistenceContext
    private EntityManager entityManager;
    private RestTemplate rest = new RestTemplate();
    private String base = "http://localhost:8080/";
    private ObjectMapper om;

    //SQL query to create table
    //create table inventory (id int(11) not null auto_increment, makeModel varchar(30) not null, year int(4) not null, retailPrice double not null, primary key (id));
    @Timed
    @Count
    @Scheduled(cron = "*/2 * * * * *")
    public void addVehicle() throws IOException {
        VehicleDao.create(VehicleDao.createRandomVehicle());
        //Query query = entityManager.createNativeQuery("SELECT * FROM inventory;");
        //System.out.println(om.readValue(query.getResultList(), Vehicle.class));
    }

    @Timed
    @Count
    @Scheduled(cron = "*/5 * * * * *")
    public void deleteVehicle() throws IOException {
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM inventory;");
            ArrayList<Vehicle> vehicleAL = new ArrayList<>(query.getResultList());
            System.out.println(vehicleAL.size() + " vehicles in inventory.");
            if(vehicleAL.size() > 0) {
                try {
                    int random = ThreadLocalRandom.current().nextInt(0, 100);
                    System.out.println("Trying to delete " + random + ".");
                    rest.delete(base + "deleteVehicle/" + random);
                }
                catch (HttpClientErrorException e){
                    System.out.print(e.getResponseBodyAsString() + "\n");
                }
                catch (HttpServerErrorException se)
                {
                    System.out.println("ID is not found for delete.");
                }
            }
        }
        catch (NullPointerException e){
            System.out.println("Query is null for delete.");
            e.printStackTrace();
        }
    }

    @Timed
    @Count
    @Scheduled(cron = "*/7 * * * * *")
    public void updateVehicle() throws IOException {
        Vehicle tmp = VehicleDao.createRandomVehicle();
        tmp.setId(ThreadLocalRandom.current().nextInt(0, 100));
        try {
            rest.put(base + "updateVehicle", tmp, Vehicle.class);
        }
        catch (HttpClientErrorException e){
            System.out.print(e.getResponseBodyAsString() + "\n");
        }
        rest.getForObject(base + "getVehicle/" + tmp.getId(), Vehicle.class);
    }

    @Timed
    @Count
    @Scheduled(cron = "0 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * * *")
    public void getLatestVehicles() throws IOException {
        try {
            rest.getForObject(base + "getLatestVehicles", List.class);
        }
        catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString() + "\n");
        }
    }





}
