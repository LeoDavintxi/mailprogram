package globales.administrativo;

import java.sql.ResultSet;
import globales.Conexion.*;

public class ModeloClientes {

	private String id;
	private String descripcion;
	private String nombre;
	private String numeroDocumento;
	private String email;
	private ResultSet tabla;
	private Conectar conectar;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
	
	public void crear(String nombre, String tipoDocumento, String numeroDocumento, String direccion, String ciudad, String estado, String contacto, String telefono, String email){
		String CadenaSQL = "INSERT INTO T_CLIENTES (NOMBRE, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, DIRECCION, CIUDAD, ESTADO, CONTACTO, TELEFONO, EMAIL) " +
				"VALUES('" + nombre + "','" + tipoDocumento + "','" + numeroDocumento + "','" + direccion + "','" + ciudad + "','" + estado + "','" + contacto + "','" + telefono + "','" + email + "');";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void editar(String nombre, String tipoDocumento, String numeroDocumento, String direccion, String ciudad, String estado, String contacto, String telefono, String email){
		String CadenaSQL = "UPDATE T_CLIENTES SET NOMBRE = '" + nombre + "', TIPO_DOCUMENTO = '" + tipoDocumento + "', NUMERO_DOCUMENTO = '" + numeroDocumento + "', DIRECCION = '" + direccion + "', CIUDAD = '" + ciudad + "', ESTADO = '" + estado + "', CONTACTO = '" + contacto + "', TELEFONO = '" + telefono + "', EMAIL = '" + email + "' WHERE (NUMERO_DOCUMENTO='" + numeroDocumento + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void eliminar(String numeroDocumento){
		String CadenaSQL = "DELETE FROM T_CLIENTES WHERE(NUMERO_DOCUMENTO='" + numeroDocumento + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public ResultSet buscar(){
		String CadenaSQL = "SELECT * FROM T_CLIENTES";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet buscarUno(String numeroDocumento){
		String CadenaSQL = "SELECT * FROM T_CLIENTES WHERE(NUMERO_DOCUMENTO='" + numeroDocumento + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
}
