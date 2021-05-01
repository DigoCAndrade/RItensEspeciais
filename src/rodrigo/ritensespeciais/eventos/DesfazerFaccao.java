package rodrigo.ritensespeciais.eventos;

import org.bukkit.event.Listener;

import com.massivecraft.factions.event.EventFactionsDisband;

public class DesfazerFaccao implements Listener{
	
	public void aoDesfazer(EventFactionsDisband e) {
		if (UsarEscudoVisionario.visionario.containsKey(e.getFaction().getName())) {
			UsarEscudoVisionario.visionario.remove(e.getFaction().getName());
		}
	}
}
