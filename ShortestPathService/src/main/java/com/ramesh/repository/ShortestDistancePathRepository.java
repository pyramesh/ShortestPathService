package com.ramesh.repository;

import org.springframework.data.repository.CrudRepository;

import com.ramesh.entity.ShortestPath;

/**
 * @author Ramesh.Yaleru
 */
public interface ShortestDistancePathRepository extends CrudRepository<ShortestPath, Long> {
}
