package ejercicio6;

public class TestSessionCache {
    public static void main(String[] args) throws InterruptedException {
        SessionCache cache = new SessionCache(7);

        // tres usuarios inician sesion con distintos tiempos de expiracion
        cache.login("abc123", "Rase", "admin", 2000); // expira en 2 seg
        cache.login("xyz789", "Diego", "user", 500);  // expira en 0.5 seg
        cache.login("qwe456", "Vale", "user", 5000);  // expira en 5 seg

        System.out.println("Tabla despues de los logins:");
        cache.printTable();

        Thread.sleep(1000); // se espera para que xyz789 quede vencido

        // validación de tokens
        System.out.println("\nValidando abc123: " + cache.validate("abc123"));
        System.out.println("Validando xyz789 (expirado): " + cache.validate("xyz789"));
        System.out.println("Validando qwe456: " + cache.validate("qwe456"));

        // un usuario cierra sesion explicitamente
        cache.logout("qwe456");
        System.out.println("\nTabla despues del logout de qwe456:");
        cache.printTable();

        // limpieza de sesiones vencidas
        cache.cleanExpired();
        System.out.println("\nTabla despues de cleanExpired():");
        cache.printTable();

        System.out.println("\nSesiones activas restantes: " + cache.countActive());
    }
}
