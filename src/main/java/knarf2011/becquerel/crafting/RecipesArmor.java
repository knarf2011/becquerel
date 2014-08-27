package knarf2011.becquerel.crafting;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import knarf2011.becquerel.crafting.RecipeManager.Recipes;

public class RecipesArmor implements Recipes
{
	public ShapedRecipe chainmailhelmetRecipe = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_HELMET, 2)).shape("fff","f f")
			.setIngredient('f', Material.FLINT);
	
	public ShapedRecipe chainmailchestplateRecipe = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 2)).shape("f f","fff","fff")
			.setIngredient('f', Material.FLINT);

	public ShapedRecipe chainmailleggingsRecipe = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_LEGGINGS, 2)).shape("fff","f f","f f")
			.setIngredient('f', Material.FLINT);
	
	public ShapedRecipe chainmailbootsRecipe = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_BOOTS, 2)).shape("f f","f f")
			.setIngredient('f', Material.FLINT);
	
	public void registerRecipes(RecipeManager rm)
	{
		rm.addRecipe(chainmailhelmetRecipe);
		rm.addRecipe(chainmailchestplateRecipe);
		rm.addRecipe(chainmailleggingsRecipe);
		rm.addRecipe(chainmailbootsRecipe);
	}
}
