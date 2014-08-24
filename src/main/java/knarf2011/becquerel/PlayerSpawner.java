package knarf2011.becquerel;

import java.util.Random;

import knarf2011.becquerel.playerdata.PlayerDataV1;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public final class PlayerSpawner implements Listener
{
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event)
	{
		event.setJoinMessage("");
		Player p = event.getPlayer();
		PlayerDataV1 pd = Becquerel.instance.players.get(p.getUniqueId());
		if(pd==null)
		{
			pd = new PlayerDataV1();
			Becquerel.instance.players.put(p.getUniqueId(), pd);
			p.sendMessage(ChatColor.LIGHT_PURPLE+"Your name in this life will be: "+ChatColor.BOLD+ChatColor.AQUA+pd.name);
		}
		pd.online = true;
		if(!p.hasPlayedBefore())
		{
			Becquerel.instance.getLogger().info("spawning a new player");
			giveBasicItems(p);
			spawnPlayer(p);
			p.sendMessage(ChatColor.LIGHT_PURPLE+"Your name in this life will be: "+ChatColor.BOLD+ChatColor.AQUA+pd.name);
		}
	}
	
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
    	Player p = event.getPlayer();
    	PlayerDataV1 pd = Becquerel.instance.players.get(p.getUniqueId());
    	pd.logout();
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onNameTag(AsyncPlayerReceiveNameTagEvent event)
	{
		PlayerDataV1 pd = Becquerel.instance.players.get(event.getNamedPlayer().getUniqueId());
		event.setTag(pd.name);
	} //*/
	
	public void giveBasicItems(Player p)
	{
		p.setGameMode(GameMode.ADVENTURE);
		PlayerInventory i = p.getInventory();
		ItemStack picaxe = new ItemStack(Material.WOOD_PICKAXE,1,(short) 57);
		ItemStack axe = new ItemStack(Material.WOOD_AXE,1,(short) 57);
		ItemStack shovel = new ItemStack(Material.WOOD_SPADE,1,(short) 48);
		i.addItem(picaxe,axe,shovel);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onRespawn(PlayerRespawnEvent event)
	{
		Becquerel.instance.getLogger().info("respawning a player");
		giveBasicItems(event.getPlayer());
		PlayerDataV1 pd = Becquerel.instance.players.get(event.getPlayer().getUniqueId());
		pd.newName();
		pd.resetYells();
		pd.regenStatus=0;
		spawnPlayer(event.getPlayer());
		event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE+"Your name in this life will be: "+ChatColor.BOLD+ChatColor.AQUA+pd.name);
	}
	
	/**
	 * Spawn a player
	 * @param p Player to be spawned
	 */
	public void spawnPlayer(Player p)
	{
		Random r = new Random();
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		if(players.length == 0)
			spawnLonePlayer(p);
		else
		{
			Player p2 = p;
			while(p2==p)
			{
				p2 = players[r.nextInt(players.length)];
			}
			spawnRelativePlayer(p, p2);
		}
	}
	
	/**
	 * Teleports a player to within 250 blocks of another player.
	 * @param p1 Player to be teleported
	 * @param p2 Player to teleport to
	 */
	public void spawnRelativePlayer(Player p1, Player p2)
	{
		Random r = new Random();
		World overWorld = Bukkit.getWorlds().get(0);
		Location p2Loc = p2.getLocation();
		
		int newX = (int)Math.round(p2Loc.getX())+r.nextInt(250)-125;
		int newZ = (int)Math.round(p2Loc.getZ())+r.nextInt(250)-125;
		Location testLoc = new Location(overWorld,newX,255,newZ);
		
		int newY = overWorld.getHighestBlockYAt(testLoc);
		Location toGo = new Location(overWorld, newX, newY, newZ);
		p1.teleport(toGo);
	}
	
	/**
	 * Teleports a player to the world spawn location, then moves said location.
	 * @param p Player to be teleported
	 */
	public void spawnLonePlayer(Player p)
	{
		Random r = new Random();
		World overWorld = p.getWorld();
		Location defSpawn = overWorld.getSpawnLocation();
		
		int Y = overWorld.getHighestBlockYAt(defSpawn);
		overWorld.loadChunk(defSpawn.getChunk());
		p.teleport(new Location(overWorld,defSpawn.getX(),Y,defSpawn.getZ()));
		
		int newX = (int)Math.round(defSpawn.getX())+r.nextInt(500)-250;
		int newZ = (int)Math.round(defSpawn.getZ())+r.nextInt(500)-250;
		Location testLoc = new Location(overWorld,newX,255,newZ);
		
		int newY = overWorld.getHighestBlockYAt(testLoc);
		overWorld.setSpawnLocation(newX, newY, newZ);
	}
}