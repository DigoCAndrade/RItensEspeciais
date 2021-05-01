package rodrigo.ritensespeciais.eventos;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;

import rodrigo.ritensespeciais.Main;
import rodrigo.ritensespeciais.apis.EspeciaisAPI;
import rodrigo.rperks.apis.PerksAPI;
import rodrigo.rperks.apis.PerksName;

public class UsarFogoPrimordial implements Listener {

	public static ArrayList<Player> primordial = new ArrayList<Player>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void aoInteragir(PlayerInteractEvent e) {
		Player p = (e.getPlayer());
		if (e.getItem() == null || e.getItem().getItemMeta() == null
				|| e.getItem().getItemMeta().getDisplayName() == null || e.getItem().getItemMeta().getLore() == null)
			return;
		if (e.getItem().getItemMeta().getDisplayName().equals("§cFogo Primordial")
				&& e.getItem().getType() == Material.BLAZE_POWDER) {
			e.setCancelled(true);
			Location loc = p.getLocation();
			PS ps = PS.valueOf(loc);
			Faction fac = BoardColl.get().getFactionAt(ps);
			if (fac.getTag().equals("ZNG") || fac.getTag().equals("ZNP") || p.getWorld().getName().equals("mina")) {
				p.sendMessage("§cVocê não pode utilizar Itens Especiais nesta região.");
				return;
			}
			EspeciaisAPI.removeItem(p);
			p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 1f, 1f);
			EspeciaisAPI.criarParticula(30, p);
			int alcance = 4;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso1)) alcance = alcance + 2;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso2)) alcance = alcance + 4;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso3)) alcance = alcance + 6;
			for (Entity entity : e.getPlayer().getNearbyEntities(alcance, alcance, alcance)) {
				if (entity instanceof Player && entity.getName() != null && !entity.getName().contains("&")) {
					Player alvo = (Player) entity;
					MPlayer mp = MPlayer.get(p);
					MPlayer ma = MPlayer.get(alvo);
					if (ma != null && !ma.getFaction().equals(mp.getFaction()) || ma.getFaction().isNone()) {
						alvo.sendTitle("§cFOGO PRIMORDIAL!", "§7Você foi ascendido por §f" + p.getName() + "§7.");
						int tempo = 5;
						if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso1)) tempo = tempo + 1;
						if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso2)) tempo = tempo + 2;
						if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso3)) tempo = tempo + 3;
						alvo.setFireTicks(20 * tempo);
						primordial.add(alvo);
						new BukkitRunnable() {

							@Override
							public void run() {
								primordial.remove(alvo);
							}
						}.runTaskLater(Main.getPlugin(Main.class), 20 * tempo);
					}
				}
			}
		}
	}
}
