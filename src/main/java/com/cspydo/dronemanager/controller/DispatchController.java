package com.cspydo.dronemanager.controller;

import com.cspydo.dronemanager.model.DispatchItem;
import com.cspydo.dronemanager.model.Drone;
import com.cspydo.dronemanager.model.Medication;
import com.cspydo.dronemanager.repository.DispatchItemRepository;
import com.cspydo.dronemanager.repository.DispatchRepository;
import com.cspydo.dronemanager.repository.DroneRepository;
import com.cspydo.dronemanager.repository.MedicationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cspydo.dronemanager.model.Dispatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for Drone.
 */
@RestController("dispatch")
@RequestMapping("/dispatch")
public class DispatchController {
    @Autowired
    DispatchItemRepository dispatchItemRepository;

    @Autowired
    DispatchRepository dispatchRepository;

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @PostMapping("/create")
    public ResponseEntity createDispatch(@RequestBody JsonNode payload) {
        try {
            Dispatch _dispatch= dispatchRepository
                    .save(new Dispatch(payload.get("drone_id").asInt(), "INITIATED"));

            Optional <Drone> drone = droneRepository.findById(payload.get("drone_id").asLong());

            if (drone.isPresent()) {
                Drone _drone = drone.get();
                if(_drone.getBatteryCapacity() < 25){
                    return new ResponseEntity<>("Drone Cannot be loaded, Battery is below 25%", HttpStatus.BAD_REQUEST);
                }

                final JsonNode arrNode = new ObjectMapper().readTree(payload.toString()).get("medications");
                DispatchItem dispatchItems[] = new DispatchItem [arrNode.size()];
                Double totalWeight = (double) 0;
                if (arrNode.isArray()) {
                    for (final JsonNode objNode : arrNode) {
                        long medication_id = Long.parseLong(objNode.toString()); 
                        Optional<Medication> medicationData = medicationRepository.findById(medication_id);

                        if (medicationData.isPresent()) {
                            Medication medication = medicationData.get();
                            totalWeight += medication.getWeight();
                        }
                    }
                }

                if(totalWeight > _drone.getWeightLimit()){
                    return new ResponseEntity<>("Drone Cannot be loaded, Load exceeds Capacity", HttpStatus.BAD_REQUEST);
                }

                _drone.setState("LOADING");
                droneRepository.save(_drone);
            
                if (arrNode.isArray()) {
                    int i=0;
                    for (final JsonNode objNode : arrNode) {
                        long medication_id = Long.parseLong(objNode.toString());       
                        DispatchItem _dispatchItem= dispatchItemRepository
                            .save(new DispatchItem(_dispatch.getId(), medication_id));
                        dispatchItems[i] = _dispatchItem;
                        i++;
                    }
                }

                // Object[]responseObject = {_dispatch,["medications"=>dispatchItems]} ;           
                return new ResponseEntity<>(_dispatch, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>("Drone Cannot be found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
