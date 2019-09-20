package com.space.controller;

import com.space.model.Ship;
import com.space.repository.SpaceRepo;
import com.space.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpaceController {
    @Autowired
    SpaceRepo spaceRepo;

    @Autowired
    SpaceService spaceService;

    @RequestMapping(path = "rest/ships", method = RequestMethod.GET)
    public List<Ship> getShips(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String planet,
                               @RequestParam(required = false) String shipType,
                               @RequestParam(required = false) Long after,
                               @RequestParam(required = false) Long before,
                               @RequestParam(required = false) Boolean isUsed,
                               @RequestParam(required = false) Double minSpeed,
                               @RequestParam(required = false) Double maxSpeed,
                               @RequestParam(required = false) Integer minCrewSize,
                               @RequestParam(required = false) Integer maxCrewSize,
                               @RequestParam(required = false) Double minRating,
                               @RequestParam(required = false) Double maxRating,
                               @RequestParam(required = false) String order,
                               @RequestParam(required = false) Integer pageNumber,
                               @RequestParam(required = false) Integer pageSize) {

        List<Ship> ships = spaceService.getFilteredShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating);

        return spaceService.getSortedShips(ships, pageNumber, pageSize, order);
    }

    @RequestMapping(path = "/rest/ships/count", method = RequestMethod.GET)
    public Integer getCount(@RequestParam(required = false) String name,
                            @RequestParam(required = false) String planet,
                            @RequestParam(required = false) String shipType,
                            @RequestParam(required = false) Long after,
                            @RequestParam(required = false) Long before,
                            @RequestParam(required = false) Boolean isUsed,
                            @RequestParam(required = false) Double minSpeed,
                            @RequestParam(required = false) Double maxSpeed,
                            @RequestParam(required = false) Integer minCrewSize,
                            @RequestParam(required = false) Integer maxCrewSize,
                            @RequestParam(required = false) Double minRating,
                            @RequestParam(required = false) Double maxRating) {
        return spaceService.getFilteredShips(name, planet, shipType, after, before,
                isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating).size();
    }

    @RequestMapping(path = "/rest/ships", method = RequestMethod.POST)
    public @ResponseBody Ship createShip(@RequestBody Ship shipJSON){
        Ship ship = spaceService.createShip(shipJSON);
        spaceRepo.save(ship);
        return ship;
    }

    @RequestMapping(path = "/rest/ships/{id}", method = RequestMethod.GET)
    public Ship getShip(@PathVariable Long id) {
        return spaceService.getShipById(id);
    }

    @RequestMapping(path = "/rest/ships/{id}", method = RequestMethod.POST)
    public @ResponseBody Ship updateShip(@RequestBody Ship shipJSON, @PathVariable Long id){
        Ship ship = spaceService.getShipById(id);
        if (spaceService.updateShip(ship,shipJSON))
            spaceRepo.save(ship);
        return ship;
    }

    @RequestMapping(path = "/rest/ships/{id}", method = RequestMethod.DELETE)
    public void deleteShip(@PathVariable Long id){
        spaceRepo.delete(getShip(id));
    }
}