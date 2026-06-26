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

public class PetrochemDieselEngineRecipeGen extends StandardProcessingRecipeGen<EngineFuelRecipe> {


    GeneratedRecipe standard_diesel = create("diesel", b -> b
            .require(PetrochemFluids.REFINED_DIESEL.get(), 1)
            .duration(10)
    );

    GeneratedRecipe fuel_oil = create("fuel_oil", b -> b
            .require(PetrochemFluids.FUEL_OIL.get(), 1)
            .duration(5)
    );

    GeneratedRecipe petrol = create("raw_petroleum", b -> b
            .require(PetrochemFluids.PETROLEUM.get(), 1)
            .duration(2)
    );


    public PetrochemDieselEngineRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries, Petrochem.MODID);
    }
    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return PetrochemRecipeTypes.DIESEL_ENGINE_FUEL;
    }
}
