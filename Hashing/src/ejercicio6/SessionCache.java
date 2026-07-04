package ejercicio6;

import java.util.ArrayList;

public class SessionCache {
    private ArrayList<Session>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public SessionCache(int size) {
        this.size = size;
        this.table = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new ArrayList<>();
        }
    }

    // funcion hash para strings: usa el hashCode del token
    private int hash(String token) {
        return Math.abs(token.hashCode()) % size;
    }

    // registra una nueva sesion con tiempo de vida en milisegundos
    public void login(String token, String username, String role, long ttlMs) {
        long expiresAt = System.currentTimeMillis() + ttlMs;
        Session session = new Session(token, username, role, expiresAt);
        int index = hash(token);
        table[index].add(session);
    }

    // retorna la sesion si el token existe y no ha expirado
    public Session validate(String token) {
        int index = hash(token);
        long now = System.currentTimeMillis();
        for (Session s : table[index]) {
            if (s.getToken().equals(token)) {
                return s.isExpired(now) ? null : s;
            }
        }
        return null;
    }

    // elimina la sesion del cache (cierre de sesion explicito)
    public void logout(String token) {
        int index = hash(token);
        table[index].removeIf(s -> s.getToken().equals(token));
    }

    // recorre toda la tabla y elimina las sesiones vencidas
    public void cleanExpired() {
        long now = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            table[i].removeIf(s -> s.isExpired(now));
        }
    }

    // cuenta cuantas sesiones activas quedan en total
    public int countActive() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            count += table[i].size();
        }
        return count;
    }

    public void printTable() {
        for (int i = 0; i < size; i++) {
            System.out.println(i + ": " + table[i]);
        }
    }
}
