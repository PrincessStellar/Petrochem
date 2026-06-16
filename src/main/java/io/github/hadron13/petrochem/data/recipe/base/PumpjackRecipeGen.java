package io.github.hadron13.petrochem.data.recipe.base;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.hadron13.petrochem.blocks.pumpjack.PumpjackRecipe;
import io.github.hadron13.petrochem.blocks.pumpjack.PumpjackRecipeParams;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class PumpjackRecipeGen extends ProcessingRecipeGen<PumpjackRecipeParams, PumpjackRecipe, PumpjackRecipe.Builder<PumpjackRecipe> > {

    public PumpjackRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return PetrochemRecipeTypes.PUMPJACK;
    }

    @Override
    protected PumpjackRecipe.Builder<PumpjackRecipe> getBuilder(ResourceLocation id) {
        return new PumpjackRecipe.Builder<>(PumpjackRecipe::new, id);
    }
}
