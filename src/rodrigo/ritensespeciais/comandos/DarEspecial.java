package rodrigo.ritensespeciais.comandos;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import rodrigo.ritensespeciais.apis.ItemBuilder;

public class DarEspecial implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (!s.hasPermission("ritensespeciais.dar")) {
			s.sendMessage("§cApenas membros da equipe podem executar este comando.");
			return false;
		}
		
		if (args.length < 1) {
			s.sendMessage("§cSintaxe incorreta, use /darespecial <tipo> <jogador> <quantia>.");
			return false;
		}	
		
		if (args.length < 3 && !(s instanceof Player)) {
			s.sendMessage("§cSintaxe incorreta, use /darespecial <tipo> <jogador> <quantia>.");
			return false;
		}
		
		ItemStack olhodivino = new ItemBuilder(Material.EYE_OF_ENDER).name("§dOlho Divino").lore("§fDuração: §710 segundos.", "", "§7Utilize este olho para ver e atravessar   ", "§7através das paredes. Quando o tempo", "§7acabar você irá para o spawn.").glow().build();
		ItemStack centelhafinal = new ItemBuilder(Material.DOUBLE_PLANT).name("§6Centelha Final").lore("§7Ao ativar este item todos os inimigos   ", "§7em um raio de 8 blocos serão", "§7arremesados para o alto.").glow().build();
		ItemStack aspectonoturno = new ItemBuilder(Material.COAL, 1, (short) 1).name("§8Aspecto Noturno").lore("§fDuração: §73 segundos.", "", "§7Ao ativar este item todos os inimigos   ", "§7em um raio de 4 blocos terão sua", "§7visão e velocidade reduzidas.").glow().build();
		ItemStack fogoprimordial = new ItemBuilder(Material.BLAZE_POWDER).name("§cFogo Primordial").lore("§7Ao ativar este item todos os inimigos   ", "§7em um raio de 4 blocos queimarão", "§7no fogo primordial.").glow().build();
		ItemStack geloverdadeiro = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).name("§bGelo Verdadeiro").lore("§7Ao ativar este item todos os   ", "§7inimigos em um raio de 4 blocos", "§7serão congelados.").head("http://textures.minecraft.net/texture/56aab58fa01fce9af469ed747aed811d7ba18c476f5a7f9088e129c31b45f3").glow().build();
		ItemStack olhorevelador = new ItemBuilder(Material.ENDER_PEARL).name("§9Olho Revelador").lore("§fDuração: §710 segundos.", "", "§7Ao ativar este item você será teleportado   ", "§7para o inimigo mais próximo de você em um", "§7raio de §f32 §7blocos de distância.").glow().build();
		ItemStack escudovisionario = new ItemBuilder(Material.ENDER_PORTAL_FRAME).name("§4Escudo Visionário").lore("§fDuração: §75 minutos.", "", "§7Ao ativar este item todos os inimigos só", "§7poderam utilizar o Olho Divino a §f80", "§7blocos de distância de seu território.").glow().build();
		
		if (args.length == 3) {
			
			if (!args[0].equalsIgnoreCase("aspecto_noturno") && !args[0].equalsIgnoreCase("centelha_final") && !args[0].equalsIgnoreCase("olho_divino") && !args[0].equalsIgnoreCase("fogo_primordial") && !args[0].equalsIgnoreCase("gelo_verdadeiro") && !args[0].equalsIgnoreCase("olho_revelador") && !args[0].equalsIgnoreCase("escudo_visionario")) {
				s.sendMessage("§cEste item especial não foi encontrado, lista de itens especiais: §7centelha_final§c, §7aspecto_noturno§c, §7olho_divino§c, §7fogo_primordial§c, §7gelo_verdadeiro§c, §7olho_revelador§c, §7escudo_visionario§c.");
				return false;
			}
			
			Player alvo = Bukkit.getPlayer(args[1]);
			if (alvo == null) {
				s.sendMessage("§cEste jogador não foi encontrado.");
				return false;
			}
			
			int quantia;
			try {
				quantia = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				s.sendMessage("§cEste número não é um número válido.");
				return false;
			}
			
			if (quantia == 0) {
				s.sendMessage("§cEste número não é um número válido.");
				return false;
			}
			
			if (args[0].equalsIgnoreCase("aspecto_noturno")) {
				aspectonoturno.setAmount(quantia);
				alvo.getInventory().addItem(aspectonoturno);
				if (quantia == 1) s.sendMessage("§aSucesso! Você enviou §71 §8Aspecto Noturno §apara o jogador §7" + alvo.getName() + "§a.");
				if (quantia > 1) s.sendMessage("§aSucesso! Você enviou §7" + quantia + " §8Aspectos Noturnos §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("centelha_final")) {
				centelhafinal.setAmount(quantia);
				alvo.getInventory().addItem(centelhafinal);
				if (quantia == 1) s.sendMessage("§aSucesso! Você enviou §71 §6Centelha Final §apara o jogador §7" + alvo.getName() + "§a.");
				if (quantia > 1) s.sendMessage("§aSucesso! Você enviou §7" + quantia + " §6Centelhas Finais §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("olho_divino")) {
				olhodivino.setAmount(quantia);
				alvo.getInventory().addItem(olhodivino);
				if (quantia == 1) s.sendMessage("§aSucesso! Você enviou §71 §dOlho Divino §apara o jogador §7" + alvo.getName() + "§a.");
				if (quantia > 1) s.sendMessage("§aSucesso! Você enviou §7" + quantia + " §dOlhos Divinos §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("fogo_primordial")) {
				fogoprimordial.setAmount(quantia);
				alvo.getInventory().addItem(fogoprimordial);
				if (quantia == 1) s.sendMessage("§aSucesso! Você enviou §71 §cFogo Primordial §apara o jogador §7" + alvo.getName() + "§a.");
				if (quantia > 1) s.sendMessage("§aSucesso! Você enviou §7" + quantia + " §cFogos Primordiais §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("gelo_verdadeiro")) {
				geloverdadeiro.setAmount(quantia);
				alvo.getInventory().addItem(geloverdadeiro);
				if (quantia == 1) s.sendMessage("§aSucesso! Você enviou §71 §bGelo Verdadeiro §apara o jogador §7" + alvo.getName() + "§a.");
				if (quantia > 1) s.sendMessage("§aSucesso! Você enviou §7" + quantia + " §bGelos Verdadeiros §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("olho_revelador")) {
				olhorevelador.setAmount(quantia);
				alvo.getInventory().addItem(olhorevelador);
				if (quantia == 1) s.sendMessage("§aSucesso! Você enviou §71 §9Olho Revelador §apara o jogador §7" + alvo.getName() + "§a.");
				if (quantia > 1) s.sendMessage("§aSucesso! Você enviou §7" + quantia + " §9Olhos Reveladores §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("escudo_visionario")) {
				escudovisionario.setAmount(quantia);
				alvo.getInventory().addItem(escudovisionario);
				if (quantia == 1) s.sendMessage("§aSucesso! Você enviou §71 §4Escudo Visionário §apara o jogador §7" + alvo.getName() + "§a.");
				if (quantia > 1) s.sendMessage("§aSucesso! Você enviou §7" + quantia + " §4Escudos Visionários §apara o jogador §7" + alvo.getName() + "§a.");
			}
			return false;
		}
		
		Player p = (Player) s;
		
		if (args.length == 2) {
			if (!args[0].equalsIgnoreCase("aspecto_noturno") && !args[0].equalsIgnoreCase("centelha_final") && !args[0].equalsIgnoreCase("olho_divino") && !args[0].equalsIgnoreCase("fogo_primordial") && !args[0].equalsIgnoreCase("gelo_verdadeiro") && !args[0].equalsIgnoreCase("olho_revelador") && !args[0].equalsIgnoreCase("escudo_visionario")) {
				s.sendMessage("§cEste item especial não foi encontrado, lista de itens especiais: §7centelha_final§c, §7aspecto_noturno§c, §7olho_divino§c, §7fogo_primordial§c, §7gelo_verdadeiro§c, §7olho_revelador§c, §7escudo_visionario§c.");
				return false;
			}
			
			Player alvo = Bukkit.getPlayer(args[1]);
			if (alvo == null) {
				p.sendMessage("§cEste jogador não foi encontrado.");
				return false;
			}
			
			if (args[0].equalsIgnoreCase("aspecto_noturno")) {
				alvo.getInventory().addItem(aspectonoturno);
				s.sendMessage("§aSucesso! Você enviou §71 §8Aspecto Noturno §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("centelha_final")) {
				alvo.getInventory().addItem(centelhafinal);
				s.sendMessage("§aSucesso! Você enviou §71 §6Centelha Final §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("olho_divino")) {
				alvo.getInventory().addItem(olhodivino);
				s.sendMessage("§aSucesso! Você enviou §71 §dOlho Divino §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("fogo_primordial")) {
				alvo.getInventory().addItem(fogoprimordial);
				s.sendMessage("§aSucesso! Você enviou §71 §cFogo Primordial §apara o jogador §7" + alvo.getName() + "§a.");

			}else if (args[0].equalsIgnoreCase("gelo_verdadeiro")) {
				alvo.getInventory().addItem(geloverdadeiro);
				s.sendMessage("§aSucesso! Você enviou §71 §bGelo Verdadeiro §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("olho_revelador")) {
				alvo.getInventory().addItem(olhorevelador);
				s.sendMessage("§aSucesso! Você enviou §71 §9Olho Revelador §apara o jogador §7" + alvo.getName() + "§a.");
			}else if (args[0].equalsIgnoreCase("escudo_visionario")) {
				alvo.getInventory().addItem(escudovisionario);
				s.sendMessage("§aSucesso! Você enviou §71 §4Escudo Visionário §apara o jogador §7" + alvo.getName() + "§a.");
			}
			
			return false;
		}
		
		if (args.length == 1) {
			if (!args[0].equalsIgnoreCase("aspecto_noturno") && !args[0].equalsIgnoreCase("centelha_final") && !args[0].equalsIgnoreCase("olho_divino") && !args[0].equalsIgnoreCase("fogo_primordial") && !args[0].equalsIgnoreCase("gelo_verdadeiro") && !args[0].equalsIgnoreCase("olho_revelador") && !args[0].equalsIgnoreCase("escudo_visionario")) {
				s.sendMessage("§cEste item especial não foi encontrado, lista de itens especiais: §7centelha_final§c, §7aspecto_noturno§c, §7olho_divino§c, §7fogo_primordial§c, §7gelo_verdadeiro§c, §7olho_revelador§c, §7escudo_visionario§c.");
				return false;
			}
			
			if (args[0].equalsIgnoreCase("aspecto_noturno")) {
				p.getInventory().addItem(aspectonoturno);
				p.sendMessage("§aSucesso! Você enviou §71 §8Aspecto Noturno §apara si mesmo.");
			}else if (args[0].equalsIgnoreCase("centelha_final")) {
				p.getInventory().addItem(centelhafinal);
				p.sendMessage("§aSucesso! Você enviou §71 §6Centelha Final §apara si mesmo.");
			}else if (args[0].equalsIgnoreCase("olho_divino")) {
				p.getInventory().addItem(olhodivino);
				p.sendMessage("§aSucesso! Você enviou §71 §dOlho Divino §apara si mesmo.");
			}else if (args[0].equalsIgnoreCase("fogo_primordial")) {
				p.getInventory().addItem(fogoprimordial);
				p.sendMessage("§aSucesso! Você enviou §71 §cFogo Primordial §apara si mesmo.");

			}else if (args[0].equalsIgnoreCase("gelo_verdadeiro")) {
				p.getInventory().addItem(geloverdadeiro);
				p.sendMessage("§aSucesso! Você enviou §71 §bGelo Verdadeiro §apara si mesmo.");
			}else if (args[0].equalsIgnoreCase("olho_revelador")) {
				p.getInventory().addItem(olhorevelador);
				p.sendMessage("§aSucesso! Você enviou §71 §9Olho Revelador §apara si mesmo.");
			}else if (args[0].equalsIgnoreCase("escudo_visionario")) {
				p.getInventory().addItem(escudovisionario);
				p.sendMessage("§aSucesso! Você enviou §71 §4Escudo Visionário §apara si mesmo.");
			}
			return false;
		}
		
		return false;
	}
}
