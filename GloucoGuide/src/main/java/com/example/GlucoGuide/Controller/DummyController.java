package com.example.GlucoGuide.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class DummyController {

   @GetMapping("/debug/all-sessions")
    public ResponseEntity<Map<String, Object>> getAllSessions(HttpServletRequest request) {
        Map<String, Object> allSessions = new HashMap<>();

        HttpSession session = request.getSession(false); // false means don't create new session if none exists
        if (session != null) {
            Map<String, Object> sessionAttributes = new HashMap<>();
            Enumeration<String> attributeNames = session.getAttributeNames();

            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = session.getAttribute(attributeName);
                sessionAttributes.put(attributeName, attributeValue);
            }

            allSessions.put(session.getId(), sessionAttributes);
        }

        return ResponseEntity.ok(allSessions);
    }
}
