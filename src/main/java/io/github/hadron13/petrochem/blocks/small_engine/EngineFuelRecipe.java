package io.github.hadron13.petrochem.blocks.small_engine;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EngineFuelRecipe extends StandardProcessingRecipe<RecipeInput> {

    public static HashMap<IRecipeTypeInfo, HashSet<Fluid>> validFuels = new HashMap<>();

    public EngineFuelRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeParams params) {
        super(typeInfo, params);
        if(!validFuels.containsKey(typeInfo)){
            validFuels.put(typeInfo, new HashSet<>());
        }
        FluidStack[]fuels = getFluidIngredients().getFirst().getFluids();
        for(FluidStack fluid : fuels){
            validFuels.get(typeInfo).add(fluid.getFluid());
        }
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


    public static EngineFuelRecipe turbine(ProcessingRecipeParams params) {
        return new EngineFuelRecipe(PetrochemRecipeTypes.TURBINE_FUEL, params);
    }

    public boolean match(FluidStack fuel){
        return getFluidIngredients().getFirst().test(fuel);
    }
    public float getConsumptionRate(){
        return (float)getFluidIngredients().getFirst().amount() / (float)getProcessingDuration();
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
