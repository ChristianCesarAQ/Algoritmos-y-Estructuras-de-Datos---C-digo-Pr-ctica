package Analisis_Ejer4;

import java.util.Arrays;

import jgraphtEjercicio2_3.GraphLink;
import listlinked.ListLinked;
import listlinked.Node;

public class GraphAnalyzer<E> {

	// CONEXO: Basado en tu DFS
    public boolean isConexo(GraphLink<E> grafo) {
	    if (grafo.isEmpty()) return true;
	    
	    //nodos visitados
	    ListLinked<E> visitados = new ListLinked<>();
	    
	    //DFS
	    dfs(grafo, grafo.get(0).getVertex().getData(), visitados);
	    
	    return visitados.size() == grafo.size();
	}
    
    private void dfs(GraphLink<E> grafo, E actual, ListLinked<E> visitados) {
        // nodo actual marcado como visitado
        visitados.addLast(actual);
        
        // Obtener vecinos del nodo actual usando el método 'adjacentVertices' 
        ListLinked<E> vecinos = grafo.adjacentVertices(actual);
        
        // Recorre cada vecino
        Node<E> current = vecinos.getFirst();
        while (current != null) {
            E vecino = current.getData();
            
            //llamada recursiva si vecino todavia no ha sido visitado
            if (!estaEnLista(visitados, vecino)) {
                dfs(grafo, vecino, visitados);
            }
            current = current.getNext();
        }
    }

    // Método auxiliar verificar si elemento ya fue visitado
    private boolean estaEnLista(ListLinked<E> lista, E dato) {
        Node<E> current = lista.getFirst();
        while (current != null) {
            if (current.getData().equals(dato)) return true;
            current = current.getNext();
        }
        return false;
    }
    
    

    //Plano, si un grafo y sus aristas no se cruzan
    //metodo para verificar Planaridad (Euler)
    public boolean isPlano(GraphLink<E> grafo) {
        int v = grafo.size();
        if (v < 3) return true;
        
        int e = grafo.getTotalEdges(); 
        return e <= (3 * v - 6);
    }
    
    //Isomorfismo
    public boolean isIsomorfo(GraphLink<E> g1, GraphLink<E> g2) {
        //Verificación básica: Cantidad de nodos y aristas
        if (g1.size() != g2.size() || g1.getTotalEdges() != g2.getTotalEdges()) {
            return false;
        }

        //Extraer la secuencia de grados de ambos grafos
        int[] grados1 = obtenerSecuenciaGrados(g1);
        int[] grados2 = obtenerSecuenciaGrados(g2);
        
        Arrays.sort(grados1);
        Arrays.sort(grados2);
        
        // Si las secuencias ordenadas son idénticas, probabilidad de isomorfismo
        return Arrays.equals(grados1, grados2);
    }

    // Método auxiliar para obtener la lista de grados de cada nodo
    private int[] obtenerSecuenciaGrados(GraphLink<E> grafo) {
        int n = grafo.size();
        int[] grados = new int[n];
        
        for (int i = 0; i < n; i++) {
            // Obtenr el vértice en la posición i
            E vertice = grafo.get(i).getVertex().getData();
            // cantidad d vecinos que tiene
            grados[i] = grafo.adjacentVertices(vertice).size();
        }
        return grados;
    
    }
    
}