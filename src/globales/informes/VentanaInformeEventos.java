package globales.informes;
import globales.administrativo.ModeloProgramador;

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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

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
public class VentanaInformeEventos extends javax.swing.JDialog {
	
	private static final long serialVersionUID = -6238787891279548585L;
	private JComboBox<String> estado;
	private JLabel jLabel1;
	private JPanel PanelFiltro;
	private JTable tablaEventos;
	private JScrollPane jScrollPane1;
	private JPanel panelTabla;
	private TablaEventos modeloTablaEventos = new TablaEventos();
	private ControlTablaEventos controlTablaEventos = new ControlTablaEventos(modeloTablaEventos);
	private Color color;
	private ResultSet tabla;
	private ModeloProgramador eventos = new ModeloProgramador();
	private TableRowSorter<TablaEventos> filtro;
	private JButton imprimir;
	private JPanel panelInferior;

	public VentanaInformeEventos(JFrame frame, Color color) {
		super(frame,"Informe Eventos",true);
		this.color=color;
		initGUI();
		consultarEvento();
	}
	
	public void consultarEvento(){
		int fila=0;
		tabla=eventos.buscarTodosEventos();
		controlTablaEventos.borraFila();
		try {
			while(tabla.next()){
				controlTablaEventos.anhadeFila(0);
				tablaEventos.setValueAt(tabla.getString("ID"), fila, 0);
				tablaEventos.setValueAt(tabla.getString("NUMERO_DOCUMENTO"), fila, 1);
				tablaEventos.setValueAt(tabla.getString("EVENTO"), fila, 2);
				tablaEventos.setValueAt(tabla.getString("FECHA"), fila, 3);
				tablaEventos.setValueAt(tabla.getString("FECHA_NOTIFICACION"), fila, 4);
				tablaEventos.setValueAt(tabla.getString("PLANTILLA"), fila, 5);
				tablaEventos.setValueAt(tabla.getString("ESTADO"), fila, 6);
				fila++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		eventos.cerrarBase();
	}
	
	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent actionEvent) {
				dispose();
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", actionListener);

		return rootPane;
	}
	
	private void initGUI() {
		try {
			ActionListener evento = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(estado==e.getSource()){
						filtro = new TableRowSorter<TablaEventos>(modeloTablaEventos);
						tablaEventos.setRowSorter(filtro);
						filtro.setRowFilter(RowFilter.regexFilter((String)estado.getSelectedItem(), 6));
					}
				}
			};
			{
				panelTabla = new JPanel();
				getContentPane().add(panelTabla, BorderLayout.CENTER);
				{
					jScrollPane1 = new JScrollPane();
					panelTabla.add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(649, 338));
					{
						tablaEventos = new JTable();
						jScrollPane1.setViewportView(tablaEventos);
						tablaEventos.setModel(modeloTablaEventos);
						RowSorter<TablaEventos> sorter = new TableRowSorter<TablaEventos>(modeloTablaEventos);
						tablaEventos.setRowSorter(sorter);
						tablaEventos.getTableHeader().setReorderingAllowed(false);
						tablaEventos.getColumnModel().getColumn(0).setMaxWidth(40);
						tablaEventos.getColumnModel().getColumn(0).setMinWidth(40);
						tablaEventos.getColumnModel().getColumn(1).setMaxWidth(80);
						tablaEventos.getColumnModel().getColumn(1).setMinWidth(80);
						tablaEventos.getColumnModel().getColumn(2).setMaxWidth(170);
						tablaEventos.getColumnModel().getColumn(2).setMinWidth(170);
						tablaEventos.getColumnModel().getColumn(3).setMaxWidth(80);
						tablaEventos.getColumnModel().getColumn(3).setMinWidth(80);
						tablaEventos.getColumnModel().getColumn(4).setMaxWidth(140);
						tablaEventos.getColumnModel().getColumn(4).setMinWidth(140);
						tablaEventos.getColumnModel().getColumn(5).setMaxWidth(55);
						tablaEventos.getColumnModel().getColumn(5).setMinWidth(55);
						tablaEventos.getTableHeader().setBackground(color);
						tablaEventos.getTableHeader().setForeground(new java.awt.Color(255,255,255));
						
						DefaultTableCellRenderer render = new DefaultTableCellRenderer();
						render.setHorizontalAlignment(SwingConstants.CENTER);
						
						tablaEventos.getColumnModel().getColumn(0).setCellRenderer(render);
						tablaEventos.getColumnModel().getColumn(1).setCellRenderer(render);
						tablaEventos.getColumnModel().getColumn(2).setCellRenderer(render);
						tablaEventos.getColumnModel().getColumn(3).setCellRenderer(render);
						tablaEventos.getColumnModel().getColumn(4).setCellRenderer(render);
						tablaEventos.getColumnModel().getColumn(5).setCellRenderer(render);
						tablaEventos.getColumnModel().getColumn(6).setCellRenderer(render);
					}
				}
			}
			{
				panelInferior = new JPanel();
				GridLayout panelInferiorLayout = new GridLayout(1, 1);
				panelInferiorLayout.setHgap(5);
				panelInferiorLayout.setVgap(5);
				panelInferiorLayout.setColumns(1);
				panelInferior.setLayout(panelInferiorLayout);
				getContentPane().add(panelInferior, BorderLayout.SOUTH);
				panelInferior.setBackground(color);
				{
					PanelFiltro = new JPanel();
					panelInferior.add(PanelFiltro);
					PanelFiltro.setPreferredSize(new java.awt.Dimension(440, 61));
					PanelFiltro.setBackground(color);
					PanelFiltro.setBorder(BorderFactory.createTitledBorder(new LineBorder(new java.awt.Color(255,255,255), 1, true), "Filtro", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI",1,12), new java.awt.Color(255,255,255)));
					{
						jLabel1 = new JLabel();
						PanelFiltro.add(jLabel1);
						jLabel1.setText("Filtrar por Estado");
						jLabel1.setPreferredSize(new java.awt.Dimension(126, 16));
						jLabel1.setForeground(new java.awt.Color(255,255,255));
						jLabel1.setFont(new java.awt.Font("Segoe UI",1,14));
						jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
						jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
					}
					{
						estado = new JComboBox<String>();
						PanelFiltro.add(estado);
						estado.addItem("");
						estado.addItem("ACTIVO");
						estado.addItem("TERMINADO");
						estado.setPreferredSize(new java.awt.Dimension(139, 23));
						estado.addActionListener(evento);
						
					}
				}
				{
					imprimir = new JButton();
					panelInferior.add(imprimir);
					imprimir.setText("Imprimir");
					imprimir.setBackground(color);
					imprimir.setFont(new java.awt.Font("Segoe UI",1,14));
					imprimir.setForeground(new java.awt.Color(255,255,255));
					imprimir.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
				}
			}
			setResizable(false);
			this.setSize(686, 453);
			setLocationRelativeTo(null);
			setTitle("Informe Eventos");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
