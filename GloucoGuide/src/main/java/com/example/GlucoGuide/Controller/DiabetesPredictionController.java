package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.Entity.DiabetesFormData;
import com.example.GlucoGuide.Service.FlaskDiabetesPredictionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/diabetes")
public class DiabetesPredictionController {
    private final FlaskDiabetesPredictionService predictionService;

    @Autowired
    public DiabetesPredictionController(FlaskDiabetesPredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    public ResponseEntity<Map<String, String>> predictDiabetes(@RequestBody DiabetesFormData diabetesFormData) {
        try {
            // Convert DiabetesFormData to Map to send to Flask service
            Map<String, Object> formData = diabetesFormData.toMap();

            // Call the Flask service to get the prediction
            String prediction = predictionService.getDiabetesPrediction(formData);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(prediction);

            // Check if the "prediction" field exists and is not empty
            JsonNode predictionNode = jsonNode.get("prediction");
            String predictionValue = predictionNode != null && predictionNode.isArray() && predictionNode.size() > 0
                    ? predictionNode.get(0).asText()
                    : "";

            // Create response map
            Map<String, String> response = new HashMap<>();
            response.put("prediction", predictionValue);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error processing prediction response"));
        }
    }
}
