package uniquindio.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants.FontConstants;

import uniquindio.VO.NodoGrafico;
import uniquindio.bussines.Constantes;


public class Pantalla extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTextPane panelTexto;
	private String datos="";
	private JPanel pantalla;
	
	//private JFrame ventana;
	
	public Pantalla(){

		this.setTitle("PANTALLA");
		this.setBounds(1000, 50, 300, 360);
		pantalla = new JPanel();
		pantalla.setSize(300,360);
		pantalla.setLayout(null);
		
		{
			panelTexto = new JTextPane();
			panelTexto.setBounds(10, 10, 250, 300);
			pantalla.add(panelTexto);
		}
		setPreferredSize(getSize());
		getContentPane().add(pantalla);
		setSize(300,360);
		
		//pack();
		
	}
	
	public String getDatos() {
		return datos;
	}

	public void setDatos(NodoGrafico dato) {
		datos += dato.getId()+", ";
	}

	public void mostrarDato(){
		
		Font fuente=new Font("Dialog", Font.BOLD, 36);
		panelTexto.setFont(fuente);
		
		panelTexto.setText(datos);
		this.setVisible(true);
	}

	public void apagarPantalla() {
		datos = "";
		panelTexto.setText(datos);
		this.setVisible(false);
	}
	
/*	
	public static void main(String args[]){
		
		Pantalla miP = new Pantalla();
		miP.setDatos(new NodoGrafico(10,10,(new Point(20,20))));
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		miP.mostrarDato();
	}
*/

}
