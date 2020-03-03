package controller;

import java.util.Scanner;

import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo */
	private Modelo modelo;

	/* Instancia de la Vista */
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * 
	 * @param capacidad
	 *            tamaNo inicial del arreglo
	 */
	public Controller() {
		view = new View();
		modelo = new Modelo();
	}

	public void run() {
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		// String codigo = "";
		String respuesta = "";

		while (!fin) {
			view.printMenu();

			int option = lector.nextInt();
			switch (option) {
			case 1:
				view.printMessage("--------- \nCargar Datos \nDar cantidad de datos cargados: ");
				modelo.crearLista();
				respuesta = modelo.getMultaMayorOBID().getProperties().toString();
				view.printMessage("Se cargaron los Comparendos en total son:" + modelo.darTamanoLista()
						+ "\nEl objeto con mayor ObjectID es:" + respuesta + "\n" + modelo.zonaMinMax());
				break;

			case 2:
				view.printMessage("--------- \nPrimer Comparendo para la localidad Indicada: ");
				dato = lector.next();
				respuesta = modelo.primerComparendoLocalidad(dato);
				if (respuesta != null) {
					view.printMessage(respuesta);
				} else {
					view.printMessage("Hubo un problema");
				}
				break;

			case 3:
				view.printMessage("--------- \nArreglo con las multas para esa fecha (YYYY/MM/DD) ");
				dato = lector.next();
				respuesta = modelo.comparendosXFecha(dato);
				if (respuesta != null) {
					view.printMessage(respuesta);
				} else {
					view.printMessage("Hubo un problema");
				}
				break;

			case 4:
				view.printMessage("--------- \nComparar Infracciones Por Fechas (YYYY/MM/DD,YYYY/MM/DD): ");
				dato = lector.next();
				respuesta = modelo.compararInfraccionesPorFechas(dato);
				if (respuesta != null) {
					view.printMessage(respuesta);
				} else {
					view.printMessage("Hubo un problema");
				}
				break;
			case 5:
				view.printMessage("--------- \nConsulta Primer comparendo por Infracción Dada ");
				dato = lector.next();
				respuesta = modelo.buscarInfraccion(dato);
				if (respuesta != null) {
					view.printMessage(respuesta);
				} else {
					view.printMessage("Hubo un problema");
				}
				break;
			case 6:
				view.printMessage("--------- \nConsulta los comparendos dado un codigo de Infraccion ");
				dato = lector.next();
				respuesta = modelo.listaPorInfraccion(dato);
				if (respuesta != null) {
					view.printMessage(respuesta);
				} else {
					view.printMessage("Hubo un problema");
				}
				break;
			case 7:
				view.printMessage(
						"--------- \nTabla con comparendos por cada codigo Infraccion para Particulares y Publicos. ");
				respuesta = modelo.compareByInfraccion();
				if (respuesta != null) {
					view.printMessage(respuesta);
				} else {
					view.printMessage("Hubo un problema");
				}
				break;
			case 8:
				view.printMessage("--------- \nuestra el numero de comparendos por codigo Infraccion por localidad. ");
				respuesta = modelo.numeroDeComparendosPorInfraccion();
				if (respuesta != null) {
					view.printMessage(respuesta);
				} else {
					view.printMessage("Hubo un problema");
				}
				break;
			case 9:
				view.printMessage("--------- \nHistograma con el numero de comparendos por Localidad ");
				respuesta = modelo.comparendosPorLocalidad();
				if (respuesta != null) {
					view.printMessage(respuesta);
				} else {
					view.printMessage("Hubo un problema");
				}
				break;
			case 0:
				view.printMessage("--------- \n Hasta pronto !! \n---------");
				lector.close();
				fin = true;
				break;

			default:
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}
}
