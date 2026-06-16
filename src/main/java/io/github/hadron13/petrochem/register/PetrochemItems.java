package io.github.hadron13.petrochem.register;

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



    private static ItemEntry<Item> ingredient(String name) {
        return REGISTRATE.item(name, Item::new)
                .register();
    }

}
