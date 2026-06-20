package stack;

import exception.ExceptionIsEmpty;
import listlinked.Node;

public class Stack<E> {
	private Node<E> top;
	public Stack() {
		this.top = null;
	}
	
	public void push(E x) {
		Node<E> newNode = new Node<E>(x);
		newNode.setNext(top); // newNode apuntando a top
		top = newNode;//newNode nuevo top
		
	}
	public E pop() throws ExceptionIsEmpty {
		//caso con pila vacia
		if(isEmpty()) throw new ExceptionIsEmpty("Stack vacia");
		
		//guardar dato de nodo a eliminar y mover apuntador top al siguiente
		E aux = top.getData();
		top = top.getNext();
		return aux;
	}

	public E top() throws ExceptionIsEmpty {
		if(isEmpty()) throw new ExceptionIsEmpty("Stack vacio");
		return top.getData();
	}

	public boolean isEmpty() {
		return top == null;
	}
	
    public String toString() {
        //desde el tope hacia abajo
        StringBuilder sb = new StringBuilder();
        Node<E> aux = top;
        while (aux != null) {
        	
        	if(aux.getNext() != null) {
        		sb.append(aux.getData()).append(" -> ");
        		
        	}else {
        		sb.append(aux.getData());
        	}
            aux = aux.getNext();
        }
        return sb.toString();
    }
}
