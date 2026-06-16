package io.github.hadron13.petrochem.data.recipe.base;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.hadron13.petrochem.blocks.electrolyzer.ElectrolyzingRecipe;
import io.github.hadron13.petrochem.blocks.electrolyzer.EnergyRecipeParams;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ElectrolyzingRecipeGen extends ProcessingRecipeGen<EnergyRecipeParams, ElectrolyzingRecipe, ElectrolyzingRecipe.Builder<ElectrolyzingRecipe>> {


    public ElectrolyzingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return PetrochemRecipeTypes.ELECTROLYZING;
    }

    @Override
    protected ElectrolyzingRecipe.Builder<ElectrolyzingRecipe> getBuilder(ResourceLocation id) {
        return new ElectrolyzingRecipe.Builder<>(ElectrolyzingRecipe::new, id);
    }
}
