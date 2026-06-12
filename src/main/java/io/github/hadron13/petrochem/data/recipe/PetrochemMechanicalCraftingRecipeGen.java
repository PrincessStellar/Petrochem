package io.github.hadron13.petrochem.data.recipe;

import com.molybdenum.alloyed.common.registry.ModBlocks;
import com.molybdenum.alloyed.common.registry.ModItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.register.PetrochemBlocks;
import io.github.hadron13.petrochem.register.PetrochemItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

public class PetrochemMechanicalCraftingRecipeGen extends MechanicalCraftingRecipeGen {



    GeneratedRecipe PUMPJACK_ARM =
    create(PetrochemBlocks.PUMPJACK_ARM::get).recipe(b -> b
            .key('S', ModItems.STEEL_SHEET)
            .key('I', ModItems.STEEL_INGOT)
            .key('B', ModBlocks.STEEL_BLOCK)
            .key('A', AllBlocks.SHAFT)
            .key('C', Items.CHAIN)
			.patternLine("SSSSSSB")
			.patternLine("C IAI  ")
    );

    public PetrochemMechanicalCraftingRecipeGen(PackOutput output) {
        super(output, Petrochem.MODID);
    }
}
