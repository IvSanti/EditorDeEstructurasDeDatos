package uniquindio.bussines.Pilas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
import uniquindio.listas.listaEnlazada.ListaEnlazada;
import uniquindio.listas.pila.Pila;

/**
 * 
 * @author Gustavo Andres Ramirez Munoz--Ivan Santigo Castaneda
 *
 */
public class logicaGraficaParaPilas {

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
	
	public void dibujarPilaNodos(Pila<NodoGrafico> miPila, Graphics2D dibujar, Graphics g) {
		
		dibujar.setStroke(new BasicStroke(3f));
		
		for (NodoGrafico nodo: miPila) {
			nodo.dibujarNodos(g);
		}
	}
	/**
	 * Dibuja los enlaces para las parejas de nodos creadas
	 * 
	 * @param pila
	 * @param g
	 */
	public void dibujarEnlancesParejas(ArrayList<ParejaNodos> pila, Graphics g) {

		for (int i = 0; i < pila.size(); i++) {
			SuperficieDibujo.dibujarEnlaces(pila.get(i).getNodo1().isEnlace(),
					pila.get(i).getNodo1().getPuntoLineaInicial(), pila.get(i).getNodo2().getPuntoLineaInicial(), g);

		}
		dibujarSencuenciaNodos(pila, g);
	}

	/**
	 * Dibuja la secuencia de nodos enlazado
	 * 
	 * @param pila
	 * @param g
	 */
	public void dibujarSencuenciaNodos(ArrayList<ParejaNodos> pila, Graphics g) {

		g.setColor(Color.black);
		String cadena = "{ ";
		String c = "";
		
		for (int i = 0; i < pila.size(); i++) {
			cadena += c + pila.get(i).toString();
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

	
	public boolean acomodarNodo(int clic, boolean seleccionado, Pila<NodoGrafico> pila,
			ArrayList<NodoGrafico> nodoSeleccionado, PanelBotones panelBotones, ArrayList<ParejaNodos> parejaNodos,
			MouseEvent e) 
	{
		if (clic != -1) {
			pila.get(pila.size()-1).setPunto(new Point(e.getX(), e.getY()));
			
			pila.get(pila.size()-1).setPuntoLineaInicial(new Point(e.getX(), e.getY()));

			clic = -1;

		}
		return RevisarEnlace(clic, seleccionado, pila, nodoSeleccionado, panelBotones, parejaNodos, e);
	}

	

	/**
	 * Despues de dar clic verifica si se dio clic sobre un nodo
	 * 
	 * @param clic
	 * @param seleccionado
	 * @param pila
	 * @param nodoSeleccionado
	 * @param verificador
	 * @param panelBotones
	 * @param secuenciaNodos
	 * @param e
	 *            evento del mouse
	 * @return falso despues de crear el enlace, fasle si de lo contrario
	 */
	public boolean RevisarEnlace(int clic, boolean seleccionado, Pila<NodoGrafico> pila,
			ArrayList<NodoGrafico> nodoSeleccionado, PanelBotones panelBotones, ArrayList<ParejaNodos> parejaNodos,
			MouseEvent e) {
		
		if (!seleccionado) {

			NodoGrafico n = new LogNodoGraficoPila().verificarInterceptoMouse(new Point(e.getX(), e.getY()), pila);
			if (n != null) {
				nodoSeleccionado.add(n);
			}

			if (nodoSeleccionado.size() >= 2) {

				new LogNodoGraficoPila().identificarEnlaces(pila, nodoSeleccionado, parejaNodos);
				nodoSeleccionado.clear();

			}
			if (panelBotones.isPanelActivo()) {

				clic = pila.size() - 1;
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
	 * @param pila
	 * @param e
	 */
	public void redibujarNodo(int clic, int opcion, Pila<NodoGrafico> pila, MouseEvent e) {
			
			if (clic != -1) {
				pila.get(clic).setPunto(new Point(e.getX() - Constantes.RADIO_NODO / 2, e.getY() - Constantes.RADIO_NODO / 2));
			}
		
		
	}


	/**
	 * Retorna el nodo que se intercepte con el mouse para cambiar su posicion
	 * en la pantall
	 * 
	 * @param opcion
	 * @param pila
	 * @param e
	 */
	public NodoGrafico rehubicarNodo(int opcion, Pila<NodoGrafico> pila, Point p) {

		Rectangle mouse = new Rectangle(p.x, p.y, REC, REC);
		for (int i = 0; i < pila.size(); i++) {
			if (mouse.intersects(pila.get(i).getColision())) {
				return pila.get(i);
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
	
	public void eliminarNodo(Pila<NodoGrafico> miPila, ArrayList<ParejaNodos> parejaNodos) {
		
				NodoGrafico nodoSelec = miPila.pop();
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
	
	public ArrayList<NodoGrafico> containsPilas(String dato, Pila<NodoGrafico> miPila, Graphics g) {
		
		ArrayList<NodoGrafico> nodosContains = new ArrayList<>();

		for (int i = 0; i < miPila.size(); i++) {
			if (String.valueOf(miPila.get(i).getId()).equals(dato)) {
				nodosContains.add(miPila.get(i));
			}
		}
		return nodosContains;
	}

	public void dibujarPantalla(Cola<NodoGrafico> miCola, Graphics2D dibujar, String cadena, Graphics g){
		Color fondo = Color.WHITE;
		Font fuente=new Font("Arial", Font.BOLD, 36);
		
		dibujar.setStroke(new BasicStroke(3f));
		g.setColor(fondo);
		g.fillRect(Constantes.ANCHO -170, 15, 130, 190);
		g.drawRect(Constantes.ANCHO -180, 10, 150, 200);
		
		
		g.setColor(Constantes.COLORLETRA);
		g.setFont(fuente);
		g.drawString(cadena, Constantes.ANCHO-160, 40);
		
				
		
	}


}