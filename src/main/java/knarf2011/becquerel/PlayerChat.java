package knarf2011.becquerel;

import java.util.Random;

import knarf2011.becquerel.playerdata.PlayerDataV1;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerChat implements Listener
{
	//TODO: write a versatile method for ranged messages. This is ridiculous.
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage(null);
		Player p = event.getPlayer();
		PlayerDataV1 pd = Becquerel.instance.players.get(p.getUniqueId());
		//store player location
		Location l1 = p.getLocation();
		double x1 = l1.getX();
		double y1 = l1.getY();
		double z1 = l1.getZ();
		Becquerel.instance.getLogger().info("Sending quit message for "+pd.name+", AKA "+p.getName()+".");
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
		{
			Location l2 = p2.getLocation();
			if(l1.getWorld() == l2.getWorld())
			{
				//calculate distance to other player
				double x2 = l2.getX();
				double y2 = l2.getY();
				double z2 = l2.getZ();
				double distx = Math.pow(Math.abs(x1-x2), 2);
				double disty = Math.pow(Math.abs(y1-y2), 2);
				double distz = Math.pow(Math.abs(z1-z2), 2);
				double dist = Math.sqrt(distx+disty+distz);
				if(dist<=40)
				{
					p2.sendMessage(pd.name+" has left.");
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		event.setDeathMessage(null);
		Player p = event.getEntity();
		PlayerDataV1 pd = Becquerel.instance.players.get(p.getUniqueId());
		//store player location
		Location l1 = p.getLocation();
		double x1 = l1.getX();
		double y1 = l1.getY();
		double z1 = l1.getZ();
		Becquerel.instance.getLogger().info("Sending death message for "+pd.name+", AKA "+p.getName()+".");
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
		{
			Location l2 = p2.getLocation();
			if(l1.getWorld() == l2.getWorld())
			{
				//calculate distance to other player
				double x2 = l2.getX();
				double y2 = l2.getY();
				double z2 = l2.getZ();
				double distx = Math.pow(Math.abs(x1-x2), 2);
				double disty = Math.pow(Math.abs(y1-y2), 2);
				double distz = Math.pow(Math.abs(z1-z2), 2);
				double dist = Math.sqrt(distx+disty+distz);
				if(dist<=40)
				{
					p2.sendMessage(pd.name+" has perished.");
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		event.setCancelled(true);
		Player p = event.getPlayer();
		PlayerDataV1 pd = Becquerel.instance.players.get(p.getUniqueId());
		//store player location
		Location l1 = p.getLocation();
		double x1 = l1.getX();
		double y1 = l1.getY();
		double z1 = l1.getZ();
		String msg = event.getMessage();
		Becquerel.instance.getLogger().info("Sending chat message from "+pd.name+", AKA "+p.getName()+": "+msg);
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
		{
			Location l2 = p2.getLocation();
			if(l1.getWorld() == l2.getWorld())
			{
				//calculate distance to other player
				double x2 = l2.getX();
				double y2 = l2.getY();
				double z2 = l2.getZ();
				double distx = Math.pow(Math.abs(x1-x2), 2);
				double disty = Math.pow(Math.abs(y1-y2), 2);
				double distz = Math.pow(Math.abs(z1-z2), 2);
				double dist = Math.sqrt(distx+disty+distz);
				if(dist>=11)
				{	//send obfuscated message if other player is within hearing range
					if(dist<50)
					{
						Random r = new Random();
						StringBuilder msg2 = new StringBuilder(msg);
						long skip = Math.round(dist)-10;
						skip = Math.max(20-skip,1);
						for(int i=r.nextInt((int)skip); i<msg2.length(); i+=skip)
						{
							msg2.setCharAt(i, '~');
						}
						p2.sendMessage(pd.name+": "+msg2.toString());
					}
				}
				else //send clear message
					p2.sendMessage(pd.name+": "+msg);
			}
		}
	}
	
	public static void yell(Player p, String msg)
	{
		PlayerDataV1 pd = Becquerel.instance.players.get(p.getUniqueId());
		//store player location
		Location l1 = p.getLocation();
		double x1 = l1.getX();
		double y1 = l1.getY();
		double z1 = l1.getZ();
		Becquerel.instance.getLogger().info("Sending chat message from "+pd.name+", AKA "+p.getName()+": "+msg);
		for (Player p2 : Bukkit.getServer().getOnlinePlayers())
		{
			Location l2 = p2.getLocation();
			if(l1.getWorld() == l2.getWorld())
			{
				//calculate distance to other player
				double x2 = l2.getX();
				double y2 = l2.getY();
				double z2 = l2.getZ();
				double distx = Math.pow(Math.abs(x1-x2), 2);
				double disty = Math.pow(Math.abs(y1-y2), 2);
				double distz = Math.pow(Math.abs(z1-z2), 2);
				double dist = Math.sqrt(distx+disty+distz);
				if(dist>=41)
				{	//send obfuscated message if other player is within hearing range
					if(dist<200)
					{
						Random r = new Random();
						StringBuilder msg2 = new StringBuilder(msg);
						long skip = Math.round(dist)-40;
						skip = Math.max((80-skip)/4,1);
						for(int i=r.nextInt((int)skip); i<msg2.length(); i+=skip)
						{
							msg2.setCharAt(i, '~');
						}
						p2.sendMessage(pd.name+" yells: "+msg2.toString());
					}
				}
				else //send clear message
					p2.sendMessage(pd.name+" yells: "+msg);
			}
		}
	}
}
