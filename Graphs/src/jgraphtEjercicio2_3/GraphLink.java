package jgraphtEjercicio2_3;

import graph.AdjList;
import graph.Edge;
import graph.Vertex;
import listlinked.ListLinked;
import listlinked.Node;

public class GraphLink<E> implements Graph<E> {
    private ListLinked<AdjList<E>> graph; 
    
    public GraphLink() {
        this.graph = new ListLinked<>(); 
    }
   
    @Override
    public void insertVertex(E data) {
        // Si tu constructor de AdjList exige un Vertex, haz esto:
        Vertex<E> nuevoVertex = new Vertex<>(data);
        graph.addLast(new AdjList<>(nuevoVertex)); 
    }

    @Override
    public void insertEdge(E origin, E destination, int weight) {
        AdjList<E> vOrigin = findVertex(origin);
        AdjList<E> vDest = findVertex(destination);
        
        if (vOrigin != null && vDest != null) {
            //arista hacia el destino
            Edge<E> edge1 = new Edge<>(vDest.getVertex());
            edge1.setWeight(weight); 
            vOrigin.getEdges().addLast(edge1);
            
            //arista hacia el origen (bidireccional)
            Edge<E> edge2 = new Edge<>(vOrigin.getVertex());
            edge2.setWeight(weight);
            vDest.getEdges().addLast(edge2);
        }
    }
    
    @Override
    public void removeVertex(E data) {
        AdjList<E> target = findVertex(data);
        if (target == null) return;

        // Limpieza en cascada: Recorrer todo el grafo
        for (int i = 0; i < graph.size(); i++) {
        	 removeEdgeFromList(graph.get(i).getEdges(), data);
        }

        // Eliminar vértice de la lista maestra
        graph.removeNode(target);
    }
    
    
    @Override
    public void removeEdge(E origin, E destination) {
        AdjList<E> vOrigin = findVertex(origin);
        AdjList<E> vDest = findVertex(destination);

        // Si alguno de los vértices no existe, no hay nada que eliminar
        if (vOrigin == null || vDest == null) return;

        // 1. Eliminar la arista en la lista del origen (hacia el destino)
        removeEdgeFromList(vOrigin.getEdges(), destination);

        // 2. Eliminar la arista en la lista del destino (hacia el origen)
        removeEdgeFromList(vDest.getEdges(), origin);
    }

    @Override
    public boolean searchVertex(E data) {
        return findVertex(data) != null;
    }
    
    @Override
    public boolean searchEdge(E origin, E destination) {
        AdjList<E> vOrigin = findVertex(origin);
        
        // Si el vértice de origen no existe, la arista tampoco puede existir
        if (vOrigin == null) return false;
        
        //recorre la lista de aristas del origen
        ListLinked<Edge<E>> listaAristas = vOrigin.getEdges();
        Node<Edge<E>> current = listaAristas.getFirst();
        
        while (current != null) {
            //si el destino de la arista coincide con el buscado
            if (current.getData().getDestination().getData().equals(destination)) {
                return true; // ¡Arista encontrada!
            }
            current = current.getNext();
        }
        
        return false;
    }
    @Override
    public ListLinked<E> adjacentVertices(E data) {
        AdjList<E> vertex = findVertex(data);
        ListLinked<E> vecinos = new ListLinked<>();
        
        if (vertex != null) {
            // Obtenemos la lista de aristas del vértice encontrado
            ListLinked<Edge<E>> listaAristas = vertex.getEdges();
            
            // Recorrer la lista de aristas para extraer el destino de cada una
            Node<Edge<E>> current = listaAristas.getFirst();
            while (current != null) {
                //Obtener el dato del nodo destino de la arista actual
                E destino = current.getData().getDestination().getData();
                
                // Agrega el dato a nuestra lista de resultados
                vecinos.addLast(destino);
                
                current = current.getNext();
            }
        }
        return vecinos;
    }

    //metodos axuliares
    private AdjList<E> findVertex(E data) {
        Node<AdjList<E>> current = graph.getFirst();
        while (current != null) {
            if (current.getData().getVertex().getData().equals(data)) {
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }
    
    private void removeEdgeFromList(ListLinked<Edge<E>> list, E destinationData) {
        Node<Edge<E>> current = list.getFirst();
        while (current != null) {
            if (current.getData().getDestination().getData().equals(destinationData)) {
                //remover el nodo que contiene la arista
                list.removeNode(current.getData());
                return; // arista encontrada y eliminada
            }
            current = current.getNext();
        }
    }
    
    public boolean isEmpty() {
        return graph.isEmptyList(); 
    }

    
    @Override
    public String toString() {
    	
        StringBuilder sb = new StringBuilder();
        Node<AdjList<E>> current = graph.getFirst(); 
        
        while (current != null) {
            sb.append(current.getData().getVertex().getData()).append(" -> ");
            
            sb.append(current.getData().getEdges().toString()); 
            sb.append("\n");
            current = current.getNext();
        }
        return sb.toString();
    }

    public int size() {
        return this.graph.size();
    }

    public AdjList<E> get(int index) {
        return this.graph.get(index);
    }
    
    public int getTotalEdges() {
        int total = 0;
        Node<AdjList<E>> current = this.graph.getFirst();
        while (current != null) {
            total += current.getData().getEdges().size();
            current = current.getNext();
        }
        return total / 2; 
    }
}
