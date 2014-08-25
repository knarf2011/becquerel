package knarf2011.becquerel;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import knarf2011.becquerel.playerdata.PlayerDataV1;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Becquerel extends JavaPlugin
{
	//initialize player data hashmap; if data is loaded successfully this will be overridden and
	//disposed of by garbage collection.
	public HashMap<UUID, PlayerDataV1> players = new HashMap<UUID, PlayerDataV1>();
	//declare the instance variable and initialize as null; if not initialized compilation errors will occur.
	public static Becquerel instance = null;
	//define bedrock recipe
	public ShapedRecipe bedrockRecipe = new ShapedRecipe(new ItemStack(Material.BEDROCK, 2)).shape("oco","coc","oco")
			.setIngredient('o', Material.OBSIDIAN).setIngredient('c', Material.COBBLESTONE);
	
	@Override //when the plugin is enabled. NOTE: not necessarily when server is starting; players may already be online.
    public void onEnable()
	{
		//Bukkit uses an instance of the server class for everything, so we need a variable to keep track of it.
		//The fact that this instance has been created means that it must be the one in use; therefore store it.
		instance = this;
		//Register event listeners
		getServer().getPluginManager().registerEvents(new PlayerSpawner(), this);
		getServer().getPluginManager().registerEvents(new PlayerConsume(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new BlockRedstone(), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new CreatureSpawn(), this);
		getServer().getPluginManager().registerEvents(new EntityHealthRegen(), this);
		//Initialize modified health regeneration
		EntityHealthRegen.initRegen();
		
		//add crafting recipe for bedrock, as defined above.
		getServer().addRecipe(bedrockRecipe);
		
		getLogger().info("Loading player data...");
		//Start of player data loading process. TODO: sometime, implement .PROPERTIES format for better portability.
		//load player data version file
		File f = new File("plugins\\BecquerelPlayerDataVersion.txt");
		Scanner fs;
		boolean tryLoad = true;
		try{fs = new Scanner(f);
			if(fs.nextLine().charAt(0)!='1')
			{
				tryLoad = false;
				getLogger().info("Player data is a higher version than this version of Project Becquerel can load!");
				getLogger().info("All player data will be reset to default values.");
			}
		}catch(Exception e){}
		if(tryLoad)
		try{ //attempt to load Java Binary Object data for player data hashmap. Failure does not cause a crash;
			 //it simply means we will use the empty hashmap defined above.
			players = SLAPI.load("plugins\\BecquerelPlayerData.bin");
			getLogger().info("Player data loaded successfully.");
	        }
		catch(Exception e)
	        {
	                //handle the exception
	                e.printStackTrace();
	                getLogger().info("Unable to load player data.");
	        }
		//add online players to HashMap where undefined.
		for (Player p : Bukkit.getServer().getOnlinePlayers())
		{
		    PlayerDataV1 pd = players.get(p.getUniqueId());
		    if(pd==null)
		    {
		    	pd = new PlayerDataV1();
		    	players.put(p.getUniqueId(), pd);
		    	p.sendMessage(ChatColor.LIGHT_PURPLE+"Your name in this life will be: "+ChatColor.BOLD+ChatColor.AQUA+pd.name);
		    }
		    else
			    pd.login();
		}
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
    	 try {
    			SLAPI.save(players,"plugins\\BecquerelPlayerData.bin");
    			//delete the version record file if it exists
    			try{File f = new File("plugins\\BecquerelPlayerDataVersion.txt");f.delete();}catch(Exception e){}
    			//write version record file
    			FileWriter fw = new FileWriter("plugins\\BecquerelPlayerDataVersion.txt");
    			fw.write("1");
    			fw.flush();
    			fw.close();
    			//log successful save
    			getLogger().info("Player data saved successfully.");
    	            } catch(Exception e) {
    	                 e.printStackTrace();
    	                 getLogger().info("Unable to save player data.");
    	            }
    	 //This is no longer the instance being used; set the instance variable to null so garbage collection can get it.
    	 instance = null;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
    {
    	Player p = (Player)sender;
		PlayerDataV1 pd = players.get(p.getUniqueId());
    	if (cmd.getName().equalsIgnoreCase("yell") && (sender instanceof Player)) //handle /yell command
    	{
    		long timeToYell = (pd.countYellsSince(System.currentTimeMillis()-600000)*500+1000)-pd.timeSinceYell();
    		if(timeToYell<=0)
    		{
    			//update player data
    			pd.yell();
    			//construct message from command arguments
    			String msg="";
    			for(String s:args)
    				msg+=(s+" ");
    			//yell message (powered by PlayerChat.java)
    			PlayerChat.yell(p, msg);
    		}
    		else
    		{
    			p.sendMessage("You cannot yell again for "+timeToYell/1000d+" more seconds!");
    		}
    		return true;
    	}
    	
    	if(cmd.getName().equalsIgnoreCase("bechelp")) //handle /bechelp command (Project Becquerel Help) TODO: write helpfile loader
    	{
    		return true;
    	}
    	return false; 
    }
}
