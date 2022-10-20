package globales.parametros;

public class ControlTablaPlantillas
{
	private TablaPlantillas modelo = null;

	public ControlTablaPlantillas(TablaPlantillas modelo)
	{
		this.modelo = modelo;
	}

	public void anhadeFila(int cantFilas)
	{
		ModeloPlantillas plantilla = new ModeloPlantillas();
		this.modelo.anhade(plantilla);
	}

	public void borraFila()
	{
		if (this.modelo.getRowCount() > 0)
			this.modelo.borrarTabla(0);
	}
}
