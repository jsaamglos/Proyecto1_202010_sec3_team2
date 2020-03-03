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

	// Parte B
	// ------------------------------------------------------------------------------------------

	// B1
	// serch for the first "Multa" by infraction type
	public String buscarInfraccion(String infracction) {
		// busca el nodo
		Node<Multa> multa = lista.darPrimeraPosicion();
		while (!multa.getElemento().getProperties().INFRACCION.equals(infracction)) {
			multa = multa.getSiguiente();
		}

		// saca la informacion del nodo
		Multa buscada = multa.getElemento();

		// return
		return buscada.getProperties().toString();
	}

	// B2
	public String listaPorInfraccion(String infraccion) {
		ListaEncadenada<Multa> lista = new ListaEncadenada<>();
		Node<Multa> actual = this.lista.darPrimeraPosicion();
		while (actual != null) {
			Multa e = actual.getElemento();
			if (e.getProperties().INFRACCION.equals(infraccion)) {
				lista.agregarElemento(e);

				Node<Multa> aOrdenar = lista.darUltimaPosicion();

				while (aOrdenar.getAnterior() != null
						&& compararFechas(aOrdenar.getElemento().getProperties().FECHA_HORA,
								aOrdenar.getAnterior().getElemento().getProperties().FECHA_HORA) < 0) {
					boolean siguienteNulo = aOrdenar.getSiguiente() == null;
					aOrdenar.getAnterior().setSiguiente(aOrdenar.getSiguiente());
					if (!siguienteNulo)
						aOrdenar.getSiguiente().setAnterior(aOrdenar.getAnterior());

					aOrdenar.setSiguiente(aOrdenar.getAnterior());
					aOrdenar.setAnterior(aOrdenar.getAnterior().getAnterior());

					if (!siguienteNulo) {
						aOrdenar.getSiguiente().setAnterior(aOrdenar);
						aOrdenar.getAnterior().setSiguiente(aOrdenar);
					}
				}
			}
			actual = actual.getSiguiente();
		}
		Node<Multa> ordenado = lista.darPrimeraPosicion();
		String retorno = "";

		while (ordenado != null) {
			retorno += ordenado.getElemento().getProperties().toString();
			retorno += "\n";
		}
		return retorno;
	}

	// compara dos strings con fechas escritas
	// asume tipo yyyy/mm/dd o yy/mm/dd
	public int compararFechas(String fecha1, String fecha2) {
		// asume equal
		int resultado = 0;

		String[] strArr;

		// remove the "/" in fecha1
		strArr = fecha1.split("/");
		fecha1 = "";

		for (String str : strArr) {
			fecha1 += str;
		}

		// remove "/" in fecha2
		strArr = fecha2.split("/");
		fecha2 = "";

		for (String str : strArr) {
			fecha2 += str;
		}

		// make them integers
		int f1 = Integer.parseInt(fecha1);
		int f2 = Integer.parseInt(fecha2);

		// compare the 2
		if (f1 < f2) {
			resultado = 1;
		} else if (f1 > f2) {
			resultado = -1;
		}

		return resultado;
	}

	// B3
	public String compareByInfraccion() {
		// crear la lista de infracciones
		ListaEncadenada<String> infracciones = new ListaEncadenada<>();

		// vectors with the values of the counts
		int[] publicCount = new int[200];
		int[] privateCount = new int[200];

		// get the first multa
		Node<Multa> actual = lista.darPrimeraPosicion();

		// iterate through the multas
		while (actual != null) {

			// asume that the infraction is in the list
			// iterate on the infractions
			Node<String> infraccion = infracciones.darPrimeraPosicion();
			int i = 0;

			Caracteristica properties = actual.getElemento().getProperties();

			// look for the infraction index
			for (; infraccion != null; i++) {

				// if found add to the respective vector index
				if (infraccion.getElemento() == properties.getInfraccion()) {
					if (properties.getServicio().equals("privado")) {
						privateCount[i]++;
					} else if (properties.getServicio().equals("publico")) {
						publicCount[i]++;
					}

					// if found stop
					break;
				}

				// infraccion: "He bro, i'm not it, are you it?"
				// next: "I dont know, let me check."
				infraccion = infraccion.getSiguiente();
			}

			// infraction was not found
			if (infraccion == null) {
				// if array is full, add more space
				if (i >= privateCount.length) {
					int[] privateTemp = new int[privateCount.length + 200];
					int[] publicTemp = new int[publicCount.length + 200];

					for (int j = 0; i < publicCount.length; i++) {
						privateTemp[j] = privateCount[j];
						publicTemp[j] = publicCount[j];
					}

					privateCount = privateTemp;
					publicCount = publicTemp;
				}

				// if not found, then add a new infraction
				infracciones.agregarElemento(properties.INFRACCION);

				// add the respective values to the vectors
				if (properties.getServicio().equals("privado")) {
					privateCount[i] = 1;
					publicCount[i] = 0;
				} else if (properties.getServicio().equals("publico")) {
					publicCount[i] = 1;
					privateCount[i] = 0;
				}
			}
		}

		// make a string to make the output
		String result = "";

		// add information to the string
		result += "Infraccion\t|publico\t|privado\n";
		Node<String> infraccion = infracciones.darPrimeraPosicion();
		for (int i = 0; infraccion != null; i++) {
			result += infraccion.getElemento() + "\t\t|";
			result += publicCount[i] + "\t\t|";
			result += privateCount[i] + "\n";
			infraccion = infraccion.getSiguiente();
		}

		// DONE
		return result;
	}

	// PARTE A//

	// Req 1A
	public String primerComparendoLocalidad(String pLocalidad) {
		Multa resp = null;
		boolean encontro = false;
		Node<Multa> actual = lista.darPrimeraPosicion();
		while (actual != null && !encontro) {
			if (actual.getElemento().getProperties().getLocalidad().equalsIgnoreCase(pLocalidad)) {
				resp = actual.getElemento();
				encontro = true;
			}
			actual = actual.getSiguiente();
		}
		if (resp == null) {
			return "No existe ningun comparendo para esa localidad";
		}

		return "El comparendo tiene " + resp.getProperties().toString();
	}

	public static void merge(Multa[] arreglo_izq, Multa[] arreglo_der, Multa[] arreglo, int tam_izq, int tam_der) {

		int i = 0, izq = 0, der = 0;
		while (izq < tam_izq && der < tam_der) {

			if (arreglo_izq[izq].getProperties().getInfraccion()
					.compareTo(arreglo_der[der].getProperties().getInfraccion()) > 0) {
				arreglo[i++] = arreglo_izq[izq++];
			} else {
				arreglo[i++] = arreglo_der[der++];
			}
		}
		while (izq < tam_izq) {
			arreglo[i++] = arreglo_izq[izq++];
		}
		while (der < tam_der) {
			arreglo[i++] = arreglo_der[der++];
		}
	}

	public static void mergeSort(Multa[] arreglo, int size) {
		if (size < 2) {
			return;
		}

		int mitad = size / 2;
		Multa[] arreglo_izq = new Multa[mitad];
		Multa[] arreglo_der = new Multa[size - mitad];

		int j = 0;
		for (int i = 0; i < size; ++i) {
			if (i < mitad) {
				arreglo_izq[i] = arreglo[i];
			} else {
				arreglo_der[j] = arreglo[i];
				j = j + 1;
			}
		}

		mergeSort(arreglo_izq, mitad);
		mergeSort(arreglo_der, size - mitad);
		merge(arreglo_izq, arreglo_der, arreglo, mitad, size - mitad);

	}

	// Req 2A
	public String comparendosXFecha(String pFecha) {
		String resp = "";
		int tamanoFecha = 0;
		Node<Multa> actual = lista.darPrimeraPosicion();
		int posicionAgregar = 0;
		while (actual != null) {
			if (actual.getElemento().getProperties().getFecha().equals(pFecha)) {
				tamanoFecha++;

			}
			actual = actual.getSiguiente();
		}
		Multa[] multas = new Multa[tamanoFecha];
		actual = lista.darPrimeraPosicion();
		while (actual != null) {
			if (actual.getElemento().getProperties().getFecha().equals(pFecha)) {
				multas[posicionAgregar] = actual.getElemento();
				posicionAgregar++;
			}
			actual = actual.getSiguiente();
		}
		System.out.println(multas.length);
		mergeSort(multas, multas.length);

		for (int i = 0; i < multas.length; i++) {
			System.out.println(i);
			resp += multas[i].getProperties().toString() + "\n";
		}
		return resp;
	}

	// Req 3A
	public String compararInfraccionesPorFechas(String pFechas) {
		String[] strArr;
		strArr = pFechas.split(",");
		String fecha1 = strArr[0];
		String fecha2 = strArr[1];
		int posicionAAgregar = 0;
		Node<Multa> actual = lista.darPrimeraPosicion();
		String[] comparendos = new String[lista.darTamano()];
		while (actual != null) {
			comparendos[posicionAAgregar] = actual.getElemento().getProperties().getInfraccion();
			posicionAAgregar++;
			actual = actual.getSiguiente();
		}
		System.out.println(comparendos.length);
		mergeSort(comparendos, comparendos.length);
		System.out.println(comparendos + "");
		ListaEncadenada<String> infracciones = new ListaEncadenada<>();
		for (int i = 1; i < comparendos.length; i++) {
			if (!(comparendos[i - 1].compareTo(comparendos[i]) == 0)) {
				infracciones.agregarElemento(comparendos[i - 1]);
			}
			if (i == comparendos.length - 1) {
				infracciones.agregarElemento(comparendos[i]);
			}
		}
		System.out.println(infracciones.darTamano());
		int[] comparendosFecha1 = new int[infracciones.darTamano()];
		int[] comparendosFecha2 = new int[infracciones.darTamano()];

		actual = lista.darPrimeraPosicion();

		while (actual != null) {
			Node<String> infActual = infracciones.darPrimeraPosicion();
			int infraccionARevisar = 0;
			while (infActual != null) {
				if (actual.getElemento().getProperties().getFecha().equals(fecha1)
						&& actual.getElemento().getProperties().getInfraccion().equals(infActual.getElemento())) {
					comparendosFecha1[infraccionARevisar]++;
				} else if (actual.getElemento().getProperties().getFecha().equals(fecha2)
						&& actual.getElemento().getProperties().getInfraccion().equals(infActual.getElemento())) {
					comparendosFecha2[infraccionARevisar]++;
				}
				infActual = infActual.getSiguiente();
				infraccionARevisar++;
			}
			actual = actual.getSiguiente();
		}
		String result = "";
		System.out.println("Salio del Loop");

		result += "Infraccion\t|" + fecha1 + "\t|" + fecha2 + "\n";
		Node<String> infraccion = infracciones.darPrimeraPosicion();
		for (int i = 0; infraccion != null; i++) {
			if (!(comparendosFecha1[i] == 0 && comparendosFecha2[i] == 0)) {
				result += infraccion.getElemento() + "\t\t|";
				result += comparendosFecha1[i] + "\t\t|";
				result += comparendosFecha2[i] + "\n";
			}
			infraccion = infraccion.getSiguiente();
		}

		return result;

	}

	private void mergeSort(String[] comparendos, int length) {
		if (length < 2) {
			return;
		}

		int mitad = length / 2;
		String[] arreglo_izq = new String[mitad];
		String[] arreglo_der = new String[length - mitad];

		int j = 0;
		for (int i = 0; i < length; ++i) {
			if (i < mitad) {
				arreglo_izq[i] = comparendos[i];
			} else {
				arreglo_der[j] = comparendos[i];
				j = j + 1;
			}
		}

		mergeSort(arreglo_izq, mitad);
		mergeSort(arreglo_der, length - mitad);
		merge(arreglo_izq, arreglo_der, comparendos, mitad, length - mitad);

	}

	private void merge(String[] arreglo_izq, String[] arreglo_der, String[] comparendos, int mitad, int tam_der) {
		int i = 0, izq = 0, der = 0;
		while (izq < mitad && der < tam_der) {

			if (arreglo_izq[izq].compareTo(arreglo_der[der]) < 0) {
				comparendos[i++] = arreglo_izq[izq++];
			} else {
				comparendos[i++] = arreglo_der[der++];
			}
		}
		while (izq < mitad) {
			comparendos[i++] = arreglo_izq[izq++];
		}
		while (der < tam_der) {
			comparendos[i++] = arreglo_der[der++];
		}
	}

	// Req 1C
	public String numeroDeComparendosPorInfraccion() {
		System.out.println("ENTRO");
		ListaEncadenada<String> infracciones = new ListaEncadenada<>();

		int[] comparendos = new int[200];

		Node<Multa> actual = lista.darPrimeraPosicion();
		while (actual != null) {
			Node<String> infraccion = infracciones.darPrimeraPosicion();
			int i = 0;

			Caracteristica properties = actual.getElemento().getProperties();

			// look for the infraction index
			for (; infraccion != null; i++) {

				// if found add to the respective vector index
				if (infraccion.getElemento() == properties.getInfraccion()) {
					// add one to the comparendo
					comparendos[i]++;

					// if found stop
					break;
				}

				// infraccion: "He bro, i'm not it, are you it?"
				// next: "I dont know, let me check."
				infraccion = infraccion.getSiguiente();
			}

			// infraction was not found
			if (infraccion == null) {
				// if array is full, add more space
				if (i >= comparendos.length) {
					int[] comparendosTemp = new int[comparendos.length + 200];

					for (int j = 0; i < comparendos.length; i++)
						comparendosTemp[j] = comparendos[j];

					comparendos = comparendosTemp;
				}

				// if not found, then add a new infraction
				infracciones.agregarElemento(properties.INFRACCION);

				// add the respective values to the vectors
				if (properties.getServicio().equals("privado")) {
					comparendos[i] = 1;
				} else if (properties.getServicio().equals("publico")) {
					comparendos[i] = 1;
				}
			}
		}

		// make a string to make the output
		String result = "";
		System.out.println("Salio del Loop");

		// add information to the string
		result += "Infraccion\t|publico\t|privado\n";
		Node<String> infraccion = infracciones.darPrimeraPosicion();
		for (int i = 0; infraccion != null; i++) {
			result += infraccion.getElemento() + "\t\t|";
			result += comparendos[i] + "\n";
			infraccion = infraccion.getSiguiente();
		}

		// DONE
		return result;
	}

	// Req 3C
	public String comparendosPorLocalidad() {
		// localidades vector
		String[] localidades = { "Antonio Nariño", "Chapinero", "Engativa", "Fontivon", "Martires", "San Fernando",
				"Santa Fe", "Teusaquillo", "Usaquen", "Usme" };

		// set vector to 0s
		int[] comparendos = new int[10];
		for (int i = 0; i < localidades.length; i++) {
			comparendos[i] = 0;
		}

		// Iterate over the Multas
		Node<Multa> actual = lista.darPrimeraPosicion();

		while (actual != null) {
			int i = 0;
			Caracteristica properties = actual.getElemento().getProperties();

			// what localidad is the current multa
			for (; i < localidades.length; i++) {
				if (properties.LOCALIDAD.equalsIgnoreCase(localidades[i])) {
					comparendos[i]++;
					break;
				}
			}
		}

		// set the names of the city to
		for (int i = 0; i < localidades.length; i++) {
			while (localidades[i].length() < 16)
				localidades[i] += "-";
			localidades[i] += "|";
		}

		// make the String
		String result = "";
		result += "Aproximacion del numero de comparendos por localidad.\n";

		for (int i = 0; i < localidades.length; i++) {
			result += localidades[i];

			if (comparendos[i] == 0)
				result += "Sin comparendos";

			for (int j = comparendos[i]; j > 0; j -= 50)
				result += "*";

			result += "\n";
		}

		// Out
		return result;
	}
}