package com.dpeter99.bloodylib.datagen.util;

import net.minecraft.data.IFinishedRecipe;

import java.util.function.Consumer;

import com.dpeter99.bloodylib.datagen.util.IBloodyRecipeProvider;

public abstract class BloodyRecipeProvider implements IBloodyRecipeProvider {

  protected Consumer<IFinishedRecipe> consumer;

  public BloodyRecipeProvider(Consumer<IFinishedRecipe> consumer) {
    this.consumer = consumer;
    init();
  }

}