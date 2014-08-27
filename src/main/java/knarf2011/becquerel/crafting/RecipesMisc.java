package knarf2011.becquerel.crafting;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import knarf2011.becquerel.crafting.RecipeManager.Recipes;

public class RecipesMisc implements Recipes
{
	public ShapedRecipe bedrockRecipe = new ShapedRecipe(new ItemStack(Material.BEDROCK, 2)).shape("oco","coc","oco")
			.setIngredient('o', Material.OBSIDIAN).setIngredient('c', Material.COBBLESTONE);
	
	public ShapedRecipe saddleRecipe = new ShapedRecipe(new ItemStack(Material.SADDLE, 2)).shape("lll","lil")
			.setIngredient('l', Material.LEATHER).setIngredient('i',Material.IRON_INGOT);
	
	public ShapedRecipe cobwebRecipe = new ShapedRecipe(new ItemStack(Material.WEB, 2)).shape("sss","sbs","sss")
			.setIngredient('s', Material.STRING).setIngredient('b', Material.SLIME_BALL);
	
	public void registerRecipes(RecipeManager rm)
	{
		rm.addRecipe(bedrockRecipe);
		rm.addRecipe(saddleRecipe);
		rm.addRecipe(cobwebRecipe);
	}

}
