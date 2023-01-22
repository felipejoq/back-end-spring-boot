package com.uncodigo.clientes.app.models.dao;

import com.uncodigo.clientes.app.models.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICountryDao extends JpaRepository<Country, Long> {
    @Query("select c from Country c where c.name like %?1% or c.code like %?2%")
    public List<Country> findByNameLikeIgnoreCase(String country, String codeCountry);
}
