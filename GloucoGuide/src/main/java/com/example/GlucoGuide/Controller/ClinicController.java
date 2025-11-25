package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.Service.ClinicService;
import com.google.maps.model.PlacesSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {
    @Autowired
    private ClinicService clinicService;

    @GetMapping("/nearest")
    public ResponseEntity<List<PlacesSearchResult>> getNearestClinics(@RequestParam double latitude, @RequestParam double longitude) throws Exception {
        return ResponseEntity.ok(clinicService.findNearestClinics(latitude, longitude));

    }
}