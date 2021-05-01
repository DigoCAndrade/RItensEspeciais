package rodrigo.ritensespeciais.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class GeloAndar implements Listener {

	@EventHandler
	public void aoAndar(PlayerMoveEvent e) {
		if (UsarGeloVerdadeiro.congelado.contains(e.getPlayer())) e.setCancelled(true);
	}
}
