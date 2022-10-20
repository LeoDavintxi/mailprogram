package globales.administrativo;

public class ControlTablaClientes
{
	private TablaClientes modelo = null;

	public ControlTablaClientes(TablaClientes modelo)
	{
		this.modelo = modelo;
	}

	public void anhadeFila(int cantFilas)
	{
		ModeloClientes estado = new ModeloClientes();
		this.modelo.anhade(estado);
	}

	public void borraFila()
	{
		if (this.modelo.getRowCount() > 0)
			this.modelo.borrarTabla(0);
	}
}
