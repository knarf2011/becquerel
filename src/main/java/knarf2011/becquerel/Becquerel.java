package knarf2011.becquerel;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import knarf2011.becquerel.playerdata.PlayerDataV1;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Becquerel extends JavaPlugin
{
	public HashMap<UUID, PlayerDataV1> players = new HashMap<UUID, PlayerDataV1>();
	public static Becquerel instance = null;
	public ShapedRecipe bedrockRecipe = new ShapedRecipe(new ItemStack(Material.BEDROCK, 2)).shape("oco","coc","oco")
			.setIngredient('o', Material.OBSIDIAN).setIngredient('c', Material.COBBLESTONE);
	
	@Override
    public void onEnable()
	{
		instance = this;
		getServer().getPluginManager().registerEvents(new PlayerSpawner(), this);
		getServer().getPluginManager().registerEvents(new PlayerConsume(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new BlockRedstone(), this);
		getServer().getPluginManager().registerEvents(new CreatureSpawn(), this);
		getServer().getPluginManager().registerEvents(new EntityHealthRegen(), this);
		EntityHealthRegen.initRegen();
		
		getServer().addRecipe(bedrockRecipe);
		
		getLogger().info("Loading player data...");
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
		try{
			
			players = SLAPI.load("plugins\\BecquerelPlayerData.bin");
			getLogger().info("Player data loaded successfully.");
	        } 
		catch(Exception e)
	        {
	                //handle the exception
	                e.printStackTrace();
	                getLogger().info("Unable to load player data.");
	        }
		for (Player p : Bukkit.getServer().getOnlinePlayers())
		{
		    PlayerDataV1 pd = players.get(p.getUniqueId());
		    if(pd==null)
		    	players.put(p.getUniqueId(), new PlayerDataV1());
		    else
			    pd.login();
		}
    }
 
    @Override
    public void onDisable()
    {
    	for (Player p : Bukkit.getServer().getOnlinePlayers())
    	{
    		PlayerDataV1 pd = players.get(p.getUniqueId());
    		pd.lastOn = System.currentTimeMillis();
        	pd.online=false;
    	}
    	getLogger().info("Saving player data...");
    	 try {
    			SLAPI.save(players,"plugins\\BecquerelPlayerData.bin");
    			//delete the version record file if it exists
    			try{File f = new File("plugins\\BecquerelPlayerDataVersion.txt");f.delete();}catch(Exception e){}
    			FileWriter fw = new FileWriter("plugins\\BecquerelPlayerDataVersion.txt");
    			fw.write("1");
    			fw.flush();
    			fw.close();
    			getLogger().info("Player data saved successfully.");
    	            } catch(Exception e) {
    	                 e.printStackTrace();
    	                 getLogger().info("Unable to save player data.");
    	            }
    	 instance = null;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
    {
    	if (cmd.getName().equalsIgnoreCase("yell") && (sender instanceof Player))
    	{
    		Player p = (Player)sender;
    		PlayerDataV1 pd = players.get(p.getUniqueId());
    		long timeToYell = (pd.countYellsSince(System.currentTimeMillis()-600000)*500+1000)-pd.timeSinceYell();
    		if(timeToYell<=0)
    		{
    			pd.yell();
    			String msg="";
    			for(String s:args)
    				msg+=(s+" ");
    			PlayerChat.yell(p, msg);
    		}
    		else
    		{
    			p.sendMessage("You cannot yell again for "+timeToYell/1000d+" more seconds!");
    		}
    		return true;
    	}
    	return false; 
    }
}
