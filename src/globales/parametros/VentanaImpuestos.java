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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
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
public class VentanaImpuestos extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel panelBotones;
	private JButton crear;
	private JButton cerrar;
	private JButton guardar;
	private JTextField codigo;
	private JTextField nombre;
	private JTable tablaImpuestos;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private JButton borrar;
	private JButton consultar;
	private JPanel panelPrincipal;
	private Color color;
	private ResultSet tabla;
	private boolean vaACrear;
	private ModeloImpuestos modeloImpuestos = new ModeloImpuestos();
	private TablaImpuestos modeloTablaImpuestos = new TablaImpuestos();
	private ControlTablaImpuestos control = new ControlTablaImpuestos(modeloTablaImpuestos);
	private InputMap im = (InputMap) UIManager.getDefaults().get("Button.focusInputMap");
	
	public VentanaImpuestos(JFrame frame, Color color) {
		super(frame,"Impuestos",true);
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
		codigo.setText("");
		nombre.setText("");
		control.borraFila();
		vaACrear=false;
	}
	
	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent actionEvent) {
				if(!guardar.isEnabled() && !borrar.isEnabled() && tablaImpuestos.getRowCount()==0){
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
	private void initGUI(){
		try {
			ActionListener evento = new ActionListener() {
				
				public void actionPerformed(ActionEvent e){
					if(borrar==e.getSource()){
						int opcion=JOptionPane.showConfirmDialog(null, "¿Desea eliminar el Impuesto Seleccionado?");
						if(opcion==0){
							modeloImpuestos.eliminar(codigo.getText());
							resetEstados();
						}
						if(opcion==1){//opcion 1 = no, Reset ventana
							resetEstados();
						}
					}
					
					if(cerrar==e.getSource()){
						dispose();
					}

					if(crear==e.getSource()){
						nombre.setEditable(true);
						crear.setEnabled(false);
						consultar.setEnabled(false);
						borrar.setEnabled(false);
						guardar.setEnabled(true);
						vaACrear=true;
					}
					if(guardar==e.getSource()){
						if(vaACrear){//para crear un banco
							int opcion=0;
							opcion=JOptionPane.showConfirmDialog(null, "¿Desea guardar el nuevo impuesto?");
							if(opcion==0){//opcion 0 = si, Guardar en BD
								tabla = modeloImpuestos.buscarUnO(codigo.getText());
								try {
									if(tabla.next()) {//consulta si el codigo ya existe
										JOptionPane.showMessageDialog(null, "El impuesto ya existe");
										resetEstados();
										vaACrear=!vaACrear;
									}else{
										modeloImpuestos.crear(codigo.getText(), nombre.getText().toUpperCase());
										resetEstados();
										vaACrear=!vaACrear;
										tabla.close();
									}
								}catch (SQLException localSQLException) {
								}
							}
							if(opcion==1){//opcion 1 = no, Reset ventana
								resetEstados();
							}	
						}
						if(!vaACrear){
							int opcion=JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios realizados en el impuesto seleccionado?");
							if(opcion==0){
								modeloImpuestos.editar(codigo.getText(), nombre.getText().toUpperCase());
								resetEstados();
							}
							if(opcion==1){//opcion 1 = no, Reset ventana
								resetEstados();
							}
						}
						
					}
					if(consultar==e.getSource()){
						crear.setEnabled(false);
						consultar.setEnabled(false);
						borrar.setEnabled(false);
						guardar.setEnabled(false);
						control.borraFila();
						JOptionPane.showMessageDialog(null, "Consultará todos los impuestos creados. Para editar uno, Haga doble clic en el impuesto. ");
						tabla = modeloImpuestos.buscar();
						try {
							int fila = 0;
							while (tabla.next()) {
								control.anhadeFila(tabla.getRow());
								modeloTablaImpuestos.setValueAt(tabla.getString("ID"), fila, 0);
								modeloTablaImpuestos.setValueAt(tabla.getString("DESCRIPCION"), fila, 1);
								fila += 1;
							}
							tabla.close();
						} catch (SQLException localSQLException) {
						}
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
				panelPrincipal.setBorder(BorderFactory.createTitledBorder("Impuestos"));
				{
					jLabel1 = new JLabel();
					panelPrincipal.add(jLabel1);
					jLabel1.setText("No");
					jLabel1.setPreferredSize(new java.awt.Dimension(21, 16));
				}
				{
					codigo = new JTextField();
					codigo.setHorizontalAlignment(SwingConstants.CENTER);
					panelPrincipal.add(codigo);
					codigo.setPreferredSize(new java.awt.Dimension(45, 23));
					codigo.setEditable(false);
				}
				{
					nombre = new JTextField();
					panelPrincipal.add(nombre);
					nombre.setPreferredSize(new java.awt.Dimension(349, 23));
					nombre.setEditable(false);
				}
				{
					jScrollPane1 = new JScrollPane();
					panelPrincipal.add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(425, 314));
					{
						tablaImpuestos = new JTable();
						jScrollPane1.setViewportView(tablaImpuestos);
						tablaImpuestos.setModel(modeloTablaImpuestos);
						tablaImpuestos.getColumnModel().getColumn(0).setMaxWidth(40);
						tablaImpuestos.getColumnModel().getColumn(0).setMinWidth(40);
						tablaImpuestos.getTableHeader().setBackground(color);
						tablaImpuestos.getTableHeader().setForeground(new java.awt.Color(255,255,255));
						
						DefaultTableCellRenderer render = new DefaultTableCellRenderer();
						render.setHorizontalAlignment(SwingConstants.CENTER);
						
						tablaImpuestos.getColumnModel().getColumn(0).setCellRenderer(render);
						
						tablaImpuestos.addMouseListener(new MouseAdapter(){
							 public void mouseClicked(MouseEvent e) {
							      if (e.getClickCount() == 2) {
							    	  nombre.setEditable(true);
							    	  consultar.setEnabled(false);
							    	  borrar.setEnabled(true);
							    	  guardar.setEnabled(true);
							    	  vaACrear=false;
							    	  JTable target = (JTable)e.getSource();
							    	  codigo.setText(tablaImpuestos.getValueAt(target.getSelectedRow(), 0)+"");
							    	  nombre.setText(tablaImpuestos.getValueAt(target.getSelectedRow(), 1)+"");
							         }
							   }
						}
						);
					}
				}
			}
			setSize(550, 410);
			setLocationRelativeTo(null);
			setResizable(false);
			setTitle("Impuestos");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
