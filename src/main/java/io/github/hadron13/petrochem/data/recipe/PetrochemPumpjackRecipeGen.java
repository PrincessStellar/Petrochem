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
            .output(PetrochemFluids.PETROLEUM.get(), 50)
    );

    GeneratedRecipe DEEP_OCEAN = create("deep_ocean", b -> b
            .biome("minecraft:deep_ocean")
            .output(PetrochemFluids.PETROLEUM.get(), 20)
    );

    GeneratedRecipe COLD_DEEP_OCEAN = create("cold_deep_ocean", b -> b
            .biome("minecraft:deep_cold_ocean")
            .output(PetrochemFluids.PETROLEUM.get(), 50)
    );

    GeneratedRecipe JUNGLE = create("jungle", b -> b
            .biome("minecraft:jungle")
            .output(PetrochemFluids.DESALTED_OIL.get(), 50)
    );

    GeneratedRecipe SWAMP = create("swamp", b -> b
            .biome("minecraft:swamp")
            .output(PetrochemFluids.DESALTED_OIL.get(), 60)
    );




    public PetrochemPumpjackRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries, Petrochem.MODID);
    }
}
