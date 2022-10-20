package globales.administrativo;

import java.sql.ResultSet;
import globales.Conexion.*;

public class ModeloUsuarios {
	
	private ResultSet tabla;
	private Conectar conectar;
	private String idUsuario;
	private String nombreUsuario;
	private String estado;
	
	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void cerrarBase() {
		conectar.cerrarbase();
	}
	
	public void crearUsuario(String usuario, String nombre, String apellido, String cedula, String email, String contrasena, String estado){
		String CadenaSQL = "INSERT INTO T_USUARIOS VALUES('" + usuario + "','" + nombre + "','" + apellido + "','" + cedula + "','" + email + "',MD5('" + contrasena + "'),'" + estado + "');";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void editarUsuario(String usuario, String nombre, String apellido, String cedula, String email, String contrasena, String estado){
		String CadenaSQL = "UPDATE T_USUARIOS SET NOMBRE = '" + nombre + "' , APELLIDO = '" + apellido + "', CEDULA = '" + cedula + "', EMAIL = '" + email + "', CONTRASENA = MD5('" + contrasena + "'), ESTADO = '" + estado + "' WHERE (ID='" + usuario + "')";
		System.out.println(CadenaSQL);
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
		
	}
	
	public void eliminarUsuario(String usuario){
		String CadenaSQL = "DELETE FROM T_USUARIOS WHERE(ID='" + usuario + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public ResultSet buscarUsuarios(){
		String CadenaSQL = "SELECT * FROM T_USUARIOS";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet buscarUnUsuario(String usuario){
		String CadenaSQL = "SELECT * FROM T_USUARIOS WHERE(ID='" + usuario + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet registroUsuarios(String usuario, String contrasena){
		String CadenaSQL = "SELECT ID, CONTRASENA FROM T_USUARIOS WHERE ID COLLATE latin1_general_cs='" + usuario + "' AND CONTRASENA = MD5('"+ contrasena +"')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet buscarUnUsuarioNombre(String nombreCompleto){
		String CadenaSQL = "SELECT * FROM T_USUARIOS WHERE CONCAT(NOMBRE,' ',APELLIDO)='" + nombreCompleto+ "'";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet buscarUsuariosCartera(){
		String CadenaSQL = "SELECT * FROM T_USUARIOS WHERE(AREA ='CARTERA')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}	
}
