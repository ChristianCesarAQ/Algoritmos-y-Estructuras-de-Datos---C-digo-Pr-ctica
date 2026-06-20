package graph;

import java.util.ArrayList;
import exception.ExceptionIsEmpty;
import listlinked.ListLinked;
import listlinked.Node;
import stack.Stack;

public class GraphLink<E> {
	
	private ListLinked<AdjList<E>> graph;
	
	public GraphLink() {
		graph = new ListLinked<>();
	}
	
	public void insertVertex(E data) {
		Vertex<E> vertex = new Vertex<>(data);
		
		graph.addLast(new AdjList<>(vertex));
	}
	
	public AdjList<E> findVertex(E data){
		for(int i = 0; i < graph.size(); i++) {
			AdjList<E> adj = graph.get(i);
			
			if(adj.getVertex().getData().equals(data))
				return adj;
		}
		return null;
	}
	
	public void insertEdge(E origin, E destination) {
		AdjList<E> v1 = findVertex(origin);
		AdjList<E> v2 = findVertex(destination);
		
		if(v1 == null || v2 == null)
			return;
		
		v1.getEdges().addLast(new Edge<>(v2.getVertex()));
		
		v2.getEdges().addLast(new Edge<>(v1.getVertex()));
		
	}
	
	//------ EJERCICIO 1 ------------
	public void insertEdgeWeight(E origin, E destination, int weight) {
        AdjList<E> v1 = findVertex(origin);
        AdjList<E> v2 = findVertex(destination);
        if (v1 == null || v2 == null) return;
        
        // Grafo no dirigido, bidireccional
        v1.getEdges().addLast(new Edge<>(v2.getVertex(), weight));
        v2.getEdges().addLast(new Edge<>(v1.getVertex(), weight));
    }

	public boolean isConexo() {
	    if (graph.isEmptyList()) return true;
	    
	    //nodos visitados
	    ListLinked<E> visitados = new ListLinked<>();
	    
	    //DFS
	    dfs(graph.get(0).getVertex().getData(), visitados);
	    
	    return visitados.size() == graph.size();
	}

	private void dfs(E data, ListLinked<E> visitados) {
	    visitados.addFirst(data); // Marcar como visitado
	    AdjList<E> adj = findVertex(data);
	    
	    for (int i = 0; i < adj.getEdges().size(); i++) {
	        E vecino = adj.getEdges().get(i).getDestination().getData();
	        if (!visitados.search(vecino)) { // Si no ha sido visitado
	            dfs(vecino, visitados);
	        }
	    }
	}

	public Stack<E> Dijkstra(E start, E end) {
	    //Inicialización de estructuras paralelas
	    ListLinked<E> nodos = new ListLinked<>();
	    ListLinked<Integer> distancias = new ListLinked<>();
	    ListLinked<E> padres = new ListLinked<>();

	    for (int i = 0; i < graph.size(); i++) {
	        E data = graph.get(i).getVertex().getData();
	        nodos.addLast(data);
	        distancias.addLast(Integer.MAX_VALUE); // Infinito
	        padres.addLast(null);
	    }

	    // Distancia inicial a start es 0
	    actualizarValor(nodos, distancias, start, 0);

	    // Cola de prioridad (Lista enlazada ordenada mnual)
	    ListLinked<NodeDist<E>> cola = new ListLinked<>();
	    cola.addFirst(new NodeDist<>(start, 0));

	    while (!cola.isEmptyList()) {
	        NodeDist<E> actual = cola.getFirst().getData();
	        cola.removeNode(actual);

	        if (actual.getData().equals(end)) break;

	        AdjList<E> adj = findVertex(actual.getData());
	        for (int i = 0; i < adj.getEdges().size(); i++) {
	            Edge<E> arista = adj.getEdges().get(i);
	            E vecino = arista.getDestination().getData();
	            
	            int distActualVecino = obtenerValor(nodos, distancias, vecino);
	            int nuevaDist = obtenerValor(nodos, distancias, actual.getData()) + arista.getWeight();

	            if (nuevaDist < distActualVecino) {
	                actualizarValor(nodos, distancias, vecino, nuevaDist);
	                actualizarValor(nodos, padres, vecino, actual.getData());
	                insertarOrdenado(cola, new NodeDist<>(vecino, nuevaDist));
	            }
	        }
	    }

	    //Reconstrucción de la ruta (Stack)
	    Stack<E> stack = new Stack<>();
	    E curr = end;
	    while (curr != null) {
	        stack.push(curr);
	        curr = obtenerValor(nodos, padres, curr);
	    }
	    return stack;
	}

