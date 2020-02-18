package model.data_structures;

public class Node<T> implements INode<T> {
	// apuntador al siguiente elemento
	private Node<T> siguienteElemento;

	// apuntador al elemento anterior
	private Node<T> anteriorElamento;

	// elemento
	private T elemento;

	// constructor
	public Node(T elemento, Node<T> anterior) {
		this.elemento = elemento;
		anteriorElamento = anterior;
		if (anterior != null) {
			anterior.setSiguiente(this);
		}
	}

	// da el elemento siguiente
	public Node<T> getSiguiente() {
		return siguienteElemento;
	}

	// da el elemento anterior
	public Node<T> getAnterior() {
		return anteriorElamento;
	}

	// returna el elemento
	public T getElemento() {
		return elemento;
	}

	// asigna el nodo por parametro al siguiete
	@Override
	public void setSiguiente(Node<T> nodo) {
		siguienteElemento = nodo;

	}

	// asigna el nodo por parametro al anterior
	@Override
	public void setAnterior(Node<T> nodo) {
		anteriorElamento = nodo;
	}
}