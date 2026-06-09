package btree;

import exceptions.ExceptionIsEmpty;
import exceptions.ItemNotFound;

public class BTree<E extends Comparable<E>> {
    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;
    private int idCounter = 0; 
    
    public BTree(int orden) {
        this.orden = orden;
        this.root = null;
    }
    
    private BNode<E> createNewNode() {
        return new BNode<E>(this.orden, ++idCounter);
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    //INSERCIÓN
    public void insert(E cl) {
        up = false;
        E mediana;
        BNode<E> pnew;
        mediana = push(this.root, cl);
        if (up) {
            pnew = createNewNode();
            pnew.idNode = ++idCounter;
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }

    private E push(BNode<E> current, E cl) {
        int[] pos = new int[1];
        E mediana;
        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        } else {
            boolean fl = current.searchNode(cl, pos);
            if (fl) {
                System.out.println("Item duplicado\n");
                up = false;
                return null;
            }
            mediana = push(current.childs.get(pos[0]), cl);
            if (up) {
                // Pasamos (orden - 1) como límite máximo de llaves
                if (current.nodeFull(this.orden - 1))
                    mediana = dividedNode(current, mediana, pos[0]);
                else {
                    up = false;
                    putNode(current, mediana, nDes, pos[0]);
                }
            }
            return mediana;
        }
    }

    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        for (int i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }
        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }

