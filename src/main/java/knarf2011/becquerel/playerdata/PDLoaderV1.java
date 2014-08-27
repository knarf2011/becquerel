package knarf2011.becquerel.playerdata;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import knarf2011.becquerel.Becquerel;
import knarf2011.becquerel.SLAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PDLoaderV1
{
	public static void savePlayerData(HashMap<UUID, PlayerDataV1> players)
	{
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
 			Becquerel.instance.getLogger().info("Player data saved successfully.");
 	            } catch(Exception e) {
 	                 e.printStackTrace();
 	                Becquerel.instance.getLogger().info("Unable to save player data.");
 	            }
	}
	
	public static HashMap<UUID, PlayerDataV1> loadPlayerData()
	{
		HashMap<UUID, PlayerDataV1> players = new HashMap<UUID, PlayerDataV1>();
		
		//load player data version file
		File f = new File("plugins\\BecquerelPlayerDataVersion.txt");
		Scanner fs = null;
		boolean tryLoad = true;
		try{
			fs = new Scanner(f);
			if(fs.nextLine().charAt(0)!='1')
			{
				tryLoad = false;
				Becquerel.instance.getLogger().info("Player data is a higher version than this version of Project Becquerel can load!");
				Becquerel.instance.getLogger().info("All player data will be reset to default values.");
			}
		}catch(Exception e){}
		if(fs!=null)
			fs.close();
		if(tryLoad)
		try{ //attempt to load Java Binary Object data for player data hashmap. Failure does not cause a crash;
			 //it simply means we will use the empty hashmap defined above.
			players = SLAPI.load("plugins\\BecquerelPlayerData.bin");
			Becquerel.instance.getLogger().info("Player data loaded successfully.");
	        }
		catch(Exception e)
	        {
	                //handle the exception
	                e.printStackTrace();
	                Becquerel.instance.getLogger().info("Unable to load player data.");
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
		return players;
	}
}
