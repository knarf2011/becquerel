package knarf2011.becquerel.crafting;

import knarf2011.becquerel.crafting.RecipeManager.Recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipesMaterials implements Recipes
{
	public ShapelessRecipe slimeballRecipeG = new ShapelessRecipe(new ItemStack(Material.SLIME_BALL, 2))
			.addIngredient(Material.LONG_GRASS).addIngredient(Material.INK_SACK);
	public ShapelessRecipe slimeballRecipe = new ShapelessRecipe(new ItemStack(Material.SLIME_BALL, 2))
			.addIngredient(Material.LEAVES).addIngredient(Material.INK_SACK);
	public ShapelessRecipe slimeballRecipeL = new ShapelessRecipe(new ItemStack(Material.SLIME_BALL, 2))
			.addIngredient(Material.WATER_LILY).addIngredient(Material.INK_SACK);

	public void registerRecipes(RecipeManager rm)
	{
		rm.addRecipe(slimeballRecipe);
		rm.addRecipe(slimeballRecipeG);
		rm.addRecipe(slimeballRecipeL);
	}
}
