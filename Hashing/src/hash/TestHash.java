package hash;

public class TestHash {
	public static void main(String[] args) {
		// 34, 3, 7, 30, 11, 8, 7, 23, 41, 16, 34
		Register r1 = new Register(34, "Jose");
		Register r2 = new Register(3, "James");
		Register r3 = new Register(7, "Terry");
		Register r4 = new Register(30, "Sofia");
		Register r5 = new Register(11, "Mauricio");
		Register r6 = new Register(8, "Greyci");
		Register r7 = new Register(7, "Miguel");
		Register r8 = new Register(23, "Carl");
		Register r9 = new Register(41, "Macarena");
		Register r10 = new Register(16, "Simon");
		Register r11 = new Register(34, "Berto");
		
		
		HashC hash = new HashC(11);
		//insersion de registros
		hash.insert(r1); //indice 1
		hash.insert(r2); //indice 3
		hash.insert(r3); //indice 7
		hash.insert(r4); //indice 8
		hash.insert(r5); //indice 0
		hash.insert(r6); //colision en 8 -> 1 linear probing(a 9)
		hash.insert(r7); //colision en 7 -> 3 linear probing(8,9, 10)
		hash.insert(r8); //colision en 1 -> 1 linear probing(a 2)
		hash.insert(r9); //colision en 8 -> 5 linear probing (9,10,0,1,2, a 4)
		hash.insert(r10); // indice 5
		hash.insert(r11); // colision en 1 -> 5 lienar probing(2,3,4,5,6)
		
		//estado de tabla hash 
		System.out.println("--- Tabla hash ---");
		hash.printTable();
		
		//eliminacion de clave 30
		System.out.println("\n--- Tbla luego de eliminacion de clave 30 ---");
		hash.delete(30);
		System.out.println();
		//estado de tabla hash 
		hash.printTable();
		
		System.out.println("\n--- Busqueda clave 23 ---");
		System.out.println(hash.search(23));
		
	}
}
