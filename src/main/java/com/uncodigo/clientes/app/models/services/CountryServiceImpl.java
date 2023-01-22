package com.uncodigo.clientes.app.models.services;

import com.uncodigo.clientes.app.models.dao.ICountryDao;
import com.uncodigo.clientes.app.models.entity.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("countryService")
public class CountryServiceImpl implements ICountryService{
    private ICountryDao countryDao;

    public CountryServiceImpl(ICountryDao countryDao){ this.countryDao = countryDao; }

    @Override
    public List<Country> findAll(String country, String codeCountry) {
        return this.countryDao.findByNameLikeIgnoreCase(country, codeCountry);
    }
}
