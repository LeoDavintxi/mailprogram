package globales.principal;

import globales.Conexion.Conectar;

import java.util.Calendar; 
import java.util.GregorianCalendar; 
import javax.swing.JLabel; 

public class Reloj extends JLabel implements Runnable { 

	private static final long serialVersionUID = 1L;
	private String dia, mes, ano, hora, minutos, segundos; 
	private Calendar calendario = new GregorianCalendar();
	private Conectar conectar = new Conectar();;
	Thread hilo; 

	public Reloj() { 
		hilo = new Thread(this); 
		hilo.start(); 
	} 

	public void run() { 
		Thread ct = Thread.currentThread(); 
		while (ct == hilo) { 
			try { 
				actualiza(); 
				int mesE; 
				mesE = Integer.valueOf(mes) + 1; 
				setText(dia+"/"+mesE+"/"+ano+"  -  "+hora+":"+minutos+":"+segundos); 
				Thread.sleep(1000);
			} catch (InterruptedException ex) { 
			} 
		} 
	} 

	public void actualiza() { 
		calendario.setTime(conectar.ConsultarHoraFechaActual());
		hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY)); 
		minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE); 
		segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND); 
		dia = calendario.get(Calendar.DATE) > 9 ? "" + calendario.get(Calendar.DATE) : "0" + calendario.get(Calendar.DATE); 
		mes = calendario.get(Calendar.MONTH) > 9 ? "" + calendario.get(Calendar.MONTH) : "0" + calendario.get(Calendar.MONTH); 
		ano = calendario.get(Calendar.YEAR) > 9 ? "" + calendario.get(Calendar.YEAR) : "0" + calendario.get(Calendar.YEAR);
	} 
} 
