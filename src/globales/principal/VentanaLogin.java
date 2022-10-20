package globales.principal;

import globales.administrativo.ModeloUsuarios;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


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
public class VentanaLogin extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JButton botonAceptar;
	private JLabel jLabel5;
	private JButton botonWeb;
	private JButton botonSalir;
	private JPasswordField textPassword;
	private JLabel jLabel4;
	private JTextField textUsuario;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JPanel jPanel4;
	private JPanel jPanel2;
	private Color color;
	private ResultSet tabla;
	private ModeloUsuarios usuario = new ModeloUsuarios();

	public void ocultar(){
		setVisible(false);
	}

	public boolean validacionUsuario(){
		boolean acceso=false;
		tabla=usuario.registroUsuarios(textUsuario.getText(),new String(textPassword.getPassword()));
		try {
			if(tabla.next()){
				acceso=true;
			}else{
				acceso=false;
			}
		} catch (SQLException e) {
		}
		usuario.cerrarBase();
		return acceso;
	}
	
	public VentanaLogin(){
		initGUI();		
	}

	private void initGUI() {
		try {
			color = new Color(0,167,234);
			setDefaultCloseOperation(2);
			setResizable(false);
			setUndecorated(true);
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("png/icono.png")).getImage());
			getContentPane().setBackground(new java.awt.Color(0,128,255));
			FocusListener action = new FocusListener() {
				
				public void focusLost(FocusEvent e) {
				}
				
				public void focusGained(FocusEvent e) {
					if(e.getSource()==textPassword){
						textPassword.selectAll();
					}
					if(e.getSource()==textUsuario){
						textUsuario.selectAll();
					}
				}
			};
			ActionListener esc = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if ((botonAceptar == e.getSource()) || (e.getSource() == textPassword)) {
						if(validacionUsuario()){
							ocultar();
							new SplashScreen(color,textUsuario.getText().toUpperCase());	
						}else{
							JOptionPane.showMessageDialog(null, "Verifique los datos ingresados");
							textPassword.requestFocus();
						}
					}
					if (botonSalir == e.getSource()) {
						System.exit(0);
					}
					if (botonWeb == e.getSource())
						try {
							Desktop.getDesktop().browse(new URI("https://twitter.com/davidleosalazar"));
						}
					catch (IOException e1) {
						e1.printStackTrace();
					}
					catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			};
			jPanel2 = new JPanel();
			GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
			jPanel2.setLayout(jPanel2Layout);
			getContentPane().add(jPanel2, "South");
			jPanel2.setPreferredSize(new Dimension(377, 56));
			jPanel2.setBackground(color);

			botonAceptar = new JButton();
			botonAceptar.setText("Aceptar");
			botonAceptar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/Apply.png")));
			botonAceptar.addActionListener(esc);

			botonSalir = new JButton();
			botonSalir.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/Close.png")));
			botonSalir.setBackground(color);
			botonSalir.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			botonSalir.addActionListener(esc);

			jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap(20, 20)
					.addComponent(botonSalir, -2, 40, -2)
					.addGap(0, 201, 32767)
					.addComponent(botonAceptar, -2, 116, -2)
					.addContainerGap(16, 16));
			jPanel2Layout.setVerticalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel2Layout.createParallelGroup()
							.addGroup(jPanel2Layout.createSequentialGroup()
									.addComponent(botonAceptar, -2, 36, -2)
									.addGap(0, 0, 32767))
									.addComponent(botonSalir, GroupLayout.Alignment.LEADING, 0, 36, 32767))
									.addContainerGap());

			jPanel1 = new JPanel();
			GridLayout jPanel1Layout = new GridLayout(1, 1);
			jPanel1Layout.setColumns(1);
			jPanel1Layout.setHgap(5);
			jPanel1Layout.setVgap(5);
			jPanel1.setLayout(jPanel1Layout);
			getContentPane().add(jPanel1, "North");
			jPanel1.setPreferredSize(new Dimension(377, 52));
			jPanel1.setBackground(color);

			jLabel1 = new JLabel();
			jPanel1.add(jLabel1);
			jLabel1.setText("Recuerde a Tiempo sus Impuestos");
			jLabel1.setForeground(new Color(255, 255, 255));
			jLabel1.setPreferredSize(new Dimension(206, 40));
			jLabel1.setHorizontalAlignment(0);
			jLabel1.setHorizontalTextPosition(0);
			jLabel1.setFont(new Font("Arial", 3, 16));

			jPanel4 = new JPanel();
			getContentPane().add(jPanel4, "Center");
			jPanel4.setBackground(new Color(255,255,255));

			jLabel2 = new JLabel();
			jPanel4.add(jLabel2);
			jLabel2.setPreferredSize(new java.awt.Dimension(370, 183));
			jLabel2.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/logo.png")));
			jLabel2.setHorizontalAlignment(0);
			jLabel2.setHorizontalTextPosition(0);

			jLabel3 = new JLabel();
			jPanel4.add(jLabel3);
			jLabel3.setText("Id Usuario:");
			jLabel3.setPreferredSize(new Dimension(115, 16));
			jLabel3.setHorizontalAlignment(4);
			jLabel3.setFont(new Font("Segoe UI", 1, 14));
			jLabel3.setForeground(new java.awt.Color(0,0,0));

			textUsuario = new JTextField();
			jPanel4.add(textUsuario);
			textUsuario.setPreferredSize(new Dimension(174, 23));
			textUsuario.addFocusListener(action);

			jLabel4 = new JLabel();
			jPanel4.add(jLabel4);
			jLabel4.setText("Contraseña:");
			jLabel4.setPreferredSize(new Dimension(115, 16));
			jLabel4.setHorizontalAlignment(4);
			jLabel4.setFont(new Font("Segoe UI", 1, 14));
			jLabel4.setForeground(new java.awt.Color(0,0,0));

			textPassword = new JPasswordField();
			jPanel4.add(textPassword);
			textPassword.setPreferredSize(new Dimension(174, 23));
			textPassword.addActionListener(esc);
			textPassword.addFocusListener(action);

			jLabel5 = new JLabel();
			jPanel4.add(jLabel5);
			jLabel5.setPreferredSize(new Dimension(184, 46));

			botonWeb = new JButton();
			jPanel4.add(botonWeb);
			botonWeb.setText("¿Olvido su contraseña?");
			botonWeb.setPreferredSize(new Dimension(187, 36));
			botonWeb.setBackground(new Color(255,255,255));
			botonWeb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			botonWeb.setForeground(new Color(0, 0, 0));
			botonWeb.setFont(new Font("Segoe UI", 1, 14));
			botonWeb.addActionListener(esc);

			pack();
			setSize(393, 409);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}