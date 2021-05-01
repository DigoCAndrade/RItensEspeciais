package rodrigo.ritensespeciais;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rodrigo.ritensespeciais.comandos.DarEspecial;
import rodrigo.ritensespeciais.eventos.GeloAndar;
import rodrigo.ritensespeciais.eventos.OlhoDivinoBlockers;
import rodrigo.ritensespeciais.eventos.PrimordialDamage;
import rodrigo.ritensespeciais.eventos.UsarAspectoNoturno;
import rodrigo.ritensespeciais.eventos.UsarCentelhaFinal;
import rodrigo.ritensespeciais.eventos.UsarEscudoVisionario;
import rodrigo.ritensespeciais.eventos.UsarFogoPrimordial;
import rodrigo.ritensespeciais.eventos.UsarGeloVerdadeiro;
import rodrigo.ritensespeciais.eventos.UsarOlhoDivino;
import rodrigo.ritensespeciais.eventos.UsarOlhoRevelador;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		getCommand("darespecial").setExecutor(new DarEspecial());
		Bukkit.getPluginManager().registerEvents(new OlhoDivinoBlockers(), this);
		Bukkit.getPluginManager().registerEvents(new PrimordialDamage(), this);
		Bukkit.getPluginManager().registerEvents(new UsarOlhoDivino(), this);
		Bukkit.getPluginManager().registerEvents(new UsarFogoPrimordial(), this);
		Bukkit.getPluginManager().registerEvents(new UsarGeloVerdadeiro(), this);
		Bukkit.getPluginManager().registerEvents(new UsarCentelhaFinal(), this);
		Bukkit.getPluginManager().registerEvents(new UsarEscudoVisionario(), this);
		Bukkit.getPluginManager().registerEvents(new UsarAspectoNoturno(), this);
		Bukkit.getPluginManager().registerEvents(new GeloAndar(), this);
		Bukkit.getPluginManager().registerEvents(new UsarOlhoRevelador(), this);
	}
}
