package rodrigo.ritensespeciais.apis;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class EspeciaisAPI {
	
	@SuppressWarnings("deprecation")
	public static void criarParticula(int loop, Player p) {
		for (int i = 0; i < loop; i++) {
			p.playEffect(p.getLocation().add(0, 0.3, 0), Effect.ENDER_SIGNAL, 0);
			for (Entity en : p.getNearbyEntities(16, 16, 16)) {
				if (en instanceof Player && en.getCustomName() != null) {
					Player pl = (Player) en;
					pl.playEffect(p.getLocation().add(0, 0.3, 0), Effect.ENDER_SIGNAL, 0);
				}
			}
		}
	}

	public static void removeItem(Player p) {
		if (p.getItemInHand().getAmount() < 2) {
			p.setItemInHand(new ItemStack(Material.AIR));
		} else {
			ItemStack item = p.getItemInHand();
			item.setAmount(item.getAmount() - 1);
		}
	}
	
	public static void actionBar(Player player, String text) {
		IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}
