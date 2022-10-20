package globales.administrativo;

import java.sql.ResultSet;
import globales.Conexion.*;

public class ModeloPermisos {
	
	private ResultSet tabla;
	private Conectar conectar;
	private String idUsuario;
	private String ventana;
	private String permiso;

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getVentana() {
		return ventana;
	}

	public void setVentana(String ventana) {
		this.ventana = ventana;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public void cerrarBase() {
		conectar.cerrarbase();
	}
	
	public void crearPermiso(String usuario, int ventana, boolean permiso){
		String CadenaSQL = "INSERT INTO P_PERMISOS (USUARIO, VENTANA, PERMISO) " +
				"VALUES('" + usuario + "','" + ventana + "','" + permiso + "');";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void editarPermiso(String usuario, int ventana, boolean permiso){
		String CadenaSQL = "UPDATE P_PERMISOS SET PERMISO = '" + permiso + "' WHERE (USUARIO='" + usuario + "' AND VENTANA='"+ ventana +"')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public ResultSet buscarPermisoUsuario(int ventana, String usuario){
		String CadenaSQL = "SELECT * FROM P_PERMISOS WHERE(USUARIO='" + usuario + "' AND VENTANA='"+ventana+"')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	
	public void eliminar(String usuario){
		String CadenaSQL = "DELETE FROM P_PERMISOS WHERE(USUARIO='" + usuario + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
}
