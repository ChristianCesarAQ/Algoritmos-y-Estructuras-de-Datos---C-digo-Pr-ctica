package exceptions; // Indica que está en la carpeta exceptions

// Aquí estaba el error: debe decir "extends Exception"
public class ExceptionIsEmpty extends Exception {
    public ExceptionIsEmpty(String mensaje) {
        super(mensaje); // Pasa el texto a la clase madre Exception
    }
}