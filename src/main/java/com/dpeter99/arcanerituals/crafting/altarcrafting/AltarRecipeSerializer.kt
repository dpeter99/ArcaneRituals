package com.dpeter99.arcanerituals.crafting.altarcrafting

import com.dpeter99.arcanerituals.crafting.ArcaneFuel
import com.google.gson.JsonObject
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.ForgeRegistryEntry
import java.util.*

//@file:JvmName("AltarRecipeSerializer")

class AltarRecipeSerializer : @JvmName("ForgeRegistryEntry<IRecipeSerializer<?>>") ForgeRegistryEntry<RecipeSerializer<*>>(), RecipeSerializer<AltarRecipe> {

    override fun fromJson(recipeId: ResourceLocation, json: JsonObject): AltarRecipe {
        val group = GsonHelper.getAsString(json, "group", "")


        var fuel = ArcaneFuel.fromJson(recipeId, json)


        val altar_type = GsonHelper.getAsString(json, "altar_type", "")
        val work = GsonHelper.getAsInt(json, "work_amount", 0)
        val ingredientsArray = GsonHelper.getAsJsonObject(json, "ingredients")

        val ingredient: MutableList<Ingredient> = ArrayList()
        ingredient.add(Ingredient.fromJson(ingredientsArray.getAsJsonObject("0")))
        ingredient.add(Ingredient.fromJson(ingredientsArray.getAsJsonObject("1")))
        ingredient.add(Ingredient.fromJson(ingredientsArray.getAsJsonObject("2")))
        ingredient.add(Ingredient.fromJson(ingredientsArray.getAsJsonObject("3")))

        val center = Ingredient.fromJson(ingredientsArray.getAsJsonObject("center"))

        val result = json.getAsJsonObject("result");
        val s1 = GsonHelper.getAsString(result, "item")




        val resourcelocation = ResourceLocation(s1)
        val itemstack = ItemStack(Optional.ofNullable(ForgeRegistries.ITEMS.getValue(resourcelocation)).orElseThrow {
            IllegalStateException(
                "Item: $s1 does not exist"
            )
        })

        return AltarRecipe(recipeId, group, ingredient, center, fuel, itemstack, altar_type, work)
    }

    override fun fromNetwork(recipeId: ResourceLocation, buffer: FriendlyByteBuf): AltarRecipe {
        val group = buffer.readUtf(32767)

        val fuel = ArcaneFuel.fromNetwork(recipeId, buffer);

        val altar_type = buffer.readUtf()
        val work = buffer.readVarInt()
        val itemstack = buffer.readItem()
        val i = buffer.readVarInt()
        val ingredients = NonNullList.withSize(i, Ingredient.EMPTY)
        for (j in ingredients.indices) {
            ingredients[j] = Ingredient.fromNetwork(buffer)
        }
        val center = Ingredient.fromNetwork(buffer)
        return AltarRecipe(recipeId, group, ingredients, center, fuel, itemstack, altar_type, work)
    }

    override fun toNetwork(buffer: FriendlyByteBuf, recipe: AltarRecipe) {
        buffer.writeUtf(recipe.group)

        recipe.fuel.toNetwork(buffer,recipe);

        buffer.writeUtf(recipe.altar_type)
        buffer.writeVarInt(recipe.work_amount)
        buffer.writeItemStack(recipe.result,false)
        buffer.writeVarInt(recipe.ingredients.size)
        for (ingredient in recipe.ingredients) {
            ingredient.toNetwork(buffer)
        }
        recipe.center.toNetwork(buffer)
    }
}