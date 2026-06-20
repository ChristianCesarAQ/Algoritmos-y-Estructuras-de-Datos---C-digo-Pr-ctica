package jgraphtEjercicio2_3;

//EJERCICIO 3
public class TestGraph {
    public static void main(String[] args) {
        Graph<String> grafo = new GraphLink<>();

        // inserción
        grafo.insertVertex("Arequipa");
        grafo.insertVertex("Cusco");
        grafo.insertVertex("Puno");
        
        grafo.insertEdge("Arequipa", "Cusco", 510);
        grafo.insertEdge("Cusco", "Puno", 390);

        System.out.println("--- Grafo Inicial ---");
        System.out.println(grafo.toString());

        // búsqueda
        System.out.println("Existe Arequipa? " + grafo.searchVertex("Arequipa"));
        System.out.println("Existe conexion Arequipa-Cusco? " + grafo.searchEdge("Arequipa", "Cusco"));

        // vecinos
        System.out.println("Vecinos de Cusco: " + grafo.adjacentVertices("Cusco"));
        
       
        // eliminación
        System.out.println("\n--- Eliminando Arequipa ---");
        grafo.removeVertex("Arequipa");
        
        System.out.println("Existe Arequipa? " + grafo.searchVertex("Arequipa"));
        System.out.println("Existe conexion Arequipa-Cusco? " + grafo.searchEdge("Arequipa", "Cusco"));
        System.out.println("Estado final:\n" + grafo.toString());
    }
}