package uniquindio.GUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import uniquindio.VO.NodoGrafico;
import uniquindio.VO.ParejaNodos;
import uniquindio.bussines.Constantes;
import uniquindio.bussines.Colas.LogNodoGrafiColas;
import uniquindio.bussines.Colas.logicaGraficaParaColas;
import uniquindio.bussines.Listas.LogicaNodosGraficos;
import uniquindio.bussines.Listas.logicaGraficaParaListas;
import uniquindio.bussines.Pilas.LogNodoGraficoPila;
import uniquindio.bussines.Pilas.logicaGraficaParaPilas;
import uniquindio.listas.cola.Cola;
import uniquindio.listas.listaCircular.ListaCircular;
import uniquindio.listas.listaDE.ListaDoblementeEnlazada;
import uniquindio.listas.listaEnlazada.ListaEnlazada;
import uniquindio.listas.pila.Pila;

/**
 * 
 * @author Gustavo Andres Ramirez Munoz--Ivan Santigo Castaneda
 *
 */
public class Ventana extends Canvas implements Runnable, MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Font fuente = new Font("Arial", Font.PLAIN, 10);

	private BufferStrategy estrategia;
	private Graphics2D dibujar;
	private JFrame ventana;
	private Graphics g;
	private PanelBotones panelBotones;
	private boolean seleccionado;
	private NodoGrafico nodoMover;
	private int ultimoNodo;
	private Point mouseCoordenadas;
	private int opcion;
	private boolean ventanaActiva;
	
	private Pantalla miPantalla;

	// Estructuras de datos
	private ListaEnlazada<NodoGrafico> listaEnlazada;
	private ListaDoblementeEnlazada<NodoGrafico> listaDoblemente;
	private ListaCircular<NodoGrafico> listaCircular;
	private Pila<NodoGrafico> miPila;
	private Cola<NodoGrafico> miCola;
	
	// Contiene la secuencia de nodos enlanzados
	private ArrayList<ParejaNodos> parejaNodos;
	private long referenciaContadorContains;
	//
	private ArrayList<NodoGrafico> nodoSeleccionado;
	private ArrayList<NodoGrafico> nodosEncontrados;

	/**
	 * Se inicializan los componente de la ventana
	 */
	public Ventana() {

		ventana = new JFrame("Editor E.D.");
		ventana.setResizable(false);
		ventana.getContentPane().add(this, BorderLayout.CENTER);
		ventana.setPreferredSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.pack();

		{
			panelBotones = new PanelBotones(ventana, this);
			ventana.getContentPane().add(panelBotones.getPanel(), BorderLayout.SOUTH);
		}
		
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
		this.addMouseListener(this);// Detecta los eventos del mouse en la
									// ventana
		this.addMouseMotionListener(this);

		seleccionado = false;
		ultimoNodo = 0;
		mouseCoordenadas = new Point(0, 0);
		opcion = 0;
		nodosEncontrados = new ArrayList<>();
		listaEnlazada = new ListaEnlazada<>();
		listaDoblemente = new ListaDoblementeEnlazada<>();
		listaCircular = new ListaCircular<>();

		miPila = new Pila<>();
		miCola = new Cola<>();
		
		parejaNodos = new ArrayList<>();
		nodoSeleccionado = new ArrayList<>();
		ventanaActiva = false;
		
		miPantalla = new Pantalla();
	}

	/**
	 * @return the ventanaActiva
	 */
	public boolean isVentanaActiva() {
		return ventanaActiva;
	}

	/**
	 * @param ventanaActiva
	 *            the ventanaActiva to set
	 */
	public void setVentanaActiva(boolean ventanaActiva) {
		this.ventanaActiva = ventanaActiva;
	}

	/**
	 * Limpia todas las listas
	 */
	public void limpiar() {

		nodoSeleccionado.clear();

		listaEnlazada.clear();
		listaDoblemente.clear();
		listaCircular.clear();
		miPila.clear();
		miCola.clear();
		
		opcion = 0;
		parejaNodos.clear();

	}

	/**
	 * Dibuja toda la parte grafica
	 */
	public void dibujar() {

		estrategia = getBufferStrategy();
		if (estrategia == null) {

			createBufferStrategy(3);
			return;
		}
		
		g = estrategia.getDrawGraphics();
		dibujar = (Graphics2D) g;
		update(g);
		g.setFont(fuente);
		g.setColor(Color.black);
		g.drawRoundRect(10, 10, Constantes.ANCHO_ZONA_DIUJABLE, Constantes.ALTO_ZONA_DIUJABLE, 10, 10);

		{

			dibujar.setStroke(new BasicStroke(5f));
			g.setColor(Color.black);

			new logicaGraficaParaListas().dibujarEnlancesParejas(parejaNodos, g);

			switch (opcion) {
			case 1:
				new logicaGraficaParaListas().dibujarListasNodos(listaEnlazada, dibujar, g);
				break;
			case 2:
				new logicaGraficaParaListas().dibujarListasNodos(listaDoblemente, dibujar, g);
				break;
			case 3:

				new logicaGraficaParaListas().dibujarListasNodos(listaCircular, dibujar, g);
				break;
			case 4:
				new logicaGraficaParaColas().dibujarColaNodos(miCola, dibujar, g);
				break;
			case 5:
				new logicaGraficaParaPilas().dibujarPilaNodos(miPila, dibujar, g);
				new logicaGraficaParaPilas().dibujarPantalla(miCola, dibujar, miPantalla.getDatos(), g);
				break;
			}
			
			if (!parejaNodos.isEmpty()) {
				new logicaGraficaParaListas().dibujarListasNodosEncontrados(nodosEncontrados, dibujar, g);
			}

		}
		
		g.dispose();
		dibujar.dispose();
		estrategia.show();
		
		

	}

	@Override
	public void run() {

		final int MILI_SEGUNDOS = 1000000000;
		long referenciaContador1 = System.nanoTime();

		while (true) {

			dibujar();
			verificaciones();

			
			
			if (System.nanoTime() - referenciaContadorContains > MILI_SEGUNDOS) {
				nodosEncontrados.clear();
			}

			if (System.nanoTime() - referenciaContador1 > MILI_SEGUNDOS * 2) {
				System.out.println("Cantidad de nodos: " + listaEnlazada.size());
				System.out.println("Nodos seleccionado: " + nodoSeleccionado.size());
				System.out.println("Opcion de listas: " + opcion);
				System.out.println("Pareja nodos: " + parejaNodos.size());
				System.out.println("Encontrado " + nodosEncontrados.size());
				System.out.println("");
				referenciaContador1 = System.nanoTime();

			}		
		}

	}

	/**
	 * Ejecuta las verificacion de intercepto entre los nodos
	 * 
	 */
	public void verificaciones() {

		switch (opcion) {
		case 1:

			new LogicaNodosGraficos().verificarNodosInterceptados(listaEnlazada);
			break;
		case 2:
			new LogicaNodosGraficos().verificarNodosInterceptados(listaDoblemente);
			break;
		
		case 3:
			new LogicaNodosGraficos().verificarNodosInterceptados(listaCircular);
			break;
		
		case 4:
			new LogNodoGrafiColas().verificarNodosInterceptados(miCola);
		
		case 5: 
			new LogNodoGraficoPila().verificarNodosInterceptados(miPila);
			break;
		}

	}

	/**
	 * Añade un nodo a la estructura de datos que se este manejando
	 * 
	 * @param dato
	 * 
	 */
	
	public void añadirNodo(int dato, int opcion) {

		this.opcion = opcion;
		
		if (panelBotones.isPanelActivo()) {
			panelBotones.setPanelActivo(false);

			seleccionado = true;
			switch (opcion) {

			case 1:
				listaEnlazada.add(new NodoGrafico(listaEnlazada.size(), dato, mouseCoordenadas));
				ultimoNodo = listaEnlazada.size() - 1;
				break;

			case 2:
				listaDoblemente.add(new NodoGrafico(listaDoblemente.size(), dato, mouseCoordenadas));
				ultimoNodo = listaDoblemente.size() - 1;
				break;

			case 3:
				listaCircular.add(new NodoGrafico(listaCircular.size(), dato, mouseCoordenadas));
				ultimoNodo = listaCircular.size() - 1;
				break;
				
			case 4:
				miCola.add(new NodoGrafico(miCola.size(), dato, mouseCoordenadas));
				ultimoNodo = miCola.size()-1;
				break;
			
			case 5:
				miPila.push(new NodoGrafico(miPila.size(), dato, mouseCoordenadas));
				ultimoNodo = 0;
				break;
			}

		}

	}

	/**
	 * Elimina el nodo seleccionado de la estructura sobre la que se trabaja
	 */
	
	
	public void eliminarNodo() {

		switch (opcion) {
		case 1:
			new logicaGraficaParaListas().eliminarNodo(listaEnlazada, nodoSeleccionado, parejaNodos);
			break;

		case 2:
			new logicaGraficaParaListas().eliminarNodo(listaDoblemente, nodoSeleccionado, parejaNodos);
			break;
			
		case 3:
			new logicaGraficaParaListas().eliminarNodo(listaCircular, nodoSeleccionado, parejaNodos);
			break;
			
		case 4:
			
			if(!miCola.isEmpty()){
				miPantalla.setDatos(miCola.peek());
				miPantalla.mostrarDato();
				new logicaGraficaParaColas().eliminarNodo(miCola, parejaNodos);
			}
			
		case 5:
			if(!miPila.isEmpty()){
				miPantalla.setDatos(miPila.top());
				miPantalla.mostrarDato();
				new logicaGraficaParaPilas().eliminarNodo(miPila, parejaNodos);
			}
			break;
		}

	}

	/**
	 * Verifica que la estrucutra que se esta menejando contiene el dato que
	 * entra por parametroF
	 * 
	 * @param dato
	 * @return
	 */
	public void constains(String dato, int opcion) {

		this.opcion = opcion;
		referenciaContadorContains = System.nanoTime();

		switch (opcion) {
		case 1:

			nodosEncontrados = new logicaGraficaParaListas().containsListas(dato, listaEnlazada, g);
			break;
		case 2:
			nodosEncontrados = new logicaGraficaParaListas().containsListas(dato, listaDoblemente, g);
			break;
		case 3:
			nodosEncontrados = new logicaGraficaParaListas().containsListas(dato, listaCircular, g);
			break;
		case 4:
			nodosEncontrados = new logicaGraficaParaColas().containsColas(dato, miCola, g);
			break;
			
		case 5:
			nodosEncontrados = new logicaGraficaParaPilas().containsPilas(dato, miPila, g);
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Detecta los clics del mouse
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (ventanaActiva) {

			ListaEnlazada<NodoGrafico> lista = new ListaEnlazada<>();
			Pila<NodoGrafico> pila = new Pila<>();
			Cola<NodoGrafico> cola = new Cola<>();
			
			switch (opcion) {
			case 1:

				lista = listaEnlazada;
				seleccionado = new logicaGraficaParaListas().acomodarNodo(ultimoNodo, seleccionado, lista,nodoSeleccionado, panelBotones, parejaNodos, e);
				ultimoNodo = -1;

				break;

			case 2:

				lista = listaDoblemente;
				seleccionado = new logicaGraficaParaListas().acomodarNodo(ultimoNodo, seleccionado, lista,nodoSeleccionado, panelBotones, parejaNodos, e);
				ultimoNodo = -1;

				break;
			case 3:

				lista = listaCircular;
				seleccionado = new logicaGraficaParaListas().acomodarNodo(ultimoNodo, seleccionado, lista,nodoSeleccionado, panelBotones, parejaNodos, e);
				ultimoNodo = -1;

				break;
				
			case 4:
				
				cola = miCola;
				seleccionado = new logicaGraficaParaColas().acomodarNodo(ultimoNodo, seleccionado, cola,nodoSeleccionado, panelBotones, parejaNodos, e);
				ultimoNodo = -1;
				break;
				
			case 5:
				
				pila = miPila;
				seleccionado = new logicaGraficaParaPilas().acomodarNodo(ultimoNodo, seleccionado, pila,nodoSeleccionado, panelBotones, parejaNodos, e);
				ultimoNodo = -1;
				break;
			}


		}
	}

	/**
	 * Deteta el movimiento del mouse
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (ventanaActiva) {
			ListaEnlazada<NodoGrafico> lista = new ListaEnlazada<>();
			Pila<NodoGrafico> pila = new Pila<>();
			Cola<NodoGrafico> cola = new Cola<>();
			
			switch (opcion) {
			case 1:

				lista = listaEnlazada;
				new logicaGraficaParaListas().redibujarNodo(ultimoNodo, opcion, lista, e);
				break;

			case 2:

				lista = listaDoblemente;
				new logicaGraficaParaListas().redibujarNodo(ultimoNodo, opcion, lista, e);
				break;
				
			case 3:

				lista = listaCircular;
				new logicaGraficaParaListas().redibujarNodo(ultimoNodo, opcion, lista, e);
				break;
			
			case 4:
				cola = miCola;
				new logicaGraficaParaColas().redibujarNodo(ultimoNodo, opcion, cola, e);
				break;
				
			case 5:
				
				pila = miPila;
				new logicaGraficaParaPilas().redibujarNodo(ultimoNodo, opcion, pila, e);
				break;
			}

		}
	}

	/**
	 * Detecta el movimiento de arrastrar al dar clic
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (ventanaActiva) {
			if (nodoMover != null) {

				nodoMover.setPunto(
						new Point(e.getX() - Constantes.RADIO_NODO / 2, e.getY() - Constantes.RADIO_NODO / 2));

				nodoMover.setPuntoLineaInicial(new Point(nodoMover.getPunto().x + Constantes.RADIO_NODO / 2,
						nodoMover.getPunto().y + Constantes.RADIO_NODO / 2));
			}
		}
	}

	/**
	 * Detecta cuano se presiona el clic
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (ventanaActiva) {
			
			ListaEnlazada<NodoGrafico> lista = new ListaEnlazada<>();
			Pila<NodoGrafico> pila = new Pila<>();
			Cola<NodoGrafico> cola = new Cola<>();
			
			switch (opcion) {
			
			case 1:
				lista = listaEnlazada;
				nodoMover = new logicaGraficaParaListas().rehubicarNodo(opcion, lista, new Point(e.getX(), e.getY()));
				break;

			case 2:
				lista = listaDoblemente;
				nodoMover = new logicaGraficaParaListas().rehubicarNodo(opcion, lista, new Point(e.getX(), e.getY()));
				break;
			case 3:
				lista = listaCircular;
				nodoMover = new logicaGraficaParaListas().rehubicarNodo(opcion, lista, new Point(e.getX(), e.getY()));
				break;
				
			case 4:
				cola = miCola;
				nodoMover = new logicaGraficaParaColas().rehubicarNodo(opcion, cola, new Point(e.getX(), e.getY()));
				break;
				
			case 5:
				pila = miPila;
				nodoMover = new logicaGraficaParaPilas().rehubicarNodo(opcion, pila, new Point(e.getX(), e.getY()));
			}
		}
	}

	/**
	 * Detecta cuando se suelta el clic
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		nodoMover = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * @return the mouseCoordenadas
	 */
	public Point getMouseCoordenadas() {
		return mouseCoordenadas;
	}


	public void MostrarNodo() {
		
		if(!miPila.isEmpty()){
			miPantalla.setDatos(miPila.top());
			miPantalla.mostrarDato();
		}
		if(!miCola.isEmpty()){
			miPantalla.setDatos(miCola.peek());
			miPantalla.mostrarDato();
		}
	}
	
	public void apagarPantalla(){
		miPantalla.apagarPantalla();
	}

}
