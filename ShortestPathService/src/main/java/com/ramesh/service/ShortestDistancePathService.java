package com.ramesh.service;

import com.ramesh.algo.Dijkstra;
import com.ramesh.controller.importFileController;
import com.ramesh.entity.*;
import com.ramesh.repository.PlanetNameRepository;
import com.ramesh.repository.PlanetRouteRepository;
import com.ramesh.repository.ShortestDistancePathRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ramesh.Yaleru
 */

@Service
public class ShortestDistancePathService {

	private static final Logger LOG = LoggerFactory.getLogger(ShortestDistancePathService.class);
	
    @Autowired
    PlanetNameRepository planetNameRepository;

    @Autowired
    PlanetRouteRepository planetRouteRepository;

    @Autowired
    ShortestDistancePathRepository shortestDistancePathRepository;

    public String shortestPath(String sourceNode, String destinationNode) {
    	LOG.info("###### start :: shortestPath ########");
        List<PlanetNames> planetNames = (List<PlanetNames>)planetNameRepository.findAll();
        List<Node> listNode = new ArrayList<>();
        planetNames.forEach(s -> {
            Node node = new Node(s.getPlanetNode());
            listNode.add(node);
        });

        List<PlanetRoutes> routes = (List<PlanetRoutes>)planetRouteRepository.findAll();
        listNode.forEach(n -> {
            addDestination(n, listNode, routes);
        });

        Graph graph1 = new Graph();
        for (Node node : listNode) {
            graph1.addNode(node);
        }
        graph1 = Dijkstra.calculateShortestPathFromSource(graph1, listNode.get(0));

        System.out.println("####### after graph1 #########");
        StringBuffer sb = new StringBuffer();
        for( Node node:graph1.getNodes()) {

            if(node.getName().equalsIgnoreCase(destinationNode)) {
                for(Node n: node.getShortestPath()) {
                    System.out.println(" getShortestPath ##### >>>>>>>>>>> "+n.getName());
                    sb.append(n.getName()).append("->");
                }
            }

        }
        

        for (PlanetNames planetName : planetNames) {
            ShortestPath shortestPath = new ShortestPath();
            for (Node node : graph1.getNodes()) {
                if (node.getName().equalsIgnoreCase(planetName.getPlanetNode())) {
                    shortestPath.setId(planetName.getId());
                    shortestPath.setPlanetNode(node.getName());
                    shortestPath.setPlanetName(planetName.getPlanetSourceName());
                    shortestPath.setPath(node.getPath());
                }

            }
           shortestDistancePathRepository.save(shortestPath);
        }
        LOG.info("###### end :: shortestPath ########");
        return sb.append(destinationNode).toString();
    }

    private void addDestination(Node n, List<Node> listNode, List<PlanetRoutes> routes) {
    	LOG.info("###### start :: addDestination ########");
        routes.forEach(r -> {
            if (r.getPlanetSource().equalsIgnoreCase(n.getName())) {
                listNode.forEach(l -> {
                    if (l.getName().equalsIgnoreCase(r.getPlanetDestination())) {
                        n.addDestination(l, r.getDistance());
                    }
                });
            }
        });
        LOG.info("###### end :: addDestination ########");
    }

}
