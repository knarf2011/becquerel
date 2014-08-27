package knarf2011.becquerel.commands;

import java.io.InputStream;
import java.util.Scanner;

import knarf2011.becquerel.Becquerel;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutorHelp implements CommandExecutor
{
	String[] helpText = loadHelp();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("bechelp")) //handle /bechelp command (Project Becquerel Help)
    	{
    		int page=1;
    		if(args.length>=1)
    		{
    			page=Integer.parseInt(args[0]) % helpText.length;
    		}
    		p.sendMessage(ChatColor.YELLOW+"[Becquerel Help: page "+page+"/"+helpText.length+"]\n"+helpText[page]);
    		return true;
    	}
    	return false; 
	}

	public String[] loadHelp()
    {
    	InputStream is = Becquerel.class.getResourceAsStream("explanation.txt");
    	Scanner s = new Scanner(is);
    	String[] ret = new String[1];
    	boolean cont = true;
    	while(s.hasNext() && cont)
    	{
    		String ln = s.nextLine();
    		if(ln.startsWith("==x"))
    			cont=false;
    		else if(ln.startsWith("==>"))
    		{
    			String[] newRet = new String[ret.length+1];
    			for(int i=0; i<ret.length; i++)
    				newRet[i]=ret[i];
    			newRet[ret.length]="";
    			ret=newRet;
    		}
    		else
    			ret[ret.length-1]+=ln+" ";
    	}
    	s.close();
    	return ret;
    }
}
