package globales.administrativo;

import com.toedter.calendar.JDateChooser;
import globales.parametros.ModeloImpuestos;
import globales.parametros.ModeloPlantillas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
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
public class VentanaProgramador extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panelBotones;
	private JButton crear;
	private JButton cerrar;
	private JButton guardar;
	private JTextField documento;
	private JTextField id;
	private JTable tablaEventos;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private JButton borrar;
	private JButton consultar;
	private JPanel panelPrincipal;
	private Color color;
	private TablaEventos modeloTablaEventos = new TablaEventos();
	private JLabel jLabel3;
	private JSeparator jSeparator1;
	private JComboBox<String> plantilla;
	private JComboBox<String> estado;
	private JLabel jLabel5;
	private JDateChooser fechaNotificacion;
	private JLabel jLabel4;
	private JLabel jLabel41;
	private JDateChooser fechaEvento;
	private JSpinner horaNotificacion;
	private JLabel nombreCliente;
	private JLabel jLabel2;
	private JComboBox<String> eventoCombo;
	private ControlTablaEventos control = new ControlTablaEventos(modeloTablaEventos);
	private InputMap im = (InputMap) UIManager.getDefaults().get("Button.focusInputMap");
	private ResultSet tabla;
	private ResultSet tabla1;	
	private ModeloProgramador modeloProgramador = new ModeloProgramador();
	private ModeloClientes cliente = new ModeloClientes();
	private ModeloImpuestos impuesto = new ModeloImpuestos();
	private ModeloPlantillas modeloPlantilla = new ModeloPlantillas();
	private SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SpinnerDateModel dateModel;
	private JSpinner.DateEditor timeEditor;

	public VentanaProgramador(JFrame frame, Color color) {
		super();
		this.color=color;
		initGUI();
		consultarImpuestos();
		consultarPlantilla();
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
		documento.setEnabled(false);
		borrar.setEnabled(false);
		eventoCombo.setEnabled(false);
		fechaEvento.setEnabled(false);
		fechaNotificacion.setEnabled(false);
		plantilla.setEnabled(false);
		documento.setText("");
		eventoCombo.setSelectedItem("");
		nombreCliente.setText("");
		fechaEvento.setDate(null);
		fechaNotificacion.setDate(null);
		plantilla.setSelectedItem("");
		control.borraFila();
		horaNotificacion.setEnabled(false);
		id.setText("");
		estado.setEnabled(false);

	}

	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent actionEvent) {
				if(!guardar.isEnabled() && !borrar.isEnabled() && !documento.isEnabled() && tablaEventos.getRowCount()==0){
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

	public void consultarImpuestos(){
		tabla=impuesto.buscar();
		try {
			while(tabla.next()){
				eventoCombo.addItem(tabla.getString("DESCRIPCION"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		impuesto.cerrarBase();
	}

	public void consultarPlantilla(){
		tabla=modeloPlantilla.buscar();
		try {
			while(tabla.next()){
				plantilla.addItem(tabla.getString("ID")+" - "+tabla.getString("NOMBRE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		modeloPlantilla.cerrarBase();
	}

	private void buscarCliente(){
		tabla=cliente.buscarUno(documento.getText());
		try {
			if(tabla.next()){
				nombreCliente.setText(tabla.getString("NOMBRE"));
			}else{
				JOptionPane.showMessageDialog(null, "El documento no aparece en la Base de datos");
				documento.requestFocus();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		cliente.cerrarBase();
	}

	private void buscarEventos(){
		tabla=modeloProgramador.buscarEventos(documento.getText());
		int fila=0;
		try {
			while(tabla.next()){
				control.anhadeFila(0);
				tablaEventos.setValueAt(tabla.getString("ID"), fila, 0);
				tablaEventos.setValueAt(tabla.getString("FECHA"), fila, 1);
				tablaEventos.setValueAt(tabla.getString("EVENTO"), fila, 2);
				tablaEventos.setValueAt(tabla.getString("FECHA_NOTIFICACION"), fila, 3);
				tablaEventos.setValueAt(tabla.getString("PLANTILLA"), fila, 4);
				fila++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		modeloProgramador.cerrarBase();
	}

	public void buscarEventoID(){
		tabla=modeloProgramador.buscarPorID(id.getText());
		try {
			if(tabla.next()){
				eventoCombo.setEnabled(true);
				fechaEvento.setEnabled(true);
				plantilla.setEnabled(true);
				fechaNotificacion.setEnabled(true);
				horaNotificacion.setEnabled(true);
				borrar.setEnabled(true);
				guardar.setEnabled(true);
				estado.setEnabled(true);

				eventoCombo.setSelectedItem(tabla.getString("EVENTO"));
				fechaEvento.setDate(formatoFecha.parse(tabla.getString("FECHA")));
				plantilla.setSelectedItem(consultarPlantillaID(tabla.getString("PLANTILLA")));
				fechaNotificacion.setDate(formatoFecha.parse(tabla.getString("FECHA_NOTIFICACION")));
				horaNotificacion.setValue(tabla.getTime("FECHA_NOTIFICACION"));
				estado.setSelectedItem(tabla.getString("ESTADO"));
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		modeloProgramador.cerrarBase();
	}

	public String consultarPlantillaID(String id){
		String plantilla="";
		tabla1=modeloPlantilla.buscarUno(id);
		try {
			if(tabla1.next()){
				plantilla=tabla1.getString("ID")+" - "+tabla1.getString("NOMBRE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		modeloPlantilla.cerrarBase();
		return plantilla;
	}

	public String fechaHora(){
		Calendar fechaNotif = Calendar.getInstance();
		fechaNotif.setTime(fechaNotificacion.getDate());
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(dateModel.getDate());
		fecha.set(Calendar.YEAR, fechaNotif.get(Calendar.YEAR));
		fecha.set(Calendar.MONTH, fechaNotif.get(Calendar.MONTH));
		fecha.set(Calendar.DAY_OF_MONTH, fechaNotif.get(Calendar.DAY_OF_MONTH));
		return formatoFecha.format(fecha.getTime());
	}

	private void initGUI(){
		try {
			FocusListener accion = new FocusListener() {
				public void focusLost(FocusEvent e) {
					if(documento==e.getSource()&&!documento.getText().equals("")){
						buscarCliente();
					}
				}
				public void focusGained(FocusEvent e) {

				}
			};

			ActionListener evento = new ActionListener() {

				public void actionPerformed(ActionEvent e){
					if(borrar==e.getSource()){
						int opcion=JOptionPane.showConfirmDialog(null, "¿Desea eliminar el Evento Seleccionado?");
						if(opcion==0){
							modeloProgramador.eliminar(id.getText());
							resetEstados();
						}
						if(opcion==1){
							resetEstados();
						}
					}
					if(cerrar==e.getSource()){
						dispose();
					}

					if(crear==e.getSource()){
						documento.setEnabled(true);
						eventoCombo.setEnabled(true);
						fechaEvento.setEnabled(true);
						fechaNotificacion.setEnabled(true);
						plantilla.setEnabled(true);
						horaNotificacion.setEnabled(true);
						crear.setEnabled(false);
						consultar.setEnabled(false);
						borrar.setEnabled(false);
						guardar.setEnabled(true);
						estado.setEnabled(true);
					}
					if(guardar==e.getSource()){
						if(guardar.isEnabled()&&!borrar.isEnabled()){
							modeloProgramador.crear(documento.getText(), (String)eventoCombo.getSelectedItem(), formatoFecha.format(fechaEvento.getDate()), fechaHora(), ((String)plantilla.getSelectedItem()).substring(0, 1).replace(" ", ""), (String)estado.getSelectedItem());
							JOptionPane.showMessageDialog(null, "El evento ha sido guardado");
							resetEstados();
						}
						if(guardar.isEnabled()&&borrar.isEnabled()){
							modeloProgramador.editar(id.getText(), (String)eventoCombo.getSelectedItem(), formatoFecha.format(fechaEvento.getDate()), fechaHora(), ((String)plantilla.getSelectedItem()).substring(0, 1).replace(" ", ""), (String)estado.getSelectedItem());
							JOptionPane.showMessageDialog(null, "El evento ha sido actualizado");
							resetEstados();
						}

					}
					if(consultar==e.getSource()){
						documento.setEnabled(true);
						documento.requestFocus();
						crear.setEnabled(false);
						consultar.setEnabled(false);
					}
					if(documento==e.getSource()&&!guardar.isEnabled()){
						buscarCliente();
						buscarEventos();
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
				panelPrincipal.setBorder(BorderFactory.createTitledBorder("Eventos"));
				{
					jLabel1 = new JLabel();
					panelPrincipal.add(jLabel1);
					jLabel1.setText("Nit");
					jLabel1.setPreferredSize(new java.awt.Dimension(116, 16));
				}
				{
					documento = new JTextField();
					panelPrincipal.add(documento);
					documento.setPreferredSize(new java.awt.Dimension(110, 23));
					documento.setHorizontalAlignment(SwingConstants.CENTER);
					documento.setEnabled(false);
					documento.addActionListener(evento);
					documento.addFocusListener(accion);
				}
				{
					nombreCliente = new JLabel();
					panelPrincipal.add(nombreCliente);
					nombreCliente.setText("");
					nombreCliente.setPreferredSize(new java.awt.Dimension(198, 23));
					nombreCliente.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
					nombreCliente.setHorizontalAlignment(SwingConstants.CENTER);
					nombreCliente.setHorizontalTextPosition(SwingConstants.CENTER);
					nombreCliente.setFont(new java.awt.Font("Segoe UI",1,14));
				}
				{
					id = new JTextField();
					panelPrincipal.add(id);
					id.setPreferredSize(new java.awt.Dimension(38, 23));
					id.setEditable(false);
					id.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					jLabel2 = new JLabel();
					panelPrincipal.add(jLabel2);
					jLabel2.setText("Evento");
					jLabel2.setPreferredSize(new java.awt.Dimension(116, 16));
				}
				{
					eventoCombo = new JComboBox<String>();
					panelPrincipal.add(eventoCombo);
					eventoCombo.addItem("");
					eventoCombo.setEnabled(false);
					eventoCombo.setPreferredSize(new java.awt.Dimension(231, 23));
				}
				{
					estado = new JComboBox<String>();
					panelPrincipal.add(estado);
					estado.addItem("");
					estado.addItem("ACTIVO");
					estado.addItem("TERMINADO");
					estado.setEnabled(false);
					estado.setPreferredSize(new java.awt.Dimension(122, 23));
				}
				{
					jLabel3 = new JLabel();
					panelPrincipal.add(jLabel3);
					jLabel3.setText("Fecha Evento");
					jLabel3.setPreferredSize(new java.awt.Dimension(116, 16));
				}
				{
					fechaEvento = new JDateChooser();
					panelPrincipal.add(fechaEvento);
					fechaEvento.setEnabled(false);
					fechaEvento.setPreferredSize(new java.awt.Dimension(110, 23));
				}
				{
					jLabel5 = new JLabel();
					panelPrincipal.add(jLabel5);
					jLabel5.setText("Plantilla");
					jLabel5.setPreferredSize(new java.awt.Dimension(81, 16));
				}
				{
					plantilla = new JComboBox<String>();
					panelPrincipal.add(plantilla);
					plantilla.addItem("");
					plantilla.setEnabled(false);
					plantilla.setPreferredSize(new java.awt.Dimension(156, 23));
				}
				{
					jLabel4 = new JLabel();
					panelPrincipal.add(jLabel4);
					jLabel4.setText("Fecha Notificacion:");
					jLabel4.setPreferredSize(new java.awt.Dimension(116, 16));
				}
				{
					fechaNotificacion = new JDateChooser();
					panelPrincipal.add(fechaNotificacion);
					fechaNotificacion.setEnabled(false);
					fechaNotificacion.setPreferredSize(new java.awt.Dimension(110, 23));
				}
				{
					jLabel41 = new JLabel();
					panelPrincipal.add(jLabel41);
					jLabel41.setText("Hora Notificacion");
					jLabel41.setPreferredSize(new java.awt.Dimension(153, 16));
				}
				{
					dateModel = new  SpinnerDateModel();
					horaNotificacion = new JSpinner(dateModel);
					panelPrincipal.add(horaNotificacion);
					timeEditor = new JSpinner.DateEditor(horaNotificacion, "HH:mm:ss");
					horaNotificacion.setEditor(timeEditor);
					horaNotificacion.setEnabled(false);
					horaNotificacion.setPreferredSize(new java.awt.Dimension(85, 23));
				}
				{
					jSeparator1 = new JSeparator();
					panelPrincipal.add(jSeparator1);
					jSeparator1.setPreferredSize(new java.awt.Dimension(515, 15));
				}
				{
					jScrollPane1 = new JScrollPane();
					panelPrincipal.add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(515, 230));
					{
						tablaEventos = new JTable();
						jScrollPane1.setViewportView(tablaEventos);
						tablaEventos.setModel(modeloTablaEventos);
						tablaEventos.getColumnModel().getColumn(0).setMaxWidth(40);
						tablaEventos.getColumnModel().getColumn(0).setMinWidth(40);
						tablaEventos.getColumnModel().getColumn(1).setMaxWidth(140);
						tablaEventos.getColumnModel().getColumn(1).setMinWidth(140);
						tablaEventos.getColumnModel().getColumn(2).setMaxWidth(130);
						tablaEventos.getColumnModel().getColumn(2).setMinWidth(130);
						tablaEventos.getColumnModel().getColumn(3).setMaxWidth(140);
						tablaEventos.getColumnModel().getColumn(3).setMinWidth(140);
						tablaEventos.getTableHeader().setBackground(color);
						tablaEventos.getTableHeader().setForeground(new java.awt.Color(255,255,255));

						DefaultTableCellRenderer render = new DefaultTableCellRenderer();
						render.setHorizontalAlignment(SwingConstants.CENTER);

						tablaEventos.getColumnModel().getColumn(0).setCellRenderer(render);

						tablaEventos.addMouseListener(new MouseAdapter(){
							public void mouseClicked(MouseEvent e) {
								if (e.getClickCount() == 2) {
									JTable target = (JTable)e.getSource();
									id.setText(tablaEventos.getValueAt(target.getSelectedRow(), 0)+"");
									buscarEventoID();
								}
							}
						}
								);
					}
				}
			}
			setSize(550, 430);
			setResizable(false);
			setTitle("Programador");
			this.setFrameIcon(new ImageIcon(getClass().getClassLoader().getResource("png/Calendar.png")));
			this.setPreferredSize(new java.awt.Dimension(630, 430));
			this.setBounds(0, 0, 630, 430);
			this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0,0,0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
