package knarf2011.becquerel.events;

import knarf2011.becquerel.Becquerel;
import knarf2011.becquerel.playerdata.PlayerDataV1;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerConsume implements Listener
{
	@EventHandler
	public void onPlayerConsume(PlayerItemConsumeEvent event)
	{
		if(event.getItem().getType().equals(Material.GLASS_BOTTLE))
		{
			Player p = event.getPlayer();
			PlayerDataV1 pd = Becquerel.instance.players.get(p.getUniqueId());
			pd.resetYells();
		}
	}
}
