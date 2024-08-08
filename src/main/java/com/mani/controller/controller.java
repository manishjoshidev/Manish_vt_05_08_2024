package com.mani.controller;

import com.mani.entity.Redirect;
import com.mani.request.redirectRequest;
import com.mani.service.RedirectService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class controller {
    private final RedirectService redirectService;
    private final Map<String, String> urlMap = new HashMap<>();

    @Autowired
    public controller(RedirectService redirectService) {
        this.redirectService = redirectService;
        loadUrls();
    }

    private void loadUrls() {
        try (CSVReader csvReader = new CSVReader(new FileReader("C:\\Users\\joshi\\Downloads\\urlShort\\urlShort\\urls.csv"))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                String shortUrl = values[0];
                String fullUrl = values[1];
                urlMap.put(shortUrl, fullUrl);
            }
        } catch (IOException | CsvValidationException e) {
            // Replace printStackTrace with a proper logging framework
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, "Failed to load URLs from CSV file", e);
            throw new RuntimeException("Failed to load URLs from CSV file", e);
        }
    }


    @GetMapping("/{alias}")
    public ResponseEntity<?> handleRedirect(@PathVariable String alias) throws URISyntaxException {
        Optional<Redirect> optionalRedirect = redirectService.getRedirect(alias);
        if (optionalRedirect.isPresent() && optionalRedirect.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            URI uri = new URI(optionalRedirect.get().getUrl());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
        } else if (urlMap.containsKey(alias)) {
            URI uri = new URI(urlMap.get(alias));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
        } else {
            return new ResponseEntity<>("URL not found or expired", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createRedirect(@Validated @RequestBody redirectRequest redirectRequest) {
        Optional<Redirect> optionalRedirect = redirectService.createRedirect(redirectRequest);
        if (optionalRedirect.isPresent()) {
            return ResponseEntity.ok(optionalRedirect.get());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create redirect");
        }
    }

    @PostMapping("/update-url")
    public ResponseEntity<?> updateRedirect(@RequestParam String alias, @RequestParam String newUrl) {
        boolean updated = redirectService.updateRedirect(alias, newUrl);
        if (updated) {
            return ResponseEntity.ok("URL updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alias not found");
        }
    }

    @PostMapping("/update-expiry")
    public ResponseEntity<?> updateExpiry(@RequestParam String alias, @RequestParam int daysToAdd) {
        boolean updated = redirectService.updateExpiry(alias, daysToAdd);
        if (updated) {
            return ResponseEntity.ok("Expiry date updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alias not found");
        }
    }
}
