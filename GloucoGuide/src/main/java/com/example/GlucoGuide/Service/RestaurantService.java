package com.example.GlucoGuide.Service;

import com.google.maps.model.PlacesSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private LocationService locationService;

    public List<PlacesSearchResult> findNearestRestaurants(double latitude, double longitude) throws Exception {
        return locationService.findNearbyPlaces(latitude, longitude, "restaurant");
    }
}