package globales.parametros;

import java.sql.ResultSet;
import globales.Conexion.*;

public class ModeloCorreoRemitente {
	
	private ResultSet tabla;
	private Conectar conectar;
	
	public void cerrarBase() {
		conectar.cerrarbase();
	}
	
	public void editarCorreoRemitente(String id, String nombreRemitente, String emailRemitente, String host, String user,  String contrasena, String port){
		String CadenaSQL = "UPDATE P_CORREO_REMITENTE SET NOMBRE_REMITENTE = '" + nombreRemitente + "' , EMAIL_REMITENTE = '" + emailRemitente + "', HOST = '" + host + "', USER = '" + user + "', PASSWORD = '" + contrasena + "', PORT = '" + port + "' WHERE (ID='" + id + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public ResultSet buscarCorreoRemitente(){
		String CadenaSQL = "SELECT * FROM P_CORREO_REMITENTE";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
}
