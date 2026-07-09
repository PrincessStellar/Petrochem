package io.github.hadron13.petrochem.register;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import io.github.hadron13.petrochem.Petrochem;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

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

}
