package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.Service.PharmacyService;
import com.google.maps.model.PlacesSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
public class PharmacyController {
    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping("/nearest")
    public ResponseEntity<List<PlacesSearchResult>> getNearestPharmacies(@RequestParam double latitude, @RequestParam double longitude) throws Exception {
        return ResponseEntity.ok(pharmacyService.findNearestPharmacies(latitude, longitude));
    }
}