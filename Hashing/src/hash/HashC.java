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
		this.size = nextPrime(size);
		table = new Element[this.size];
		
		//crear objetos Element
		for(int i = 0; i < this.size; i++) {
	        table[i] = new Element();
	    }
	}
		
	//funcion hash para calcular indice a partir de clave
	public int hash(int key) {
		return key % size;
	}
	
	//Método para insertar un nuevo registro a la tabla hash (linear probing)
	public void insertLinealProbing(Register reg) {
		
		int index = hash(reg.getKey());

	    if (!table[index].isAvailable) {
	        index = linearProbing(index);
	    }

	    if (index == -1) {
	        System.out.println("Tabla llena");
	        return;
	    }

	    table[index].register = reg;
	    table[index].isAvailable = false;
	}
	
	public void insertCuadraticProbing(Register reg) {
		
		int index = hash(reg.getKey());

	    if (!table[index].isAvailable) {
	        index = quadraticProbing(index);
	    }

	    if (index == -1) {
	        System.out.println("Tabla llena");
	        return;
	    }

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
	
	public int linearProbing(int index) {
		 int count = 0;

		 while (!table[index].isAvailable && count < size) {
		     index = (index + 1) % size;
		     count++;
		 }

		 if (count == size) {
			 return -1;
		 } 

		 return index;
	}
			
	
	private int quadraticProbing(int index) {

		int original = index;
	    int i = 1;

	    while (!table[index].isAvailable && i < size) {
	        index = (original + i*i) % size;
	        i++;
	    }

	    if (!table[index].isAvailable)
	        return -1;

	    return index;
	}
	
	// aux. method para verificar si un número es primo
    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    //econtrar siguiente primo cercano hacia arriba, si el numero original no es primo
    private int nextPrime(int n) {
        while (!isPrime(n)) {
            n++;
        }
        return n;
    }

}
