package io.github.hadron13.petrochem.data.recipe;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.blocks.small_engine.EngineFuelRecipe;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class PetrochemGasolineEngineRecipeGen extends StandardProcessingRecipeGen<EngineFuelRecipe> {

    GeneratedRecipe STANDARD_GASOLINE = create("gasoline", b -> b
            .require(PetrochemFluids.GASOLINE.get(), 1)
            .duration(15)
    );

    GeneratedRecipe STANDARD_KEROSENE = create("kerosene", b -> b
            .require(PetrochemFluids.KEROSENE.get(), 1)
            .duration(20)
    );



    public PetrochemGasolineEngineRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries, Petrochem.MODID);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return PetrochemRecipeTypes.GASOLINE_ENGINE_FUEL;
    }
}
