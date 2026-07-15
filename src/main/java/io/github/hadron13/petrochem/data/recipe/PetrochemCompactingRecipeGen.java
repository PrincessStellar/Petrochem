package io.github.hadron13.petrochem.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.CompactingRecipeGen;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import io.github.hadron13.petrochem.register.PetrochemItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public class PetrochemCompactingRecipeGen extends CompactingRecipeGen {

    GeneratedRecipe COKING = create("coking", b -> b
            .require(PetrochemFluids.HEAVY_OIL_RESIDUE.get(), 600)
            .output(PetrochemFluids.FUEL_OIL.get(), 400)
            .output(PetrochemItems.PET_COKE, 2)
    );



    public PetrochemCompactingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Petrochem.MODID);
    }
}
