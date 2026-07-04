package ejercicio5;

import hash.Register;

public class TestHashDynamic {
    public static void main(String[] args) {
        //tabla con tamaño inicial 7
        HashDynamic tabla = new HashDynamic(7);
        int[] valores = {2, 9, 16, 23, 4, 11};

        System.out.println("--- Insercion de valores a tabla Hash ---");

        for (int val : valores) {
            // Mostrar estado previo antes de insertar
            if ((double) (tabla.getElementCount() + 1) / tabla.getSize() > 0.75) {
                System.out.println("\n--- Tabla ANTES del rehash ---");
                tabla.printTable();
            }
            
            // Inserción
            tabla.insert(new Register(val, "Nombre" + val));
            
            // calculo de alpha correctamente
            double alpha = (double) tabla.getElementCount() / tabla.getSize();
            System.out.println("Insertando " + val + " | Factor de carga: " + String.format("%.2f", alpha));
        }

        // estado final
        System.out.println("\n--- Tabla DESPUES del Rehash ---");
        tabla.printTable();
    }
}