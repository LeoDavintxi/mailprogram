package globales.administrativo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

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
public class VentanaPermisos extends javax.swing.JDialog {
	private static final long serialVersionUID = 2205053636633787495L;
	private JCheckBox impuestos;
	private JCheckBox eventos;
	private JCheckBox iniciar;
	private JCheckBox programador;
	private JCheckBox clientes;
	private JCheckBox usuarios;
	private JCheckBox plantillasCorreo;
	private JCheckBox correoRemitente;
	private JPanel jPanel2;
	private JLabel jPanel1;
	private Color color;
	private JButton guardar;
	private String usuario;
	private ModeloPermisos permisos = new ModeloPermisos();
	private JCheckBox[] lista = new JCheckBox[9];
	private ResultSet tabla;

	public VentanaPermisos(JFrame frame,Color color,String usuario) {
		super(frame,"Permisos Usuarios",true);
		this.color=color;
		this.usuario=usuario;
		initGUI();
		consultarPermisosUsuario();
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

	private void consultarPermisosUsuario(){
		for(int i=1;i<=8;i++){
			tabla=permisos.buscarPermisoUsuario(i, usuario);
			try {
				if(tabla.next()){
					lista[i].setSelected(tabla.getBoolean("PERMISO"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			permisos.cerrarBase();
		}
	}

	private void guardarPermiso(){
		for(int i=1;i<=8;i++){
			permisos.editarPermiso(usuario, i, lista[i].isSelected());	
		}
	}

	private void initGUI() {
		try {
			setSize(300, 350);
			setDefaultCloseOperation(1);
			setLocationRelativeTo(null);
			setResizable(false);

			ActionListener evento = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if(guardar==e.getSource()){
						guardarPermiso();
						JOptionPane.showMessageDialog(null, "Permisos Guardados");
						dispose();
					}
				}
			};
			{
				jPanel1 = new JLabel();
				getContentPane().add(jPanel1, BorderLayout.NORTH);
				jPanel1.setPreferredSize(new java.awt.Dimension(294, 68));
				jPanel1.setText("ID Usuario: "+usuario);
				jPanel1.setHorizontalAlignment(SwingConstants.CENTER);
				jPanel1.setHorizontalTextPosition(SwingConstants.CENTER);
				jPanel1.setBackground(color);
				jPanel1.setFont(new java.awt.Font("Segoe UI",1,16));
				jPanel1.setOpaque(true);
				jPanel1.setForeground(new java.awt.Color(255,255,255));
			}
			{
				jPanel2 = new JPanel();
				getContentPane().add(jPanel2, BorderLayout.CENTER);
				jPanel2.setPreferredSize(new java.awt.Dimension(219, 269));
				{
					impuestos = new JCheckBox();
					jPanel2.add(impuestos);
					impuestos.setText("Parametros - Impuestos");
					impuestos.setPreferredSize(new java.awt.Dimension(263, 20));
					lista[1]=impuestos;

				}
				{
					correoRemitente = new JCheckBox();
					jPanel2.add(correoRemitente);
					correoRemitente.setText("Parametros - Correo remitente");
					correoRemitente.setPreferredSize(new java.awt.Dimension(263, 20));
					lista[2]=correoRemitente;
				}
				{
					plantillasCorreo = new JCheckBox();
					jPanel2.add(plantillasCorreo);
					plantillasCorreo.setText("Parametros - Plantillas Correo");
					plantillasCorreo.setPreferredSize(new java.awt.Dimension(263, 20));
					lista[3]=plantillasCorreo;
				}
				{
					usuarios = new JCheckBox();
					jPanel2.add(usuarios);
					usuarios.setText("Administrativo - Usuarios");
					usuarios.setPreferredSize(new java.awt.Dimension(263, 20));
					lista[4]=usuarios;
				}
				{
					clientes = new JCheckBox();
					jPanel2.add(clientes);
					clientes.setText("Administrativo -Clientes");
					clientes.setPreferredSize(new java.awt.Dimension(263, 20));
					lista[5]=clientes;
				}
				{
					programador = new JCheckBox();
					jPanel2.add(programador);
					programador.setText("Administrativo - Programador");
					programador.setPreferredSize(new java.awt.Dimension(263, 20));
					lista[6]=programador;
				}
				{
					iniciar = new JCheckBox();
					jPanel2.add(iniciar);
					iniciar.setText("Administrativo  - Iniciar");
					iniciar.setPreferredSize(new java.awt.Dimension(263, 20));
					lista[7]=iniciar;
				}
				{
					eventos = new JCheckBox();
					jPanel2.add(eventos);
					eventos.setText("Informes - Eventos");
					eventos.setPreferredSize(new java.awt.Dimension(263, 20));
					lista[8]=eventos;
				}
			}
			{
				guardar = new JButton();
				getContentPane().add(guardar, BorderLayout.SOUTH);
				guardar.setPreferredSize(new java.awt.Dimension(294, 48));
				guardar.setText("Guardar");
				guardar.addActionListener(evento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
