package knarf2011.becquerel.events;

import knarf2011.becquerel.Becquerel;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.ItemStack;

public class BlockRedstone implements Listener
{
	@EventHandler
	public void onRedstoneUpdate(BlockRedstoneEvent event)
	{
		Block b1 = event.getBlock();
		int X = b1.getX();
		int Y = b1.getY();
		int Z = b1.getZ();
		World w = b1.getWorld();
		for(int xi=-1; xi<=1; xi++)
		for(int yi=-1; yi<=1; yi++)
		for(int zi=-1; zi<=1; zi++)
		{Block b = w.getBlockAt(X+xi, Y+yi, Z+zi);
		if(b.getType().equals(Material.DIAMOND_BLOCK))
		{
			if(event.getOldCurrent()==0) //if the current increased
			{
				int x = b.getX();
				int y = b.getY();
				int z = b.getZ();
				int broken=0;
				for(int i=-1; i<=1; i+=2)
				{
					Location l = new Location(w,x+i,y,z);
					if(l.getBlock().getType().equals(Material.BEDROCK))
					{
						l.getBlock().setType(Material.AIR);
						broken++;
						Becquerel.instance.getLogger().info("Bedrock broken!");
					}
					l = new Location(w,x,y+i,z);
					if(l.getBlock().getType().equals(Material.BEDROCK))
					{
						l.getBlock().setType(Material.AIR);
						broken++;
						Becquerel.instance.getLogger().info("Bedrock broken!");
					}
					l = new Location(w,x,y,z+i);
					if(l.getBlock().getType().equals(Material.BEDROCK))
					{
						l.getBlock().setType(Material.AIR);
						broken++;
						Becquerel.instance.getLogger().info("Bedrock broken!");
					}
				}
				Location l = new Location(w,x,y+1,z);
				l.setY(y);
				if(broken>0)
				{
					w.dropItemNaturally(l, new ItemStack(Material.BEDROCK, broken));
					w.playSound(l, Sound.DIG_STONE, 10f+(2f*broken), 1f);
				}
			}
		}
		}
	}
}
