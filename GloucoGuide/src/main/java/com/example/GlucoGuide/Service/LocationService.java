package com.example.GlucoGuide.Service;


import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LocationService {
    private final GeoApiContext context;

    public LocationService(@Value("${google.maps.api.key}") String apiKey) {
        this.context = new GeoApiContext.Builder().apiKey(apiKey).build();
    }

    public double calculateDistance(LatLng start, LatLng end) {
        double earthRadius = 6371; // kilometers
        double dLat = Math.toRadians(end.lat - start.lat);
        double dLng = Math.toRadians(end.lng - start.lng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(start.lat)) * Math.cos(Math.toRadians(end.lat)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    public List<PlacesSearchResult> findNearbyPlaces(double latitude, double longitude, String placeType) throws Exception {
        PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, new LatLng(latitude, longitude))
                .radius(5000) // 5 km radius, adjust as needed
                .type(PlaceType.valueOf(placeType.toUpperCase()))
                .await();

        return Arrays.asList(response.results);
    }
}
