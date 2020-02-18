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
		int dato = 0;
		String codigo = "";
		String respuesta = "";

		while (!fin) {
			view.printMenu();

			int option = lector.nextInt();
			switch (option) {
			case 1:
				view.printMessage("--------- \nCargar Datos \nDar cantidad de datos cargados: ");
				modelo.crearLista();
				view.printMessage("Se cargaron los Comparendos en total son:" + modelo.darTamanoLista());
				break;

			case 2:
				view.printMessage(
						"--------- \nMostrar la información del comparendo con el mayor OBJECTID encontrado.");
				respuesta = modelo.getMultaMayorOBID().getProperties().toString();
				if (respuesta != null) {
					view.printMessage("Dato encontrado: " + respuesta);
				} else {
					view.printMessage("Dato NO encontrado");
				}
				break;

			case 3:
				view.printMessage("--------- \nDar tamaño de la pila de comparendos: ");
				dato = Integer.parseInt(lector.next());
				view.printMessage("--------- \nDar el codigo de la infraccion: ");
				codigo = lector.next();

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
