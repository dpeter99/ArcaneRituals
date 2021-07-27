package com.dpeter99.bloodylib.datagen.util;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import com.dpeter99.bloodylib.datagen.util.IBloodyRecipeProvider;

public abstract class BloodyRecipeProvider implements IBloodyRecipeProvider {

  protected Consumer<FinishedRecipe> consumer;

  public BloodyRecipeProvider(Consumer<FinishedRecipe> consumer) {
    this.consumer = consumer;
    init();
  }

}