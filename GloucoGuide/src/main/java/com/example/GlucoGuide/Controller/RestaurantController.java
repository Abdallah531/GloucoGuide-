package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.Service.RestaurantService;
import com.google.maps.model.PlacesSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/nearest")
    public ResponseEntity<List<PlacesSearchResult>> getNearestRestaurants(@RequestParam double latitude, @RequestParam double longitude) throws Exception {
        return ResponseEntity.ok(restaurantService.findNearestRestaurants(latitude, longitude));
    }
}