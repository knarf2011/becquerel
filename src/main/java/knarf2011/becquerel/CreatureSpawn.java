package knarf2011.becquerel;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawn implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		Random r = new Random();
		short res = (short)r.nextInt(3);
		if(res!=0)
			event.setCancelled(true);
	}
}