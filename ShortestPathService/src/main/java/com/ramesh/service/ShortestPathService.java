package com.ramesh.service;

import java.util.List;

import com.ramesh.repository.PlanetRouteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramesh.entity.PlanetRoutes;

/**
/**
 * @author Ramesh.Yaleru
 */

@Service
public class ShortestPathService {

	private static final Logger LOG = LoggerFactory.getLogger(ShortestPathService.class);
	
	@Autowired
    PlanetRouteRepository planetRouteRepository;
	
	public List<PlanetRoutes>getAllPlanetRoutes(){
		return (List<PlanetRoutes>) planetRouteRepository.findAll();
	}
	
	public void saveRoute(PlanetRoutes route) {
		LOG.info("#### start ShortestPathService :: saveRoute #######");
		planetRouteRepository.save(route);
	}
	
	public void saveorupdateRoute(PlanetRoutes route) {
		LOG.info("#### start ShortestPathService :: saveorupdateRoute #######");
	    PlanetRoutes routeDB= planetRouteRepository.findById(route.getId()).orElse(null);;
        routeDB.setDistance(route.getDistance());
        routeDB.setPlanetDestination(route.getPlanetSource());
        routeDB.setPlanetDestination(route.getPlanetDestination());
		planetRouteRepository.save(routeDB);
	}
	
	
	public void deleteRoute(Long routeId) {
		LOG.info("#### start ShortestPathService :: deleteRoute #######");
		planetRouteRepository.deleteById(routeId);
	}
	
}
