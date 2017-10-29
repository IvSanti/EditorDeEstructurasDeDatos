package uniquindio.GUI.subPaneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uniquindio.GUI.PanelBotones;
import uniquindio.GUI.Ventana;
import uniquindio.GUI.VentanaNuevoNodo;
import uniquindio.bussines.CambioInformacion;
import uniquindio.bussines.Constantes;
import uniquindio.bussines.Pilas.logicaGraficaParaPilas;

import javax.swing.JLabel;
/**
 * 
 * @author Gustavo Andres Ramirez Munoz--Ivan Santigo Castaneda
 *
 */
public class PanelPila extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JButton botonPush, botonPop;
	private JButton botonContains;
	private JButton botonTop;
	private boolean isVisible;

	private JLabel labelPila;
	private JLabel labelEspacio;
	private JButton botonRegresar;

	private JFrame ventana;
	private PanelBotones panelBotones;
	private Ventana ventanaPrincipal;

	public PanelPila(JFrame ventana, PanelBotones panelBotones, Ventana ventanaPrincipal) {

		this.ventana = ventana;
		this.panelBotones = panelBotones;
		this.ventanaPrincipal = ventanaPrincipal;

		panel = new JPanel();
		panel.setBackground(new Color(153, 153, 153));

		labelPila = new JLabel("Estructura de pila    ");
		labelPila.setFont(Constantes.FUENTE);
		panel.add(labelPila);

		botonPush = new JButton("Push");
		botonPush.setFont(Constantes.FUENTE);
		botonPush.setForeground(Constantes.COLORLETRA);
		botonPush.setBackground(Constantes.COLORBOTON);
		botonPush.addActionListener(this);
		panel.add(botonPush);

		botonPop = new JButton("Pop");
		botonPop.setFont(Constantes.FUENTE);
		botonPop.setForeground(Constantes.COLORLETRA);
		botonPop.setBackground(Constantes.COLORBOTON);
		botonPop.addActionListener(this);
		panel.add(botonPop);

		botonContains = new JButton("Contains");
		botonContains.setFont(Constantes.FUENTE);
		botonContains.setForeground(Constantes.COLORLETRA);
		botonContains.setBackground(Constantes.COLORBOTON);
		botonContains.addActionListener(this);
		panel.add(botonContains);

		botonTop = new JButton("Top");
		botonTop.setFont(Constantes.FUENTE);
		botonTop.setForeground(Constantes.COLORLETRA);
		botonTop.setBackground(Constantes.COLORBOTON);
		botonTop.addActionListener(this);
		panel.add(botonTop);


		labelEspacio = new JLabel("                                ");
		panel.add(labelEspacio);

		botonRegresar = new JButton("Regresar");
		botonRegresar.setFont(Constantes.FUENTE);
		botonRegresar.setForeground(Constantes.COLORLETRA);
		botonRegresar.setBackground(Constantes.COLORBOTON);
		botonRegresar.addActionListener(this);
		panel.add(botonRegresar);

		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		isVisible = false;
		panel.setVisible(isVisible);

	}

	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible
	 *            the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
		panel.setVisible(isVisible);
	}

	/**
	 * 
	 * @return retorna el panel con lo que este contenga
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Se detectan los eventos de los botones
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		
		if (e.getSource() == botonPush) {
			System.out.println("Boton push");
			new CambioInformacion().cambiarInfoVentanaNodoAdd(new VentanaNuevoNodo(ventanaPrincipal, 5));
			this.setPanelActivo(true);
		}
		if (e.getSource() == botonPop) {
			System.out.println("Boton pop");
			new CambioInformacion().cambiarInfoVentanaPop(ventanaPrincipal);
		}
		if (e.getSource() == botonContains) {
			System.out.println("Boton contains");
			new CambioInformacion().cambiarInfoVentanaNodoContains(new VentanaNuevoNodo(ventanaPrincipal, 5, 2));
		}
		if (e.getSource() == botonTop) {
			System.out.println("Boton Top");
			new CambioInformacion().cambiarInfoVentanaTop(ventanaPrincipal);
			
		}

		if (e.getSource() == botonRegresar) {
			setVisible(false);
			ventanaPrincipal.apagarPantalla();
			panelBotones.setVisible(true);
			ventana.getContentPane().add(panelBotones.getPanel(), BorderLayout.SOUTH);
			ventanaPrincipal.limpiar();
		}
	}

	private void setPanelActivo(boolean b) {
		panelBotones.setPanelActivo(true);
	}
}