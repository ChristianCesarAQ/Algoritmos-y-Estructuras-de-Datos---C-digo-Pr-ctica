package btree;

import exceptions.ItemNotFound;

public class BTreeTest {
    public static void main(String[] args) {
    	// Un árbol de orden 3 tiene máximo 2 llaves por nodo.
        // 3er elemento causa un split automático.
        BTree<Integer> arbol = new BTree<>(3);

        System.out.println("--- 1. Insertando datos ---");
        int[] datos = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int d : datos) {
            arbol.insert(d);
        }
        System.out.println(arbol.toString());

        System.out.println("--- 2.  Busqueda ---");
        try {
            arbol.search(6);   // Existe
            arbol.search(100); // No existe
        } catch (ItemNotFound e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n--- 3. Probando rango ---");
        // Debería imprimir: 6 7 10 12 17 20
        arbol.searchRange(6, 20);

        System.out.println("\n--- 4. Probando eliminacion ---");
        try {
            System.out.println("Eliminando 6...");
            arbol.remove(6);
            System.out.println(arbol.toString());
            
            System.out.println("Eliminando 10 (raiz)...");
            arbol.remove(10);
            System.out.println(arbol.toString());
        } catch (Exception e) {
            System.out.println("Error en eliminacion: " + e.getMessage());
        }
    }
}