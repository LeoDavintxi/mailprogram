package globales.administrativo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Acerca extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JButton botonAceptar;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JSeparator jSeparator1;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private Color color;
	private String version;

	public Acerca(JFrame frame, Color color, String version) {
		super(frame,"Acerca de ...",true);
		this.color=color;
		this.version=version;
		initGUI();
	}
	
	private void initGUI() {
		try {
			ActionListener evento = new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if(botonAceptar==e.getSource()){
						dispose();
					}
				}
			};
			
			this.setSize(400, 340);
			setLocationRelativeTo(null);
			setUndecorated(true);
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setPreferredSize(new java.awt.Dimension(344, 178));
					jLabel1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/logo.png")));
					jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
					jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2);
					jLabel2.setText("Copyrigt (C) 2016, David Leonardo Salazar");
					jLabel2.setPreferredSize(new java.awt.Dimension(370, 16));
					jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
					jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
				}
				{
					jLabel5 = new JLabel();
					jPanel1.add(jLabel5);
					jLabel5.setText("Vesion: "+version);
					jLabel5.setPreferredSize(new java.awt.Dimension(301, 16));
					jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
					jLabel5.setHorizontalTextPosition(SwingConstants.CENTER);
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3);
					jLabel3.setText("Todos los derechos reservados");
					jLabel3.setPreferredSize(new java.awt.Dimension(327, 16));
					jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
					jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					jLabel4 = new JLabel();
					jPanel1.add(jLabel4);
					jLabel4.setText("https://twitter.com/davidleosalazar");
					jLabel4.setPreferredSize(new java.awt.Dimension(377, 23));
					jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
					jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
				}
				{
					jSeparator1 = new JSeparator();
					jPanel1.add(jSeparator1);
					jSeparator1.setPreferredSize(new java.awt.Dimension(354, 7));
					jSeparator1.setBackground(color);
				}
				{
					botonAceptar = new JButton();
					jPanel1.add(botonAceptar);
					botonAceptar.setText("Aceptar");
					botonAceptar.setPreferredSize(new java.awt.Dimension(129, 32));
					botonAceptar.setBackground(new java.awt.Color(0,0,0));
					botonAceptar.setForeground(new java.awt.Color(255,255,255));
					botonAceptar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					botonAceptar.addActionListener(evento);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
