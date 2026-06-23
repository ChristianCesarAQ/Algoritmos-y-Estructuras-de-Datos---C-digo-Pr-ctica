package OpenHash;

public class TestHashO {
	public static void main(String[] args) {
		
		HashO hashTable = new HashO(11);
		
		//insercion de registros
		hashTable.insert(new Register(3, "Will"));
		hashTable.insert(new Register(36, "Carl"));
		hashTable.insert(new Register(1, "May"));
		hashTable.insert(new Register(46, "Magui"));
		hashTable.insert(new Register(2, "Ney"));
		hashTable.insert(new Register(10, "Julio"));
		hashTable.insert(new Register(5, "Bran"));
		hashTable.insert(new Register(28, "Gustavo"));
		hashTable.insert(new Register(25, "Jose"));
		
		//estado actual de hash table
		System.out.println("--- Hash Table ---");
		hashTable.printTable();
		
		// busqueda
		System.out.println("\n--- Busqueda de clave 25 ---");
		System.out.println(hashTable.search(25));
		
		//eliminacion
		System.out.println("\n--- Eliminacion de clave 25 ---");
		hashTable.delete(25);
		//estado actual de hash table
		System.out.println("\n--- Hash Table despues de eliminacion de clave 25 ---");
		hashTable.printTable();
	}
}
