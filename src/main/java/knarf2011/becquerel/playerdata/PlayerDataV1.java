package knarf2011.becquerel.playerdata;

import java.io.Serializable;

import knarf2011.becquerel.namegen.NameGenerator;

public class PlayerDataV1 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 415640513452402068L;
	public long[] yells = new long[0];
	public long lastOn = System.currentTimeMillis();
	public boolean online = true;
	public String name = NameGenerator.getName();
	public int regenStatus = 0;
	
	public boolean doRegen(int ticks)
	{
		this.regenStatus++;
		if(this.regenStatus>=ticks)
		{
			this.regenStatus=0;
			return true;
		}
		return false;
	}
	
	/**
	 * Generate a new name.
	 */
	public void newName()
	{
		this.name = NameGenerator.getName();
	}
	
	/**
	 * Record a yell
	 */
	public void yell()
	{
		long[] newYells = new long[yells.length+1];
		for(int i=0; i<yells.length; i++)
			newYells[i+1]=yells[i];
		newYells[0]=System.currentTimeMillis();
		yells=newYells;
	}
	
	/**
	 * Get a count of yells by this player since a specified time.
	 * @param start time to look forward from
	 * @return number of yells since start time
	 */
	public int countYellsSince(long start)
	{
		int ret=0;
		boolean done=false;
		for(; ret<yells.length&&!done; ret++)
		{
			if(yells[ret]<start)
				done=true;
		}
		return ret;
	}
	
	public long timeSinceYell()
	{
		if(yells.length==0)
			return 99999999;
		return System.currentTimeMillis()-yells[0];
	}
	
	/**
	 * Erase the yells array.
	 */
	public void resetYells()
	{
		this.yells = new long[0];
	}
	
	/**
	 * Do all tasks necessary for properly logging this player in.
	 */
	public void login()
	{
		long diff = System.currentTimeMillis() - this.lastOn;
		for(int i=0; i<yells.length; i++)
			yells[i]+=diff;
		this.online=true;
	}
	
	/**
	 * Do all tasks necessary for properly logging this player out.
	 */
	public void logout()
	{
		this.lastOn = System.currentTimeMillis();
		this.online=false;
	}
}
