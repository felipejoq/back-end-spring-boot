package com.uncodigo.clientes.app.controllers;

import com.uncodigo.clientes.app.models.entity.Country;
import com.uncodigo.clientes.app.models.services.ICountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@RestController
@RequestMapping(value = "/api")
public class CountryRestController {
    private final ICountryService countryService;

    public CountryRestController(ICountryService countryService) { this.countryService = countryService; }

    @GetMapping("/countries/{term}")
    public ResponseEntity<?> searchCountryByName(@PathVariable String term) {

        System.out.println(term);

        Map<String, Object> response = new HashMap<>();

        List<Country> countries = this.countryService.findAll(term, term);

        response.put("ok", true);
        response.put("countries", countries);
        response.put("message", "List of countries from term: " + term);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
