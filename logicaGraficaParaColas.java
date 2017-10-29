package uniquindio.bussines.Colas;

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
import uniquindio.listas.cola.Cola;


/**
 * 
 * @author Gustavo Andres Ramirez Munoz--Ivan Santigo Castaneda
 *
 */
public class logicaGraficaParaColas {

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
	 * @param cola
	 * @param dibujar
	 * @param g
	 */
	public void dibujarListasNodosEncontrados(ArrayList<NodoGrafico> cola, Graphics2D dibujar, Graphics g) {

		for (int i = 0; i < cola.size(); i++) {
			SuperficieDibujo.dibujarNodoEncontrado(cola.get(i).getId(), cola.get(i).getPunto(),
					cola.get(i).getDIFERENCIA(), g);
		}
	}

	/**
	 * Manda los nodos de la arraylist para que se dibujen
	 * 
	 * @param lista
	 * @param dibujar
	 * @param g
	 */
	
	public void dibujarColaNodos(Cola<NodoGrafico> miCola, Graphics2D dibujar, Graphics g) {
		
		dibujar.setStroke(new BasicStroke(3f));
		for (NodoGrafico nodo: miCola) {
			nodo.dibujarNodos(g);
		}
	}
	
	/**
	 * Dibuja los enlaces para las parejas de nodos creadas
	 * 
	 * @param cola
	 * @param g
	 */
	public void dibujarEnlancesParejas(ArrayList<ParejaNodos> cola, Graphics g){

		for (int i = 0; i < cola.size(); i++) {
			SuperficieDibujo.dibujarEnlaces(cola.get(i).getNodo1().isEnlace(),
					cola.get(i).getNodo1().getPuntoLineaInicial(), cola.get(i).getNodo2().getPuntoLineaInicial(), g);
		}
		dibujarSencuenciaNodos(cola, g);
	}

	/**
	 * Dibuja la secuencia de nodos enlazado
	 * 
	 * @param cola
	 * @param g
	 */

	public void dibujarSencuenciaNodos(ArrayList<ParejaNodos> cola, Graphics g){

		g.setColor(Color.black);
		String cadena = "{ ";
		String c = "";
		
		for (int i = 0; i < cola.size(); i++) {
			cadena += c + cola.get(i).toString();
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

	
	public boolean acomodarNodo(int clic, boolean seleccionado, Cola<NodoGrafico> cola,
			ArrayList<NodoGrafico> nodoSeleccionado, PanelBotones panelBotones, ArrayList<ParejaNodos> parejaNodos,
			MouseEvent e) 
	{
		if (clic != -1) {
			cola.peek().setPunto(new Point(e.getX(), e.getY()));
			cola.peek().setPuntoLineaInicial(new Point(e.getX(), e.getY()));
			clic = -1;
		}
		
		return RevisarEnlace(clic, seleccionado, cola, nodoSeleccionado, panelBotones, parejaNodos, e);
	}

	

	/**
	 * Despues de dar clic verifica si se dio clic sobre un nodo
	 * 
	 * @param clic
	 * @param seleccionado
	 * @param cola
	 * @param nodoSeleccionado
	 * @param verificador
	 * @param panelBotones
	 * @param secuenciaNodos
	 * @param e
	 *            evento del mouse
	 * @return falso despues de crear el enlace, fasle si de lo contrario
	 */
	public boolean RevisarEnlace(int clic, boolean seleccionado, Cola<NodoGrafico> cola,
			ArrayList<NodoGrafico> nodoSeleccionado, PanelBotones panelBotones, ArrayList<ParejaNodos> parejaNodos,
			MouseEvent e) {
		
		if (!seleccionado) {

			NodoGrafico n = new LogNodoGrafiColas().verificarInterceptoMouse(new Point(e.getX(), e.getY()), cola);
			if (n != null) {
				nodoSeleccionado.add(n);
			}

			if (nodoSeleccionado.size() >= 2) {

				new LogNodoGrafiColas().identificarEnlaces(cola, nodoSeleccionado, parejaNodos);
				nodoSeleccionado.clear();

			}
			if (panelBotones.isPanelActivo()) {

				clic = cola.size() - 1;
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
	 * @param Cola
	 * @param e
	 */
	public void redibujarNodo(int clic, int opcion, Cola<NodoGrafico> cola, MouseEvent e) {
			
			if (clic != -1) {
				cola.get(clic).setPunto(new Point(e.getX() - Constantes.RADIO_NODO / 2, e.getY() - Constantes.RADIO_NODO / 2));
			}		
	}


	/**
	 * Retorna el nodo que se intercepte con el mouse para cambiar su posicion
	 * en la pantall
	 * 
	 * @param opcion
	 * @param cola
	 * @param e
	 */
	public NodoGrafico rehubicarNodo(int opcion, Cola<NodoGrafico> cola, Point p) {

		Rectangle mouse = new Rectangle(p.x, p.y, REC, REC);
		for (int i = 0; i < cola.size(); i++) {
			if (mouse.intersects(cola.get(i).getColision())) {
				return cola.get(i);
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
	
	public void eliminarNodo(Cola<NodoGrafico> miCola, ArrayList<ParejaNodos> parejaNodos) {
		
				NodoGrafico nodoSelec = miCola.poll();
				reEnlazarNodo(nodoSelec, parejaNodos);
				nodoSelec=null;
	}


	/**
	 * Reacomoda los elances cuando se elimina un nodo
	 * 
	 * @param nodoSelec
	 * @param parejaNodos
	 */
	public void reEnlazarNodo(NodoGrafico nodoSelec, ArrayList<ParejaNodos> parejaNodos) {

		if (nodoSelec!=null && !parejaNodos.isEmpty()) {
			parejaNodos.remove(parejaNodos.size() - 1);
			return;
		}
	}
	
	public ArrayList<NodoGrafico> containsColas(String dato, Cola<NodoGrafico> miCola, Graphics g) {
		
		ArrayList<NodoGrafico> nodosContains = new ArrayList<>();

		for (int i = 0; i < miCola.size(); i++) {
			if (String.valueOf(miCola.get(i).getId()).equals(dato)) {
				nodosContains.add(miCola.get(i));
			}
		}
		return nodosContains;
	}


}