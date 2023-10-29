package com.truckcompany.example.TruckCompany.Controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.truckcompany.example.TruckCompany.DataAbstraction.ITruckPartInventoryService;
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
            ObjectId objectId = new ObjectId(id);
            TruckPartInventory truckPartInventory = truckPartInventoryService.get(objectId.toHexString());
            if (truckPartInventory == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(truckPartInventory, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody TruckPartInventory truckPartInventory) {
        try {
            truckPartInventoryService.insert(truckPartInventory);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody TruckPartInventory truckPartInventory) {
        try {
            ObjectId objectId = new ObjectId(id);
            TruckPartInventory existingTruckPartInventory = truckPartInventoryService.get(objectId.toHexString());
            if (existingTruckPartInventory == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            truckPartInventory.setId(objectId.toHexString());
            truckPartInventoryService.update(truckPartInventory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            TruckPartInventory existingTruckPartInventory = truckPartInventoryService.get(objectId.toHexString());
            if (existingTruckPartInventory == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            truckPartInventoryService.delete(objectId.toHexString());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<TruckPartInventory>> getAll() {
        try{
             List<TruckPartInventory> truckPartInventories = truckPartInventoryService.getAll();
             return new ResponseEntity<>(truckPartInventories, HttpStatus.OK);
        }
        catch(IllegalArgumentException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/category/{categoryId}/truck/{truckId}")
    public ResponseEntity<List<TruckPartInventory>> getByCategoryIdAndTruckId(@PathVariable String categoryId, @PathVariable String truckId) {
        try {
            List<TruckPartInventory> truckPartInventories = truckPartInventoryService.GetTruckPartInventoryByTruckPartId(categoryId, truckId);
            return new ResponseEntity<>(truckPartInventories, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}