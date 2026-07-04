package ejercicio5;

import ejercicio4.HashLazy;
import hash.Register;

public class HashDynamic extends HashLazy{
	private int elementCount = 0; // Contador de elementos ocupados

    public HashDynamic(int size) {
        super(size);
    }
    
    public double getElementCount() {
		return this.elementCount;
	}
    @Override
    public void insert(Register reg) {
        // Verificar factor de carga antes de insertar
        if ((double) (elementCount + 1) / getSize() > 0.75) {
            System.out.println("Factor de carga superado, haciendo REHASH ");
            rehash();
        }
        
        super.insert(reg);
        elementCount++;
    }

    private void rehash() {
    	int oldSize = getSize();
        Element[] oldTable = getTable();
        
        
        // Calcular el siguiente tamaño de forma dinámica
        int newSize = super.nextPrime(oldSize * 2);
        setSize(newSize); 
        setTable(new Element[newSize]);
        
        // inicializar la nueva tabla
        for (int i = 0; i < newSize; i++) {
            getTable()[i] = new Element();
        }
        this.elementCount = 0;

        //reinsertar los ocupados
        for (int i = 0; i < oldSize; i++) {
            if (oldTable[i].getState() == State.OCCUPIED) {
                this.insert(oldTable[i].getRegister());
                this.elementCount++;
            }
        }
    }
}
