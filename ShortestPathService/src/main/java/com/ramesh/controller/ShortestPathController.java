

package com.ramesh.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramesh.entity.PlanetNames;
import com.ramesh.entity.ShortestPath;
import com.ramesh.repository.ShortestDistancePathRepository;
import com.ramesh.service.ShortestDistancePathService;



/**
 * @author Ramesh.Yaleru
 *
 */
@Controller
public class ShortestPathController {
    private static final Logger LOG = LoggerFactory.getLogger(ShortestPathController.class);

    @Autowired
    private ShortestDistancePathRepository shortestDistanceRepository;

    @Autowired
    private ShortestDistancePathService shortestPathService;

    
    @GetMapping("/")
    public String loadUIPage(Model model) {
        model.addAttribute("planetNames", new PlanetNames());
        return "displayUI";
    }
    
    

    @PostMapping("/findshortestpath")
    public String findshortestpath(Model model, @ModelAttribute PlanetNames planetNames) {
    	LOG.info("###### start :: findshortestpath ######## with sourceNode "+planetNames.getPlanetSourceName()+ "and distination Name "+planetNames.getDestinationPlanetName());

        ShortestPath shortestDistance = new ShortestPath();
        List<ShortestPath> listShortestDistance = (List<ShortestPath>)shortestDistanceRepository.findAll();
        listShortestDistance.forEach(l -> {
            if (l.getPlanetNode().equalsIgnoreCase(planetNames.getPlanetSourceName())) {
                shortestDistance.setPath(l.getPath());
                
            }
        });
       
        String shortestPath = shortestPathService.shortestPath(planetNames.getPlanetSourceName(), planetNames.getDestinationPlanetName());
        LOG.info("###### end :: findshortestpath ######## ");
        model.addAttribute("shortestPath", shortestPath);
        return "success";
    }
    
   

}
