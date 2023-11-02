package com.truckcompany.example.TruckCompany.Controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.truckcompany.example.TruckCompany.DataAbstraction.ITruckPartInventoryService;
import com.truckcompany.example.TruckCompany.DataAbstraction.MyException;
import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;

@RestController
@RequestMapping("/truckpartinventory")
public class TruckPartInventoryController {
    private final ITruckPartInventoryService truckPartInventoryService;

    public TruckPartInventoryController(ITruckPartInventoryService truckPartInventoryService) {
        this.truckPartInventoryService = truckPartInventoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckPartInventory> get(@PathVariable String id) {
        try {
            if (id == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ObjectId objectId = new ObjectId(id);
            TruckPartInventory truckPartInventory = truckPartInventoryService.get(objectId.toHexString());
            if (truckPartInventory == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(truckPartInventory, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody TruckPartInventory truckPartInventory) {
        try {
            if (truckPartInventory == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            truckPartInventoryService.insert(truckPartInventory);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody TruckPartInventory truckPartInventory) {
        try {
            if (id == null || truckPartInventory == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ObjectId objectId = new ObjectId(id);
            TruckPartInventory existingTruckPartInventory = truckPartInventoryService.get(objectId.toHexString());
            if (existingTruckPartInventory == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            truckPartInventory.setId(objectId.toHexString());
            truckPartInventoryService.update(truckPartInventory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            if (id == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ObjectId objectId = new ObjectId(id);
            TruckPartInventory existingTruckPartInventory = truckPartInventoryService.get(objectId.toHexString());
            if (existingTruckPartInventory == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            truckPartInventoryService.delete(objectId.toHexString());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<TruckPartInventory>> getAll() {
        try {
            List<TruckPartInventory> truckPartInventories = truckPartInventoryService.getAll();
            if (truckPartInventories == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(truckPartInventories, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{categoryId}/truck/{truckId}")
    public ResponseEntity<List<TruckPartInventory>> getByCategoryIdAndTruckId(@PathVariable String categoryId,
            @PathVariable String truckId) {
        try {
            if (categoryId == null || truckId == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<TruckPartInventory> truckPartInventories = truckPartInventoryService
                    .GetTruckPartInventoryByTruckPartId(categoryId, truckId);
            if (truckPartInventories == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(truckPartInventories, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

/**
 * TruckPartInventoryController is a REST controller that handles HTTP requests related to TruckPartInventory entities.
 * It uses the ITruckPartInventoryService to perform operations on the TruckPartInventory entities.
 * 
 * The class has the following methods:
 * 
 * - get(String id): Retrieves a TruckPartInventory by its id. Returns 404 if the TruckPartInventory is not found, 400 if the id is not a valid ObjectId, and 200 along with the TruckPartInventory if it is found.
 * 
 * - insert(TruckPartInventory truckPartInventory): Inserts a new TruckPartInventory. The request body should contain the details of the TruckPartInventory to be inserted. Returns 201 if the insertion is successful, and 400 if the request is not valid.
 * 
 * - update(String id, TruckPartInventory truckPartInventory): Updates an existing TruckPartInventory. The request body should contain the TruckPartInventory to be updated, including its id. Returns 204 if the update is successful, and 400 if the request is not valid.
 * 
 * - delete(String id): Deletes a TruckPartInventory by its id. Returns 204 if the deletion is successful, and 400 if the id is not a valid ObjectId.
 * 
 * - getAll(): Retrieves all TruckPartInventories. Returns 200 along with the list of TruckPartInventories.
 * 
 * - getByCategoryIdAndTruckId(String categoryId, String truckId): Retrieves all TruckPartInventories by category ID and truck ID. Returns 200 along with the list of TruckPartInventories if they are found, and 400 if the request is not valid.
 */