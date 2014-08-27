package knarf2011.becquerel.commands;

import knarf2011.becquerel.Becquerel;
import knarf2011.becquerel.events.PlayerChat;
import knarf2011.becquerel.playerdata.PlayerDataV1;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutorYell implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
    {
    	Player p = (Player)sender;
		PlayerDataV1 pd = Becquerel.instance.players.get(p.getUniqueId());
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
    	return false; 
    }
}
