package ejercicio4;

import hash.Register;

public class TestEjer_4 {
    public static void main(String[] args) {
        HashLazy hash = new HashLazy(6);

        // Insertar claves: 5, 12, 19, 26
        hash.insert(new Register(5, "A"));
        hash.insert(new Register(12, "B"));
        hash.insert(new Register(19, "C"));
        hash.insert(new Register(26, "D"));
        System.out.println("Hash Table:");
        hash.printTable();

        // Eliminacion lógica de 12
        System.out.println("\nHash Table despues de eliminar 12:");
        hash.delete(12);
        hash.printTable();

        // Busqueda de 19
        System.out.println("\nBuscando 19...");
        System.out.println("Resultado: " + hash.search(19));

        // Reinsertar 33 (ocupa la posición DELETED)
        System.out.println("Reinsertando 33 : ");
        hash.insert(new Register(33, "E"));
        hash.printTable();
        
    }
}