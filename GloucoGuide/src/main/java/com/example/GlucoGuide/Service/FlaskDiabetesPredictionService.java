package com.example.GlucoGuide.Service;

import java.util.Map;

public interface FlaskDiabetesPredictionService {
    String getDiabetesPrediction(Map<String, Object> data);
}
