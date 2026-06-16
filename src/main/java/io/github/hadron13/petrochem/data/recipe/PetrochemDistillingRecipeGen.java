package io.github.hadron13.petrochem.data.recipe;

import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.blocks.distillation_tower.DistillationControllerBlockEntity;
import io.github.hadron13.petrochem.data.recipe.base.DistillingRecipeGen;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

import static io.github.hadron13.petrochem.blocks.distillation_tower.DistillationControllerBlockEntity.DistilMode.*;

public class PetrochemDistillingRecipeGen extends DistillingRecipeGen {

    GeneratedRecipe BASIC_FLASH = create("basic_flash", b -> b
            .mode(DISTIL_FLASH)
            .require(PetrochemFluids.DESALTED_OIL.get(), 1200)
            .output(PetrochemFluids.OIL.get(), 1000)
            .output(PetrochemFluids.LIGHT_NAPHTA.get(), 200)
            .output(PetrochemFluids.LPG.get(), 200)
            .whenModMissing(Petrochem.REALISTIC_MODID)
    ),

    BASIC_ATMOSPHERIC = create("basic_atmospheric", b -> b
            .mode(DISTIL_ATMOSPHERIC)
            .require(PetrochemFluids.OIL.get(), 2000)
            .output(PetrochemFluids.OIL_RESIDUE.get(), 800)
            .output(PetrochemFluids.REFINED_DIESEL.get(), 400)
            .output(PetrochemFluids.KEROSENE.get(), 300)
            .output(PetrochemFluids.HEAVY_NAPHTA.get(), 500)
            .whenModMissing(Petrochem.REALISTIC_MODID)
    ),
    BASIC_VACUUM = create("basic_vacuum", b -> b
            .mode(DISTIL_VACUUM)
            .require(PetrochemFluids.OIL_RESIDUE.get(), 800)
            .output(PetrochemFluids.HEAVY_OIL_RESIDUE.get(), 200)
            .output(PetrochemFluids.HEAVY_GAS_OIL.get(), 300)
            .output(PetrochemFluids.FUEL_OIL.get(), 300)
            .whenModMissing(Petrochem.REALISTIC_MODID)
    );




    public PetrochemDistillingRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries, Petrochem.MODID);
    }
}