	// Método para mantener la cola ordenada (Min-Priority)
	private void insertarOrdenado(ListLinked<NodeDist<E>> cola, NodeDist<E> nuevo) {
	    if (cola.isEmptyList() || nuevo.dist < cola.getFirst().getData().dist) {
	        cola.addFirst(nuevo);
	        return;
	    }
	    Node<NodeDist<E>> current = cola.getFirst();
	    while (current.getNext() != null && current.getNext().getData().dist < nuevo.dist) {
	        current = current.getNext();
	    }
	    Node<NodeDist<E>> temp = current.getNext();
	    current.setNext(new Node<>(nuevo));
	    current.getNext().setNext(temp);
	}
	
  
	// Busca el índice del nodo y devuelve el valor que está en la misma posición en la lista de valores
    private <T> T obtenerValor(ListLinked<E> llNodos, ListLinked<T> llValores, E target) {
        Node<E> nodoActual = llNodos.getFirst();
        Node<T> valorActual = llValores.getFirst();
        
        while (nodoActual != null) {
            if (nodoActual.getData().equals(target)) {
                return valorActual.getData();
            }
            nodoActual = nodoActual.getNext();
            valorActual = valorActual.getNext();
        }
        return null;
    }

    // Busca el índice del nodo y actualiza el valor en esa misma posición
    private <T> void actualizarValor(ListLinked<E> llNodos, ListLinked<T> llValores, E target, T nuevoValor) {
        Node<E> nodoActual = llNodos.getFirst();
        Node<T> valorActual = llValores.getFirst();
        
        while (nodoActual != null) {
            if (nodoActual.getData().equals(target)) {
                valorActual.setData(nuevoValor);
                return;
            }
            nodoActual = nodoActual.getNext();
            valorActual = valorActual.getNext();
        }
    }
    
    public ArrayList<E> shortPath(E start, E end) {
        Stack<E> stack = Dijkstra(start, end);
        ArrayList<E> path = new ArrayList<>();
        while (!stack.isEmpty())
			try {
				path.add(stack.pop());
			} catch (ExceptionIsEmpty e) {
				e.printStackTrace();
			}
        return path;
    }
    

    // Clase auxiliar para la cola de prioridad
    private static class NodeDist<E> implements Comparable<NodeDist<E>> {
        E data;
        int dist;

        public NodeDist(E data, int dist) {
            this.data = data;
            this.dist = dist;
        }
        public E getData() {
        	return this.data;
        }

        @Override
        public int compareTo(NodeDist<E> other) {
            return Integer.compare(this.dist, other.dist);
        }
	
    }
    
    //--- Elimininacion ---
    
    public void removeEdge(E origin, E destination) {
        AdjList<E> v1 = findVertex(origin);
        AdjList<E> v2 = findVertex(destination);

        // Validación: Si alguno no existe, no hay conexión que eliminar
        if (v1 == null || v2 == null) return;

        // Limpieza bidireccional: Buscamos y removemos el Edge en ambas listas
        removeEdgeFromList(v1.getEdges(), destination);
        removeEdgeFromList(v2.getEdges(), origin);
    }

    public void removeVertex(E data) {
        AdjList<E> target = findVertex(data);
        if (target == null) return;

        // Limpieza en cascada: Primero eliminar todas las aristas que llegan a este nodo
        // Recorrido de todo el grafo para "desconectar" el vértice de los demás
        for (int i = 0; i < graph.size(); i++) {
            removeEdgeFromList(graph.get(i).getEdges(), data);
        }

        // remover el vértice de la lista maestra
        graph.removeNode(target); 
    }

    // Método auxiliar para no repetir código de búsqueda en listas enlazadas
    private void removeEdgeFromList(ListLinked<Edge<E>> list, E destinationData) {
        // encontrar el nodo que contiene el Edge hacia destinationData
        Node<Edge<E>> current = list.getFirst();
        
        // Recorrer la lista buscando el Edge cuyo destino coincida
        while (current != null) {
            if (current.getData().getDestination().getData().equals(destinationData)) {
                // encontrado
                list.removeNode(current.getData()); 
                return;
            }
            current = current.getNext();
        }
    }
	
	
	@Override
	public String toString() {
		 StringBuilder sb = new StringBuilder(); 
		 
		    for(int i = 0; i < graph.size(); i++) { 
		 
		        AdjList<E> adj = graph.get(i); 
		 
		        sb.append(adj.getVertex()) 
		          .append(" -> "); 
		 
		        for(int j = 0; j < adj.getEdges().size(); j++) { 
		 
		            sb.append( 
		                adj.getEdges().get(j) 
		            ).append(" "); 
		        } 
		 
		        sb.append("\n"); 
		    } 
		 
		    return sb.toString(); 
	}
	
	
	
}
