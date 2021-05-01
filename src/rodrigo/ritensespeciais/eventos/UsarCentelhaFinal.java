package rodrigo.ritensespeciais.eventos;

import org.bukkit.Location;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import rodrigo.ritensespeciais.apis.EspeciaisAPI;
import rodrigo.rperks.apis.PerksAPI;
import rodrigo.rperks.apis.PerksName;

public class UsarCentelhaFinal implements Listener {

	@EventHandler
	public void aoInteragir(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() == null || e.getItem().getItemMeta() == null
				|| e.getItem().getItemMeta().getDisplayName() == null || e.getItem().getItemMeta().getLore() == null)
			return;
		if (!e.getItem().getItemMeta().getDisplayName().equals("§6Centelha Final")
				|| e.getItem().getType() != Material.DOUBLE_PLANT)
			return;
		e.setCancelled(true);
		Location loc = p.getLocation();
		PS ps = PS.valueOf(loc);
		Faction fac = BoardColl.get().getFactionAt(ps);
		if (fac.getTag().equals("ZNG") || fac.getTag().equals("ZNP") || p.getWorld().getName().equals("mina")) {
			p.sendMessage("§cVocê não pode utilizar Itens Especiais nesta região.");
			return;
		}
		EspeciaisAPI.removeItem(p);
		p.playSound(p.getLocation(), Sound.LAVA_POP, 1f, 1f);
		EspeciaisAPI.criarParticula(30, p);
		int alcance = 8;
		if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso1)) alcance = alcance + 2;
		if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso2)) alcance = alcance + 4;
		if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso3)) alcance = alcance + 6;
		for (Entity entity : p.getNearbyEntities(alcance, alcance, alcance)) {
			if (entity instanceof Player && entity.getName() != null && !entity.getName().contains("&")) {
				Player alvo = (Player) entity;
				MPlayer mp = MPlayer.get(p);
				MPlayer ma = MPlayer.get(alvo);
				double subir = 1.3;
				if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso1)) subir = subir + 0.1;
				if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso2)) subir = subir + 0.2;
				if (PerksAPI.hasSpecificPerk(p.getName(), PerksName.AprimoramentoImpetuoso3)) subir = subir + 0.3;
				if (ma != null && !ma.getFaction().equals(mp.getFaction()) || ma.getFaction().isNone()){
					alvo.setVelocity(new Vector(0, subir, 0));
					alvo.playSound(alvo.getLocation(), Sound.SHOOT_ARROW, 1f, 1f);
				}
			}
		}
	}
}
