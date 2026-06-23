package io.github.hadron13.petrochem;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import io.github.hadron13.petrochem.config.PetrochemConfig;
import io.github.hadron13.petrochem.data.PetrochemDatagen;
import io.github.hadron13.petrochem.register.*;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Petrochem.MODID)
public class Petrochem {

    public static final String MODID = "petrochem";
    public static final String REALISTIC_MODID = "petrochem_expert";
    public static boolean expertEnabled = false;

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static IEventBus modEventBus;
    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    static {
        REGISTRATE
                .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
                .setTooltipModifierFactory((item) -> (new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)).andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }


    public Petrochem(IEventBus modEventBus, ModContainer modContainer) {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();


        REGISTRATE.registerEventListeners(modEventBus);

        PetrochemSoundEvents.prepare();
        PetrochemCreativeModeTabs.register(modEventBus);
        PetrochemBlocks.register();
        PetrochemItems.register();
        PetrochemBlockEntities.register();
        PetrochemFluids.register();
        PetrochemPartialModels.init();
        PetrochemRecipeTypes.register(modEventBus);
        PetrochemConfig.register(modLoadingContext, modContainer);

        modEventBus.addListener(PetrochemSoundEvents::register);
        modEventBus.addListener(EventPriority.HIGHEST, PetrochemDatagen::gatherDataHighPriority);
        modEventBus.addListener(EventPriority.LOWEST, PetrochemDatagen::gatherData);

//        NeoForge.EVENT_BUS.register(this);

        expertEnabled = ModList.get().isLoaded(REALISTIC_MODID);

    }




    public static void clientInit(final FMLClientSetupEvent event){
    }

    public static CreateRegistrate registrate(){
        return REGISTRATE;
    }


    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
