package io.github.hadron13.petrochem.register.data;

import io.github.hadron13.petrochem.Petrochem;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class PetrochemDamageTypes {
//    public static final ResourceKey<DamageType>
//        laser = key("laserdeath");

    private static ResourceKey<DamageType> key(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, Petrochem.asResource(name));
    }

    public static void bootstrap(BootstrapContext<DamageType> ctx) {
//        new DamageTypeBuilder(laser).scaling(DamageScaling.ALWAYS).register(ctx);
    }
}
