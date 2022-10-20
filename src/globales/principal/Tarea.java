package globales.principal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import globales.administrativo.ModeloProgramador;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.TriggerBuilder.*;

public class Tarea {
	private Scheduler scheduler;
	private ModeloProgramador programador=new ModeloProgramador();
	private ResultSet tabla;
	private Date fecha=null;
	private String id;

	public void apagar(){
		try {
			if(!scheduler.isShutdown()){
				scheduler.shutdown();	
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void siguienteEvento(){
		tabla=programador.buscarSiguienteEvento();
		try {
			if(tabla.next()){
				fecha=tabla.getTimestamp("FECHA_NOTIFICACION");
				id=tabla.getString("ID");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		programador.cerrarBase();
	}

	public Tarea(){
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			siguienteEvento();
			if(fecha!=null){
				if(fecha.after(Calendar.getInstance().getTime())){
					SimpleTrigger trigger = (SimpleTrigger) newTrigger()
							.withIdentity("TriggerTarea", "Grupo1")
							.startAt(fecha)
							.forJob("Enviar Email","Grupo1")
							.build();

					JobDetail job = JobBuilder
							.newJob(EnviarEmail.class)
							.withIdentity("Enviar Email", "Grupo1")
							.withDescription(id)
							.build();

					scheduler.start();
					scheduler.scheduleJob(job, trigger);
				}else{
					JOptionPane.showMessageDialog(null, "Existen evento pasados aun activos sin Notificar.");
				}
			}else{
				JOptionPane.showMessageDialog(null, "No hay eventos registrados");
			}


		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
