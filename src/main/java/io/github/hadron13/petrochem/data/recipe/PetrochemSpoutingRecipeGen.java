package io.github.hadron13.petrochem.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.CompactingRecipeGen;
import com.simibubi.create.api.data.recipe.FillingRecipeGen;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import io.github.hadron13.petrochem.register.PetrochemItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

import java.util.concurrent.CompletableFuture;

public class PetrochemSpoutingRecipeGen extends FillingRecipeGen {

    GeneratedRecipe TIN = create("tin", b -> b.require(PotionFluidHandler.potionIngredient(Potions.THICK, 25))
            .require(AllItems.ZINC_NUGGET.get())
            .output(PetrochemItems.TIN_NUGGET));



    public PetrochemSpoutingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Petrochem.MODID);
    }
}
