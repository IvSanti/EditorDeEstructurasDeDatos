package uniquindio.bussines.Listas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import uniquindio.GUI.PanelBotones;
import uniquindio.VO.NodoGrafico;
import uniquindio.VO.ParejaNodos;
import uniquindio.bussines.Constantes;
import uniquindio.bussines.SuperficieDibujo;
import uniquindio.listas.listaEnlazada.ListaEnlazada;
import uniquindio.listas.pila.Pila;

/**
 * 
 * @author Gustavo Andres Ramirez Munoz--Ivan Santigo Castaneda
 *
 */
public class logicaGraficaParaListas {

	private final int REC = 10;

	public static void dormir() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Manda los nodos de la arraylist para que se dibujen
	 * 
	 * @param lista
	 * @param dibujar
	 * @param g
	 */
	public void dibujarListasNodosEncontrados(ArrayList<NodoGrafico> lista, Graphics2D dibujar, Graphics g) {

		for (int i = 0; i < lista.size(); i++) {
			SuperficieDibujo.dibujarNodoEncontrado(lista.get(i).getId(), lista.get(i).getPunto(),
					lista.get(i).getDIFERENCIA(), g);

		}
	}

	/**
	 * Manda los nodos de la arraylist para que se dibujen
	 * 
	 * @param lista
	 * @param dibujar
	 * @param g
	 */
	public void dibujarListasNodos(ListaEnlazada<NodoGrafico> lista, Graphics2D dibujar, Graphics g) {

		dibujar.setStroke(new BasicStroke(3f));
		for (int i = 0; i < lista.size(); i++) {
			lista.get(i).dibujarNodos(g);

		}
	}
	
	/**
	 * Dibuja los enlaces para las parejas de nodos creadas
	 * 
	 * @param lista
	 * @param g
	 */
	public void dibujarEnlancesParejas(ArrayList<ParejaNodos> lista, Graphics g) {

		for (int i = 0; i < lista.size(); i++) {
			SuperficieDibujo.dibujarEnlaces(lista.get(i).getNodo1().isEnlace(),
					lista.get(i).getNodo1().getPuntoLineaInicial(), lista.get(i).getNodo2().getPuntoLineaInicial(), g);

		}
		dibujarSencuenciaNodos(lista, g);

	}

	/**
	 * Dibuja la secuencia de nodos enlazado
	 * 
	 * @param lista
	 * @param g
	 */
	public void dibujarSencuenciaNodos(ArrayList<ParejaNodos> lista, Graphics g) {

		g.setColor(Color.black);
		String cadena = "{ ";
		String c = "";
		for (int i = 0; i < lista.size(); i++) {
			cadena += c + lista.get(i).toString();
			c = ",";
		}
		g.setFont(Constantes.FUENTE1);
		g.drawString(cadena + " }", 20, Constantes.ALTO_ZONA_DIUJABLE - 10);
	}

	/**
	 * Al dar clic se deja el nodo en posicion X y Y,
	 * 
	 * @param clic
	 * @param seleccionado
	 * @param lista
	 * @param nodosSeleccionados
	 * @param verificador
	 * @param panelBotones
	 * @param secuenciaNodos
	 * @param e
	 * @return retorna falso si se creo algun enlace, fasle si de lo contrario
	 */
	public boolean acomodarNodo(int clic, boolean seleccionado, ListaEnlazada<NodoGrafico> lista,
			ArrayList<NodoGrafico> nodosSeleccionados, PanelBotones panelBotones, ArrayList<ParejaNodos> parejaNodos,
			MouseEvent e)

	{
		if (clic != -1) {
			lista.get(clic)
					.setPunto(new Point(e.getX() - Constantes.RADIO_NODO / 2, e.getY() - Constantes.RADIO_NODO / 2));
			lista.get(clic).setPuntoLineaInicial(new Point(e.getX(), e.getY()));

			clic = -1;

		}
		return RevisarEnlace(clic, seleccionado, lista, nodosSeleccionados, panelBotones, parejaNodos, e);
	}

