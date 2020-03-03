package view;

import model.logic.Modelo;

public class View {
	/**
	 * Metodo constructor
	 */
	public View() {

	}

	public void printMenu() {
		System.out.println("1. Cargar Datos en la Lista Encadenada");
		System.out.println("2. Retorna el primer comparendo para la localidad indicada");
		System.out.println("3. Arreglo con las multas para esa fecha (YYYY/MM/DD)");
		System.out.println("4. Comparar Infracciones Por Fechas (YYYY/MM/DD,YYYY/MM/DD)");
		System.out.println("5. Consulta Primer comparendo por Infracción Dada");
		System.out.println("6. Consulta los comparendos dado un codigo de Infraccion");
		System.out.println("7. Tabla con comparendos por cada codigo Infraccion para Particulares y Publicos.");
		System.out.println("8. Muestra el numero de comparendos por codigo Infraccion en una Localidad dada.");
		System.out.println("9. Histograma con el numero de comparendos por Localidad.");
		System.out.println("0. Exit");
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}

	public void printModelo(Modelo modelo) {
		// TODO implementar
	}
}
