package hash;

//clase que representa un registro con una clave y nombre
public class Register {
	private int key;
	private String name;
	
	//constructor inicilizando registro con clave y nombre
	public Register(int key, String name) {
		this.key = key;
		this.name = name;
	}
	
	//return de clave de registro
	public int getKey() {
		return this.key;
	}
	
	//return de nombre del registro
	public String getName() {
		return this.name;
	}
	
	//objeto register como texto
	public String toString() {
		return "(" + key + ", "+ name + ")";
	}
}
