package com.ramesh.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ramesh.entity.PlanetNames;

/**
 * @author Ramesh.Yaleru
 */
@Repository
public interface PlanetNameRepository extends CrudRepository<PlanetNames, Long> {
}
