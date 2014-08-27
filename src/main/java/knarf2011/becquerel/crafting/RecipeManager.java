package knarf2011.becquerel.crafting;

import org.bukkit.Server;
import org.bukkit.inventory.Recipe;

public class RecipeManager
{
	public static RecipeManager instance = null;
	
	public Recipe[] recipes = new Recipe[0];
	
	public RecipeManager()
	{
		instance = this;
	}
	
	public void addRecipes(Server server)
	{
		(new RecipesArmor()).registerRecipes(this);
		(new RecipesMaterials()).registerRecipes(this);
		(new RecipesMisc()).registerRecipes(this);
		
		for(Recipe r:recipes)
			server.addRecipe(r);
	}
	
	public void addRecipe(Recipe recipe)
	{
		Recipe[] newRecipes = new Recipe[recipes.length+1];
		for(int i=0; i<recipes.length; i++)
			newRecipes[i]=recipes[i];
		newRecipes[recipes.length]=recipe;
		recipes = newRecipes;
	}
	
	public interface Recipes
	{
		public abstract void registerRecipes(RecipeManager rm);
	}
}
