package globales.parametros;

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
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
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
public class VentanaPlantillas extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel panelBotones;
	private JButton crear;
	private JButton cerrar;
	private JButton guardar;
	private JTextField nombre;
	private JTextField id;
	private JTable jTabla;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private JButton borrar;
	private JButton consultar;
	private JPanel panelPrincipal;
	private Color color;
	private TablaPlantillas modeloTablaEventos = new TablaPlantillas();
	private JPanel jPanel1;
	private JScrollPane jScrollPane2;
	private JButton botonNombre;
	private JTextArea body;
	private JLabel jLabel2;
	private JButton botonFecha;
	private JButton botonEvento;
	private JButton botonEmail;
	private JButton botonTelefono;
	private JButton botonDireccion;
	private JButton botonDocumento;
	private JTextField asunto;
	private ControlTablaPlantillas control = new ControlTablaPlantillas(modeloTablaEventos);
	private InputMap im = (InputMap) UIManager.getDefaults().get("Button.focusInputMap");
	private ResultSet tabla;
	private ModeloPlantillas modelo = new ModeloPlantillas();

	public VentanaPlantillas(JFrame frame, Color color) {
		super(frame,"Plantillas",true);
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
		nombre.setEnabled(false);
		borrar.setEnabled(false);
		asunto.setEnabled(false);
		botonNombre.setEnabled(false);
		botonDocumento.setEnabled(false);
		botonDireccion.setEnabled(false);
		botonTelefono.setEnabled(false);
		botonEmail.setEnabled(false);
		botonEvento.setEnabled(false);
		botonFecha.setEnabled(false);
		body.setEnabled(false);
		nombre.setText("");
		asunto.setText("");
		body.setText("");
		id.setText("");
		control.borraFila();
	}

	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent actionEvent) {
				if(!guardar.isEnabled() && !borrar.isEnabled() && jTabla.getRowCount()==0){
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
	
	public void consultarUno(){
		tabla=modelo.buscarUno(id.getText());
		try {
			if(tabla.next()){
				nombre.setText(tabla.getString("NOMBRE"));
				asunto.setText(tabla.getString("ASUNTO"));
				body.setText(tabla.getString("BODY"));
				nombre.setEnabled(true);
				asunto.setEnabled(true);
				crear.setEnabled(false);
				consultar.setEnabled(false);
				borrar.setEnabled(true);
				guardar.setEnabled(true);
				botonNombre.setEnabled(true);
				botonDocumento.setEnabled(true);
				botonDireccion.setEnabled(true);
				botonTelefono.setEnabled(true);
				botonEmail.setEnabled(true);
				botonEvento.setEnabled(true);
				botonFecha.setEnabled(true);
				body.setEnabled(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		modelo.cerrarBase();
	}
	
	public void consultarTodos(){
		tabla=modelo.buscar();
		int fila=0;
		try {
			while(tabla.next()){
				control.anhadeFila(0);
				jTabla.setValueAt(tabla.getString("ID"), fila, 0);
				jTabla.setValueAt(tabla.getString("NOMBRE"), fila, 1);
				jTabla.setValueAt(tabla.getString("ASUNTO"), fila, 2);
				fila++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		modelo.cerrarBase();
	}
	
	private void initGUI(){
		try {
			ActionListener evento = new ActionListener() {

				public void actionPerformed(ActionEvent e){
					if(borrar==e.getSource()){
						int opcion=JOptionPane.showConfirmDialog(null, "¿Desea eliminar la plantilla Seleccionada?");
						if(opcion==0){
							modelo.eliminar(id.getText());
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
						nombre.setEnabled(true);
						asunto.setEnabled(true);
						crear.setEnabled(false);
						consultar.setEnabled(false);
						borrar.setEnabled(false);
						guardar.setEnabled(true);
						botonNombre.setEnabled(true);
						botonDocumento.setEnabled(true);
						botonDireccion.setEnabled(true);
						botonTelefono.setEnabled(true);
						botonEmail.setEnabled(true);
						botonEvento.setEnabled(true);
						botonFecha.setEnabled(true);
						body.setEnabled(true);
					}
					if(guardar==e.getSource()){
						if(guardar.isEnabled()&&!borrar.isEnabled()){
							modelo.crear(nombre.getText(), asunto.getText(), body.getText());
							JOptionPane.showMessageDialog(null, "Se guardo la plantilla correctamente");
							resetEstados();
						}
						if(guardar.isEnabled()&&borrar.isEnabled()){
							modelo.editar(id.getText(), nombre.getText(), asunto.getText(), body.getText());
							JOptionPane.showMessageDialog(null, "Se Edito la plantilla seleccionada.");
							resetEstados();
						}
						
					}
					if(consultar==e.getSource()){
						consultarTodos();
					}
					if(botonNombre==e.getSource()){
						body.setText(body.getText()+" "+"<<NOMBRE>>");
						body.requestFocus();
					}
					if(botonDocumento==e.getSource()){
						body.setText(body.getText()+" "+"<<DOCUMENTO>>");
						body.requestFocus();
					}
					if(botonDireccion==e.getSource()){
						body.setText(body.getText()+" "+"<<DIRECCION>>");
						body.requestFocus();
					}
					if(botonTelefono==e.getSource()){
						body.setText(body.getText()+" "+"<<TELEFONO>>");
						body.requestFocus();
					}
					if(botonEmail==e.getSource()){
						body.setText(body.getText()+" "+"<<EMAIL>>");
						body.requestFocus();
					}
					if(botonEvento==e.getSource()){
						body.setText(body.getText()+" "+"<<EVENTO>>");
						body.requestFocus();
					}
					if(botonFecha==e.getSource()){
						body.setText(body.getText()+" "+"<<FECHA>>");
						body.requestFocus();
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
				panelPrincipal.setBorder(BorderFactory.createTitledBorder("Plantillas"));
				{
					jLabel1 = new JLabel();
					panelPrincipal.add(jLabel1);
					jLabel1.setText("Nombre");
					jLabel1.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					nombre = new JTextField();
					panelPrincipal.add(nombre);
					nombre.setPreferredSize(new java.awt.Dimension(406, 23));
					nombre.setEnabled(false);
				}
				{
					id = new JTextField();
					panelPrincipal.add(id);
					id.setPreferredSize(new java.awt.Dimension(52, 23));
					id.setEnabled(false);
					id.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					jLabel2 = new JLabel();
					panelPrincipal.add(jLabel2);
					jLabel2.setText("Asunto");
					jLabel2.setPreferredSize(new java.awt.Dimension(80, 16));
				}
				{
					asunto = new JTextField();
					panelPrincipal.add(asunto);
					asunto.setEnabled(false);
					asunto.setPreferredSize(new java.awt.Dimension(464, 23));
				}
				{
					jScrollPane2 = new JScrollPane();
					panelPrincipal.add(jScrollPane2);
					jScrollPane2.setPreferredSize(new java.awt.Dimension(431, 220));
					{
						body = new JTextArea();
						jScrollPane2.setViewportView(body);
						body.setEnabled(false);
					}
				}
				{
					jPanel1 = new JPanel();
					GridLayout jPanel1Layout1 = new GridLayout(0, 1);
					jPanel1Layout1.setColumns(1);
					jPanel1Layout1.setHgap(5);
					jPanel1Layout1.setVgap(5);
					jPanel1.setLayout(jPanel1Layout1);
					panelPrincipal.add(jPanel1);
					jPanel1.setPreferredSize(new java.awt.Dimension(110, 220));
					jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
					{
						botonNombre = new JButton();
						jPanel1.add(botonNombre);
						botonNombre.setText("Nombre");
						botonNombre.setEnabled(false);
						botonNombre.addActionListener(evento);
					}
					{
						botonDocumento = new JButton();
						jPanel1.add(botonDocumento);
						botonDocumento.setText("Documento");
						botonDocumento.setEnabled(false);
						botonDocumento.addActionListener(evento);
					}
					{
						botonDireccion = new JButton();
						jPanel1.add(botonDireccion);
						botonDireccion.setText("Direccion");
						botonDireccion.setEnabled(false);
						botonDireccion.addActionListener(evento);
					}
					{
						botonTelefono = new JButton();
						jPanel1.add(botonTelefono);
						botonTelefono.setText("Telefono");
						botonTelefono.setEnabled(false);
						botonTelefono.addActionListener(evento);
					}
					{
						botonEmail = new JButton();
						jPanel1.add(botonEmail);
						botonEmail.setText("Email");
						botonEmail.setEnabled(false);
						botonEmail.addActionListener(evento);
					}
					{
						botonEvento = new JButton();
						jPanel1.add(botonEvento);
						botonEvento.setText("Evento");
						botonEvento.setEnabled(false);
						botonEvento.addActionListener(evento);
					}
					{
						botonFecha = new JButton();
						jPanel1.add(botonFecha);
						botonFecha.setText("Fecha");
						botonFecha.setEnabled(false);
						botonFecha.addActionListener(evento);
					}
				}
				{
					jScrollPane1 = new JScrollPane();
					panelPrincipal.add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(550, 106));
					{
						jTabla = new JTable();
						jScrollPane1.setViewportView(jTabla);
						jTabla.setModel(modeloTablaEventos);
						jTabla.getColumnModel().getColumn(0).setMaxWidth(30);
						jTabla.getColumnModel().getColumn(0).setMinWidth(30);
						jTabla.getColumnModel().getColumn(1).setMaxWidth(300);
						jTabla.getColumnModel().getColumn(1).setMinWidth(300);
						jTabla.getTableHeader().setBackground(color);
						jTabla.getTableHeader().setForeground(new java.awt.Color(255,255,255));

						DefaultTableCellRenderer render = new DefaultTableCellRenderer();
						render.setHorizontalAlignment(SwingConstants.CENTER);

						jTabla.getColumnModel().getColumn(0).setCellRenderer(render);

						jTabla.addMouseListener(new MouseAdapter(){
							public void mouseClicked(MouseEvent e) {
								if (e.getClickCount() == 2) {
									JTable target = (JTable)e.getSource();
									id.setText(jTabla.getValueAt(target.getSelectedRow(), 0)+"");
									consultarUno();
								}
							}
						}
								);
					}
				}
			}
			setSize(685, 454);
			setLocationRelativeTo(null);
			setResizable(false);
			setTitle("Plantillas");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
