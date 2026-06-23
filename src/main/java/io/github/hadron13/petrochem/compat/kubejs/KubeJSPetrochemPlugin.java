package io.github.hadron13.petrochem.compat.kubejs;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeFactoryRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import io.github.hadron13.petrochem.compat.kubejs.schemas.ProcessingRecipeSchema;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;

import java.util.HashMap;
import java.util.Map;

public class KubeJSPetrochemPlugin implements KubeJSPlugin {

    private static final Map<PetrochemRecipeTypes, RecipeSchema> recipeSchemas = new HashMap<>();

    @Override
    public void registerRecipeFactories(RecipeFactoryRegistry registry) {
        registry.register(ProcessingRecipeSchema.ELECTROLYZING_FACTORY);
        registry.register(ProcessingRecipeSchema.PUMPJACK_FACTORY);
        registry.register(ProcessingRecipeSchema.DISTIL_FACTORY);
    }
    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(PetrochemRecipeTypes.ELECTROLYZING.id, ProcessingRecipeSchema.ELECTROLYZING_SCHEMA);
        registry.register(PetrochemRecipeTypes.PUMPJACK.id, ProcessingRecipeSchema.PUMPJACK_SCHEMA);
        registry.register(PetrochemRecipeTypes.DISTILLING.id, ProcessingRecipeSchema.DISTILLATION_SCHEMA);
    }
}