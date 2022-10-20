package globales.principal;

import globales.administrativo.Acerca;
import globales.administrativo.ModeloPermisos;
import globales.administrativo.VentanaClientes;
import globales.administrativo.VentanaProgramador;
import globales.administrativo.VentanaUsuarios;
import globales.informes.VentanaInformeEventos;
import globales.parametros.VentanaCorreoRemitente;
import globales.parametros.VentanaImpuestos;
import globales.parametros.VentanaPlantillas;
import java.awt.*;
import java.awt.event.*;
import java.awt.TrayIcon.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;


public class VentanaPrincipal extends JFrame{

	private static final long serialVersionUID = 1L;
	private JMenu menuAyuda;
	private JMenuBar menu;
	private JPanel panelProgramas;
	private JButton botonUsuarios;
	private JMenuItem menuItemAcerca;
	private JDesktopPane panelPrincipal;
	private JButton botonSalir;
	private JButton botonIniciar;
	private JPanel panelEstado;
	private JButton botonProgramador;
	private JLabel jLabel5;
	private Reloj labelReloj;
	private JSeparator jSeparator1;
	private JMenuItem menuItemSalir;
	private JMenu menuItemParametros;
	private JMenuItem menuItemImpuestos;
	private JMenuItem menuItemCorreoRemitente;
	private JMenuItem menuItemPlantillas;
	private JMenuItem menuItemEventos;
	private JMenu menuAdministrativo;
	private JMenu menuInformes;
	private JPanel panelInformacion;
	private JPanel panelAyuda;
	private JLabel jLabel3;
	private JLabel textAyuda;
	private JButton botonClientes;
	private Color color;
	private String usuario;
	private String version;
	private PopupMenu menuTray = new PopupMenu();
	private Image imagen =new ImageIcon(getClass().getResource("/png/trayIcon.png")).getImage() ;
	private TrayIcon icon = new TrayIcon(imagen, "Program Mail", menuTray);
	private MenuItem itemCerrar;
	private MenuItem itemRestaurar;
	private Tarea tarea;
	private AbstractButton[] lista = new AbstractButton[9];
	private ResultSet tabla;
	private ModeloPermisos permisos = new ModeloPermisos();

	public VentanaPrincipal(Color color, String usuario) {
		super();
		this.color=color;
		this.usuario=usuario;
		version="1.0.01";
		initGUI();
		consultarPermisosUsuario();
	}
	
