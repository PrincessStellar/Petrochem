package io.github.hadron13.petrochem.data;

import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.register.data.PetrochemDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PetrochemGeneratedEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, PetrochemDamageTypes::bootstrap);

    public PetrochemGeneratedEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Petrochem.MODID));
    }

    @Override
    public String getName() {
        return "Gearbox's Generated Registry Entries";
    }
}
