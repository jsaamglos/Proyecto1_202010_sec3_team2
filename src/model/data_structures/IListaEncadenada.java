package model.data_structures;

public interface IListaEncadenada<T>
{

	/**
	 * Retornar el numero de elementos presentes en la lista.
	 * 
	 * @return
	 */
	public int darTamano();

	/**
	 * Se agrega un nuevo elemento a la lista
	 * 
	 * @param dato
	 *            nuevo elemento
	 */
	public void agregarElemento(T dato);

	/**
	 * Buscar un dato en la lista.
	 * 
	 * @param dato
	 *            Objeto de busqueda en el lista
	 * @return True si se encontro el elemento. False de lo contrario
	 */
	public boolean existeElemento(T dato);

	/**
	 * Eliminar un dato de la lista..
	 * 
	 * @param dato
	 *            Objeto de eliminacion en la lista.
	 */
	public void eliminarElemento(T dato);

	/**
	 * Verifica si la lista esta vacia
	 * 
	 * @return True si esta vacia, False de lo contrario
	 */
	public boolean isEmpty();

	/**
	 * Retorna el primer elemento de la lista.
	 * 
	 * @return El primer elemento de la lista.
	 */
	public T darPrimerElemento();

	/**
	 * Retorna el ultimo elemento de la lista.
	 * 
	 * @return El ultimo elemento de la lista.
	 */
	public T darUltimoElemento();

}
