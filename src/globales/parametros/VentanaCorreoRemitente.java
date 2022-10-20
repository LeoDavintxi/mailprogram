package globales.parametros;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

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
public class VentanaCorreoRemitente extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JButton cerrar;
	private JButton guardar;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JTextField puerto;
	private JPasswordField contrasena;
	private JTextField usuario;
	private JPanel jPanel3;
	private JTextField host;
	private JLabel jLabel9;
	private JTextField correo;
	private JTextField nombre;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JButton consultar;
	private JPanel jPanel2;
	private Color color;
	private ResultSet tabla;
	private ModeloCorreoRemitente modeloCorreoRemitente = new ModeloCorreoRemitente();

	public VentanaCorreoRemitente(JFrame frame, Color color) {
		super(frame,"Correo Remitente",true);
		this.color=color;
		initGUI();
	}

	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent actionEvent) {
				if(!guardar.isEnabled()){
					dispose();
				}else{
					resetear();
				}
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", actionListener);

		return rootPane;
	}

	public String encriptar(String cadena) { 
		StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
		s.setPassword("asodatos"); 
		return s.encrypt(cadena); 
	} 

	public void resetear(){
		consultar.setEnabled(true);
		guardar.setEnabled(false);
		nombre.setEditable(false);
		correo.setEditable(false);
		host.setEditable(false);
		usuario.setEditable(false);
		contrasena.setEditable(false);
		puerto.setEditable(false);
		nombre.setText("");
		correo.setText("");
		host.setText("");
		usuario.setText("");
		contrasena.setText("");
		puerto.setText("");
	}

	public void guardarCorreo(){
		modeloCorreoRemitente.editarCorreoRemitente("1", nombre.getText(), correo.getText(), host.getText(), usuario.getText(), encriptar(new String(contrasena.getPassword())), puerto.getText());
		JOptionPane.showMessageDialog(null, "Se han guardado los datos Ingresados en la Base de Datos");
		resetear();
	}

	public void consultarCorreo(){		
		consultar.setEnabled(false);
		guardar.setEnabled(true);
		nombre.setEditable(true);
		correo.setEditable(true);
		host.setEditable(true);
		usuario.setEditable(true);
		contrasena.setEditable(true);
		puerto.setEditable(true);
		tabla=modeloCorreoRemitente.buscarCorreoRemitente();
		try {
			if(tabla.next()){
				nombre.setText(tabla.getString("NOMBRE_REMITENTE"));
				correo.setText(tabla.getString("EMAIL_REMITENTE"));
				host.setText(tabla.getString("HOST"));
				usuario.setText(tabla.getString("USER"));
				contrasena.setText(tabla.getString("PASSWORD"));
				puerto.setText(tabla.getString("PORT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		modeloCorreoRemitente.cerrarBase();
	}


	private void initGUI() {
		try {
			this.setTitle("Correo Remitente");
			ActionListener evento = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cerrar==e.getSource()){
						dispose();
					}

					if(consultar==e.getSource()){
						consultarCorreo();
					}
					if(guardar==e.getSource()){
						guardarCorreo();
					}
				}
			};
			{
				jPanel2 = new JPanel();
				GridLayout jPanel2Layout = new GridLayout(0, 1);
				jPanel2Layout.setHgap(5);
				jPanel2Layout.setVgap(5);
				jPanel2Layout.setColumns(1);
				jPanel2.setLayout(jPanel2Layout);
				getContentPane().add(jPanel2, BorderLayout.WEST);
				jPanel2.setPreferredSize(new java.awt.Dimension(85, 383));
				jPanel2.setBackground(color);
				{
					consultar = new JButton();
					jPanel2.add(consultar);
					consultar.setText("Consultar");
					consultar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					consultar.setForeground(new java.awt.Color(255,255,255));
					consultar.setBackground(color);
					consultar.setPreferredSize(new java.awt.Dimension(85, 39));
					consultar.addActionListener(evento);
				}
				{
					guardar = new JButton();
					jPanel2.add(guardar);
					guardar.setText("Guardar");
					guardar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					guardar.setForeground(new java.awt.Color(255,255,255));
					guardar.setBackground(color);
					guardar.setPreferredSize(new java.awt.Dimension(85, 39));
					guardar.setEnabled(false);
					guardar.addActionListener(evento);
				}
				{
					cerrar = new JButton();
					jPanel2.add(cerrar);
					cerrar.setText("Cerrar");
					cerrar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					cerrar.setForeground(new java.awt.Color(255,255,255));
					cerrar.setBackground(new java.awt.Color(0,0,0));
					cerrar.setPreferredSize(new java.awt.Dimension(85, 39));
					cerrar.addActionListener(evento);
				}
			}
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setBorder(BorderFactory.createTitledBorder("Informacion Correo Remitente"));
				jPanel1.setPreferredSize(new java.awt.Dimension(407, 375));
				{
					jPanel3 = new JPanel();
					jPanel1.add(jPanel3);
					jPanel3.setPreferredSize(new java.awt.Dimension(421, 54));
					jPanel3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				}

				{
					jLabel4 = new JLabel();
					jPanel1.add(jLabel4);
					jLabel4.setText("Nombre:");
					jLabel4.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					nombre = new JTextField();
					jPanel1.add(nombre);
					nombre.setPreferredSize(new java.awt.Dimension(310, 20));
				}
				{
					jLabel5 = new JLabel();
					jPanel1.add(jLabel5);
					jLabel5.setText("Correo:");
					jLabel5.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					correo = new JTextField();
					jPanel1.add(correo);
					correo.setPreferredSize(new java.awt.Dimension(310, 20));
				}
				{
					jLabel9 = new JLabel();
					jPanel1.add(jLabel9);
					jLabel9.setText("Host (SMTP):");
					jLabel9.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					host = new JTextField();
					jPanel1.add(host);
					host.setPreferredSize(new java.awt.Dimension(310,20));
				}
				{
					jLabel6 = new JLabel();
					jPanel1.add(jLabel6);
					jLabel6.setText("Usuario:");
					jLabel6.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					usuario = new JTextField();
					jPanel1.add(usuario);
					usuario.setPreferredSize(new java.awt.Dimension(310, 20));
				}
				{
					jLabel7 = new JLabel();
					jPanel1.add(jLabel7);
					jLabel7.setText("Contraseña:");
					jLabel7.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					contrasena = new JPasswordField();
					jPanel1.add(contrasena);
					contrasena.setPreferredSize(new java.awt.Dimension(310, 20));
				}
				{
					jLabel8 = new JLabel();
					jPanel1.add(jLabel8);
					jLabel8.setText("Puerto");
					jLabel8.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					puerto = new JTextField();
					jPanel1.add(puerto);
					puerto.setPreferredSize(new java.awt.Dimension(310, 20));
				}
			}
			this.setSize(550, 270);
			setLocationRelativeTo(null);
			setResizable(false);
			resetear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}