    private E dividedNode(BNode<E> current, E cl, int k) {
        BNode<E> rd = nDes;
        int posMdna = (k <= this.orden / 2) ? this.orden / 2 : this.orden / 2 + 1;
        
        nDes = createNewNode();
        nDes.idNode = ++idCounter;
        
        for (int i = posMdna; i < this.orden - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }
        
        nDes.count = (this.orden - 1) - posMdna;
        current.count = posMdna;
        
        if (k <= this.orden / 2)
            putNode(current, cl, rd, k);
        else
            putNode(nDes, cl, rd, k - posMdna);
        
        E median = current.keys.get(current.count - 1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;
        return median;
    }
    
    
	//EJER. 1
	public boolean search(E cl) throws ItemNotFound {
	    int[] pos = new int[1];
	    BNode<E> res = searchRecursive(this.root, cl, pos);
	    
	    if (res != null) {
	        System.out.println(cl + " se encuentra en el nodo " + res.idNode + " en la posicion " + pos[0]);
	        return true;
	    } else {
	        throw new ItemNotFound("La clave " + cl + " no existe en el arbol.");
	    }
	}
	
	private BNode<E> searchRecursive(BNode<E> current, E cl, int[] pos) {
	    if (current == null) return null;
	    
	    boolean found = current.searchNode(cl, pos);
	    if (found) return current;
	    
	    // Si no está en este nodo, bajamos al hijo correspondiente
	    return searchRecursive(current.childs.get(pos[0]), cl, pos);
	}
    
    //EJER. 2
	public void searchRange(E min, E max) {
        System.out.println("Rango [" + min + ", " + max + "]:");
        rangeRecursive(this.root, min, max);
        System.out.println();
    }

    private void rangeRecursive(BNode<E> current, E min, E max) {
        if (current == null) return;
        int i = 0;
        while (i < current.count && current.keys.get(i).compareTo(min) < 0) i++;
        for (int j = 0; j <= current.count; j++) {
            if (j >= i) rangeRecursive(current.childs.get(j), min, max);
            if (j < current.count && current.keys.get(j).compareTo(min) >= 0 && current.keys.get(j).compareTo(max) <= 0) {
                System.out.print(current.keys.get(j) + " ");
            }
            if (j < current.count && current.keys.get(j).compareTo(max) > 0) break;
        }
    }
    
    
    //EJER. 3: REMOVE
    public void remove(E cl) throws ExceptionIsEmpty, ItemNotFound {
        if (isEmpty()) throw new ExceptionIsEmpty("El arbol esta vacio.");
        
        // Primero validar si existe
        int[] pos = new int[1];
        if (searchRecursive(this.root, cl, pos) == null) 
            throw new ItemNotFound("La clave " + cl + " no existe.");

        removeRecursive(this.root, cl);
        
        // Si la raíz quedó vacía tras una fusión, ajustar el árbol
        if (this.root.count == 0 && this.root.childs.get(0) != null) {
            this.root = this.root.childs.get(0);
        } else if (this.root.count == 0) {
            this.root = null;
        }
    }

    private void removeRecursive(BNode<E> current, E cl) {
        int[] pos = new int[1];
        boolean found = current.searchNode(cl, pos);

        if (found) {
            if (current.childs.get(0) == null) { // Es una hoja
                removeFromLeaf(current, pos[0]);
            } else {
                // Es nodo interno: reemplazamos con predecesor y eliminamos recursivamente
                E pred = getPredecessor(current, pos[0]);
                current.keys.set(pos[0], pred);
                removeRecursive(current.childs.get(pos[0]), pred);
            }
        } else {
            removeRecursive(current.childs.get(pos[0]), cl);
        }

        // Tras la eliminación, verificar si el hijo está desbalanceado
        if (current.childs.get(pos[0]) != null && current.childs.get(pos[0]).count < (orden / 2)) {
            fixBalance(current, pos[0]);
        }
    }
    
    //auxiliares para remove
    private void removeFromLeaf(BNode<E> node, int pos) {
        for (int i = pos; i < node.count - 1; i++) {
            node.keys.set(i, node.keys.get(i + 1));
        }
        node.count--;
    }

    private E getPredecessor(BNode<E> node, int pos) {
        BNode<E> curr = node.childs.get(pos);
        while (curr.childs.get(curr.count) != null) curr = curr.childs.get(curr.count);
        return curr.keys.get(curr.count - 1);
    }

    private void fixBalance(BNode<E> parent, int pos) {
        // Definimos los vecinos
        BNode<E> leftSibling = (pos > 0) ? parent.childs.get(pos - 1) : null;
        BNode<E> rightSibling = (pos < parent.count) ? parent.childs.get(pos + 1) : null;
        BNode<E> child = parent.childs.get(pos);

        // 1. INTENTAR REDISTRIBUCIÓN (ROBAR)
        if (leftSibling != null && leftSibling.count > (orden / 2)) {
            // Rotación derecha: le quitamos una llave al hermano izquierdo
            // Mover llave del padre al hijo, y llave del hermano al padre
            rotateRight(parent, pos, leftSibling, child);
        } else if (rightSibling != null && rightSibling.count > (orden / 2)) {
            // Rotación izquierda: le quitamos una llave al hermano derecho
            rotateLeft(parent, pos, rightSibling, child);
        } 
        // 2. FUSIÓN (MERGE) - Si ninguno pudo prestar
        else {
            if (leftSibling != null) {
                merge(parent, pos - 1, leftSibling, child);
            } else {
                merge(parent, pos, child, rightSibling);
            }
        }
    }
    
	//metodos aux para fixbalance()
 
    // Mover una clave del hermano izquierdo al hijo (el nodo pobre)
    private void rotateRight(BNode<E> parent, int pos, BNode<E> sibling, BNode<E> child) {
        //1 Desplazar claves del hijo a la derecha para hacer espacio
        for (int i = child.count; i > 0; i--) child.keys.set(i, child.keys.get(i - 1));
        child.keys.set(0, parent.keys.get(pos - 1)); // Bajar llave del padre
        
        //2 Subir llave del hermano al padre
        parent.keys.set(pos - 1, sibling.keys.get(sibling.count - 1));
        
        //3 Mover hijo del hermano al nodo pobre (si no es hoja)
        if (sibling.childs.get(sibling.count) != null) {
            for (int i = child.count + 1; i > 0; i--) child.childs.set(i, child.childs.get(i - 1));
            child.childs.set(0, sibling.childs.get(sibling.count));
        }
        
        sibling.count--;
        child.count++;
    }

    // Mover una clave del hermano derecho al hijo
    private void rotateLeft(BNode<E> parent, int pos, BNode<E> sibling, BNode<E> child) {
        child.keys.set(child.count, parent.keys.get(pos)); // Bajar llave del padre
        parent.keys.set(pos, sibling.keys.get(0)); // Subir llave del hermano
        
        // Desplazar claves del hermano a la izquierda
        for (int i = 0; i < sibling.count - 1; i++) sibling.keys.set(i, sibling.keys.get(i + 1));
        
        if (sibling.childs.get(0) != null) {
            child.childs.set(child.count + 1, sibling.childs.get(0));
            for (int i = 0; i < sibling.count; i++) sibling.childs.set(i, sibling.childs.get(i + 1));
        }
        
        sibling.count--;
        child.count++;
    }
    
    private void merge(BNode<E> parent, int pos, BNode<E> left, BNode<E> right) {
        // 1 Bajar la llave del padre al nodo izquierdo
        left.keys.set(left.count, parent.keys.get(pos));
        
        // 2 Copiar claves y punteros del derecho al izquierdo
        for (int i = 0; i < right.count; i++) {
            left.keys.set(left.count + 1 + i, right.keys.get(i));
            left.childs.set(left.count + 1 + i, right.childs.get(i));
        }
        left.childs.set(left.count + right.count + 1, right.childs.get(right.count));
        
        left.count += right.count + 1;
        
        // 3 Eliminar la llave y el puntero del nodo derecho en el padre
        for (int i = pos; i < parent.count - 1; i++) {
            parent.keys.set(i, parent.keys.get(i + 1));
            parent.childs.set(i + 1, parent.childs.get(i + 2));
        }
        parent.count--;
    }
    
    
    public String toString() {
        if (isEmpty()) return "BTree is empty...";
        return writeTree(this.root, 0);
    }

    private String writeTree(BNode<E> current, int level) {
        if (current == null) return "";
        String s = "";
        // Indentación para visualizar niveles (Jerarquía)
        for (int i = 0; i < level; i++) s += "    ";
        s += current.toString() + "\n";
        
        // Llamada recursiva a los hijos
        for (int i = 0; i <= current.count; i++) {
            s += writeTree(current.childs.get(i), level + 1);
        }
        return s;
    }
}
