package bibliotecadigital;

import java.io.*;
import btree.BTree;

public class Biblioteca {
    private BTree<Libro> arbol;

    public void cargarArchivo(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea = br.readLine();
            if (linea == null) return;
            int orden = Integer.parseInt(linea.trim());
            this.arbol = new BTree<>(orden);

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    Libro nuevo = new Libro(datos[0].trim(), datos[1].trim(), datos[2].trim(), Integer.parseInt(datos[3].trim()));
                    arbol.insert(nuevo);
                }
            }
            System.out.println("Biblioteca cargada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al cargar archivo: " + e.getMessage());
        }
    }

    public void buscarLibro(Libro busqueda) {
        System.out.print("Camino recorrido: ");
        //llamada a método de búsqueda en BTree imprimiendo idNode visitados
        arbol.searchWithLog(busqueda);
    }

    public void mostrarTodos() {
        arbol.inOrder(); //Impresion de libros ordenados por ISBN
    }

    public int getAltura() {
        return arbol.getAltura();
    }

    public int getCantidadTotal() {
        return arbol.countTotal(); //recorre nodos y suma sus counts
    }
}