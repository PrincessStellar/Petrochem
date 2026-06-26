package io.github.hadron13.petrochem.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.register.PetrochemBlocks;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import io.github.hadron13.petrochem.register.PetrochemItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public class PetrochemMixingRecipeGen extends MixingRecipeGen {

    GeneratedRecipe

    SULFURIC_ACID = create("sulfuric_acid", b -> b
            .require(PetrochemItems.SULFUR_DUST)
            .require(Fluids.WATER, 1000)
            .require(AllItems.GOLDEN_SHEET)
            .output(AllItems.GOLDEN_SHEET)
            .output(PetrochemFluids.SULFURIC_ACID.get(), 1000)
            .whenModLoaded(Petrochem.REALISTIC_MODID)
    ),
    STEAM = create("steam", b -> b
            .require(Fluids.WATER, 100)
            .requiresHeat(HeatCondition.HEATED)
            .output(PetrochemFluids.STEAM.get(), 1000)
    ),
    CAUSTIC_PAPER = create("naoh_paper", b -> b
            .require(AllItems.PULP)
            .require(AllItems.PULP)
            .require(PetrochemItems.CAUSTIC_SODA)
            .output(Items.PAPER, 12)
    ),
    CHLORINE_POISON = create("chlorine_poison", b -> b
            .require(PetrochemFluids.CHLORINE.get(), 250)
            .require(Fluids.WATER, 500)
            .output(PotionFluidHandler.getFluidFromPotionItem(PotionContents.createItemStack(Items.LINGERING_POTION, Potions.POISON)))
    ),

    BASIC_GASOLINE = create("basic_gasoline", b -> b
            .require(PetrochemFluids.HEAVY_GAS_OIL.get(), 300)
            .require(PetrochemFluids.HEAVY_NAPHTA.get(), 500)
            .requiresHeat(HeatCondition.HEATED)
            .output(PetrochemFluids.GASOLINE.get(), 800)
            .whenModMissing(Petrochem.REALISTIC_MODID)
    ),
    BASIC_DEASPHALTING = create("basic_deasphalting", b -> b
            .require(PetrochemFluids.FUEL_OIL.get(), 300)
            .require(PetrochemFluids.LPG.get(), 50)
            .requiresHeat(HeatCondition.HEATED)
            .output(PetrochemFluids.LUBRICANT.get(), 100)
            .output(PetrochemBlocks.ASPHALT_BLOCK.get(), 2)
            .whenModMissing(Petrochem.REALISTIC_MODID)
    ),
    BASIC_PLASTIC = create("basic_plastic", b -> b
            .require(PetrochemFluids.LIGHT_NAPHTA.get(), 500)
            .requiresHeat(HeatCondition.HEATED)
            .output(PetrochemFluids.PLASTIC.get(), 500)
    )
    ;

    public PetrochemMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Create.ID);
    }
}
