package io.github.hadron13.petrochem.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import io.github.hadron13.petrochem.register.PetrochemItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

public class PetrochemMixingRecipeGen extends MixingRecipeGen {

    GeneratedRecipe

    SULFURIC_ACID = create("sulfuric_acid", b -> b
            .require(PetrochemItems.SULFUR_DUST)
            .require(Fluids.WATER, 1000)
            .require(AllItems.GOLDEN_SHEET)
            .output(AllItems.GOLDEN_SHEET)
            .output(PetrochemFluids.SULFURIC_ACID.get(), 1000)
    ),
            BASIC_GASOLINE = create("basic_gasoline", b -> b
                    .require(PetrochemFluids.HEAVY_GAS_OIL.get(), 300)
                    .require(PetrochemFluids.HEAVY_NAPHTA.get(), 500)
                    .requiresHeat(HeatCondition.HEATED)
                    .output(PetrochemFluids.GASOLINE.get(), 800)
                    .whenModMissing(Petrochem.REALISTIC_MODID)
            )
            ;



    public PetrochemMixingRecipeGen(PackOutput output) {
        super(output, Petrochem.MODID);
    }
}
