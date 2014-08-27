package knarf2011.becquerel.events;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener
{
	public HashMap<Material, Integer> breakables = initBreakables();
	public HashMap<Material, Integer> tools = initTools();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player p = event.getPlayer();
		ItemStack hi = p.getItemInHand();
		Block b = event.getBlock();
		int requiredTool = 0; try{requiredTool = breakables.get(b.getType());}catch(Exception e){}
		if(requiredTool == 0)
			return;
		int usedTool = 0; try{usedTool = tools.get(hi.getType());}catch(Exception e){}
		if(requiredTool == usedTool)
			return;
		event.setCancelled(true);
	}
	
	public HashMap<Material, Integer> initTools()
	{
		Material[][] mats = new Material[][]
		{
				{}, //this makes checking stuff a lot easier
				{Material.WOOD_AXE,Material.GOLD_AXE,Material.STONE_AXE,Material.IRON_AXE,Material.DIAMOND_AXE},
				{Material.WOOD_PICKAXE,Material.GOLD_PICKAXE,Material.STONE_PICKAXE,Material.IRON_PICKAXE,Material.DIAMOND_PICKAXE},
				{Material.WOOD_SPADE,Material.GOLD_SPADE,Material.STONE_SPADE,Material.IRON_SPADE,Material.DIAMOND_SPADE}
		};
		
		HashMap<Material,Integer> ret = new HashMap<Material,Integer>();
		for(int i1=0; i1<mats.length; i1++)
		{
			Material[] a = mats[i1];
			for(int i2=0; i2<a.length; i2++)
			{
				ret.put(a[i2], i1);
			}
		}
		return ret;
	}
	
	public HashMap<Material, Integer> initBreakables()
	{
		Material[][] mats =  new Material[][]
		{
				{ //No tool
					/*
					Material.LEAVES,
					Material.BED,
					Material.WORKBENCH,
					Material.CROPS,
					Material.LONG_GRASS,
					Material.DEAD_BUSH  //*/
				},
				{ //Axe
					Material.LOG,
					Material.WOOD_PLATE,
					Material.WOOD_STEP,
					Material.WOOD_STAIRS
				},
				{ //Pickaxe
					
				},
				{ //Shovel
					Material.DIRT,
					Material.GRASS,
					Material.GRAVEL,
					Material.SAND
				}
		};
		
		HashMap<Material,Integer> ret = new HashMap<Material,Integer>();
		for(int i1=0; i1<mats.length; i1++)
		{
			Material[] a = mats[i1];
			for(int i2=0; i2<a.length; i2++)
			{
				ret.put(a[i2], i1);
			}
		}
		return ret;
	}
}
