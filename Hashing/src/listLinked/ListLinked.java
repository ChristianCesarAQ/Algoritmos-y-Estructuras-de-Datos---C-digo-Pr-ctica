package listlinked;

public class ListLinked<T> {
    Node<T> first;
    Node<T> last;
    int size = 0;
    
    public Node<T> getFirst() {
    	return this.first;
    }

    public boolean isEmptyList() { return first == null; }

    public void addFirst(T x) {
        Node<T> newNode = new Node<>(x);
        
        if(isEmptyList()) {
        	first = last = newNode;
        }else {
        	newNode.next = first;
            first = newNode;
        }
        
        size++;
    }

    public void addLast(T x) {
    	Node<T> newNode = new Node<>(x);
        if (isEmptyList()) {
        	first = last = newNode; 
        }else {
        	last.next = newNode;
        	last = newNode;
        }
       
        size++;
    }

    public boolean search(T x) {
        Node<T> current = first;
        while (current != null) {
            if (current.data.equals(x)) return true;
            current = current.next;
        }
        return false;
    }

    public boolean removeNode(T x) {
        if (isEmptyList()) return false;
        if (first.data.equals(x)) { 
        	first = first.next; 
        	if(first == null) {
        		last = null;
        	}
        	size--;
        	return true; 
        }
        
        Node<T> current = first;
        while (current.next != null && !current.next.data.equals(x)) 
        	current = current.next;
        
        if (current.next != null) {
        	if(current.next == last) {
        		last = current;
        	}
            current.next = current.next.next;
            size--;
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }
    
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> current = first;
        for (int i = 0; i < index; i++) 
        	current = current.next;
        return current.data;
    }

    public void print() {
        Node<T> current = first;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
    
    public T find(T x) {

        Node<T> current = first;

        while(current != null) {

            if(current.data.equals(x))
                return current.data;

            current = current.next;
        }

        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = getFirst();
        
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) sb.append(", ");
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }


}
