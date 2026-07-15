package io.github.hadron13.petrochem.data.recipe;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.register.PetrochemBlocks;
import io.github.hadron13.petrochem.register.PetrochemItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class PetrochemMechanicalCraftingRecipeGen extends MechanicalCraftingRecipeGen {



    GeneratedRecipe PUMPJACK_ARM =
    create(PetrochemBlocks.PUMPJACK_ARM::get).recipe(b -> b
            .key('S', PetrochemItems.STEEL_SHEET.get())
            .key('I', PetrochemItems.STEEL_INGOT.get())
            .key('B', PetrochemBlocks.STEEL_BlOCK.asItem())
            .key('A', AllBlocks.SHAFT)
            .key('C', Items.CHAIN)
			.patternLine("SSSSSSB")
			.patternLine("C IAI  ")
    );

    public PetrochemMechanicalCraftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Petrochem.MODID);
    }
}
