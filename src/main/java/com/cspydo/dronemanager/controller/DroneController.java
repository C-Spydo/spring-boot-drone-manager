package com.cspydo.dronemanager.controller;

import com.cspydo.dronemanager.repository.DroneRepository;
import com.cspydo.dronemanager.model.Dispatch;
import com.cspydo.dronemanager.model.DispatchItem;
import com.cspydo.dronemanager.model.Drone;
import com.cspydo.dronemanager.model.Medication;
import com.cspydo.dronemanager.repository.DispatchItemRepository;
import com.cspydo.dronemanager.repository.DispatchRepository;
import com.cspydo.dronemanager.repository.MedicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Controller for Drone.
 */
@RestController("drone")
@RequestMapping("/drone")
public class DroneController {

    Logger logger = LoggerFactory.getLogger(DroneController.class);

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    DispatchRepository dispatchRepository;

    @Autowired
    DispatchItemRepository dispatchItemRepository;

    @Autowired
    MedicationRepository  medicationRepository;

    /**
     * Register a Drone
     */
    @PostMapping("/register")
    public ResponseEntity<Drone> registerDrone(@RequestBody @Valid Drone drone) {
        try {
            Drone _drone= droneRepository
                    .save(new Drone(drone.getSerialNumber(), drone.getModel(), drone.getWeightLimit(), drone.getBatteryCapacity(), "IDLE"));
            return new ResponseEntity<>(_drone, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * List all Drones
     */
    @GetMapping("/all")
	public ResponseEntity<List<Drone>> getAllDrones() {
		try {
			List<Drone> drones = new ArrayList<Drone>();
				droneRepository.findAll().forEach(drones::add);

			if (drones.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(drones, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    /**
     * Check Drone Battery Level
     */
    @GetMapping("battery-level/{droneId}")
    public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable long droneId){
        try {
            int battery_level = droneRepository.getBatteryLevel(droneId);
            return new ResponseEntity<>(battery_level, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Current Drone Load
     */
    @GetMapping("currentload/{droneId}")
    public ResponseEntity getCurrentLoad(@PathVariable int droneId){
        try {
            Dispatch _dispatch = dispatchRepository.getDispatchOnDrone(droneId);
            if(_dispatch != null){
                List<DispatchItem> dispatchItems = new ArrayList<DispatchItem>();
                dispatchItemRepository.getDispatchItems(_dispatch.getId()).forEach(dispatchItems::add);
                Medication medications[] = new Medication[dispatchItems.size()];
                int i=0;
                for(DispatchItem d : dispatchItems){
                    Optional<Medication> medicationData = medicationRepository.findById(d.getMedication());

                    if (medicationData.isPresent()) {
                        Medication medication = medicationData.get();
                        medications[i] = medication;
                        i++;
                    }
                    
                }
                return new ResponseEntity<>(medications, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Drone is not Loaded", HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Check Drone Battery Level
     */
    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones(){
        try {
            List<Drone> drones = droneRepository.getAvailable("IDLE");
            if (drones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(drones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Scheduled drone battery level checker
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void checkDroneBattery() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        List<Drone> drones = new ArrayList<Drone>();
        droneRepository.findAll().forEach(drones::add);
        if (!drones.isEmpty()) {
            for (Drone drone : drones) {
                logger.info("Drone " + drone.getId() + "Battery Level at " +dateFormat.format(new Date()) + "::", drone.getBatteryCapacity()  );
            }
        }
    }

    /**
     * ValidationHandler
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
