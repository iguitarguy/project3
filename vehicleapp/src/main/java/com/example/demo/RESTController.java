package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RESTController {

    private static File file1 = new File("./inventory.txt");

    private int count = 1;

    @Autowired
    private VehicleDao VehicleDao;

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle createVehicle(@RequestBody Vehicle v) throws IOException {
        VehicleDao.create(v);
        return v;
    }

    @RequestMapping(value = "/getLatestVehicles", method = RequestMethod.GET)
    public List getLatestVehicles() throws IOException {
       return VehicleDao.getLatest();
    }

    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        return VehicleDao.getById(id);
    }

    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        return VehicleDao.update(newVehicle);
    }

    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public String deleteVehicle(@PathVariable("id") int id) throws IOException {
        return VehicleDao.delete(id);
    }


}
