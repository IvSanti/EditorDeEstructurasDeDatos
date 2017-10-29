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

import javax.swing.JLabel;
/**
 * 
 * @author Gustavo Andres Ramirez Munoz--Ivan Santigo Castaneda
 *
 */
public class PanelCola extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JButton botonAdd, botonPoll;
	private JButton botonContains;
	private JButton botonPeek;
	private boolean isVisible;

	private JLabel labelCola;
	private JLabel labelEspacio;
	private JButton botonRegresar;

	private JFrame ventana;
	private PanelBotones panelBotones;
	private Ventana ventanaPrincipal;

	public PanelCola(JFrame ventana, PanelBotones panelBotones, Ventana ventanaPrincipal) {

		this.ventana = ventana;
		this.panelBotones = panelBotones;
		this.ventanaPrincipal = ventanaPrincipal;

		panel = new JPanel();
		panel.setBackground(new Color(153, 153, 153));

		labelCola = new JLabel("Estructura de Cola    ");
		labelCola.setFont(Constantes.FUENTE);
		panel.add(labelCola);

		botonAdd = new JButton("Add");
		botonAdd.setFont(Constantes.FUENTE);
		botonAdd.setForeground(Constantes.COLORLETRA);
		botonAdd.setBackground(Constantes.COLORBOTON);
		botonAdd.addActionListener(this);
		panel.add(botonAdd);

		botonPoll = new JButton("Poll");
		botonPoll.setFont(Constantes.FUENTE);
		botonPoll.setForeground(Constantes.COLORLETRA);
		botonPoll.setBackground(Constantes.COLORBOTON);
		botonPoll.addActionListener(this);
		panel.add(botonPoll);

		botonContains = new JButton("Contains");
		botonContains.setFont(Constantes.FUENTE);
		botonContains.setForeground(Constantes.COLORLETRA);
		botonContains.setBackground(Constantes.COLORBOTON);
		botonContains.addActionListener(this);
		panel.add(botonContains);

		botonPeek = new JButton("Peek");
		botonPeek.setFont(Constantes.FUENTE);
		botonPeek.setForeground(Constantes.COLORLETRA);
		botonPeek.setBackground(Constantes.COLORBOTON);
		botonPeek.addActionListener(this);
		panel.add(botonPeek);


		labelEspacio = new JLabel("                             ");
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

		
		if (e.getSource() == botonAdd) {
			System.out.println("Boton Add");
			new CambioInformacion().cambiarInfoVentanaNodoAdd(new VentanaNuevoNodo(ventanaPrincipal,4));
			this.setPanelActivo(true);
		}
		if (e.getSource() == botonPoll) {
			System.out.println("Boton Poll");
			new CambioInformacion().cambiarInfoVentanaPoll(ventanaPrincipal);
		}
		if (e.getSource() == botonContains) {
			System.out.println("Boton contains");
			new CambioInformacion().cambiarInfoVentanaNodoContains(new VentanaNuevoNodo(ventanaPrincipal, 4, 2));
		}
		if (e.getSource() == botonPeek) {
			System.out.println("Boton Peek");
			new CambioInformacion().cambiarInfoVentanaPeek(ventanaPrincipal);
			
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
