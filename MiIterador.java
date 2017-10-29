package uniquindio.listas.cola;

import java.util.Iterator;

import uniquindio.listas.Nodo;

public class MiIterador<E> implements Iterator<E> {

	private Nodo<E> dato;

	public MiIterador(Nodo<E> dato) {
		this.dato = dato;
	}

	@Override
	public boolean hasNext() {
		return dato != null;// Mientrassea difernete null
	}

	@Override
	public E next() {
		E aux = dato.getDato();
		dato = dato.getSiguiente();
		return aux;
	}

}