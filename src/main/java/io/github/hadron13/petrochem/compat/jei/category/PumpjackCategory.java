package io.github.hadron13.petrochem.compat.jei.category;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import io.github.hadron13.petrochem.blocks.pumpjack.PumpjackRecipe;
import io.github.hadron13.petrochem.compat.jei.category.animations.AnimatedPumpjackWell;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.fluids.FluidStack;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Predicate;

public class PumpjackCategory extends CreateRecipeCategory<PumpjackRecipe>{
    public final AnimatedPumpjackWell well = new AnimatedPumpjackWell();

    public static final int xcenter = 177/2;
    public static final int ycenter = 65/2;

    public PumpjackCategory(CreateRecipeCategory.Info<PumpjackRecipe> info){super(info);}

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PumpjackRecipe recipe, IFocusGroup focuses) {
        FluidStack fluidResult = recipe.getFluidResults().get(0);
        addFluidSlot(builder, xcenter + 40, ycenter + 5, fluidResult);

    }

    public static String capitalize(final String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        final int strLen = str.length();
        final int[] newCodePoints = new int[strLen];
        int outOffset = 0;

        boolean capitalizeNext = true;
        for (int index = 0; index < strLen;) {
            final int codePoint = str.codePointAt(index);

             if(codePoint == ' '){
                 capitalizeNext = true;
                 newCodePoints[outOffset++] = codePoint;
                 index += Character.charCount(codePoint);
             } else if (capitalizeNext) {
                final int titleCaseCodePoint = Character.toTitleCase(codePoint);
                newCodePoints[outOffset++] = titleCaseCodePoint;
                index += Character.charCount(titleCaseCodePoint);
                capitalizeNext = false;
            } else {
                newCodePoints[outOffset++] = codePoint;
                index += Character.charCount(codePoint);
            }
        }
        return new String(newCodePoints, 0, outOffset);
    }

    @Override
    public void draw(PumpjackRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {


        Font font = Minecraft.getInstance().font;

        Component biome_name;
        if(recipe.biome_tag == null){
            biome_name = Component.translatable("biome." + recipe.biome.location().toLanguageKey());
        }else{
            String name = recipe.biome_tag.location().getPath();
            if(name.startsWith("is_")){
                name = name.substring(3);
            }

            name = name.replace('_', ' ');
            biome_name = Component.literal(capitalize(name));

            graphics.renderFakeItem(new ItemStack(Items.NAME_TAG), 5, 5);
        }

        int width = font.width(biome_name);

        graphics.drawString(Minecraft.getInstance().font, biome_name, xcenter - width/2, ycenter-10, 0xFFFFFF);

        AllGuiTextures.JEI_ARROW.render(graphics, xcenter-20, ycenter+10);
        well.draw(graphics, xcenter - 60, ycenter+22);

    }

    @Override
    protected List<Component> getTooltipStrings(PumpjackRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {

        if(recipe.biome_tag == null)
            return List.of();

        if(mouseX < xcenter - 40 || mouseX > xcenter + 40 || mouseY < ycenter - 30 || mouseY > ycenter + 10)
            return List.of();

        return List.of(Component.literal(recipe.biome_tag.location().toString()));
    }
}
