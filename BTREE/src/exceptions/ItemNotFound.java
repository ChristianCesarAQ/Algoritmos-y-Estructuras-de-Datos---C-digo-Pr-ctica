package exceptions;

public class ItemNotFound extends Exception {
    public ItemNotFound(String mensaje) {
        super(mensaje);
    }
}