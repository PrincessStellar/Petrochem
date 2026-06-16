package io.github.hadron13.petrochem.blocks.small_engine;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public class EngineFuelRecipe extends StandardProcessingRecipe<RecipeInput> {


    public EngineFuelRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    public static EngineFuelRecipe gasoline(ProcessingRecipeParams params) {
        return new EngineFuelRecipe(PetrochemRecipeTypes.GASOLINE_ENGINE_FUEL, params);
    }

    public static EngineFuelRecipe diesel(ProcessingRecipeParams params) {
        return new EngineFuelRecipe(PetrochemRecipeTypes.DIESEL_ENGINE_FUEL, params);
    }

    public static EngineFuelRecipe ship(ProcessingRecipeParams params) {
        return new EngineFuelRecipe(PetrochemRecipeTypes.SHIP_ENGINE_FUEL, params);
    }

    public boolean match(FluidStack fuel){
        return getFluidIngredients().get(0).test(fuel);
    }
    public float getConsumptionRate(){
        return (float)getFluidIngredients().get(0).amount() / (float)getProcessingDuration();
    }

    @Override
    protected boolean canSpecifyDuration() {
        return true;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }

    @Override
    protected int getMaxInputCount() {
        return 0;
    }

    @Override
    protected int getMaxOutputCount() {
        return 0;
    }


    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return false;
    }
}
