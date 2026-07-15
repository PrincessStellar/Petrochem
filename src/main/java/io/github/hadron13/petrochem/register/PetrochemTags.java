package io.github.hadron13.petrochem.register;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.recipe.Mods;
import io.github.hadron13.petrochem.Petrochem;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.simibubi.create.AllTags.NameSpace.COMMON;
import static com.simibubi.create.AllTags.NameSpace.MOD;

public class PetrochemTags {


    public enum NameSpace {
        PETROCHEM(Petrochem.MODID),
        CREATE(Create.ID),
        COMMON("c");

        public final String id;

        NameSpace(String id) {
            this.id = id;
        }

        public ResourceLocation id(String path) {
            return ResourceLocation.fromNamespaceAndPath(this.id, path);
        }

        public ResourceLocation id(Enum<?> entry, @Nullable String pathOverride) {
            return this.id(pathOverride != null ? pathOverride : Lang.asId(entry.name()));
        }
    }

    public enum FluidTags {
        CRUDE_OIL(COMMON),
        GASOLINE(COMMON),
        DIESEL(COMMON);

        public final TagKey<Fluid> tag;

        FluidTags() {
            this(MOD);
        }

        FluidTags(AllTags.NameSpace namespace) {
            this(namespace, null);
        }

        FluidTags(AllTags.NameSpace namespace, @Nullable String pathOverride) {
            this.tag = TagKey.create(Registries.FLUID, namespace.id(this, pathOverride));
        }

        public boolean matches(FluidState state) {
            return state.is(tag);
        }
    }



    public enum Metals {

        BRONZE(false);


        /**
         * The name of this metal, for use in IDs. Note that a metal's name may be different depending on mod context.
         *
         * @see #getName(Mods)
         */
        public final String name;

        /**
         * True is this metal generates naturally. If false, the following tags are nonsense:
         * <ul>
         *     <li>{@link #ores}</li>
         *     <li>{@link #rawOres}</li>
         *     <li>{@link #rawStorageBlocks}</li>
         * </ul>
         */
        public final boolean isNatural;

        public final Metals.ItemLikeTag ores;
        public final TagKey<Item> rawOres;
        public final Metals.ItemLikeTag rawStorageBlocks;
        public final TagKey<Item> ingots;
        public final Metals.ItemLikeTag storageBlocks;
        public final TagKey<Item> nuggets;
        public final TagKey<Item> plates;



        Metals(boolean natural) {
            this.name = Lang.asId(name());
            this.isNatural = natural;

            this.ores = new Metals.ItemLikeTag("ores/" + this.name);
            this.rawOres = itemTag("raw_materials/" + this.name);
            this.rawStorageBlocks = new Metals.ItemLikeTag("storage_blocks/raw_" + this.name);
            this.ingots = itemTag("ingots/" + this.name);
            this.storageBlocks = new Metals.ItemLikeTag("storage_blocks/" + this.name);
            this.nuggets = itemTag("nuggets/" + this.name);
            this.plates = itemTag("plates/" + this.name);
        }

        public String getName(Mods mod) {
            return name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        private static TagKey<Item> itemTag(String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
        }

        private static TagKey<Block> blockTag(String path) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", path));
        }

        public record ItemLikeTag(TagKey<Item> items, TagKey<Block> blocks) {
            private ItemLikeTag(String path) {
                this(itemTag(path), blockTag(path));
            }
        }
    }

}
