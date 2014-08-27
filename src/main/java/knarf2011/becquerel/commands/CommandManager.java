package knarf2011.becquerel.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager
{
	public CommandManager instance = null;
	
	public CommandExecutor[] executors = new CommandExecutor[0];
	public String[][] commands = new String[0][];
	
	public CommandManager()
	{
		instance = this;
	}
	
	public void addExecutors(JavaPlugin p)
	{
		addExecutor(new CommandExecutorHelp(), "bechelp");
		addExecutor(new CommandExecutorYell(), "yell");
		
		for(int i=0;i<executors.length;i++)
			for(int j=0;j<commands[i].length;j++)
				p.getCommand(commands[i][j]).setExecutor(executors[i]);
	}
	
	public void addExecutor(CommandExecutor exe, String... cmd)
	{
		CommandExecutor[] newExecutors = new CommandExecutor[executors.length+1];
		for(int i=0;i<executors.length;i++)
			newExecutors[i]=executors[i];
		newExecutors[executors.length]=exe;
		executors=newExecutors;
		
		String[][] newCommands = new String[commands.length+1][];
		for(int i=0;i<commands.length;i++)
			newCommands[i]=commands[i];
		newCommands[commands.length]=cmd;
		commands=newCommands;
	}
}
