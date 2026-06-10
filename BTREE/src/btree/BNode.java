package btree;

import java.util.ArrayList;

import exceptions.ItemNotFound;

public class BNode<E> {
	
	protected ArrayList<E> keys;
	protected ArrayList<BNode<E>> childs;
	protected int count;
	public int idNode;
	
	public BNode(int n, int id) {
		this.keys = new ArrayList<E>(n);
		this.childs = new ArrayList<BNode<E>>(n);
		
		this.count = 0;
		this.idNode = id;
		
		for(int i=0; i < n; i++){ 
			this.keys.add(null);
			this.childs.add(null);
		}
	}
	
	
	//Check if the current node is full
	public boolean nodeFull (int maxKeys) {
		//count, numbers of current keys 
		//is full, if count = n - 1
		return this.count == maxKeys;
	}
	
	//Check if the current node is empty
	public boolean nodeEmpty () {
		return this.count == 0;
	}
	
	//Search for a key in the current node, if found it returns true and
	//the position where it is located, otherwise, returns false and the
	//position of the child where it should descend.
	public boolean searchNode (E key, int[] pos) {
		pos[0] = 0; // key or index where to search
		
		while(pos[0] < count && ((Comparable<E>) key).compareTo(keys.get(pos[0])) > 0) {
			pos[0]++;
		}
		if (pos[0] < count && ((Comparable<E>) key).compareTo(keys.get(pos[0])) == 0) {
	        return true; // La llave existe en este nodo
	    } else {
	        return false; // La llave no está, pero pos[0] indica el índice del hijo donde debe seguir
	    }
	}
	
	//Return the keys found in the node.
	@Override
    public String toString() {
        //IdNodo | [claves...]
        StringBuilder sb = new StringBuilder();
        sb.append("IdNodo: ").append(this.idNode).append(" | Claves: [");
        for (int i = 0; i < count; i++) {
            sb.append(keys.get(i));
            if (i < count - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
	
}
