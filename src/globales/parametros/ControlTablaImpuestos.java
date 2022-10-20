package globales.parametros;

public class ControlTablaImpuestos
{
	private TablaImpuestos modelo = null;

	public ControlTablaImpuestos(TablaImpuestos modelo)
	{
		this.modelo = modelo;
	}

	public void anhadeFila(int cantFilas)
	{
		ModeloImpuestos estado = new ModeloImpuestos();
		this.modelo.anhade(estado);
	}

	public void borraFila()
	{
		if (this.modelo.getRowCount() > 0)
			this.modelo.borrarTabla(0);
	}
}
