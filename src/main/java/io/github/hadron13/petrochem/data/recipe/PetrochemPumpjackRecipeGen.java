package io.github.hadron13.petrochem.data.recipe;

import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.data.recipe.base.PumpjackRecipeGen;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class PetrochemPumpjackRecipeGen extends PumpjackRecipeGen {

    GeneratedRecipe DESERT = create("desert", b -> b
            .biome("minecraft:desert")
            .output(PetrochemFluids.PETROLEUM.get(), 80)
    );

    GeneratedRecipe DEEP_OCEAN = create("deep_ocean", b -> b
            .biome("#minecraft:is_deep_ocean")
            .output(PetrochemFluids.PETROLEUM.get(), 95)
    );

    GeneratedRecipe JUNGLE = create("jungle", b -> b
            .biome("#minecraft:is_jungle")
            .output(PetrochemFluids.DESALTED_OIL.get(), 100)
    );

    GeneratedRecipe SWAMP = create("swamp", b -> b
            .biome("minecraft:swamp")
            .output(PetrochemFluids.DESALTED_OIL.get(), 110)
    );




    public PetrochemPumpjackRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries, Petrochem.MODID);
    }
}
