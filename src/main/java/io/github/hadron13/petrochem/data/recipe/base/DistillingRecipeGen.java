package io.github.hadron13.petrochem.data.recipe.base;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.hadron13.petrochem.blocks.distillation_tower.DistillationRecipeParams;
import io.github.hadron13.petrochem.blocks.distillation_tower.DistillingRecipe;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class DistillingRecipeGen extends ProcessingRecipeGen<DistillationRecipeParams, DistillingRecipe, DistillingRecipe.Builder<DistillingRecipe>> {


    public DistillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return PetrochemRecipeTypes.DISTILLING;
    }

    @Override
    protected DistillingRecipe.Builder<DistillingRecipe> getBuilder(ResourceLocation id) {
        return new DistillingRecipe.Builder<>(DistillingRecipe::new, id);
    }
}
