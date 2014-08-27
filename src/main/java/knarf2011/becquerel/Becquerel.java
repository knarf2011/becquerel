package knarf2011.becquerel;

import java.util.HashMap;
import java.util.UUID;

import knarf2011.becquerel.commands.CommandManager;
import knarf2011.becquerel.crafting.RecipeManager;
import knarf2011.becquerel.events.EntityHealthRegen;
import knarf2011.becquerel.events.EventManager;
import knarf2011.becquerel.playerdata.PDLoaderV1;
import knarf2011.becquerel.playerdata.PlayerDataV1;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Becquerel extends JavaPlugin
{
	//initialize player data hashmap; if data is loaded successfully this will be overridden and
	//disposed of by garbage collection.
	public HashMap<UUID, PlayerDataV1> players = null;
	//declare the instance variable and initialize as null; if not initialized compilation errors will occur.
	public static Becquerel instance = null;
	//declare the managers
	public CommandManager commandManager = new CommandManager();
	public EventManager eventManager = new EventManager();
	public RecipeManager recipeManager = new RecipeManager();
	
	@Override //when the plugin is enabled. NOTE: not necessarily when server is starting; players may already be online.
    public void onEnable()
	{
		//Bukkit uses an instance of the server class for everything, so we need a variable to keep track of it.
		//The fact that this instance has been created means that it must be the one in use; therefore store it.
		instance = this;
		
		commandManager.addExecutors(this); //register commands
		eventManager.addEvents(this); //Register event listeners
		recipeManager.addRecipes(this.getServer()); //add crafting recipes
		
		EntityHealthRegen.initRegen(); //Initialize modified health regeneration
		
		getLogger().info("Loading player data...");
		players = PDLoaderV1.loadPlayerData();
    }
 
    @Override //when the plugin is disabled.
    public void onDisable()
    {
    	for (Player p : Bukkit.getServer().getOnlinePlayers())
    	{
    		PlayerDataV1 pd = players.get(p.getUniqueId());
    		pd.logout();
    	}
    	getLogger().info("Saving player data...");
    	PDLoaderV1.savePlayerData(players);
    	//This is no longer the instance being used; set the instance variable to null so garbage collection can get it.
    	instance = null;
    }
}
