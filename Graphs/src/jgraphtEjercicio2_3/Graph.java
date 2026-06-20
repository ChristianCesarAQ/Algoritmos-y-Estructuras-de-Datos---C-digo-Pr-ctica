package jgraphtEjercicio2_3;

import listlinked.ListLinked;

public interface Graph<E> {
    void insertVertex(E data);
    void insertEdge(E origin, E destination, int weight);
    void removeVertex(E data);
    void removeEdge(E origin, E destination);
    boolean searchVertex(E data);
    boolean searchEdge(E origin, E destination);
    // Retorna los vecinos de un vértice dado
    ListLinked<E> adjacentVertices(E data); 
}