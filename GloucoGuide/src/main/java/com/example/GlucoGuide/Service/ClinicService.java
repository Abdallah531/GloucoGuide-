package com.example.GlucoGuide.Service;

import com.google.maps.model.PlacesSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {
    @Autowired
    private LocationService locationService;

    public List<PlacesSearchResult> findNearestClinics(double latitude, double longitude) throws Exception {
        return locationService.findNearbyPlaces(latitude, longitude, "hospital"); // or another suitable place type
    }
}