package globales.parametros;

import java.sql.ResultSet;
import globales.Conexion.*;

public class ModeloImpuestos {

	private String id;
	private String descripcion;
	private ResultSet tabla;
	private Conectar conectar;
	
	public String getId() {
		return id;
	}
	public void setId(String id_estado) {
		this.id = id_estado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void cerrarBase(){
		conectar.cerrarbase();
	}
	
	public void crear(String id_estado, String descripcion){
		String CadenaSQL = "INSERT INTO P_IMPUESTOS ( DESCRIPCION )VALUES('" + descripcion + "');";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void editar(String id_estado, String descripcion){
		String CadenaSQL = "UPDATE P_IMPUESTOS SET DESCRIPCION = '" + descripcion + "' WHERE (ID='" + id_estado + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void eliminar(String id_estado){
		String CadenaSQL = "DELETE FROM P_IMPUESTOS WHERE(ID='" + id_estado + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public ResultSet buscar(){
		String CadenaSQL = "SELECT * FROM P_IMPUESTOS";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet buscarUnO(String id_estado){
		String CadenaSQL = "SELECT * FROM P_IMPUESTOS WHERE(ID='" + id_estado + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
}
