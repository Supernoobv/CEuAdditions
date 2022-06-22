package ceuadditions.common.recipe;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.sound.GTSounds;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.ceuadditions.recipe.CEuARecipeMaps")
public class CEuARecipeMaps {
    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> SCRUBBING_RECIPES = new RecipeMap<>("scrubber",
            1, 0, 0, 3,0, 0, 1, 1, new SimpleRecipeBuilder(), true)
            .setSound(GTSounds.TURBINE)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressWidget.MoveType.HORIZONTAL);

    public static final RecipeMap<SimpleRecipeBuilder> LARGE_SCRUBBING_RECIPES = new RecipeMap<>("scrubber",
            1, 0, 0, 3,1, 1, 1, 1, new SimpleRecipeBuilder(), true)
            .setSound(GTSounds.TURBINE)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressWidget.MoveType.HORIZONTAL);
}

