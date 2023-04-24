package ro.extra;

import lombok.extern.log4j.Log4j2;
import org.graph4j.Graph;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ParallelMSTAlgorithm {
    protected Graph graph;

    public ParallelMSTAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public List<Edge> computeMST() {
        List<Edge> weightedEdges = new ArrayList<>();
        for (int nodeId = 0; nodeId < graph.numVertices(); ++nodeId) {
            int[] neighbours = graph.neighbors(nodeId);
            for (int neighbourId : neighbours) {
                weightedEdges.add(new Edge(nodeId, neighbourId, graph.getEdgeWeight(nodeId, neighbourId)));
            }
        }

        ParallelEdgeSorter parallelEdgeSorter = new ParallelEdgeSorter(weightedEdges);
        Thread t = new Thread(parallelEdgeSorter);
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        List<Edge> sortedEdges = parallelEdgeSorter.getOrderedEdges();
        List<Edge> minimumSpanningTree = new ArrayList<>();
        DisjointSetUnion dsu = new DisjointSetUnion(graph.numVertices());

        for (Edge edge : sortedEdges) {
            if (dsu.addEdge(edge.getFrom(), edge.getTo())) {
                minimumSpanningTree.add(edge);
            }
        }
        return minimumSpanningTree;
    }
}
