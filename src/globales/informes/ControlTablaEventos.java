package globales.informes;

import globales.administrativo.ModeloProgramador;

public class ControlTablaEventos
{
	private TablaEventos modelo = null;

	public ControlTablaEventos(TablaEventos modelo)
	{
		this.modelo = modelo;
	}

	public void anhadeFila(int cantFilas)
	{
		ModeloProgramador estado = new ModeloProgramador();
		this.modelo.anhade(estado);
	}

	public void borraFila()
	{
		if (this.modelo.getRowCount() > 0)
			this.modelo.borrarTabla(0);
	}
}
