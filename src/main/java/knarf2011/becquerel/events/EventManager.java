package knarf2011.becquerel.events;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EventManager
{
	public static EventManager instance = null;
	
	public Listener[] listeners = new Listener[0];
	
	public EventManager()
	{
		instance=this;
	}
	
	public void addEvent(Listener l)
	{
		Listener[] newListeners = new Listener[listeners.length+1];
		for(int i=0;i<listeners.length;i++)
			newListeners[i]=listeners[i];
		newListeners[listeners.length]=l;
		listeners=newListeners;
	}
	
	public void addEvents(JavaPlugin p)
	{
		addEvent(new BlockBreak());
		addEvent(new BlockRedstone());
		addEvent(new CreatureSpawn());
		addEvent(new EntityHealthRegen());
		addEvent(new PlayerChat());
		addEvent(new PlayerConsume());
		addEvent(new PlayerSpawner());
		
		Server s = p.getServer();
		PluginManager pm = s.getPluginManager();
		for(Listener l:listeners)
			pm.registerEvents(l, p);
	}
}
