package rodrigo.ritensespeciais.eventos;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PrimordialDamage implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if (!UsarFogoPrimordial.primordial.contains(p)) return;
		if (e.getCause() == DamageCause.FIRE_TICK) {
			e.setCancelled(true);
			p.playSound(p.getLocation(), Sound.ARROW_HIT, 1f, 1f);
			if (p.getHealth() - 3 < 0) {
				p.setHealth(0.0);
				return;
			}
			p.setHealth(p.getHealth() - 3);
		}
	}

}