	private void consultarPermisosUsuario(){
		for(int i=1;i<=8;i++){
			tabla=permisos.buscarPermisoUsuario(i, usuario);
			try {
				if(tabla.next()){
					lista[i].setEnabled(tabla.getBoolean("PERMISO"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			permisos.cerrarBase();
		}
	}

	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent actionEvent) {
				if(panelPrincipal.getAllFrames().length==0){
					System.exit(0);
				}
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", actionListener);

		return rootPane;
	}	

	private void initGUI() {
		try {
			WindowStateListener accion = new WindowStateListener() {
				public void windowStateChanged(WindowEvent e) {
					if(e.getNewState()==ICONIFIED){
						try {
							setState(NORMAL);
							setVisible(false);
							icon.setImageAutoSize(true);
							SystemTray.getSystemTray().add(icon);
							icon.displayMessage("Program Mail", "El programa se ejecutara en segundo plano.", MessageType.INFO);
							tarea = new Tarea();
						} catch (AWTException ex) {
							ex.printStackTrace();
						}
					}
				}
			};

			ActionListener evento = new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					if(botonIniciar==e.getSource()){
						setExtendedState(ICONIFIED);
					}
					if(botonSalir==e.getSource()||menuItemSalir==e.getSource()){
						System.exit(0);
					}
					if(itemCerrar==e.getSource()){
						System.exit(0);
					}
					if(itemRestaurar==e.getSource()){
						setVisible(true);
						SystemTray.getSystemTray().remove(icon);
						tarea.apagar();

					}
					if(menuItemImpuestos==e.getSource()){
						VentanaImpuestos impuestos = new VentanaImpuestos(null, color);
						impuestos.setVisible(true);
					}
					if(menuItemCorreoRemitente==e.getSource()){
						VentanaCorreoRemitente correoRemitente = new VentanaCorreoRemitente(null, color);
						correoRemitente.setVisible(true);
					}
					if(menuItemPlantillas==e.getSource()){
						VentanaPlantillas plantillaCorreo = new VentanaPlantillas(null, color);
						plantillaCorreo.setVisible(true);
					}
					if(menuItemEventos==e.getSource()){
						VentanaInformeEventos informeEventos = new VentanaInformeEventos(null, color);
						informeEventos.setVisible(true);
					}
					if(menuItemAcerca==e.getSource()){
						Acerca acerca = new Acerca(null, color, version);
						acerca.setVisible(true);
					}
					if(botonUsuarios==e.getSource()){
						VentanaUsuarios usuarios = new VentanaUsuarios(color);
						panelPrincipal.add(usuarios);
						usuarios.setLocation((panelPrincipal.getWidth() - usuarios.getWidth())/2, (panelPrincipal.getHeight() - usuarios.getHeight())/2);
						usuarios.setVisible(true);
					}
					if(botonClientes==e.getSource()){
						VentanaClientes clientes = new VentanaClientes(null, color);
						panelPrincipal.add(clientes);
						clientes.setLocation((panelPrincipal.getWidth() - clientes.getWidth())/2, (panelPrincipal.getHeight() - clientes.getHeight())/2);
						clientes.setVisible(true);
					}
					if(botonProgramador==e.getSource()){
						VentanaProgramador eventos = new VentanaProgramador(null, color);
						panelPrincipal.add(eventos);
						eventos.setLocation((panelPrincipal.getWidth() - eventos.getWidth())/2, (panelPrincipal.getHeight() - eventos.getHeight())/2);
						eventos.setVisible(true);
					}
				}
			};
			{
				itemRestaurar = new MenuItem("Pausar - Restaurar");
				menuTray.add(itemRestaurar);
				itemRestaurar.addActionListener(evento);
			}
			{
				itemCerrar = new MenuItem("Cerrar");
				menuTray.add(itemCerrar);
				itemCerrar.addActionListener(evento);
			}

			{
				this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("png/icono.png")).getImage());
				getContentPane().setForeground(new java.awt.Color(255,255,255));
				this.setTitle("ProgramMail");
			}
			{
				panelProgramas = new JPanel();
				GridLayout jPanel1Layout = new GridLayout(0, 1);
				jPanel1Layout.setColumns(1);
				jPanel1Layout.setHgap(5);
				jPanel1Layout.setVgap(5);
				panelProgramas.setLayout(jPanel1Layout);
				getContentPane().add(panelProgramas, BorderLayout.WEST);
				panelProgramas.setPreferredSize(new java.awt.Dimension(113, 345));
				panelProgramas.setBackground(new java.awt.Color(0,0,0));
				panelProgramas.setEnabled(false);
				{
					botonUsuarios = new JButton();
					panelProgramas.add(botonUsuarios);
					botonUsuarios.setText("Usuarios");
					botonUsuarios.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/admin.png")));
					botonUsuarios.setHorizontalTextPosition(SwingConstants.CENTER);
					botonUsuarios.setVerticalTextPosition(SwingConstants.BOTTOM);
					botonUsuarios.setBackground(new Color(0,0,0));
					botonUsuarios.setForeground(new java.awt.Color(255,255,255));
					botonUsuarios.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					botonUsuarios.addActionListener(evento);
					lista[4]=botonUsuarios;
				}
				{
					botonClientes = new JButton();
					panelProgramas.add(botonClientes);
					botonClientes.setText("Clientes");
					botonClientes.setVerticalTextPosition(SwingConstants.BOTTOM);
					botonClientes.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/User group.png")));
					botonClientes.setHorizontalTextPosition(SwingConstants.CENTER);
					botonClientes.setBackground(new Color(0,0,0));
					botonClientes.setForeground(new java.awt.Color(255,255,255));
					botonClientes.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					botonClientes.addActionListener(evento);
					lista[5]=botonClientes;
				}
				{
					botonProgramador = new JButton();
					panelProgramas.add(botonProgramador);
					botonProgramador.setText("Programador");
					botonProgramador.setHorizontalTextPosition(SwingConstants.CENTER);
					botonProgramador.setVerticalTextPosition(SwingConstants.BOTTOM);
					botonProgramador.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/Calendar.png")));
					botonProgramador.setBackground(new Color(0,0,0));
					botonProgramador.setForeground(new java.awt.Color(255,255,255));
					botonProgramador.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					botonProgramador.addActionListener(evento);
					lista[6]=botonProgramador;
				}
				{
					botonSalir = new JButton();
					panelProgramas.add(botonSalir);
					botonSalir.setText("Salir");
					botonSalir.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/Exit.png")));
					botonSalir.setVerticalTextPosition(SwingConstants.BOTTOM);
					botonSalir.setHorizontalTextPosition(SwingConstants.CENTER);
					botonSalir.setBackground(new Color(0,0,0));
					botonSalir.setForeground(new java.awt.Color(255,255,255));
					botonSalir.setFont(new java.awt.Font("Segoe UI",1,12));
					botonSalir.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					botonSalir.addActionListener(evento);
				}
				{
					botonIniciar = new JButton();
					panelProgramas.add(botonIniciar);
					botonIniciar.setText("Iniciar");
					botonIniciar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/Sync.png")));
					botonIniciar.setVerticalTextPosition(SwingConstants.BOTTOM);
					botonIniciar.setHorizontalTextPosition(SwingConstants.CENTER);
					botonIniciar.setBackground(color);
					botonIniciar.setForeground(new java.awt.Color(255,255,255));
					botonIniciar.setFont(new java.awt.Font("Segoe UI",1,12));
					botonIniciar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					botonIniciar.addActionListener(evento);
					lista[7]=botonIniciar;
				}
			}
			{
				panelPrincipal = new JDesktopPane();
				getContentPane().add(panelPrincipal, BorderLayout.CENTER);
				panelPrincipal.setBorder(new BackgroundImage());
			}
			{
				panelEstado = new JPanel();
				GridLayout jLabel1Layout = new GridLayout(1, 1);
				jLabel1Layout.setColumns(1);
				jLabel1Layout.setHgap(5);
				jLabel1Layout.setVgap(5);
				panelEstado.setLayout(jLabel1Layout);
				getContentPane().add(panelEstado, BorderLayout.SOUTH);

				panelEstado.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				panelEstado.setPreferredSize(new java.awt.Dimension(637, 31));
				{
					panelAyuda = new JPanel();
					GridLayout panelAyudaLayout = new GridLayout(1, 1);
					panelAyudaLayout.setColumns(1);
					panelAyudaLayout.setHgap(5);
					panelAyudaLayout.setVgap(5);
					panelAyuda.setLayout(panelAyudaLayout);
					panelEstado.add(panelAyuda);
					{
						textAyuda = new JLabel();
						panelAyuda.add(textAyuda);
						textAyuda.setText("Program Mail - Recuerde a Tiempo sus Impuestos");
					}
				}
				{
					panelInformacion = new JPanel();
					panelEstado.add(panelInformacion);
					GridLayout panelInformacionLayout = new GridLayout(1, 1);
					panelInformacionLayout.setColumns(1);
					panelInformacionLayout.setHgap(5);
					panelInformacionLayout.setVgap(5);
					panelInformacion.setLayout(panelInformacionLayout);
					{
						jLabel3 = new JLabel();
						panelInformacion.add(jLabel3);
						jLabel3.setText(usuario);
						jLabel3.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
						jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
						jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
					}
					{
						labelReloj = new Reloj();
						panelInformacion.add(labelReloj);
						labelReloj.setHorizontalAlignment(SwingConstants.CENTER);
						labelReloj.setHorizontalTextPosition(SwingConstants.CENTER);
					}
					{
						jLabel5 = new JLabel();
						panelInformacion.add(jLabel5);
						jLabel5.setText("Version: "+version);
						jLabel5.setHorizontalTextPosition(SwingConstants.CENTER);
						jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
						jLabel5.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
						jLabel5.setPreferredSize(new java.awt.Dimension(54, 27));
					}
				}
			}
			{
				menu = new JMenuBar();
				setJMenuBar(menu);
				menu.setBackground(color);
				menu.setForeground(new java.awt.Color(255,255,255));
				menu.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				{
					menuAdministrativo = new JMenu();
					menu.add(menuAdministrativo);
					menuAdministrativo.setText("Administrativo");
					menuAdministrativo.setMnemonic(65);
					menuAdministrativo.setForeground(new java.awt.Color(255,255,255));
					menuAdministrativo.setFont(new java.awt.Font("Segoe UI",1,12));
					{
						menuItemParametros = new JMenu();
						menuAdministrativo.add(menuItemParametros);
						menuItemParametros.setText("Parametros");
						menuItemParametros.addActionListener(evento);
						{
							menuItemImpuestos = new JMenuItem();
							menuItemParametros.add(menuItemImpuestos);
							menuItemImpuestos.setText("Impuestos");
							menuItemImpuestos.addActionListener(evento);
							lista[1]=menuItemImpuestos;
						}
						{
							menuItemCorreoRemitente = new JMenuItem();
							menuItemParametros.add(menuItemCorreoRemitente);
							menuItemCorreoRemitente.setText("Correo Remitente");
							menuItemCorreoRemitente.addActionListener(evento);
							lista[2]=menuItemCorreoRemitente;
						}
						{
							menuItemPlantillas = new JMenuItem();
							menuItemParametros.add(menuItemPlantillas);
							menuItemPlantillas.setText("Plantillas Correos");
							menuItemPlantillas.addActionListener(evento);
							lista[3]=menuItemPlantillas;
						}
					}
					{
						jSeparator1 = new JSeparator();
						menuAdministrativo.add(jSeparator1);
						jSeparator1.setBounds(87, 42, 70, 8);
					}
					{
						menuItemSalir = new JMenuItem();
						menuAdministrativo.add(menuItemSalir);
						menuItemSalir.setText("Salir");
						menuItemSalir.setBackground(color);
						menuItemSalir.setForeground(new java.awt.Color(255,255,255));
						menuItemSalir.setAccelerator(KeyStroke.getKeyStroke(115,8));
						menuItemSalir.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/Exit.png")));
						menuItemSalir.addActionListener(evento);
					}
				}
				{
					menuInformes = new JMenu();
					menu.add(menuInformes);
					menuInformes.setText("Informes");
					menuInformes.setMnemonic(65);
					menuInformes.setForeground(new java.awt.Color(255,255,255));
					menuInformes.setFont(new java.awt.Font("Segoe UI",1,12));
					{
						menuItemEventos = new JMenuItem();
						menuInformes.add(menuItemEventos);
						menuItemEventos.setText("Eventos");
						menuItemEventos.addActionListener(evento);
						lista[8]=menuItemEventos;
					}
				}
				{
					menuAyuda = new JMenu();
					menu.add(menuAyuda);
					menuAyuda.setText("Ayuda");
					menuAyuda.setMnemonic('u');
					menuAyuda.setForeground(new java.awt.Color(255,255,255));
					menuAyuda.setFont(new java.awt.Font("Segoe UI",1,12));
					{
						menuItemAcerca = new JMenuItem();
						menuAyuda.add(menuItemAcerca);
						menuItemAcerca.setText("Acerca de...");
						menuItemAcerca.addActionListener(evento);
					}
				}
			}
			this.setUndecorated(true);
			this.setSize(800, 600);
			this.setResizable(false);
			this.addWindowStateListener(accion);
			this.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
