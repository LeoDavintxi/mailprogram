package globales.parametros;

import java.util.LinkedList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TablaPlantillas implements TableModel
{
	private LinkedList datos = new LinkedList();
	private LinkedList listeners = new LinkedList();
	private ModeloPlantillas aux = new ModeloPlantillas();

	public void borrarDatos() {
		this.datos.clear();
	}

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return this.datos.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		ModeloPlantillas aux = (ModeloPlantillas)this.datos.get(rowIndex);
		switch (columnIndex)
		{
		case 0:
			return aux.getId();
		case 1:
			return aux.getNombre();
		case 2:
			return aux.getAsunto();
		}
		return null;
	}

	public void borrarTabla(int fila)
	{
		this.datos.clear();
		TableModelEvent evento = new TableModelEvent(this, fila, fila, -1, -1);
		avisaSuscriptores(evento);
	}

	public void anhade(ModeloPlantillas nuevo)
	{
		this.datos.add(nuevo);

		TableModelEvent evento = new TableModelEvent(this, getRowCount() - 1, getRowCount() - 1, -1,
				1);
		avisaSuscriptores(evento);
	}

	public void addTableModelListener(TableModelListener l) {
		this.listeners.add(l);
	}

	public Class getColumnClass(int columnIndex) {
		switch (columnIndex)
		{
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		}
		return Object.class;
	}

	public String getColumnName(int columnIndex)
	{
		switch (columnIndex)
		{
		case 0:
			return "ID";
		case 1:
			return "Nombre Plantilla";
		case 2:
			return "Asunto";
		}
		return null;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	public void removeTableModelListener(TableModelListener l) {
		this.listeners.remove(l);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		this.aux = ((ModeloPlantillas)this.datos.get(rowIndex));
		switch (columnIndex)
		{
		case 0:
			this.aux.setId((String)aValue);
			break;
		case 1:
			this.aux.setNombre((String)aValue);
			break;
		case 2:
			this.aux.setAsunto((String)aValue);
			break;
		}
		TableModelEvent evento = new TableModelEvent(this, rowIndex, rowIndex, columnIndex);
		avisaSuscriptores(evento);
	}

	private void avisaSuscriptores(TableModelEvent evento)
	{
		for (int i = 0; i < this.listeners.size(); i++)
			((TableModelListener)this.listeners.get(i)).tableChanged(evento);
	}
}