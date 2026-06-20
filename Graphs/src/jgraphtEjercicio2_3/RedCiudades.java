package jgraphtEjercicio2_3;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

//EJERCICIO 2
public class RedCiudades {

    public static void main(String[] args) {
        // grafo ponderado no dirigido
        Graph<String, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // agregar ciudades (Vértices)
        String[] ciudades = {"Arequipa", "Cusco", "Puno", "Tacna", "Moquegua"};
        for (String c : ciudades) grafo.addVertex(c);

        // Agregar carreteras (Aristas con peso)
        agregarCarretera(grafo, "Arequipa", "Cusco", 510);
        agregarCarretera(grafo, "Arequipa", "Moquegua", 230);
        agregarCarretera(grafo, "Moquegua", "Tacna", 160);
        agregarCarretera(grafo, "Cusco", "Puno", 390);
        agregarCarretera(grafo, "Puno", "Tacna", 420);

        // Mostrar información
        System.out.println("--- Lista de Ciudades ---");
        System.out.println(grafo.vertexSet());

        System.out.println("\n--- Carreteras Registradas ---");
        for (DefaultWeightedEdge edge : grafo.edgeSet()) {
            System.out.println(edge + " - Distancia: " + grafo.getEdgeWeight(edge) + " km");
        }

        // cálculo de camino más corto (Dijkstra)
        String origen = "Arequipa";
        String destino = "Tacna";
        
        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(grafo);
        var camino = dijkstra.getPath(origen, destino);

        System.out.println("\n--- Ruta mas corta: " + origen + " a " + destino + " ---");
        if (camino != null) {
            System.out.println("Camino: " + camino.getVertexList());
            System.out.println("Costo total: " + camino.getWeight() + " km");
        } else {
            System.out.println("No existe ruta entre esas ciudades.");
        }
    }

    private static void agregarCarretera(Graph<String, DefaultWeightedEdge> g, String u, String v, double peso) {
        DefaultWeightedEdge e = g.addEdge(u, v);
        g.setEdgeWeight(e, peso);
    }
}