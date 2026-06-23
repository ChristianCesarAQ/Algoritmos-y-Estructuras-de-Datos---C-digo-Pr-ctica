package OpenHash;
import listlinked.ListLinked;

public class HashO {
	ListLinked<Register>[] table; //array de listas enlazadas
	private int size;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashO(int size) {
		this.size = size;
		this.table = new ListLinked[size];
		
		//inicializacion de posciones con lista enlazada vacia
		for(int i=0; i < size; i++) {
			table[i] = new ListLinked();
		}
	}
	
	
	//funcion hash 
	private int hash(int key) {
		return key % size;
	}
	
	//insertar registro en la posicion adecuada
	public void insert(Register reg) {
		int index = hash(reg.getKey());
		table[index].addLast(reg);
		
	}
	
	//busqueda de registro por clave
	public Register search(int key) {
		
		int index = hash(key);
		
		Register reg = new Register(key, "");
		
		return table[index].find(reg);
	}
	
	//eliminaicon de registro por clave
	public void delete(int key) {
		int index = hash(key);
		
		Register reg = new Register(key, "");
		table[index].removeNode(reg);
		
	}
	
	//mostrar contenido de tabla hash
	public void printTable() {
		for(int i= 0; i < size; i++) {
			System.out.println( i + ": " + table[i]);
		}
		
	}
	
	
	

}
