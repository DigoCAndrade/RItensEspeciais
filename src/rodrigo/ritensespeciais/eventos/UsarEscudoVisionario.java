package rodrigo.ritensespeciais.eventos;

import java.util.Date;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import rodrigo.ritensespeciais.Main;
import rodrigo.ritensespeciais.apis.EspeciaisAPI;
import rodrigo.rperks.apis.PerksAPI;
import rodrigo.rperks.apis.PerksName;

public class UsarEscudoVisionario implements Listener{
	
	public static HashMap<String, Long> visionario = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void aoInteragir(PlayerInteractEvent e) {
		Player p = (e.getPlayer());
		if (e.getItem() == null || e.getItem().getItemMeta() == null || e.getItem().getItemMeta().getDisplayName() == null || e.getItem().getItemMeta().getLore() == null) return;
		if (e.getItem().getItemMeta().getDisplayName().equals("§4Escudo Visionário") && e.getItem().getType() == Material.ENDER_PORTAL_FRAME) {
			e.setCancelled(true);
			MPlayer mp = MPlayer.get(p);
			Faction fac = mp.getFaction();
			if (mp.hasFaction() == false) {
				p.sendMessage("§cVocê precisa estar em uma facção para usar este item.");
				return;
			}
			EspeciaisAPI.removeItem(p);
			p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
			int tempo = 5;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso1)) tempo = tempo + 2;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso2)) tempo = tempo + 4;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso3)) tempo = tempo + 6;
			visionario.put(fac.getName(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(tempo));
			BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(Main.class), new BukkitRunnable() {
				@Override
				public void run() {
					for (Player fp : fac.getOnlinePlayers()) {
						EspeciaisAPI.actionBar(fp, getTempo(fac.getName()));
					}
				}
			}, 0, 20);
			Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(Main.class), new BukkitRunnable() {
				@Override
				public void run() {
					task.cancel();
					visionario.remove(fac.getName());
				}
			}, 20 * 60 * tempo);
		}
	}
	
	public static String getTempo(String faccao) {
			Date vencimento = new Date(visionario.get(faccao));
			Date atual = new Date();

			long variacao = atual.getTime() - vencimento.getTime();
			long varsegundos = variacao / 1000 % 60;
			long varminutos = variacao / 60000 % 60;

			String segundos = String.valueOf(varsegundos).replaceAll("-", "");
			String minutos = String.valueOf(varminutos).replaceAll("-", "");
			if (minutos.equals("0")) {
				return "§4Tempo de Proteção: §f" + segundos + " §7segundos.";
			}
			return "§4Tempo de Proteção: §f" + minutos + " §7minutos §f" + segundos + " §7segundos.";
	}
}
