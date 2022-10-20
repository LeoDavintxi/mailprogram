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
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
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
public class VentanaClientes extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panelBotones;
	private JButton crear;
	private JButton cerrar;
	private JButton guardar;
	private JTextField nombre;
	private JTable tablaClientes;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private JButton borrar;
	private JButton consultar;
	private JPanel panelPrincipal;
	private Color color;
	private TablaClientes modeloTablaImpuestos = new TablaClientes();
	private JLabel jLabel3;
	private JSeparator jSeparator1;
	private JLabel jLabel9;
	private JLabel jLabel8;
	private JTextField email;
	private JTextField nombreContacto;
	private JLabel jLabel7;
	private JTextField telefono;
	private JLabel jLabel6;
	private JComboBox<String> estado;
	private JLabel jLabel5;
	private JTextField ciudad;
	private JLabel jLabel4;
	private JTextField direccion;
	private JTextField numeroDocumento;
	private JLabel jLabel2;
	private JComboBox<String> tipoDocumento;
	private ControlTablaClientes control = new ControlTablaClientes(modeloTablaImpuestos);
	private InputMap im = (InputMap) UIManager.getDefaults().get("Button.focusInputMap");
	private ResultSet tabla;
	private ModeloClientes clientes = new ModeloClientes();

	public VentanaClientes(JFrame frame, Color color) {
		super();
		this.color=color;
		initGUI();
		Object pressedAction = im.get(KeyStroke.getKeyStroke("pressed SPACE"));
		Object releasedAction = im.get(KeyStroke.getKeyStroke("released SPACE"));
		im.remove(KeyStroke.getKeyStroke("pressed SPACE"));
		im.remove(KeyStroke.getKeyStroke("released SPACE"));
		im.put(KeyStroke.getKeyStroke("pressed ENTER"), pressedAction);
		im.put(KeyStroke.getKeyStroke("released ENTER"), releasedAction);
	}

	public void resetEstados (){
		crear.setEnabled(true);
		consultar.setEnabled(true);
		guardar.setEnabled(false);
		nombre.setEditable(false);
		borrar.setEnabled(false);
		tipoDocumento.setEnabled(false);
		numeroDocumento.setEditable(false);
		direccion.setEditable(false);
		ciudad.setEditable(false);
		estado.setEnabled(false);
		nombreContacto.setEditable(false);
		telefono.setEditable(false);
		email.setEditable(false);
		nombre.setText("");
		tipoDocumento.setSelectedItem("");
		numeroDocumento.setText("");
		direccion.setText("");
		ciudad.setText("");
		estado.setSelectedItem("");
		nombreContacto.setText("");
		telefono.setText("");
		email.setText("");
		control.borraFila();
	}

	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent actionEvent) {
				if(!guardar.isEnabled() && !borrar.isEnabled() && !numeroDocumento.isEditable() && tablaClientes.getRowCount()==0){
					dispose();
				}else{
					resetEstados();
				}
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", actionListener);

		return rootPane;
	}
	
	private void consultarCliente(){
		tabla=clientes.buscarUno(numeroDocumento.getText());
		try {
			if(tabla.next()){
				nombre.setEditable(true);
				borrar.setEnabled(true);
				guardar.setEnabled(true);
				tipoDocumento.setEnabled(true);
				numeroDocumento.setEditable(true);
				direccion.setEditable(true);
				ciudad.setEditable(true);
				estado.setEnabled(true);
				nombreContacto.setEditable(true);
				telefono.setEditable(true);
				email.setEditable(true);
				crear.setEnabled(false);
				consultar.setEnabled(false);
				nombre.setText(tabla.getString("NOMBRE"));
				tipoDocumento.setSelectedItem(tabla.getString("TIPO_DOCUMENTO"));
				direccion.setText(tabla.getString("DIRECCION"));
				ciudad.setText(tabla.getString("CIUDAD"));
				estado.setSelectedItem(tabla.getString("ESTADO"));
				nombreContacto.setText(tabla.getString("CONTACTO"));
				telefono.setText(tabla.getString("TELEFONO"));
				email.setText(tabla.getString("EMAIL"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		clientes.cerrarBase();
	}
	
	private void consultarTodos(){
		tabla=clientes.buscar();
		int fila=0;
		try {
			while(tabla.next()){
				control.anhadeFila(fila);
				tablaClientes.setValueAt(tabla.getString("NUMERO_DOCUMENTO"), fila, 0);
				tablaClientes.setValueAt(tabla.getString("NOMBRE"), fila, 1);
				tablaClientes.setValueAt(tabla.getString("EMAIL"), fila, 2);
				fila++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initGUI(){
		try {
			ActionListener evento = new ActionListener() {

				public void actionPerformed(ActionEvent e){
					if(borrar==e.getSource()){

					}
					if(cerrar==e.getSource()){
						dispose();
					}
					if(crear==e.getSource()){
						nombre.setEditable(true);
						tipoDocumento.setEnabled(true);
						numeroDocumento.setEditable(true);
						direccion.setEditable(true);
						ciudad.setEditable(true);
						estado.setEnabled(true);
						nombreContacto.setEditable(true);
						telefono.setEditable(true);
						email.setEditable(true);
						crear.setEnabled(false);
						consultar.setEnabled(false);
						borrar.setEnabled(false);
						guardar.setEnabled(true);
					}
					if(guardar==e.getSource()&&borrar.isEnabled()){
						clientes.editar(nombre.getText(), (String)tipoDocumento.getSelectedItem(), numeroDocumento.getText(), direccion.getText(), ciudad.getText(), (String)estado.getSelectedItem(), nombreContacto.getText(), telefono.getText(), email.getText());
						JOptionPane.showMessageDialog(null, "Se aplican los cambios realizados.");
						resetEstados();
					}
					if(guardar==e.getSource()&&!borrar.isEnabled()){
						clientes.crear(nombre.getText(), (String)tipoDocumento.getSelectedItem(), numeroDocumento.getText(), direccion.getText(), ciudad.getText(), (String)estado.getSelectedItem(), nombreContacto.getText(), telefono.getText(), email.getText());
						JOptionPane.showMessageDialog(null, "Se guardan los cambios realizados.");
						resetEstados();
					}
					if(consultar==e.getSource()){
						control.borraFila();
						consultarTodos();
					}
					if(borrar==e.getSource()){
						clientes.eliminar(numeroDocumento.getText());
						JOptionPane.showMessageDialog(null, "Se elimina el registro eliminado.");
						resetEstados();
					}

				}
			};
			{
				panelBotones = new JPanel();
				GridLayout jPanel1Layout = new GridLayout(0, 1);
				jPanel1Layout.setHgap(5);
				jPanel1Layout.setVgap(5);
				jPanel1Layout.setColumns(1);
				panelBotones.setLayout(jPanel1Layout);
				getContentPane().add(panelBotones, BorderLayout.WEST);
				panelBotones.setPreferredSize(new java.awt.Dimension(85, 372));
				panelBotones.setBackground(color);
				{
					crear = new JButton();
					panelBotones.add(crear);
					crear.setText("Crear");
					crear.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					crear.setForeground(new java.awt.Color(255,255,255));
					crear.setBackground(color);
					crear.addActionListener(evento);
				}
				{
					consultar = new JButton();
					panelBotones.add(consultar);
					consultar.setText("Consultar");
					consultar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					consultar.setForeground(new java.awt.Color(255,255,255));
					consultar.setBackground(color);
					consultar.addActionListener(evento);
				}
				{
					borrar = new JButton();
					panelBotones.add(borrar);
					borrar.setText("Borrar");
					borrar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					borrar.setForeground(new java.awt.Color(255,255,255));
					borrar.setBackground(color);
					borrar.setEnabled(false);
					borrar.addActionListener(evento);
				}
				{
					guardar = new JButton();
					panelBotones.add(guardar);
					guardar.setText("Guardar");
					guardar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					guardar.setForeground(new java.awt.Color(255,255,255));
					guardar.setBackground(color);
					guardar.setEnabled(false);
					guardar.addActionListener(evento);
				}
				{
					cerrar = new JButton();
					panelBotones.add(cerrar);
					cerrar.setText("Cerrar");
					cerrar.setBackground(new java.awt.Color(0,0,0));
					cerrar.setForeground(new java.awt.Color(255,255,255));
					cerrar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					cerrar.addActionListener(evento);
				}
			}
			{
				panelPrincipal = new JPanel();
				getContentPane().add(panelPrincipal, BorderLayout.CENTER);
				panelPrincipal.setBorder(BorderFactory.createTitledBorder("Datos Clientes"));
				{
					jLabel1 = new JLabel();
					panelPrincipal.add(jLabel1);
					jLabel1.setText("Nombre");
					jLabel1.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					nombre = new JTextField();
					panelPrincipal.add(nombre);
					nombre.setPreferredSize(new java.awt.Dimension(340, 23));
					nombre.setEditable(false);
				}
				{
					jLabel2 = new JLabel();
					panelPrincipal.add(jLabel2);
					jLabel2.setText("Tipo Doc:");
					jLabel2.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					tipoDocumento = new JComboBox<String>();
					panelPrincipal.add(tipoDocumento);
					tipoDocumento.addItem("");
					tipoDocumento.addItem("CC");
					tipoDocumento.addItem("NIT");
					tipoDocumento.addItem("CE");
					tipoDocumento.addItem("PP");
					tipoDocumento.addItem("NUIP");
					tipoDocumento.setEnabled(false);
					tipoDocumento.setPreferredSize(new java.awt.Dimension(88, 23));
				}
				{
					jLabel3 = new JLabel();
					panelPrincipal.add(jLabel3);
					jLabel3.setText("Numero Doc:");
					jLabel3.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					numeroDocumento = new JTextField();
					panelPrincipal.add(numeroDocumento);
					numeroDocumento.setText("");
					numeroDocumento.setEditable(false);
					numeroDocumento.setPreferredSize(new java.awt.Dimension(162, 23));
					numeroDocumento.addActionListener(evento);
				}
				{
					jLabel4 = new JLabel();
					panelPrincipal.add(jLabel4);
					jLabel4.setText("Direccion");
					jLabel4.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					direccion = new JTextField();
					panelPrincipal.add(direccion);
					direccion.setText("");
					direccion.setEditable(false);
					direccion.setPreferredSize(new java.awt.Dimension(340,23));
				}
				{
					jLabel5 = new JLabel();
					panelPrincipal.add(jLabel5);
					jLabel5.setText("Ciudad");
					jLabel5.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					ciudad = new JTextField();
					panelPrincipal.add(ciudad);
					ciudad.setText("");
					ciudad.setEditable(false);
					ciudad.setPreferredSize(new java.awt.Dimension(162,23));
				}
				{
					jLabel6 = new JLabel();
					panelPrincipal.add(jLabel6);
					jLabel6.setText("Estado");
					jLabel6.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					estado = new JComboBox<String>();
					panelPrincipal.add(estado);
					estado.addItem("");
					estado.addItem("Activo");
					estado.addItem("Inactivo");
					estado.setEnabled(false);
					estado.setPreferredSize(new java.awt.Dimension(88,23));
				}
				{
					jLabel7 = new JLabel();
					panelPrincipal.add(jLabel7);
					jLabel7.setText("Contacto");
					jLabel7.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					nombreContacto = new JTextField();
					panelPrincipal.add(nombreContacto);
					nombreContacto.setText("");
					nombreContacto.setEditable(false);
					nombreContacto.setPreferredSize(new java.awt.Dimension(340, 23));
				}
				{
					jLabel8 = new JLabel();
					panelPrincipal.add(jLabel8);
					jLabel8.setText("Telefono");
					jLabel8.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					telefono = new JTextField();
					panelPrincipal.add(telefono);
					telefono.setText("");
					telefono.setEditable(false);
					telefono.setPreferredSize(new java.awt.Dimension(88, 23));
				}
				{
					jLabel9 = new JLabel();
					panelPrincipal.add(jLabel9);
					jLabel9.setText("Email");
					jLabel9.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					email = new JTextField();
					panelPrincipal.add(email);
					email.setText("");
					email.setEditable(false);
					email.setPreferredSize(new java.awt.Dimension(162, 23));
				}
				{
					jSeparator1 = new JSeparator();
					panelPrincipal.add(jSeparator1);
					jSeparator1.setPreferredSize(new java.awt.Dimension(423, 15));
				}
				{
					jScrollPane1 = new JScrollPane();
					panelPrincipal.add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(425, 171));
					{
						tablaClientes = new JTable();
						jScrollPane1.setViewportView(tablaClientes);
						tablaClientes.setModel(modeloTablaImpuestos);
						tablaClientes.getColumnModel().getColumn(0).setMaxWidth(80);
						tablaClientes.getColumnModel().getColumn(0).setMinWidth(80);
						tablaClientes.getColumnModel().getColumn(1).setMaxWidth(160);
						tablaClientes.getColumnModel().getColumn(1).setMinWidth(160);
						tablaClientes.getTableHeader().setBackground(color);
						tablaClientes.getTableHeader().setForeground(new java.awt.Color(255,255,255));

						DefaultTableCellRenderer render = new DefaultTableCellRenderer();
						render.setHorizontalAlignment(SwingConstants.CENTER);

						tablaClientes.getColumnModel().getColumn(0).setCellRenderer(render);

						tablaClientes.addMouseListener(new MouseAdapter(){
							public void mouseClicked(MouseEvent e) {
								if (e.getClickCount() == 2) {
									JTable target = (JTable)e.getSource();
									numeroDocumento.setText(tablaClientes.getValueAt(target.getSelectedRow(), 0)+"");
									consultarCliente();
								}
							}
						}
								);
					}
				}
			}
			setSize(550, 430);
			setResizable(false);
			setTitle("Clientes");
			this.setFrameIcon(new ImageIcon(getClass().getClassLoader().getResource("png/User group.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
