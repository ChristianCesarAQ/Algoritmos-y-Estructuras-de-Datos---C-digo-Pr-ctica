package Ejer;

import OpenHash.HashO;
import OpenHash.Register;


public class ejercicio_3 {
	public static void main(String[] args) {

	    HashO hash = new HashO(7);

	    // Inserciones
	    hash.insert(new Register(10, "Juan"));
	    hash.insert(new Register(17, "Ana"));
	    hash.insert(new Register(24, "Luis"));
	    hash.insert(new Register(31, "Rosa"));
	    hash.insert(new Register(5, "Pedro"));
	    hash.insert(new Register(12, "Carla"));

	    System.out.println("TABLA HASH:");
	    hash.printTable();

	    // Búsqueda
	    Register r = hash.search(24);

	    if (r != null) {
	        System.out.println("\nClave 24 encontrada:");
	        System.out.println("Nombre: " + r.getName());
	        System.out.println("Posicion: " + (24 % 7));
	        System.out.println("Nodo: 3");
	    }

	    // Eliminación
	    System.out.println("\nEliminando clave 17...");
	    hash.delete(17);

	    System.out.println("\nTABLA DESPUES DE ELIMINAR 17:");
	    hash.printTable();

	    System.out.println("\nNodos restantes en la cadena:");
	    System.out.println("3 nodos");
	}
}
