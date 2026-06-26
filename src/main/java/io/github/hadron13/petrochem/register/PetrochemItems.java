package io.github.hadron13.petrochem.register;

import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.datamaps.BlazeBurnerFuel;
import com.simibubi.create.api.registry.CreateDataMaps;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import io.github.hadron13.petrochem.Petrochem;
import net.minecraft.world.item.Item;

public class PetrochemItems {
    private static final CreateRegistrate REGISTRATE = Petrochem.registrate().setCreativeTab(PetrochemCreativeModeTabs.INGREDIENTS);

    public static void register() {}
    public static final ItemEntry<Item>
        SULFUR_DUST = ingredient("sulfur_dust"),
        SALT_DUST = ingredient("salt_dust"),
        CAUSTIC_SODA = ingredient("caustic_soda");

    public static final ItemEntry<Item> PET_COKE = REGISTRATE.item("petroleum_coke", Item::new)
            .burnTime(3200)
            .register();

    public static final ItemEntry<Item> BLAZE_CANDY = REGISTRATE.item("blaze_candy", Item::new)
            .dataMap(CreateDataMaps.SUPERHEATED_BLAZE_BURNER_FUELS, new BlazeBurnerFuel(4000))
            .burnTime(8000)
            .register();

    static{
        PetrochemCreativeModeTabs.expert_item_ids.add("sulfur_dust");
    }


    private static ItemEntry<Item> ingredient(String name) {
        return REGISTRATE.item(name, Item::new)
                .register();
    }

}
