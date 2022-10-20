package globales.Conexion;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Conectar{
	private Connection dbConecta;
	private String CadenaSql;
	private String host;
	private String puerto;
	private String dataBase;
	private String usuario;

	public Connection getDbConecta() {
		return dbConecta;
	}

	public Conectar(){
		try{
			System.out.println("Tratando de cargar el driver....");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception ex){
			System.out.println("Se produjo un error al cargar Driver");
			ex.printStackTrace();
		}
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("configuracion.ini"));
			host = p.getProperty("host");
			puerto = p.getProperty("puerto");
			dataBase = p.getProperty("Db");
			usuario = p.getProperty("user");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: 1100. Falta el archivo configuracion.ini. Consulte con el administrador");
			System.exit(0);
		}
		try{
			String cone = "jdbc:mysql://"+host+":"+puerto+"/"+dataBase+"?user="+usuario+"&password=asodatos";
			dbConecta = DriverManager.getConnection(cone);
			System.out.println("ESTABLECIENDO COMUNICACION CON EL SERVIDOR  ");
		}
		catch (SQLException sqlEx){
			JOptionPane.showMessageDialog(null, "Error: 1101. Conexion fallida. Verifique los datos en configuracion.ini ó consulte con el administrador de la red");
			System.exit(0);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public void SetCadena(String cadena){
		this.CadenaSql = cadena;
	}

	public String EjecutarSql(){
		int register = 0;
		try{
			Statement stmt = dbConecta.createStatement();
			register = stmt.executeUpdate(this.CadenaSql);
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return "se modificaron " + register + " Registros";
	}
	
	public void cerrarbase(){
		try {
			this.dbConecta.close();
			System.out.println("la base se cerro correctamente");
		}
		catch (SQLException e){
			System.out.println("se produjo un error");
		}
	}

	public ResultSet Consultar(){
		ResultSet tabla = null;
		try{
			Statement stmt = dbConecta.createStatement();
			tabla = stmt.executeQuery(this.CadenaSql);
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return tabla;
	}

	public Date ConsultarFechaActual(){
		ResultSet tabla = null;
		Date fechaActual = null;
		try{
			Statement stmt = dbConecta.createStatement();
			tabla = stmt.executeQuery("SELECT NOW()");
			if (tabla.next()){
				fechaActual = tabla.getDate(1);	
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return fechaActual;
	}

	public Date ConsultarHoraFechaActual(){
		ResultSet tabla = null;
		Date fechaActual = null;
		try{
			Statement stmt = dbConecta.createStatement();
			tabla = stmt.executeQuery("SELECT NOW()");
			if (tabla.next()) 
				fechaActual = tabla.getTimestamp(1);
		}
		catch (SQLException ex){
		}
		return fechaActual;
	}

	public Time ConsultarHoraActual(){
		ResultSet tabla = null;
		Time horaActual = null;
		try{
			Statement stmt = dbConecta.createStatement();
			tabla = stmt.executeQuery("SELECT CURTIME()");
			if (tabla.next())
				horaActual = tabla.getTime(1);
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return horaActual;
	}
}
