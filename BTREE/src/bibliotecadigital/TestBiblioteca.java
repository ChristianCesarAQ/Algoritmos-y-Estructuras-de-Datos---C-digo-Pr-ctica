package bibliotecadigital;

public class TestBiblioteca {
    public static void main(String[] args) {
        Biblioteca biblio = new Biblioteca();
        
        //Carga de datos
        biblio.cargarArchivo("biblioteca.txt");
        
        //Mostrar todos los libros ordenados (In-Order)
        System.out.println("\n--- Lista de libros ordenados por ISBN ---");
        biblio.mostrarTodos();
        
        //altura del árbol
        System.out.println("\n--- Altura del Arbol B ---");
        System.out.println("Altura actual: " + biblio.getAltura());
        
        //cantidad total de libros
        System.out.println("\n--- Cantidad total de libros ---");
        System.out.println("Total: " + biblio.getCantidadTotal());
        
        //Prueba de búsqueda con camino
        System.out.println("\n--- BUsqueda de libro, (9780201633610) ---");
        biblio.buscarLibro(new Libro("9780201633610", "", "", 0));
        
    }
}