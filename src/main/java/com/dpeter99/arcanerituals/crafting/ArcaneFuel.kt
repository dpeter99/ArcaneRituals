package com.dpeter99.arcanerituals.crafting

import com.dpeter99.arcanerituals.ArcaneRituals
import com.dpeter99.arcanerituals.crafting.altarcrafting.AltarRecipe
import com.dpeter99.arcanerituals.registry.ARRegistry
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.JsonToNBT
import net.minecraft.network.PacketBuffer
import net.minecraft.util.JSONUtils
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.ForgeRegistries

class ArcaneFuel(
    val fuelFluid: FluidStack,
    val fuelItem: ItemStack,
    val amount: Int
) {



    constructor(fuelFluid: FluidStack, amount: Int) : this(fuelFluid, ItemStack.EMPTY, amount) {

    }

    constructor(fuelItem: ItemStack, amount: Int) : this(FluidStack.EMPTY, fuelItem, amount) {

    }

    fun toNetwork(buffer: PacketBuffer, recipe: AltarRecipe) {
        buffer.writeInt(amount);
        fuelFluid.writeToPacket(buffer);
        buffer.writeItemStack(fuelItem,false)
    }

    companion object {

        private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

        fun fromJson(recipeId: ResourceLocation, json: JsonObject): ArcaneFuel {

            var fuel_stack_fluid: FluidStack = FluidStack.EMPTY
            var fuel_stack_item: ItemStack = ItemStack.EMPTY

            val fuelObject = json.getAsJsonObject("fuel")
            val fuel_amount = JSONUtils.getAsInt(fuelObject, "amount");
            val fuel_type = JSONUtils.getAsString(fuelObject, "type", "###");
            if (fuel_type.equals("###")) {
                ArcaneRituals.LOGGER.error("Recipe: $recipeId doesn't have a fuel:type");
            }

            val f = ForgeRegistries.FLUIDS.getValue(ResourceLocation.tryParse(fuel_type))
            f?.let {

                val element = json["nbt"]
                val nbt =
                    if (element.isJsonObject)
                        JsonToNBT.parseTag(GSON.toJson(element))
                    else JsonToNBT.parseTag(JSONUtils.convertToString(element, "nbt"))


                fuel_stack_fluid = FluidStack(f, fuel_amount);
                fuel_stack_fluid.tag = nbt;
                /*
                if (f.isSame(ARRegistry.BLOOD.get())) {
                    val b = JSONUtils.getAsString(fuelObject, "owner")

                }
                */

            }

            if(fuel_stack_fluid == FluidStack.EMPTY) {
                val i = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(fuel_type))
                i?.let {
                    fuel_stack_item = ItemStack(i, fuel_amount);
                }
            }

            if (fuel_stack_fluid != FluidStack.EMPTY) {
                return ArcaneFuel(fuel_stack_fluid, fuel_amount)
            } else {
                return ArcaneFuel(fuel_stack_item, fuel_amount)
            }

        }

        fun fromNetwork(recipeId: ResourceLocation, buffer: PacketBuffer): ArcaneFuel {
            val amount = buffer.readInt();
            val fluid = FluidStack.readFromPacket(buffer);
            val item = buffer.readItem();

            return ArcaneFuel(fluid,item, amount);
        }


    }
}