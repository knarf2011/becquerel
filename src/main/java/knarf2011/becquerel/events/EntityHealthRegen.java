package knarf2011.becquerel.events;

import knarf2011.becquerel.Becquerel;
import knarf2011.becquerel.playerdata.PlayerDataV1;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class EntityHealthRegen implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityHealthRegen(EntityRegainHealthEvent event)
	{
		if(event.getEntityType().equals(EntityType.PLAYER) && event.getRegainReason().equals(RegainReason.SATIATED))
		{
			event.setCancelled(true);
		}
	}
	
	/**
	 * Initialize health regeneration; the plugin disables it for players so that this works properly.
	 */
	public static void initRegen()
	{
		Becquerel.instance.getServer().getScheduler().scheduleSyncRepeatingTask(Becquerel.instance, new Runnable()
		{
			public void run()
			{
				Player[] players = Bukkit.getServer().getOnlinePlayers();
				for(Player p : players)
				{
					PlayerDataV1 pd = Becquerel.instance.players.get(p.getUniqueId());
						if(pd.doRegen(22-p.getFoodLevel()))
							p.setHealth(Math.min(p.getHealth()+1,20d));
				}
			}
			
		}, 1, 300);
	}
}