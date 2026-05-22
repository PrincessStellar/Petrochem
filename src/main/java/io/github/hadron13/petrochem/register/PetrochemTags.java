package io.github.hadron13.petrochem.register;

import io.github.hadron13.petrochem.Petrochem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class PetrochemTags {


    public static TagKey<Item> EXPERT_ONLY_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(Petrochem.MODID, "expert_item"));
    public static TagKey<Fluid> EXPERT_ONLY_FLUID = TagKey.create(Registries.FLUID, new ResourceLocation(Petrochem.MODID, "expert_fluid"));
    public static TagKey<Block> EXPERT_ONLY_BLOCK = TagKey.create(Registries.BLOCK, new ResourceLocation(Petrochem.MODID, "expert_fluid"));

    public static TagKey<Item> BASE_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(Petrochem.MODID, "base_item"));
    public static TagKey<Fluid> BASE_FLUID = TagKey.create(Registries.FLUID, new ResourceLocation(Petrochem.MODID, "base_fluid"));
    public static TagKey<Block> BASE_BLOCK = TagKey.create(Registries.BLOCK, new ResourceLocation(Petrochem.MODID, "base_fluid"));

}
