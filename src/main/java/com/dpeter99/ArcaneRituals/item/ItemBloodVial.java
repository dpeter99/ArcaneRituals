package com.dpeter99.ArcaneRituals.item;

import com.dpeter99.ArcaneRituals.arcane_fluid.Blood;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemBloodVial extends Item {

    public ItemBloodVial() {
        super(new Properties().maxStackSize(16));
    }

    public static String getOwnerName(CompoundNBT nbt){
        if(nbt != null && nbt.contains("Owner")){
            CompoundNBT owner = nbt.getCompound("Owner");
            if(owner.contains("UUIDMost")){
                UUID uuid = owner.getUniqueId("UUID");
                String name = UsernameCache.getLastKnownUsername(uuid);
                return name;
            }
            else if(owner.contains("mob")){
                String uuid = owner.getString("mob");
                //uuid = new TranslationTextComponent(uuid).toString();
                String name = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryCreate(uuid)).getName().getString();
                //String name = UsernameCache.getLastKnownUsername(uuid);
                return name;
            }
        }
        return "Nobody";
    }

    public static Blood GetBlood(ItemStack stack){
        CompoundNBT nbt = stack.getTag();
        String name = "";
        if(nbt != null && nbt.contains("Owner")){
            CompoundNBT owner = nbt.getCompound("Owner");
            if(owner.contains("UUIDMost")){
                UUID uuid = owner.getUniqueId("UUID");
                name = UsernameCache.getLastKnownUsername(uuid);

            }
            else if(owner.contains("mob")){
                String uuid = owner.getString("mob");
                name = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryCreate(uuid)).getName().getString();
            }
        }

        return new Blood(300,name);
    }

    public static void SetOwner(ItemStack stack, UUID player){
        CompoundNBT nbt = stack.getOrCreateTag();
        CompoundNBT owner_nbt = new CompoundNBT();
        owner_nbt.putUniqueId("UUID",player);
        nbt.put("Owner",owner_nbt);
    }

    public static void SetOwner(ItemStack stack, String mob){
        CompoundNBT nbt = stack.getOrCreateTag();
        CompoundNBT owner_nbt = new CompoundNBT();
        owner_nbt.putString("mob",mob);
        nbt.put("Owner",owner_nbt);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     *
     * @param stack
     * @param worldIn
     * @param tooltip
     * @param flagIn
     */
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //super.addInformation(stack, worldIn, tooltip, flagIn);
        String owner = "From: " + getOwnerName(stack.getTag());
        tooltip.add(new StringTextComponent(owner).applyTextStyle(TextFormatting.DARK_RED));


    }
}
