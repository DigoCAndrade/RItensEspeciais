package rodrigo.ritensespeciais.eventos;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class OlhoDivinoBlockers implements Listener {
	
	@EventHandler
	public void aoTeleportar(PlayerTeleportEvent e) {
		if (!UsarOlhoDivino.olhodivino.contains(e.getPlayer()) && !UsarOlhoRevelador.revelando.containsKey(e.getPlayer())) return;
		if (e.getCause() == TeleportCause.SPECTATE || e.getCause() == TeleportCause.ENDER_PEARL) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void aoSair(PlayerQuitEvent e) {
		if (!UsarOlhoDivino.olhodivino.contains(e.getPlayer()) && !UsarOlhoRevelador.revelando.containsKey(e.getPlayer())) return;
		Player p = e.getPlayer();
		p.setGameMode(GameMode.SURVIVAL);
		if (UsarOlhoDivino.olhodivino.contains(e.getPlayer())) {
		p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 180, 0.5));
		UsarOlhoDivino.olhodivino.remove(p);
		}else if (UsarOlhoRevelador.revelando.containsKey(e.getPlayer())) {
			p.teleport(UsarOlhoRevelador.revelando.get(p));
			UsarOlhoRevelador.revelando.remove(p);
		}
	}
	
	@EventHandler
	public void aoComando(PlayerCommandPreprocessEvent e) {
		if (!UsarOlhoDivino.olhodivino.contains(e.getPlayer()) && !UsarOlhoRevelador.revelando.containsKey(e.getPlayer())) return;
		e.setCancelled(true);
		e.getPlayer().sendMessage("§cVocê não pode executar comandos enquanto estiver no olho divino.");
	}
}
