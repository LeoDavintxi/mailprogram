package globales.administrativo;

public class ControlTablaUsuarios
{
	private TablaUsuarios modelo = null;

	public ControlTablaUsuarios(TablaUsuarios modelo)
	{
		this.modelo = modelo;
	}

	public void anhadeFila(int cantFilas)
	{
		ModeloUsuarios estado = new ModeloUsuarios();
		this.modelo.anhade(estado);
	}

	public void borraFila()
	{
		if (this.modelo.getRowCount() > 0)
			this.modelo.borrarTabla(0);
	}
}
