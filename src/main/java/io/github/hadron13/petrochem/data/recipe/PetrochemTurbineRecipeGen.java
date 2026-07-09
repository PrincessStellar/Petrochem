package io.github.hadron13.petrochem.data.recipe;

import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.blocks.small_engine.EngineFuelRecipe;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class PetrochemTurbineRecipeGen extends StandardProcessingRecipeGen<EngineFuelRecipe> {

    GeneratedRecipe STANDARD_LPG = create("lpg", b -> b
            .require(PetrochemFluids.LPG.get(), 1)
            .duration(20)
    );
    GeneratedRecipe STANDARD_STEAM = create("steam", b -> b
            .require(PetrochemFluids.STEAM.get(), 1000)
            .duration(1)
    );



    public PetrochemTurbineRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries, Petrochem.MODID);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return PetrochemRecipeTypes.TURBINE_FUEL;
    }
}
