package io.github.hadron13.petrochem.data.recipe;

import com.google.common.base.Supplier;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.molybdenum.alloyed.common.registry.ModBlocks;
import com.molybdenum.alloyed.common.registry.ModItems;
import com.mrh0.createaddition.index.CAItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import io.github.hadron13.petrochem.Petrochem;
import io.github.hadron13.petrochem.register.PetrochemBlocks;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class PetrochemStandardRecipeGen extends BaseRecipeProvider {
    final List<GeneratedRecipe> all = new ArrayList<>();

    GeneratedRecipe STEEL_PIPE =
            create(PetrochemBlocks.STEEL_FLUID_PIPE).returns(6)
                    .unlockedBy(ModItems.STEEL_SHEET::get)
                    .viaShaped(b -> b
                            .define('I', ModItems.STEEL_INGOT.get())
                            .define('S', ModItems.STEEL_SHEET.get())
                            .pattern("SIS")
                    );

    GeneratedRecipe STEEL_PIPE_2 =
            create(PetrochemBlocks.STEEL_FLUID_PIPE).withSuffix("_vertical").returns(6)
                    .unlockedBy(ModItems.STEEL_SHEET::get)
                    .viaShaped(b -> b
                            .define('I', ModItems.STEEL_INGOT.get())
                            .define('S', ModItems.STEEL_SHEET.get())
                            .pattern("S")
                            .pattern("I")
                            .pattern("S")
                    );

    GeneratedRecipe STEEL_PUMP =
            create(PetrochemBlocks.STEEL_PUMP)
                    .unlockedBy(ModItems.STEEL_SHEET::get)
                    .viaShapeless(b -> b
                            .requires(AllBlocks.COGWHEEL.get())
                            .requires(PetrochemBlocks.STEEL_FLUID_PIPE)
                    );


    GeneratedRecipe STEEL_FLUID_TANK = create(PetrochemBlocks.STEEL_FLUID_TANK).returns(2).unlockedByTag(() -> Tags.Items.BARRELS_WOODEN)
            .viaShaped(b -> b
                    .define('S', ModItems.STEEL_SHEET)
                    .define('B', Tags.Items.BARRELS_WOODEN)
                    .pattern("S")
                    .pattern("B")
                    .pattern("S"));

    GeneratedRecipe ELECTROLYZER =
            create(PetrochemBlocks.ELECTROLYZER)
                    .unlockedBy(AllBlocks.MECHANICAL_MIXER::get)
                    .viaShaped(b -> b
                            .define('M', AllBlocks.MECHANICAL_MIXER.get())
                            .define('S', ModItems.STEEL_SHEET.get())
                            .define('R', CAItems.COPPER_ROD.get())
                            .define('C', Items.COPPER_INGOT)
                            .define('Z', AllItems.ZINC_INGOT.get())
                            .pattern(" S ")
                            .pattern("RMR")
                            .pattern("ZSC")
                    );

    GeneratedRecipe DISTILLATION_CONTROLLER =
            create(PetrochemBlocks.DISTILLATION_CONTROLLER)
                    .unlockedBy(PetrochemBlocks.STEEL_FLUID_TANK::get)
                    .viaShaped(b -> b
                            .define('B', ModItems.BRONZE_SHEET.get())
                            .define('S', ModItems.STEEL_SHEET.get())
                            .define('P', PetrochemBlocks.STEEL_FLUID_PIPE.get())
                            .define('C', ModBlocks.STEEL_CASING.get())
                            .pattern(" B ")
                            .pattern("PCP")
                            .pattern("SBS")
                    );

    GeneratedRecipe FLARESTACK =
            create(PetrochemBlocks.FLARESTACK).returns(2)
                    .unlockedBy(PetrochemBlocks.STEEL_FLUID_TANK::get)
                    .viaShaped(b -> b
                            .define('B', ModItems.BRONZE_SHEET.get())
                            .define('S', ModItems.STEEL_SHEET.get())
                            .define('P', PetrochemBlocks.STEEL_FLUID_PIPE.get())
                            .pattern(" P ")
                            .pattern("BPB")
                            .pattern("SPS")
                    );

    GeneratedRecipe DISTILLATION_OUTPUT =
            create(PetrochemBlocks.DISTILLATION_OUTPUT).returns(2)
                    .unlockedBy(PetrochemBlocks.STEEL_FLUID_TANK::get)
                    .viaShaped(b -> b
                            .define('S', ModItems.STEEL_SHEET.get())
                            .define('P', PetrochemBlocks.STEEL_FLUID_PIPE.get())
                            .pattern("PSS")
                            .pattern(" P ")
                    );


    GeneratedRecipe PUMPJACK_WELL =
            create(PetrochemBlocks.PUMPJACK_WELL)
                    .unlockedBy(ModItems.STEEL_SHEET::get)
                    .viaShaped(b -> b
                            .define('S', ModItems.STEEL_SHEET.get())
                            .define('P', PetrochemBlocks.STEEL_FLUID_PIPE.get())
                            .pattern("SPS")
                            .pattern("PPS")
                            .pattern("SPS")
                    );

    GeneratedRecipe PUMPJACK_CRANK =
            create(PetrochemBlocks.PUMPJACK_CRANK)
                    .unlockedBy(ModItems.STEEL_SHEET::get)
                    .viaShaped(b -> b
                            .define('S', ModItems.STEEL_SHEET.get())
                            .define('G', AllBlocks.ROTATION_SPEED_CONTROLLER.get())
                            .pattern("SSS")
                            .pattern("SGS")
                            .pattern("SSS")
                    );




    static class Marker {
    }

    String currentFolder = "";

    Marker enterFolder(String folder) {
        currentFolder = folder;
        return new Marker();
    }

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ResourceLocation result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike> result) {
        return create(result::get);
    }

    GeneratedRecipe createSpecial(Supplier<? extends SimpleCraftingRecipeSerializer<?>> serializer, String recipeType,
                                  String path) {
        ResourceLocation location = Petrochem.asResource(recipeType + "/" + currentFolder + "/" + path);
        return register(consumer -> {
            SpecialRecipeBuilder b = SpecialRecipeBuilder.special(serializer.get());
            b.save(consumer, location.toString());
        });
    }


    GeneratedRecipe metalCompacting(List<ItemProviderEntry<? extends ItemLike>> variants,
                                    List<Supplier<TagKey<Item>>> ingredients) {
        GeneratedRecipe result = null;
        for (int i = 0; i + 1 < variants.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = variants.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = variants.get(i + 1);
            Supplier<TagKey<Item>> currentIngredient = ingredients.get(i);
            Supplier<TagKey<Item>> nextIngredient = ingredients.get(i + 1);

            result = create(nextEntry).withSuffix("_from_compacting")
                    .unlockedBy(currentEntry::get)
                    .viaShaped(b -> b.pattern("###")
                            .pattern("###")
                            .pattern("###")
                            .define('#', currentIngredient.get()));

            result = create(currentEntry).returns(9)
                    .withSuffix("_from_decompacting")
                    .unlockedBy(nextEntry::get)
                    .viaShapeless(b -> b.requires(nextIngredient.get()));
        }
        return result;
    }

    GeneratedRecipe conversionCycle(List<ItemProviderEntry<? extends ItemLike>> cycle) {
        GeneratedRecipe result = null;
        for (int i = 0; i < cycle.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = cycle.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = cycle.get((i + 1) % cycle.size());
            result = create(nextEntry).withSuffix("_from_conversion")
                    .unlockedBy(currentEntry::get)
                    .viaShapeless(b -> b.requires(currentEntry.get()));
        }
        return result;
    }

    GeneratedRecipe clearData(ItemProviderEntry<? extends ItemLike> item) {
        return create(item).withSuffix("_clear")
                .unlockedBy(item::get)
                .viaShapeless(b -> b.requires(item.get()));
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> p_200404_1_) {
        all.forEach(c -> c.register(p_200404_1_));
        Petrochem.LOGGER.info(getName() + " registered " + all.size() + " recipe" + (all.size() == 1 ? "" : "s"));
    }

    protected GeneratedRecipe register(GeneratedRecipe recipe) {
        all.add(recipe);
        return recipe;
    }

    class GeneratedRecipeBuilder {

        private String path;
        private String suffix;
        private Supplier<? extends ItemLike> result;
        private ResourceLocation compatDatagenOutput;
        List<ICondition> recipeConditions;

        private Supplier<ItemPredicate> unlockedBy;
        private int amount;

        private GeneratedRecipeBuilder(String path) {
            this.path = path;
            this.recipeConditions = new ArrayList<>();
            this.suffix = "";
            this.amount = 1;
        }

        public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
            this(path);
            this.result = result;
        }

        public GeneratedRecipeBuilder(String path, ResourceLocation result) {
            this(path);
            this.compatDatagenOutput = result;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(item.get())
                    .build();
            return this;
        }

        GeneratedRecipeBuilder unlockedByTag(Supplier<TagKey<Item>> tag) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(tag.get())
                    .build();
            return this;
        }

        GeneratedRecipeBuilder whenModLoaded(String modid) {
            return withCondition(new ModLoadedCondition(modid));
        }

        GeneratedRecipeBuilder whenModMissing(String modid) {
            return withCondition(new NotCondition(new ModLoadedCondition(modid)));
        }

        GeneratedRecipeBuilder withCondition(ICondition condition) {
            recipeConditions.add(condition);
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        // FIXME 5.1 refactor - recipe categories as markers instead of sections?
        GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
            return register(consumer -> {
                ShapedRecipeBuilder b =
                        builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createLocation("crafting"));
            });
        }

        GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
            return register(consumer -> {
                ShapelessRecipeBuilder b =
                        builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

                b.save(result -> {
                    consumer.accept(!recipeConditions.isEmpty()
                            ? new ConditionSupportingShapelessRecipeResult(result, recipeConditions)
                            : result);
                }, createLocation("crafting"));
            });
        }

        GeneratedRecipe viaNetheriteSmithing(Supplier<? extends Item> base, Supplier<Ingredient> upgradeMaterial) {
            return register(consumer -> {
                SmithingTransformRecipeBuilder b =
                        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                Ingredient.of(base.get()), upgradeMaterial.get(), RecipeCategory.COMBAT, result.get()
                                        .asItem());
                b.unlocks("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(base.get())
                        .build()));
                b.save(consumer, createLocation("crafting"));
            });
        }

        private ResourceLocation createSimpleLocation(String recipeType) {
            return Petrochem.asResource(recipeType + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation createLocation(String recipeType) {
            return Petrochem.asResource(recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation getRegistryName() {
            return compatDatagenOutput == null ? CatnipServices.REGISTRIES.getKeyOrThrow(result.get()
                    .asItem()) : compatDatagenOutput;
        }

        GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCooking(Supplier<? extends ItemLike> item) {
            return unlockedBy(item).viaCookingIngredient(() -> Ingredient.of(item.get()));
        }

        GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingTag(Supplier<TagKey<Item>> tag) {
            return unlockedByTag(tag).viaCookingIngredient(() -> Ingredient.of(tag.get()));
        }

        GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingIngredient(Supplier<Ingredient> ingredient) {
            return new GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder(ingredient);
        }

        class GeneratedCookingRecipeBuilder {

            private Supplier<Ingredient> ingredient;
            private float exp;
            private int cookingTime;

            private final RecipeSerializer<? extends AbstractCookingRecipe> FURNACE = RecipeSerializer.SMELTING_RECIPE,
                    SMOKER = RecipeSerializer.SMOKING_RECIPE, BLAST = RecipeSerializer.BLASTING_RECIPE,
                    CAMPFIRE = RecipeSerializer.CAMPFIRE_COOKING_RECIPE;

            GeneratedCookingRecipeBuilder(Supplier<Ingredient> ingredient) {
                this.ingredient = ingredient;
                cookingTime = 200;
                exp = 0;
            }

            GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder forDuration(int duration) {
                cookingTime = duration;
                return this;
            }

            GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder rewardXP(float xp) {
                exp = xp;
                return this;
            }

            GeneratedRecipe inFurnace() {
                return inFurnace(b -> b);
            }

            GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                return create(FURNACE, builder, 1);
            }

            GeneratedRecipe inSmoker() {
                return inSmoker(b -> b);
            }

            GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                create(CAMPFIRE, builder, 3);
                return create(SMOKER, builder, .5f);
            }

            GeneratedRecipe inBlastFurnace() {
                return inBlastFurnace(b -> b);
            }

            GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                return create(BLAST, builder, .5f);
            }

            private GeneratedRecipe create(RecipeSerializer<? extends AbstractCookingRecipe> serializer,
                                           UnaryOperator<SimpleCookingRecipeBuilder> builder, float cookingTimeModifier) {
                return register(consumer -> {
                    boolean isOtherMod = compatDatagenOutput != null;

                    SimpleCookingRecipeBuilder b = builder.apply(SimpleCookingRecipeBuilder.generic(ingredient.get(),
                            RecipeCategory.MISC, isOtherMod ? Items.DIRT : result.get(), exp,
                            (int) (cookingTime * cookingTimeModifier), serializer));

                    if (unlockedBy != null)
                        b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

                    b.save(result -> {
                        consumer.accept(
                                isOtherMod ? new ModdedCookingRecipeResult(result, compatDatagenOutput, recipeConditions)
                                        : result);
                    }, createSimpleLocation(CatnipServices.REGISTRIES.getKeyOrThrow(serializer)
                            .getPath()));
                });
            }
        }
    }

