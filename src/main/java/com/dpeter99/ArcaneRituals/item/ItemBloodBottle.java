package com.dpeter99.ArcaneRituals.item;

import com.dpeter99.ArcaneRituals.structure.BloodAltar;
import com.mojang.brigadier.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;
import net.minecraftforge.server.command.TextComponentHelper;

public class ItemBloodBottle extends Item {

    public ItemBloodBottle() {
        super(new Item.Properties());
        setRegistryName("blood_bottle");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BloodAltar a = new BloodAltar();
        if(a.detectStructure(context.getWorld(),context.getPos())) {
            context.getPlayer().sendStatusMessage(TextComponentUtils.toTextComponent(new Message() {
                @Override
                public String getString() {
                    return "XD";
                }
            }), true);
            a.createStructure(context.getWorld(),context.getPos());
        }
        return super.onItemUse(context);
    }


}