package io.github.hadron13.petrochem.ponder;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.hadron13.petrochem.ponder.scenes.fluids.DistillationScenes;
import io.github.hadron13.petrochem.ponder.scenes.fluids.PumpjackScenes;
import io.github.hadron13.petrochem.register.PetrochemBlocks;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.FLUIDS;

public class PetrochemPonderScenes {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(PetrochemBlocks.PUMPJACK_WELL, PetrochemBlocks.PUMPJACK_CRANK, PetrochemBlocks.PUMPJACK_ARM)
                .addStoryBoard("pumpjack", PumpjackScenes::pumpjack, FLUIDS);

        HELPER.forComponents(PetrochemBlocks.DISTILLATION_CONTROLLER, PetrochemBlocks.DISTILLATION_OUTPUT, PetrochemBlocks.STEEL_FLUID_TANK)
                .addStoryBoard("distillation", DistillationScenes::distillation_tower, FLUIDS);
;
    }

}
