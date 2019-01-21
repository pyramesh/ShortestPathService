package com.ramesh.algo;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ramesh.entity.Graph;
import com.ramesh.entity.Node;

/**
 * @author Ramesh.Yaleru
 *
 */
public class Dijkstra {

	private static final Logger LOG = LoggerFactory.getLogger(Dijkstra.class);
	
    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
    	LOG.info("############### start :: calculateShortestPathFromSource ##############");
        source.setDistance(0f);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Entry<Node, Float> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Float edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        LOG.info("############### end :: calculateShortestPathFromSource ##############");
        return graph;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Float edgeWeigh, Node sourceNode) {
    	LOG.info("############### start :: CalculateMinimumDistance ##############");
        Float sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
        LOG.info("############### end :: CalculateMinimumDistance ##############");
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
    	LOG.info("############### start :: getLowestDistanceNode ##############");
        Node lowestDistanceNode = null;
        Float lowestDistance = Float.MAX_VALUE;
        for (Node node : unsettledNodes) {
            Float nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        LOG.info("############### end :: getLowestDistanceNode ##############");
        return lowestDistanceNode;
    }
}