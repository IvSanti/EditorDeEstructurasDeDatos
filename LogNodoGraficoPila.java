package uniquindio.bussines.Pilas;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import uniquindio.VO.NodoGrafico;
import uniquindio.VO.ParejaNodos;
import uniquindio.bussines.Constantes;
import uniquindio.listas.listaEnlazada.ListaEnlazada;
import uniquindio.listas.pila.Pila;

/**
 * 
 * @author Gustavo Andres Ramirez Munoz--Ivan Santigo Castaneda
 *
 */
public class LogNodoGraficoPila {

	private final int REC = 5;

	/**
	 * Acomoda los nodos que se encuentren encima de otros y los corre hacia un
	 * lado
	 * 
	 * @param lista
	 */
	public void verificarNodosInterceptados(ListaEnlazada<NodoGrafico> lista) {

		for (int i = 0; i < lista.size(); i++) {
			for (int j = 0; j < lista.size(); j++) {

				if (i != j && lista.get(i).getColision().intersects(lista.get(j).getColision())) {
					Point nuevo = new Point(lista.get(j).getPunto().x + Constantes.RADIO_NODO * 2,
							+lista.get(j).getPunto().y);
					lista.get(j).setPunto(nuevo);
				}
			//	verificarUbicacionNodo(lista.get(i));
			}
		}
	}
	
	public void verificarNodosInterceptados(Pila<NodoGrafico> miPila) {
		
		for (int i = 0; i < miPila.size(); i++) {
			for (int j = 0; j < miPila.size(); j++) {

				if (i != j && miPila.get(i).getColision().intersects(miPila.get(j).getColision())) {
					Point nuevo = new Point(miPila.get(j).getPunto().x + Constantes.RADIO_NODO * 2, miPila.get(j).getPunto().y);
					miPila.get(j).setPunto(nuevo);
				}
			//	verificarUbicacionNodo(lista.get(i));
			}
		}
	}

	/**
	 * Verifica que el nodo no se vaya dibujar fuera del rango admitido en
	 * pantalla
	 * 
	 * @param nodo
	 */
	public void verificarUbicacionNodo(NodoGrafico nodo) {

		if (nodo.getColision().x + nodo.getColision().width > Constantes.ANCHO_ZONA_DIUJABLE + REC) {
			nodo.setPunto(
					new Point(Constantes.ANCHO_ZONA_DIUJABLE - nodo.getColision().width + REC, nodo.getPunto().y));
		}
		if (nodo.getColision().x < 10) {
			nodo.setPunto(new Point(nodo.getColision().width / 4 + REC, nodo.getPunto().y));
		}
		if (nodo.getColision().y < 10) {
			nodo.setPunto(new Point(nodo.getPunto().x, nodo.getColision().height / 4 + REC));
		}
		if (nodo.getColision().y + nodo.getColision().height + REC > Constantes.ALTO_ZONA_DIUJABLE) {
			nodo.setPunto(
					new Point(nodo.getPunto().x, Constantes.ALTO_ZONA_DIUJABLE + REC - nodo.getColision().height));
		}

	}

	/**
	 * Cambia el color de un nodo seleccionado
	 * 
	 * @param p
	 * @param pila
	 */
	public NodoGrafico verificarInterceptoMouse(Point p, Pila<NodoGrafico> pila) {

		Rectangle mouse = new Rectangle(p.x, p.y, REC, REC);

		for (int i = 0; i < pila.size(); i++) {
			if (mouse.intersects(pila.get(i).getColision())) {
				pila.get(i).setSeleccionado(true);
				return pila.get(i);
			}
		}
		return null;
	}

