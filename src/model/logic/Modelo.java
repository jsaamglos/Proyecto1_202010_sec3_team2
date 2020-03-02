package model.logic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import model.data_structures.ListaEncadenada;
import model.data_structures.Node;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */

	private ListaEncadenada<Multa> lista;

	private PrimeraClase prClase;
	private final static String path2 = "./data/comparendos_dei_2018_small.geojson";

	private final static String path = "./data/comparendos_dei_2018.geojson";

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo() {
		Gson gson = new Gson();
		lista = new ListaEncadenada<Multa>();
		try {
			FileInputStream inputStream = new FileInputStream(path);
			InputStreamReader ISReader = new InputStreamReader(inputStream);
			BufferedReader bf = new BufferedReader(ISReader);
			PrimeraClase pc = gson.fromJson(bf, PrimeraClase.class);
			prClase = pc;
			System.out.println(pc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Se crea la lista con base en los datos cargados
	 */

	public void crearLista() {
		Multa[] multas = prClase.getFeatures();
		for (Multa multa : multas) {
			agregar(multa);
		}
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo
	 * 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamanoLista() {
		return lista.darTamano();
	}

	/**
	 * Requerimiento de agregar dato
	 * 
	 * @param dato
	 */

	public void agregar(Multa dato) {

		lista.agregarElemento(dato);
	}

	/**
	 * Requerimiento eliminar dato
	 * 
	 * @param dato
	 *            Dato a eliminar
	 * @return dato eliminado
	 */

	public void eliminar(Multa dato) {
		lista.eliminarElemento(dato);
	}

	public Multa getMultaMayorOBID() {
		return lista.darUltimoElemento();
	}

	public String zonaMinMax() {
		double minLat = 100;
		double maxLat = 0;
		double minLon = 100;
		double maxLon = 0;
		Node<Multa> actual = lista.darPrimeraPosicion();
		while (actual != null) {
			if (actual.getElemento().getGeometry().getCoord()[0] < minLat) {
				minLat = actual.getElemento().getGeometry().getCoord()[0];
			} else if (actual.getElemento().getGeometry().getCoord()[0] > maxLat) {
				maxLat = actual.getElemento().getGeometry().getCoord()[0];
			}
			if (actual.getElemento().getGeometry().getCoord()[1] < minLon) {
				minLon = actual.getElemento().getGeometry().getCoord()[1];
			} else if (actual.getElemento().getGeometry().getCoord()[1] > maxLon) {
				maxLon = actual.getElemento().getGeometry().getCoord()[1];
			}
			actual = actual.getSiguiente();

		}

		return "La minima Latitud es: " + minLat + "\nLa minima Longitud es: " + minLon + "\nLa minima Latitud es:"
				+ maxLat + "\nLa maxima Longitud es:" + maxLon;
	}

	//Parte B
	//------------------------------------------------------------------------------------------

	//B1
	//serch for the first "Multa" by infraction type
	public String buscarInfraccion(String infracction)
	{
		//busca el nodo
		Node<Multa> multa = lista.darPrimeraPosicion();
		while(!multa.getElemento().getProperties().INFRACCION.equals(infracction))
		{
			multa = multa.getSiguiente();
		}

		//saca la informacion del nodo
		Multa buscada = multa.getElemento();

		//return
		return buscada.toString();
	}

	//B2
	public String listaPorInfraccion(String infraccion)
	{
		ListaEncadenada<Multa> lista = new ListaEncadenada<>();
		Node<Multa> actual = this.lista.darPrimeraPosicion();
		while(actual != null)
		{
			Multa e = actual.getElemento();
			if(e.getProperties().INFRACCION.equals(infraccion))
			{
				lista.agregarElemento(e);

				Node<Multa> aOrdenar = lista.darUltimaPosicion();

				while(aOrdenar.getAnterior() != null && 
					compararFechas(aOrdenar.getElemento().getProperties().FECHA_HORA, 
						aOrdenar.getAnterior().getElemento().getProperties().FECHA_HORA) < 0)
				{
					boolean siguienteNulo = aOrdenar.getSiguiente() == null;
					aOrdenar.getAnterior().setSiguiente(aOrdenar.getSiguiente());
					if(!siguienteNulo)
						aOrdenar.getSiguiente().setAnterior(aOrdenar.getAnterior());

					aOrdenar.setSiguiente(aOrdenar.getAnterior());					
					aOrdenar.setAnterior(aOrdenar.getAnterior().getAnterior());

					if(!siguienteNulo)
						aOrdenar.getSiguiente().setAnterior(aOrdenar);
					aOrdenar.getAnterior().setSiguiente(aOrdenar);
				}
			}
		}
		Node<Multa> ordenado = lista.darPrimeraPosicion();
		String retorno = "";

		while(ordenado != null)
		{
			retorno += ordenado.getElemento().getProperties().toString();
			retorno += "\n";
		}
		return retorno;
	}

	public int compararFechas(String fecha1, String fecha2)
	{
		//asume equal
		int resultado = 0;

		return resultado;
	}
}