package com.ramesh.controller;

import com.ramesh.entity.PlanetRoutes;
import com.ramesh.repository.PlanetRouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author Ramesh.Yaleru
 */

@RestController
@RequestMapping("/importFileSer")
public class importFileController {

    @Autowired
    PlanetRouteRepository shortestPathRepository;
    private static final Logger LOG = LoggerFactory.getLogger(importFileController.class);

    @RequestMapping(value = "/importFile", method = RequestMethod.GET)
    public String importFile() throws IOException {
        LOG.info("###### start:: importFile #################");
        File file = new ClassPathResource("planetroutes.txt").getFile();
        try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            String readLine = "";
            b.readLine();
            while ((readLine = b.readLine()) != null) {
                PlanetRoutes planetRoute = new PlanetRoutes();
                planetRoute.setId(Long.parseLong(readLine.substring(0, 4).trim()));
                planetRoute.setPlanetSource(readLine.substring(4, 8).trim());
                planetRoute.setPlanetDestination(readLine.substring(8, 12).trim());
                planetRoute.setDistance(Float.parseFloat(readLine.substring(12).trim()));
                shortestPathRepository.save(planetRoute);
                LOG.info("###### end:: importFile #################");

            }

            List<PlanetRoutes> routeList = (List<PlanetRoutes>)shortestPathRepository.findAll();
            LOG.info("########### size of the list is from H2 DB ############"+routeList.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "File imported successfully";
    }
}
