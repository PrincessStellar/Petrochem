package io.github.hadron13.petrochem.data.recipe;

import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.data.recipe.base.ElectrolyzingRecipeGen;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import io.github.hadron13.petrochem.register.PetrochemItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

public class PetrochemElectrolyzingRecipeGen extends ElectrolyzingRecipeGen {

    GeneratedRecipe BASIC_DESALTED_OIL = createElectrolyzing("basic_desalting", b -> (ElectrolyzingRecipeBuilder) b
            .energy(100)
            .require(PetrochemFluids.PETROLEUM.get(), 500)
            .output(PetrochemFluids.DESALTED_OIL.get(), 500)
            .output(0.5f, PetrochemItems.SALT_DUST)
            .whenModMissing(Petrochem.REALISTIC_MODID)
    ),
    WATER_ELECTROLYSIS = createElectrolyzing("water_electrolysis", b -> (ElectrolyzingRecipeBuilder) b
            .energy(150)
            .require(Fluids.WATER, 300)
            .output(PetrochemFluids.HYDROGEN.get(), 200)
            .output(PetrochemFluids.OXYGEN.get(), 100)
    ),
    CHLOR_ALKALI = createElectrolyzing("chlor_alkali", b -> (ElectrolyzingRecipeBuilder) b
            .energy(150)
            .require(Fluids.WATER, 500)
            .require(PetrochemItems.SALT_DUST.get())
            .output(PetrochemFluids.CHLORINE.get(), 250)
            .output(PetrochemItems.CAUSTIC_SODA)
    )






            ;


    public PetrochemElectrolyzingRecipeGen(PackOutput generator) {
        super(generator, Petrochem.MODID);
    }
}
