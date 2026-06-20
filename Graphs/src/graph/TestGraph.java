package graph;

import java.util.ArrayList;

public class TestGraph {
	public static void main(String[] args) { 
		GraphLink<String> g = new GraphLink<>();
        
        // inserción de vértices
        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        
        // inserción de aristas con peso
        g.insertEdgeWeight("A", "B", 4);
        g.insertEdgeWeight("A", "C", 2);
        g.insertEdgeWeight("B", "C", 1);
        g.insertEdgeWeight("B", "D", 5);
        g.insertEdgeWeight("C", "D", 8);
        g.insertEdgeWeight("D", "E", 2);
        
        System.out.println("--- Grafo Representado ---");
        System.out.println(g);
        
        // Conexidad
        System.out.println("Es conexo?: " + g.isConexo());
        
        // Dijkstra (Ruta más corta)
        // Ejemplo: Camino de A a E
        // La ruta A -> B -> C es 5, A -> C es 2, etc. 
        // El camino más corto debería ser A -> B -> C -> D -> E con un peso total de 4+1+8+2... 
        
        try {
            ArrayList<String> ruta = g.shortPath("A", "E");
            System.out.println("Ruta mas corta de A a E: " + ruta);
        } catch (Exception e) {
            System.out.println("Error al calcular ruta: " + e.getMessage());
        }
	}
}
