package com.dpeter99.arcanerituals.crafting.altarcrafting

import com.dpeter99.arcanerituals.ArcaneRituals
import com.dpeter99.arcanerituals.crafting.ArcaneFuel
import net.minecraftforge.registries.ForgeRegistryEntry
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.util.ResourceLocation
import com.google.gson.JsonObject
import net.minecraft.util.JSONUtils
import net.minecraftforge.registries.ForgeRegistries
import com.dpeter99.arcanerituals.registry.ARRegistry
import net.minecraft.item.crafting.Ingredient
import net.minecraft.item.ItemStack
import java.lang.IllegalStateException
import net.minecraft.network.PacketBuffer
import net.minecraft.util.NonNullList
import net.minecraftforge.fluids.FluidStack
import java.util.*

//@file:JvmName("AltarRecipeSerializer")

class AltarRecipeSerializer : @JvmName("ForgeRegistryEntry<IRecipeSerializer<?>>") ForgeRegistryEntry<IRecipeSerializer<*>>(), IRecipeSerializer<AltarRecipe> {

    override fun fromJson(recipeId: ResourceLocation, json: JsonObject): AltarRecipe {
        val group = JSONUtils.getAsString(json, "group", "")


        var fuel = ArcaneFuel.fromJson(recipeId, json)


        val altar_type = JSONUtils.getAsString(json, "altar_type", "")
        val work = JSONUtils.getAsInt(json, "work_amount", 0)
        val ingredientsArray = JSONUtils.getAsJsonObject(json, "ingredients")

        val ingredient: MutableList<Ingredient> = ArrayList()
        ingredient.add(Ingredient.fromJson(ingredientsArray.getAsJsonObject("0")))
        ingredient.add(Ingredient.fromJson(ingredientsArray.getAsJsonObject("1")))
        ingredient.add(Ingredient.fromJson(ingredientsArray.getAsJsonObject("2")))
        ingredient.add(Ingredient.fromJson(ingredientsArray.getAsJsonObject("3")))

        val center = Ingredient.fromJson(ingredientsArray.getAsJsonObject("center"))

        val result = json.getAsJsonObject("result");
        val s1 = JSONUtils.getAsString(result, "item")




        val resourcelocation = ResourceLocation(s1)
        val itemstack = ItemStack(Optional.ofNullable(ForgeRegistries.ITEMS.getValue(resourcelocation)).orElseThrow {
            IllegalStateException(
                "Item: $s1 does not exist"
            )
        })

        return AltarRecipe(recipeId, group, ingredient, center, fuel, itemstack, altar_type, work)
    }

    override fun fromNetwork(recipeId: ResourceLocation, buffer: PacketBuffer): AltarRecipe {
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

    override fun toNetwork(buffer: PacketBuffer, recipe: AltarRecipe) {
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