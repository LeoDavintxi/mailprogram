package globales.parametros;

import java.sql.ResultSet;
import globales.Conexion.*;

public class ModeloPlantillas {

	private String id;
	private String nombre;
	private String asunto;
	private String body;
	private ResultSet tabla;
	private Conectar conectar;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void cerrarBase(){
		conectar.cerrarbase();
	}
	
	public void crear(String nombre, String asunto, String body){
		String CadenaSQL = "INSERT INTO P_PLANTILLA (NOMBRE, ASUNTO, BODY)" +
				"VALUES('" + nombre + "','" + asunto + "','" + body + "');";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void editar(String id, String nombre, String asunto, String body){
		String CadenaSQL = "UPDATE P_PLANTILLA SET NOMBRE = '" + nombre + "', ASUNTO = '" + asunto + "', BODY = '" + body + "' WHERE (ID='" + id + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void eliminar(String id){
		String CadenaSQL = "DELETE FROM P_PLANTILLA WHERE(ID='" + id + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public ResultSet buscar(){
		String CadenaSQL = "SELECT * FROM P_PLANTILLA";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet buscarUno(String id){
		String CadenaSQL = "SELECT * FROM P_PLANTILLA WHERE(ID='" + id + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
}
