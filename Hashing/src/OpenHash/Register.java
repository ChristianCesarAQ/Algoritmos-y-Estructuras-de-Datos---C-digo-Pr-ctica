package OpenHash;

//clase que representa un registro con una clave y nombre
public class Register {
	private int key;
	private String name;
	
	public Register(int key, String name) {
		this.key = key;
		this.name = name;
	}
	
	//getters
	public int getKey() {
		return this.key;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "(" + key + ", "+ name + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj instanceof Register) {
	        Register other = (Register) obj;
	        return this.key == other.key;
	    }
	    return false;
	}
}

