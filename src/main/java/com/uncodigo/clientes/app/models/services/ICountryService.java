package com.uncodigo.clientes.app.models.services;

import com.uncodigo.clientes.app.models.entity.Country;

import java.util.List;

public interface ICountryService {
    public List<Country> findAll(String country, String codeCountry);
}