//    @Override
//    public String getName() {
//        return "Petrochem's Standard Recipes";
//    }

    public PetrochemStandardRecipeGen(PackOutput output) {
        super(output, Petrochem.MODID);
    }

    private record ModdedCookingRecipeResult(FinishedRecipe wrapped, ResourceLocation outputOverride,
                                             List<ICondition> conditions) implements FinishedRecipe {
        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return wrapped.getType();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return wrapped.serializeAdvancement();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return wrapped.getAdvancementId();
        }

        @Override
        public void serializeRecipeData(JsonObject object) {
            wrapped.serializeRecipeData(object);
            object.addProperty("result", outputOverride.toString());

            JsonArray conds = new JsonArray();
            conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            object.add("conditions", conds);
        }
    }

    private record ConditionSupportingShapelessRecipeResult(FinishedRecipe wrapped, List<ICondition> conditions)
            implements FinishedRecipe {
        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return wrapped.getType();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return wrapped.serializeAdvancement();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return wrapped.getAdvancementId();
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            wrapped.serializeRecipeData(pJson);

            JsonArray conds = new JsonArray();
            conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            pJson.add("conditions", conds);
        }
    }
    
    
    public PetrochemStandardRecipeGen(PackOutput output, String defaultNamespace) {
        super(output, defaultNamespace);
    }
}
