package globales.administrativo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

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
public class VentanaUsuarios extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JButton crear;
	private JButton cerrar;
	private JButton guardar;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JPasswordField verificacion;
	private JPasswordField contrasena;
	private JTextField email;
	private JButton permisos;
	private JTextField cedula;
	private JLabel jLabel9;
	private JTextField apellido;
	private JTextField nombre;
	private JComboBox<String> estado;
	private JTextField usuario;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JLabel jLabel2;
	private JTable tablaUsuarios;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private JButton borrar;
	private JButton consultar;
	private JPanel jPanel2;
	private Color color;
	private ModeloUsuarios user= new ModeloUsuarios();
	private ResultSet tabla;
	private TablaUsuarios modeloTablaUsuarios = new TablaUsuarios();
	private ControlTablaUsuarios controlTablaUsuarios = new ControlTablaUsuarios(modeloTablaUsuarios);
	private ModeloPermisos permiso = new ModeloPermisos(); 
	
	public VentanaUsuarios(Color color) {
		super();
		this.color=color;
		initGUI();
	}

	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent actionEvent) {
				if(!guardar.isEnabled() && !borrar.isEnabled() && tablaUsuarios.getRowCount()==0){
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

	public void resetear(){
		crear.setEnabled(true);
		consultar.setEnabled(true);
		borrar.setEnabled(false);
		guardar.setEnabled(false);
		usuario.setEditable(false);
		estado.setEnabled(false);
		nombre.setEditable(false);
		apellido.setEditable(false);
		cedula.setEditable(false);
		email.setEditable(false);
		contrasena.setEditable(false);
		verificacion.setEditable(false);
		permisos.setEnabled(false);
		usuario.setText("");
		estado.setSelectedItem("");
		nombre.setText("");
		apellido.setText("");
		cedula.setText("");
		email.setText("");
		contrasena.setText("");
		verificacion.setText("");
		controlTablaUsuarios.borraFila();
	} 
	
	public void consultarUsuarios(){
		int fila=0;
		tabla=user.buscarUsuarios();
		controlTablaUsuarios.borraFila();
		try {
			while(tabla.next()){
				controlTablaUsuarios.anhadeFila(0);
				tablaUsuarios.setValueAt(tabla.getString("ID"), fila, 0);
				tablaUsuarios.setValueAt(tabla.getString("NOMBRE")+" "+tabla.getString("APELLIDO"), fila, 1);
				tablaUsuarios.setValueAt(tabla.getString("ESTADO"), fila, 2);
				fila++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		user.cerrarBase();
	}
	
	private void crearPermisos(){
		for(int i=1;i<=8;i++){
			permiso.crearPermiso(usuario.getText(), i, false);	
		}
	}
	
	public void consultarUnUsuario(){
		tabla=user.buscarUnUsuario(usuario.getText().toUpperCase());
		try {
			if(tabla.next()){
				crear.setEnabled(false);
				consultar.setEnabled(false);
				borrar.setEnabled(true);
				guardar.setEnabled(true);
				usuario.setEditable(false);
				estado.setEnabled(true);
				nombre.setEditable(true);
				apellido.setEditable(true);
				cedula.setEditable(true);
				email.setEditable(true);
				contrasena.setEditable(true);
				verificacion.setEditable(true);
				permisos.setEnabled(true);
				usuario.setText(tabla.getString("ID"));
				estado.setSelectedItem(tabla.getString("ESTADO"));
				nombre.setText(tabla.getString("NOMBRE"));
				apellido.setText(tabla.getString("APELLIDO"));
				cedula.setText(tabla.getString("CEDULA"));
				email.setText(tabla.getString("EMAIL"));
				contrasena.setText(tabla.getString("CONTRASENA"));
				verificacion.setText(tabla.getString("CONTRASENA"));
			}
		} catch (SQLException e1) {
		}
		user.cerrarBase();
	}

	private void initGUI() {
		try {
			this.setTitle("Usuarios");
			this.setResizable(false);
			this.setFrameIcon(new ImageIcon(getClass().getClassLoader().getResource("png/admin.png")));

			ActionListener evento = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getSource()==guardar&&!borrar.isEnabled()){
						if((new String(contrasena.getPassword())).equals(new String(verificacion.getPassword()))){
							user.crearUsuario(usuario.getText().toUpperCase(), nombre.getText().toUpperCase(), apellido.getText().toUpperCase(), cedula.getText(), email.getText().toLowerCase(), new String(contrasena.getPassword()), (String)estado.getSelectedItem());
							crearPermisos();
							JOptionPane.showMessageDialog(null, "El usuario ha sido creado en el Sistema.");
							resetear();	
						}else{
							JOptionPane.showMessageDialog(null, "Verifique las contraseñas ingresadas.");
						}
						
					}
					if(e.getSource()==guardar&&borrar.isEnabled()){
						if((new String(contrasena.getPassword())).equals(new String(verificacion.getPassword()))){
							user.editarUsuario(usuario.getText().toUpperCase(), nombre.getText().toUpperCase(), apellido.getText().toUpperCase(), cedula.getText(), email.getText().toLowerCase(), new String(contrasena.getPassword()), (String)estado.getSelectedItem());
							JOptionPane.showMessageDialog(null, "El usuario ha sido modificado en el Sistema");
							resetear();	
						}else{
							JOptionPane.showMessageDialog(null, "Verifique las contraseñas ingresadas.");
						}
						
					}
					if(e.getSource()==cerrar){
						dispose();
					}
					if(e.getSource()==consultar){
						consultarUsuarios();
						crear.setEnabled(false);
						consultar.setEnabled(false);
					}
					if(borrar==e.getSource()){
						int opcion=JOptionPane.showConfirmDialog(null, "¿Desea eliminar el usuario Seleccionado?");
						if(opcion==0){
							user.eliminarUsuario(usuario.getText().toUpperCase());
							permiso.eliminar(usuario.getText());
							resetear();
						}
						if(opcion==1){
							resetear();
						}

					}
					if(permisos==e.getSource()){
						VentanaPermisos permisos = new VentanaPermisos(null, color, usuario.getText());
						permisos.setVisible(true);
					}
					
					if(crear==e.getSource()){
						crear.setEnabled(false);
						consultar.setEnabled(false);
						borrar.setEnabled(false);
						guardar.setEnabled(true);
						usuario.setEditable(true);
						estado.setEnabled(true);
						nombre.setEditable(true);
						apellido.setEditable(true);
						cedula.setEditable(true);
						email.setEditable(true);
						contrasena.setEditable(true);
						verificacion.setEditable(true);
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
					crear = new JButton();
					jPanel2.add(crear);
					crear.setText("Crear");
					crear.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					crear.setForeground(new java.awt.Color(255,255,255));
					crear.setBackground(color);
					crear.setPreferredSize(new java.awt.Dimension(85, 39));
					crear.addActionListener(evento);
				}
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
					borrar = new JButton();
					jPanel2.add(borrar);
					borrar.setText("Borrar");
					borrar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					borrar.setForeground(new java.awt.Color(255,255,255));
					borrar.setBackground(color);
					borrar.setPreferredSize(new java.awt.Dimension(85, 39));
					borrar.setEnabled(false);
					borrar.addActionListener(evento);
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
				jPanel1.setBorder(BorderFactory.createTitledBorder("Informacion Usuarios"));
				jPanel1.setPreferredSize(new java.awt.Dimension(407, 375));
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText("ID:");
					jLabel1.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					usuario = new JTextField();
					jPanel1.add(usuario);
					usuario.setPreferredSize(new java.awt.Dimension(128, 20));
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2);
					jLabel2.setText("Estado:");
					jLabel2.setPreferredSize(new java.awt.Dimension(71, 20));
				}
				{
					estado = new JComboBox<String>();
					jPanel1.add(estado);
					estado.setPreferredSize(new java.awt.Dimension(100, 20));
					estado.addItem("");
					estado.addItem("Activo");
					estado.addItem("Inactivo");
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
					jLabel5.setText("Apellido:");
					jLabel5.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					apellido = new JTextField();
					jPanel1.add(apellido);
					apellido.setPreferredSize(new java.awt.Dimension(310, 20));
				}
				{
					jLabel9 = new JLabel();
					jPanel1.add(jLabel9);
					jLabel9.setText("Cedula:");
					jLabel9.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					cedula = new JTextField();
					jPanel1.add(cedula);
					cedula.setPreferredSize(new java.awt.Dimension(310,20));
				}
				{
					jLabel6 = new JLabel();
					jPanel1.add(jLabel6);
					jLabel6.setText("Email:");
					jLabel6.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					email = new JTextField();
					jPanel1.add(email);
					email.setPreferredSize(new java.awt.Dimension(310, 20));
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
					jLabel8.setText("Verificar:");
					jLabel8.setPreferredSize(new java.awt.Dimension(101, 20));
				}
				{
					verificacion = new JPasswordField();
					jPanel1.add(verificacion);
					verificacion.setPreferredSize(new java.awt.Dimension(310, 20));
				}
				{
					jScrollPane1 = new JScrollPane();
					jPanel1.add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(417, 161));
					{
						tablaUsuarios = new JTable();
						jScrollPane1.setViewportView(tablaUsuarios);
						tablaUsuarios.setModel(modeloTablaUsuarios);
						tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(70);
						tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(70);
						tablaUsuarios.getColumnModel().getColumn(1).setMaxWidth(250);
						tablaUsuarios.getColumnModel().getColumn(1).setMinWidth(250);
						tablaUsuarios.getTableHeader().setBackground(color);
						tablaUsuarios.getTableHeader().setForeground(new java.awt.Color(255,255,255));

						DefaultTableCellRenderer render = new DefaultTableCellRenderer();
						render.setHorizontalAlignment(SwingConstants.CENTER);

						tablaUsuarios.getColumnModel().getColumn(0).setCellRenderer(render);
						
						tablaUsuarios.addMouseListener(new MouseAdapter(){
							public void mouseClicked(MouseEvent e) {
								if (e.getClickCount() == 2) {
									JTable target = (JTable)e.getSource();
									usuario.setText((String)tablaUsuarios.getValueAt(target.getSelectedRow(), 0));
									consultarUnUsuario();
								}
							}
						}
								);
					}
				}
				{
					permisos = new JButton();
					jPanel1.add(permisos);
					permisos.setPreferredSize(new java.awt.Dimension(421, 24));
					permisos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					permisos.setBackground(color);
					permisos.setText("Permisos");
					permisos.setForeground(new java.awt.Color(255,255,255));
					permisos.setFont(new java.awt.Font("Segoe UI",1,14));
					permisos.setEnabled(false);
					permisos.addActionListener(evento);
				}
			}
			this.setSize(550, 270);
			this.setPreferredSize(new java.awt.Dimension(550, 428));
			this.setBounds(0, 0, 550, 428);
			resetear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}