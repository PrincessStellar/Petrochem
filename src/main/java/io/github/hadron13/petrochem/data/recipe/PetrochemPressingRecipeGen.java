package io.github.hadron13.petrochem.data.recipe;

import com.simibubi.create.api.data.recipe.CompactingRecipeGen;
import com.simibubi.create.api.data.recipe.PressingRecipeGen;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import io.github.hadron13.petrochem.register.PetrochemItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class PetrochemPressingRecipeGen extends PressingRecipeGen {

    GeneratedRecipe STEEL_SHEET = create("steel_sheet", b -> b
            .require(PetrochemItems.STEEL_INGOT)
            .output(PetrochemItems.STEEL_SHEET)
    );
    GeneratedRecipe BRONZE_SHEET = create("bronze_sheet", b -> b
            .require(PetrochemItems.BRONZE_INGOT)
            .output(PetrochemItems.BRONZE_SHEET)
    );



    public PetrochemPressingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Petrochem.MODID);
    }
}
