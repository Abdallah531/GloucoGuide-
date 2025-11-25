package com.example.GlucoGuide.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class DiabetesPredictionService implements FlaskDiabetesPredictionService{

    private final String FLASK_API_URL = "https://gg-ml.azurewebsites.net/predict"; // Replace this with your Flask API URL

    @Override
    public String getDiabetesPrediction(Map<String, Object> data) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(FLASK_API_URL, data, String.class);
    }
}