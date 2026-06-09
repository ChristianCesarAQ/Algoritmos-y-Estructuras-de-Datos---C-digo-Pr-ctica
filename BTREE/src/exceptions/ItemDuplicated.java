package exceptions;

public class ItemDuplicated extends Exception {
    public ItemDuplicated(String mensaje) {
        super(mensaje);
    }
}