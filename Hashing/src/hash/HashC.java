package hash;

//implementacion de tabla hash usando hash cerrado (linear probing)
public class HashC {
	
	//clase interna para representar una celda de la tabla hash
	private static class Element {
		Register register; 
		boolean isAvailable;
		
		public Element() {
			this.register = null;
			this.isAvailable = true; //celda disponible inicialmente
		}
	}
	
	private Element[] table;//tabla hash
	private int size; 		//tañamno de tabla hash
		
	public HashC(int size) {
		this.size = size;
		table = new Element[size];
		
		//crear objetos Element
		for(int i = 0; i < size; i++) {
	        table[i] = new Element();
	    }
	}
		
	//funcion hash para calcular indice a partir de clave
	public int hash(int key) {
		return key % size;
	}
	
	//Método para insertar un nuevo registro a la tabla hash (linear probing)
	public void insert(Register reg) {
		int index = hash(reg.getKey());
		
		while(!table[index].isAvailable) {
			index = (index + 1) % size;
		}
		
		/*if(search(reg.getKey()) != null){
		    System.out.println("La clave ya existe");
		    return;
		}
		*/
		table[index].register = reg;
		table[index].isAvailable = false;
	}
		
		
	//método para buscar un registro en la tabla por clave (linear probing)
	public Register search(int key) {
		
		int index = hash(key);
		
		while(!table[index].isAvailable) {
			if(table[index].register.getKey() == key) {
				return table[index].register;
			}
			
			index = (index + 1) % size;
		}
		return null;
	}
		
	// Método para eliminar un registro de forma logica
	public void delete(int key) {
		int index = hash(key);
		while(!table[index].isAvailable) {
			if(table[index].register.getKey() == key) {
				table[index].register = null;
				table[index].isAvailable = true;
				return;
			}
			
			index = (index + 1) % size;
		}
		
	}
		
	//método para imprimir estado actual de la tabla hash
	public void printTable() {
		for(int i = 0; i < size; i++) {
			if (table[i].isAvailable) {
	            System.out.println(i + ": vacio");
	        } else {
	            System.out.println(
	                i + ": " +
	                table[i].register.getKey() +
	                " - " +
	                table[i].register.getName()
	            );
	        }
		}
	}
		
		
}
