package ejercicio4;

import hash.Register;
public class HashLazy {

    public enum State { EMPTY, OCCUPIED, DELETED }

    public static class Element {
        private Register register;
        private State state;

        public Element() {
            this.setState(State.EMPTY);
        }

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public Register getRegister() {
			return register;
		}

		public void setRegister(Register register) {
			this.register = register;
		}
    }

    private Element[] table;
    private int size;
    
    public int getSize() { return this.size; }
    protected Element[] getTable() { return this.table; }
    protected void setTable(Element[] newTable) { this.table = newTable; }
    protected void setSize(int newSize) { this.size = newSize; }
    

    public HashLazy(int size) {
        this.size = nextPrime(size); // calculo de num. primo más cercano hacia arriba
        table = new Element[this.size];
        for (int i = 0; i < this.size; i++) {
            table[i] = new Element();
        }
    }

    public int hash(int key) {
        return key % size;
    }

    public void insert(Register reg) {
        int index = hash(reg.getKey());
        linearProbing(index, reg);
    }

    //busqueda 
    public Register search(int key) {
        int index = hash(key);
        int i = 0;
        int curr = index;

        //buscr mientras no sea EMPTY
        while (i < size && table[curr].getState() != State.EMPTY) {
            if (table[curr].getState() == State.OCCUPIED && table[curr].getRegister().getKey() == key) {
                return table[curr].getRegister();
            }
            curr = (curr + 1) % size;
            i++;
        }
        return null;
    }

    public void delete(int key) {
        int index = hash(key);
        int i = 0;
        int curr = index;

        while (i < size && table[curr].getState() != State.EMPTY) {
            if (table[curr].getState() == State.OCCUPIED && table[curr].getRegister().getKey() == key) {
                table[curr].setState(State.DELETED); // Eliminación logic
                table[curr].setRegister(null); 
                return;
            }
            curr = (curr + 1) % size;
            i++;
        }
    }

    public void printTable() {
        for (int i = 0; i < size; i++) {
            System.out.print(i + ": [" + table[i].getState() + "] ");
            if (table[i].getState() == State.OCCUPIED) {
                System.out.println(table[i].getRegister());
            } else {
                System.out.println();
            }
        }
    }
    
    //linear probing
    public void linearProbing(int index, Register reg) {
    	
    	int firstDeletedIndex = -1;
    	   
    	int i = 0;
        int curr = index;
        while (i < size && table[curr].getState() != State.EMPTY) {
            if (table[curr].getState() == State.DELETED && firstDeletedIndex == -1) {
                firstDeletedIndex = curr;
            }
            if (table[curr].getState() == State.OCCUPIED && table[curr].getRegister().getKey() == reg.getKey()) {
                System.out.println("La clave " + reg.getKey() + " ya existe.");
                return;
            }
            curr = (curr + 1) % size;
            i++;
        }

        //Insertar en celda DELETED encontrada o en la primera libre
        int target = (firstDeletedIndex != -1) ? firstDeletedIndex : curr;
        table[target].setRegister(reg);
        table[target].setState(State.OCCUPIED);
    }
    
    //Cuadratic probing
    public void quadraticProbing(int index, Register reg) {
        int i = 0;
        int curr = index;

        // Busque da poscion donde insertar : (hash(key) + i^2) % size
        while (i < size) {
            curr = (index + (i * i)) % size;

            if (table[curr].getState() != State.OCCUPIED) {
                // Se encontró una posición EMPTY o DELETED
                table[curr].setRegister(reg);
                table[curr].setState(State.OCCUPIED);
                return;
            } else if (table[curr].getRegister().getKey() == reg.getKey()) {
                System.out.println("La clave " + reg.getKey() + " ya existe.");
                return;
            }
            
            i++;
        }
        System.out.println("Tabla llena o no se encontró posición disponible para: " + reg.getKey());
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
    protected int nextPrime(int n) {
        while (!isPrime(n)) {
            n++;
        }
        return n;
    }
}