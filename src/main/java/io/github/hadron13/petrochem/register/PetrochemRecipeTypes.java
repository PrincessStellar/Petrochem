package io.github.hadron13.petrochem.register;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.blocks.distillation_tower.DistillationControllerBlockEntity;
import io.github.hadron13.petrochem.blocks.distillation_tower.DistillingRecipe;
import io.github.hadron13.petrochem.blocks.electrolyzer.ElectrolyzingRecipe;
import io.github.hadron13.petrochem.blocks.pumpjack.PumpjackRecipe;
import io.github.hadron13.petrochem.blocks.pumpjack.PumpjackRecipeParams;
import io.github.hadron13.petrochem.blocks.pumpjack.PumpjackWellBlockEntity;
import io.github.hadron13.petrochem.blocks.small_engine.EngineFuelRecipe;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public enum PetrochemRecipeTypes implements IRecipeTypeInfo, StringRepresentable {

    ELECTROLYZING(() -> new ElectrolyzingRecipe.Serializer<>(ElectrolyzingRecipe::new)),
    PUMPJACK(PumpjackRecipe::new),
//    LASER_DRILLING(LaserDrillingRecipe::new),
//    DIPPING(DippingRecipe::new),
//    REACTING(ReactingRecipe::new),
    DISTILLING(() -> new DistillingRecipe.Serializer<>(DistillingRecipe::new)),
    DIESEL_ENGINE_FUEL(EngineFuelRecipe::diesel),
    GASOLINE_ENGINE_FUEL(EngineFuelRecipe::gasoline),
    SHIP_ENGINE_FUEL(EngineFuelRecipe::ship)
    ;

    public final ResourceLocation id;
    public final Supplier<RecipeSerializer<?>> serializerSupplier;
    private final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> serializerObject;
    @Nullable
    private final DeferredHolder<RecipeType<?>, RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;


    PetrochemRecipeTypes(StandardProcessingRecipe.Factory<?> processingFactory) {
        this(() -> new StandardProcessingRecipe.Serializer<>(processingFactory));
    }

    PetrochemRecipeTypes(ProcessingRecipe.Factory<PumpjackRecipeParams, PumpjackRecipe> pumpjackFactory) {
        this(() -> new PumpjackRecipe.Serializer<>(pumpjackFactory));
    }


    PetrochemRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = Petrochem.asResource(name);
        this.serializerSupplier = serializerSupplier;
        serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        typeObject = Registers.TYPE_REGISTER.register(name, () -> RecipeType.simple(id));
        type = typeObject;
    }

    public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
        String stringId = id.toString();
        return new RecipeType<T>() {
            @Override
            public String toString() {
                return stringId;
            }
        };
    }



    public static void register(IEventBus modEventBus) {
        ShapedRecipePattern.setCraftingSize(9, 9);
        Registers.SERIALIZER_REGISTER.register(modEventBus);
        Registers.TYPE_REGISTER.register(modEventBus);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerObject.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> getType() {
        return (RecipeType<R>) type.get();
    }


    public <I extends RecipeInput, R extends Recipe<I>> Optional<RecipeHolder<R>> find(I inv, Level world) {
        return world.getRecipeManager()
                .getRecipeFor(getType(), inv, world);
    }

    public Optional<PumpjackRecipe> find(PumpjackWellBlockEntity blockEntity, Level world){
        if(world.isClientSide())
            return Optional.empty();


        List<RecipeHolder<PumpjackRecipe>> allRecipes = world.getRecipeManager().getAllRecipesFor(PetrochemRecipeTypes.PUMPJACK.getType());

        Stream<PumpjackRecipe> matchingRecipes =
                allRecipes.stream().filter(recipe -> PumpjackRecipe.match(blockEntity, recipe.value()) ).map(RecipeHolder::value);

        return matchingRecipes.findAny();
    }

    public Optional<DistillingRecipe> find(DistillationControllerBlockEntity blockEntity, Level world){
        if(world.isClientSide())
            return Optional.empty();

        List<RecipeHolder<DistillingRecipe>> allRecipes = world.getRecipeManager().getAllRecipesFor(PetrochemRecipeTypes.DISTILLING.getType());

        Stream<DistillingRecipe> matchingRecipes =
                allRecipes.stream().filter(recipe -> DistillingRecipe.match(blockEntity, recipe.value()) ).map(RecipeHolder::value);

        return matchingRecipes.findAny();
    }



    @Override
    public String getSerializedName() {
        return id.toString();
    }

    private static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Petrochem.MODID);
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, Petrochem.MODID);
    }

}