	/**
	 * Despues de dar clic verifica si se dio clic sobre un nodo
	 * 
	 * @param clic
	 * @param seleccionado
	 * @param lista
	 * @param nodosSeleccionados
	 * @param verificador
	 * @param panelBotones
	 * @param secuenciaNodos
	 * @param e
	 *            evento del mouse
	 * @return falso despues de crear el enlace, fasle si de lo contrario
	 */
	public boolean RevisarEnlace(int clic, boolean seleccionado, ListaEnlazada<NodoGrafico> lista,
			ArrayList<NodoGrafico> nodosSeleccionados, PanelBotones panelBotones, ArrayList<ParejaNodos> parejaNodos,
			MouseEvent e) {
		if (!seleccionado) {

			NodoGrafico n = new LogicaNodosGraficos().verificarInterceptoMouse(new Point(e.getX(), e.getY()), lista);
			if (n != null) {
				nodosSeleccionados.add(n);
			}

			if (nodosSeleccionados.size() >= 2) {

				new LogicaNodosGraficos().identificarEnlaces(lista, nodosSeleccionados, parejaNodos);
				nodosSeleccionados.clear();

			}
			if (panelBotones.isPanelActivo()) {

				clic = lista.size() - 1;
			}
		} else {
			seleccionado = false;
		}
		return seleccionado;
	}

	/**
	 * Re dibuja el nuevo nodo que se halla creado mientras esta en el mouse,
	 * hasta que se clic
	 * 
	 * @param clic
	 * @param opcion
	 * @param lista
	 * @param e
	 */
	public void redibujarNodo(int clic, int opcion, ListaEnlazada<NodoGrafico> lista, MouseEvent e) {

		if (clic != -1) {

			lista.get(clic)
					.setPunto(new Point(e.getX() - Constantes.RADIO_NODO / 2, e.getY() - Constantes.RADIO_NODO / 2));

		}
	}

	/**
	 * Retorna el nodo que se intercepte con el mouse para cambiar su posicion
	 * en la pantall
	 * 
	 * @param opcion
	 * @param lista
	 * @param e
	 */
	public NodoGrafico rehubicarNodo(int opcion, ListaEnlazada<NodoGrafico> lista, Point p) {

		Rectangle mouse = new Rectangle(p.x, p.y, REC, REC);
		for (int i = 0; i < lista.size(); i++) {
			if (mouse.intersects(lista.get(i).getColision())) {
				return lista.get(i);
			}
		}

		return null;
	}

	/**
	 * Elimina el nodo que se encuentre seleccionado
	 * 
	 * @param lista
	 * @param nodoSeleccionado
	 */
	public void eliminarNodo(ListaEnlazada<NodoGrafico> lista, ArrayList<NodoGrafico> nodoSeleccionado,
			ArrayList<ParejaNodos> parejaNodos) {
		System.out.println("Entro");

		for (int i = 0; i < lista.size(); i++) {
			lista.get(i).setIndice(i);
			if (!nodoSeleccionado.isEmpty() && lista.get(i).getIndice() == nodoSeleccionado.get(0).getIndice()) {
				lista.remove(lista.get(i));
				i--;
				reEnlazarNodo(nodoSeleccionado, parejaNodos);
				nodoSeleccionado.clear();

			}
		}

	}

	/**
	 * Reacomoda los elances cuando se elimina un nodo
	 * 
	 * @param nodoSeleccionado
	 * @param parejaNodos
	 */
	public void reEnlazarNodo(ArrayList<NodoGrafico> nodoSeleccionado, ArrayList<ParejaNodos> parejaNodos) {

		if (!parejaNodos.isEmpty()) {
			if (parejaNodos.size() == 1) {
				parejaNodos.clear();
				return;
			} else {
				if (nodoSeleccionado.get(0).getIndice() == 0) {
					parejaNodos.remove(0);
					return;
				}
				if (nodoSeleccionado.get(0).getIndice() == parejaNodos.get(parejaNodos.size() - 1).getNodo2()
						.getIndice()) {
					parejaNodos.remove(parejaNodos.size() - 1);
					return;
				}
				for (int i = 0; i < parejaNodos.size() - 1; i++) {

					if (nodoSeleccionado.get(0).getIndice() == parejaNodos.get(i).getNodo2().getIndice()) {
						parejaNodos.get(i).setNodo2(parejaNodos.get(i + 1).getNodo2());
						parejaNodos.remove(i + 1);
						return;
					}

				}
			}
		}
	}

	public ArrayList<NodoGrafico> containsListas(String dato, ListaEnlazada<NodoGrafico> lista, Graphics g) {

		ArrayList<NodoGrafico> nodosContains = new ArrayList<>();

		for (int i = 0; i < lista.size(); i++) {
			if (String.valueOf(lista.get(i).getId()).equals(dato)) {
				nodosContains.add(lista.get(i));
			}

		}

		return nodosContains;
	}

}