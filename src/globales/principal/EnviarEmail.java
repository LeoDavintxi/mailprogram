package globales.principal;

import globales.administrativo.ModeloClientes;
import globales.administrativo.ModeloProgramador;
import globales.parametros.ModeloCorreoRemitente;
import globales.parametros.ModeloPlantillas;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class EnviarEmail implements Job{
	private Properties properties;
	private Session session;
	private String from;
	private String emailFrom;
	private String emailTo;
	private String host;
	private String user;
	private String pass;
	private String asunto;
	private String texto="";
	private int port;
	private ModeloCorreoRemitente modeloCorreoRemitente = new ModeloCorreoRemitente();
	private ResultSet tabla;
	private String id;
	private ModeloProgramador programador = new ModeloProgramador();
	private ModeloClientes cliente = new ModeloClientes();
	private ModeloPlantillas plantilla = new ModeloPlantillas();
	private SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
	private String numeroDocumento;
	private String numeroPlantilla;
	private String nombreCliente;
	private String direccionCliente;
	private String telefonoCliente;
	private Date fechaEvento;
	private String evento;	

	public void consultarDocumento(){
		tabla=programador.buscarPorID(id);
		try {
			if(tabla.next()){
				numeroDocumento=tabla.getString("NUMERO_DOCUMENTO");
				numeroPlantilla=tabla.getString("PLANTILLA");
				fechaEvento=tabla.getDate("FECHA");
				evento=tabla.getString("EVENTO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		programador.cerrarBase();
	}

	public void consultarInfoCliente(){
		tabla=cliente.buscarUno(numeroDocumento);
		try {
			if(tabla.next()){
				emailTo=tabla.getString("EMAIL");
				nombreCliente=tabla.getString("NOMBRE");
				direccionCliente=tabla.getString("DIRECCION");
				telefonoCliente=tabla.getString("TELEFONO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cliente.cerrarBase();
	}

	public void consultarPlantilla(){
		tabla=plantilla.buscarUno(numeroPlantilla);
		try {
			if(tabla.next()){
				texto=tabla.getString("BODY");
				asunto=tabla.getString("ASUNTO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		plantilla.cerrarBase();
	}


	public void datosCorreoRemitente(){
		tabla=modeloCorreoRemitente.buscarCorreoRemitente();
		try {
			if(tabla.next()){
				from=tabla.getString("NOMBRE_REMITENTE");
				emailFrom=tabla.getString("EMAIL_REMITENTE");
				host=tabla.getString("HOST");
				user=tabla.getString("USER");
				pass=tabla.getString("PASSWORD");
				port=tabla.getInt("PORT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		modeloCorreoRemitente.cerrarBase();
	}

	public void personalizarMensaje(){
		texto=texto.replaceAll("<<NOMBRE>>", nombreCliente);
		texto=texto.replaceAll("<<DOCUMENTO>>", numeroDocumento);
		texto=texto.replaceAll("<<DIRECCION>>", direccionCliente);
		texto=texto.replaceAll("<<TELEFONO>>", telefonoCliente);
		texto=texto.replaceAll("<<EMAIL>>", emailTo);
		texto=texto.replaceAll("<<FECHA>>", formatoFecha.format(fechaEvento));
		texto=texto.replaceAll("<<EVENTO>>", evento);
	}

	public String descriptar(String cadena) { 
		StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
		s.setPassword("asodatos"); 
		String devuelve = ""; 
		try { 
			devuelve = s.decrypt(cadena); 
		} catch (Exception e) { 
		} 
		return devuelve; 
	} 

	public void execute(JobExecutionContext e) throws JobExecutionException {
		try{
			id=e.getJobDetail().getDescription();
			datosCorreoRemitente();			
			consultarDocumento();
			consultarInfoCliente();
			consultarPlantilla();
			personalizarMensaje();

			properties=System.getProperties();
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.user", user);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.startssl.enable", "true");

			session = Session.getDefaultInstance(properties);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailFrom, from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			message.setSubject(asunto);
			message.setContent(texto, "text/plain; charset=utf-8");

			Transport t = session.getTransport("smtp");
			t.connect(host, user, descriptar(pass));
			t.sendMessage(message, message.getAllRecipients());
			t.close();

			e.getScheduler().shutdown();
			programador.actualizarEstado(id);
			new Tarea();
		}catch (Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage());
		}

	}
}
