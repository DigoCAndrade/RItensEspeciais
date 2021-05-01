package rodrigo.ritensespeciais.eventos;

import java.util.ArrayList;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import rodrigo.ritensespeciais.Main;
import rodrigo.ritensespeciais.apis.EspeciaisAPI;
import rodrigo.rperks.apis.PerksAPI;
import rodrigo.rperks.apis.PerksName;

public class UsarOlhoDivino implements Listener {

	public static ArrayList<Player> olhodivino = new ArrayList<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void aoInteragir(PlayerInteractEvent e) {
		Player p = (e.getPlayer());
		if (e.getItem() == null || e.getItem().getItemMeta() == null || e.getItem().getItemMeta().getDisplayName() == null || e.getItem().getItemMeta().getLore() == null) return;
		if (e.getItem().getItemMeta().getDisplayName().equals("§dOlho Divino") && e.getItem().getType() == Material.EYE_OF_ENDER) {
			e.setCancelled(true);
			Location loc = p.getLocation();
			PS ps = PS.valueOf(loc);
			Faction fac = BoardColl.get().getFactionAt(ps);
			if (fac.getTag().equals("ZNG") || fac.getTag().equals("ZNP") || p.getWorld().getName().equals("mina")) {
				p.sendMessage("§cVocê não pode utilizar Itens Especiais nesta região.");
				return;
			}
			if (olhodivino.contains(p)) {
				p.sendMessage("§cVocê já esta utilizando um Olho Divino, aguarde para utilizar outro.");
				return;
			}
			
			PS pse = PS.valueOf(p.getLocation().getChunk());
			Set<PS> nearbyChunks = BoardColl.getNearbyChunks(pse, 5);
			for (String faction : UsarEscudoVisionario.visionario.keySet()) {
				Faction facs = FactionColl.get().getByName(faction);
				MPlayer mp = MPlayer.get(p);
				if (!facs.getMPlayers().contains(mp)) {
					Set<PS> chunks = BoardColl.get().getChunks(facs);
					for (PS chunk : chunks) {
						if (nearbyChunks.contains(chunk)) {
							p.sendMessage("§cVocê não pode usar o Olho Divino perto de uma facção com Escudo Visionário ativo.");
							return;
						}
					}
				}
			}
				
			EspeciaisAPI.removeItem(p);
			olhodivino.add(p);
			p.playSound(p.getLocation(), Sound.GHAST_CHARGE, 1f, 1f);
			p.setGameMode(GameMode.SPECTATOR);
			p.sendTitle("§d§lOLHO DIVINO", "");
			long tempo = 10;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso1)) tempo = tempo + 5;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso2)) tempo = tempo + 10;
			if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso3)) tempo = tempo + 15;
			new BukkitRunnable() {

				@Override
				public void run() {
					if (p != null) {
						p.setGameMode(GameMode.SURVIVAL);
						p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 180, 0.5));
						p.sendMessage("§eO tempo de seu §dOlho Divino§e acabou, você foi levado para o spawn!");
					}
					olhodivino.remove(p);
					cancel();
				}
			}.runTaskLater(Main.getPlugin(Main.class), 20 * tempo);
		}
	}
}
