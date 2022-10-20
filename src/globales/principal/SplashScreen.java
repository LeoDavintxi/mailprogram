package globales.principal;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

public class SplashScreen extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel labelImagen;
	private JProgressBar barraProgreso;
	private Color color;
	private String usuario;
	private VentanaPrincipal principal;

	public class Worker extends SwingWorker<Void, Void> {

		public Void doInBackground(){
			barraProgreso.setString("Esperando conexion con el servidor...");
			principal = new VentanaPrincipal(color,usuario);
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(10);
					barraProgreso.setValue(i);
					barraProgreso.setString("Creando permisos de Usuario. Carga en "+i+"%");
				} catch (InterruptedException e) {
				}
			}
			return null;
		}

		public void done() {
			ocultar();
			principal.setVisible(true);
		}
	}
	
	public SplashScreen(Color color, String usuario) {
		this.color=color;
		this.usuario=usuario;
		initGUI();
		setVisible(true);
		ejecucion();
	}
	
	public void ejecucion(){
		Worker worker = new Worker();
		worker.execute();
	}
	
	public void ocultar(){
		dispose();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				labelImagen = new JLabel();
				getContentPane().add(labelImagen, BorderLayout.CENTER);
				labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
				labelImagen.setHorizontalTextPosition(SwingConstants.CENTER);
				labelImagen.setIcon(new ImageIcon(getClass().getClassLoader().getResource("png/screen.png")));
				labelImagen.setPreferredSize(new java.awt.Dimension(600, 343));
			}
			{
				barraProgreso = new JProgressBar();
				getContentPane().add(barraProgreso, BorderLayout.SOUTH);
				barraProgreso.setPreferredSize(new java.awt.Dimension(408, 21));
				barraProgreso.setStringPainted(true);
				barraProgreso.setBorderPainted(true);
				barraProgreso.setBackground(new java.awt.Color(0,0,0));
				barraProgreso.setForeground(color);
				barraProgreso.setFont(new java.awt.Font("Segoe UI",3,14));
			}
			setUndecorated(true);
			setSize(600, 355);
			setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
