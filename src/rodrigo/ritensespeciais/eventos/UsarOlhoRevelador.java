package rodrigo.ritensespeciais.eventos;

import java.util.HashMap;
import java.util.Map.Entry;
import org.bukkit.GameMode;
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

public class UsarOlhoRevelador implements Listener{
	
	HashMap<Double, Player> distancias = new HashMap<Double, Player>();
	public static HashMap<Player, Location> revelando = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void aoInteragir(PlayerInteractEvent e) {
		Player p = (e.getPlayer());
		if (e.getItem() == null || e.getItem().getItemMeta() == null
				|| e.getItem().getItemMeta().getDisplayName() == null || e.getItem().getItemMeta().getLore() == null)
			return;
		if (e.getItem().getItemMeta().getDisplayName().equals("§9Olho Revelador")
				&& e.getItem().getType() == Material.ENDER_PEARL) {
			e.setCancelled(true);
			Location loc = p.getLocation();
			PS ps = PS.valueOf(loc);
			Faction fac = BoardColl.get().getFactionAt(ps);
			if (fac.getTag().equals("ZNG") || fac.getTag().equals("ZNP") || p.getWorld().getName().equals("mina")) {
				p.sendMessage("§cVocê não pode utilizar Itens Especiais nesta região.");
				return;
			}
			EspeciaisAPI.removeItem(p);
			p.playSound(p.getLocation(), Sound.WITHER_HURT, 1f, 1f);
			EspeciaisAPI.criarParticula(30, p);
			int alcance = 32;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso1)) alcance = alcance + 8;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso2)) alcance = alcance + 16;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso3)) alcance = alcance + 32;
			for (Entity en : p.getNearbyEntities(32, 256, 32)) {
				if (en instanceof Player && en.getName() != null && !en.getName().contains("&") && ((Player) en).isOnline()) {
					Player pl = (Player) en;
					MPlayer mp = MPlayer.get(p);
					MPlayer ma = MPlayer.get(pl);
					if (ma != null && !ma.getFaction().equals(mp.getFaction()) || ma.getFaction().isNone()) distancias.put(pl.getLocation().distance(p.getLocation()), pl);
				}
			}
			
			if (distancias.isEmpty()) {
				p.sendMessage("§cNenhum inimigo foi encontrado em uma raio de §732 §cblocos.");
				return;
			}
			
			double min = Double.MAX_VALUE;

			for(Entry<Double, Player> a : distancias.entrySet()) 
			if (a.getKey() < min) min = a.getKey();
			Player minPlayer = distancias.get(min);
			distancias.remove(min);
			
			minPlayer.sendTitle("§9Olho Revelador", "§f" + p.getName() + " §7revelou você por §f5 §7segundos.");
			revelando.put(p, p.getLocation());
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(minPlayer.getLocation().add(0, 5, 0));
			p.sendTitle("§9Olho Revelador", "§7Você está revelando §f" + minPlayer.getName() + "§7.");
			long tempo = 10;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso1)) tempo = tempo + 5;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso2)) tempo = tempo + 10;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso3)) tempo = tempo + 15;
			new BukkitRunnable() {
				public void run() {
					if (revelando.containsKey(p)) {
						p.setGameMode(GameMode.SURVIVAL);
						p.teleport(revelando.get(p));
						revelando.remove(p);
					}
				}
			}.runTaskLater(Main.getPlugin(Main.class), 20 * tempo);
		}
	}
}
