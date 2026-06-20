package Analisis_Ejer4;

import jgraphtEjercicio2_3.GraphLink; // Asegura que este sea tu paquete correcto

public class TestAnalyzer {
    public static void main(String[] args) {
        GraphLink<String> grafo = new GraphLink<>();
        GraphAnalyzer<String> analista = new GraphAnalyzer<>();

        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");
        grafo.insertEdge("A", "B", 1);
        grafo.insertEdge("B", "C", 1);
        grafo.insertEdge("C", "A", 1);

        System.out.println("--- Analisis del Grafo ---");
        System.out.println("Es Conexo?: " + analista.isConexo(grafo));
        System.out.println("Es Plano?: " + analista.isPlano(grafo));
        
        grafo.insertVertex("D");
        System.out.println("\n--- Tras agregar nodo aislado ---");
        System.out.println("Es Conexo?: " + analista.isConexo(grafo));

        //ISOMORFISMO 
        System.out.println("\n--- Prueba de Isomorfismo ---");
        
        //cuadrado (G1)
        GraphLink<String> g1 = new GraphLink<>();
        g1.insertVertex("1"); g1.insertVertex("2"); g1.insertVertex("3"); g1.insertVertex("4");
        g1.insertEdge("1", "2", 1); g1.insertEdge("2", "3", 1); 
        g1.insertEdge("3", "4", 1); g1.insertEdge("4", "1", 1);
        
        //cuadrado (G2) 
        GraphLink<String> g2 = new GraphLink<>();
        g2.insertVertex("A"); g2.insertVertex("B"); g2.insertVertex("C"); g2.insertVertex("D");
        g2.insertEdge("A", "B", 1); g2.insertEdge("B", "C", 1); 
        g2.insertEdge("C", "D", 1); g2.insertEdge("D", "A", 1);
        
        System.out.println("Es G1 isomorfo a G2?: " + analista.isIsomorfo(g1, g2)); //true
        System.out.println("Es G1 isomorfo al Triangulo original?: " + analista.isIsomorfo(g1, grafo)); //false
    }
}