	/**
	 * Identifica que par de nodos han sido seleccionado para hacer un enlace
	 * 
	 * @param pila
	 */
	public boolean identificarEnlaces(Pila<NodoGrafico> pila, ArrayList<NodoGrafico> nodoSeleccionado,
			ArrayList<ParejaNodos> parejaNodos) {


		System.out.println("	SELECCIONADOS "+nodoSeleccionado.size());
		
		if(parejaNodos.isEmpty()){
			verificarEnlaces(nodoSeleccionado.get(0), nodoSeleccionado.get(1), parejaNodos);
			nodoSeleccionado.get(0).setSeleccionado(false);
			nodoSeleccionado.get(1).setSeleccionado(false);
		}else{
			
			if (pila.size()-1 == nodoSeleccionado.get(0).getIndice() && nodoSeleccionado.get(1).getIndice() == (parejaNodos.get(parejaNodos.size()-1)).getNodo1().getIndice()) {

					verificarEnlaces(nodoSeleccionado.get(0), nodoSeleccionado.get(1), parejaNodos);
					nodoSeleccionado.get(0).setSeleccionado(false);
					nodoSeleccionado.get(1).setSeleccionado(false);
					return true;
			}
			nodoSeleccionado.get(0).setSeleccionado(false);
			nodoSeleccionado.get(1).setSeleccionado(false);
		}
		return false;
	}


	/**
	 * Verifica si se puede hacer el enlance entre un par de nodos, de ser asi
	 * se realiza el enlance, de lo conrtrario no hace nada
	 * 
	 * @param n1
	 *            nodo de salida
	 * @param n2
	 *            nodo de llegada
	 */
	public void verificarEnlaces(NodoGrafico n1, NodoGrafico n2, ArrayList<ParejaNodos> parejaNodos) {
		
		Point punto1 = n1.getPuntoLineaInicial();
		Point punto2 = n2.getPuntoLineaInicial();
		Rectangle punto2Rec = new Rectangle(n2.getColision());

		//if (n1.equals(n2)) {
			///return;
		//}
		//if (n2.getIndice() != n1.getIndice() + 1) {
			//return;
		//}

		if (parejaNodos.isEmpty()) {
			
			parejaNodos.add(new ParejaNodos(n1, n2));
			crearEnlaces(punto2, punto1, punto2Rec, n2);
			n1.setEnlace(true);
		}
		// El indice del nodo seleccionado debe ser igual al indice del ultimo
		// nodo con el que se hizo un enlace y el indice del nodo con el que se
		// va hacer el enlance debe ser diferente del indice del primer
		// nodo(Evita que sea una lista circular)
		
		else {

			parejaNodos.add(new ParejaNodos(n1, n2));
			n1.setEnlace(true);
			crearEnlaces(punto2, punto1, punto2Rec, n2); 
		}
	}

	public void crearEnlaces(Point punto1, Point punto2, Rectangle punto2Rec, NodoGrafico n1) {

		if (punto2.x < punto1.x && punto2.y < punto1.y) {

			n1.setPuntoLineaFinal(new Point(punto2Rec.x, punto2Rec.y));
			n1.setEnlace(true);
			// System.out.println("1");
			return;

		}
		if (punto2.x > punto1.x && punto2.y > punto1.y) {
			n1.setPuntoLineaFinal(new Point(punto2Rec.x + punto2Rec.width, punto2Rec.y + punto2Rec.height));
			n1.setEnlace(true);
			// System.out.println("2");
			return;
		}
		if (punto2.x < punto1.x && punto2.y > punto1.y) {
			n1.setPuntoLineaFinal(new Point(punto2Rec.x, punto2Rec.y + punto2Rec.height));
			n1.setEnlace(true);
			// System.out.println("3");
			return;
		}
		if (punto2.x > punto1.x) {
			n1.setPuntoLineaFinal(
					new Point(punto2Rec.x + Constantes.RADIO_NODO + 5, punto2Rec.y + punto2Rec.height / 2));
			n1.setEnlace(true);
			// System.out.println("4");
			return;
		}

		if (punto2.y < punto1.y) {
			n1.setPuntoLineaFinal(new Point(punto2Rec.x + punto2Rec.height / 2, punto2Rec.y - 5));
			n1.setEnlace(true);
			// System.out.println("5");
			return;

		}
		if (punto2.y > punto1.y) {
			n1.setPuntoLineaFinal(new Point(punto2Rec.x + punto2Rec.height / 2, punto2Rec.y + punto2Rec.width + 5));
			n1.setEnlace(true);
			// System.out.println("6");
			return;

		} else {

			n1.setPuntoLineaFinal(new Point(punto2Rec.x - 5, punto2Rec.y + Constantes.RADIO_NODO / 2));
			n1.setEnlace(true);
			// System.out.println("6");
			return;

		}
	}

}
