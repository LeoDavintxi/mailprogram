package globales.administrativo;

import java.sql.ResultSet;
import globales.Conexion.*;

public class ModeloProgramador {

	private String id;
	private String numeroDocumento;
	private String evento;
	private String fecha;
	private String fechaNotificacion;
	private String plantilla;
	private String estado;
	private ResultSet tabla;
	private Conectar conectar;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public String getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}

	public void cerrarBase(){
		conectar.cerrarbase();
	}
	
	public void crear(String documento, String evento, String fecha, String fechaNotificacion, String plantilla, String estado){
		String CadenaSQL = "INSERT INTO T_EVENTOS (NUMERO_DOCUMENTO, EVENTO, FECHA, FECHA_NOTIFICACION, PLANTILLA, ESTADO)" +
				"VALUES('" + documento + "','" + evento + "','" + fecha + "','" + fechaNotificacion + "','" + plantilla + "','" + estado + "');";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void editar(String id, String evento, String fecha, String fechaNotificacion, String plantilla, String estado){
		String CadenaSQL = "UPDATE T_EVENTOS SET EVENTO = '" + evento + "', FECHA = '" + fecha + "', FECHA_NOTIFICACION = '" + fechaNotificacion + "', PLANTILLA = '" + plantilla + "', ESTADO = '" + estado + "' WHERE (ID='" + id + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public void eliminar(String id){
		String CadenaSQL = "DELETE FROM T_EVENTOS WHERE(ID='" + id + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public ResultSet buscarUnEvento(String numeroDocumento){
		String CadenaSQL = "SELECT * FROM T_EVENTOS WHERE(NUMERO_DOCUMENTO='" + numeroDocumento + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet buscarTodosEventos(){
		String CadenaSQL = "SELECT * FROM T_EVENTOS";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet buscarEventos(String numeroDocumento){
		String CadenaSQL = "SELECT * FROM T_EVENTOS WHERE(NUMERO_DOCUMENTO='" + numeroDocumento + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	public ResultSet buscarSiguienteEvento(){
		String CadenaSQL = "SELECT * FROM T_EVENTOS WHERE ESTADO='ACTIVO' ORDER BY FECHA_NOTIFICACION LIMIT 1;";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	
	public void actualizarEstado(String id){
		String CadenaSQL = "UPDATE T_EVENTOS SET ESTADO = 'TERMINADO' WHERE (ID='" + id + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		conectar.EjecutarSql();
	}
	
	public ResultSet buscarPorID(String id){
		String CadenaSQL = "SELECT * FROM T_EVENTOS WHERE(ID='" + id + "')";
		conectar = new Conectar();
		conectar.SetCadena(CadenaSQL);
		tabla = conectar.Consultar();
		return tabla;
	}
	
}
