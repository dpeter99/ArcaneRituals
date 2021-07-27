package com.dpeter99.arcanerituals.tileentities;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Quaternion;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;
import org.jetbrains.annotations.Nullable;

public class AltarTileEntityRenderer implements BlockEntityRenderer<AltarTileEntity> {

    public static final ResourceLocation BLOOD_TEXTURE = ArcaneRituals.location("fluid/blood");

    public AltarTileEntityRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        //super(rendererDispatcherIn);
    }

    float progress_base = 100;

    float itemScale = 0.45f;

    @Override
    public void render(AltarTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        progress_base -=partialTicks;
        if(progress_base <= 0)
            progress_base = 100;

        float progress = 0;



        double y = 0.86;
        double delta = 0;
        float rot = 0;

        double centerDist = (0.9375-0.5)-0.05;
        if(tileEntityIn.isWorking()){
            progress = (float) (tileEntityIn.progress_from - tileEntityIn.progress) / tileEntityIn.progress_from;
            //progress = 1- (progress_base / 100);
            y += Math.sin(progress/2*Math.PI)*0.4;
            delta = Math.sin(progress*(Math.PI/2))*centerDist;
            rot+= 360*progress;
        }

        //Draw items
        {
            RenderItem(0, tileEntityIn,new Vec3(0.0625 + delta, y, 0.9375 - delta),new Quaternion(Vector3f.YP, 90.0f+rot, true), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);

            RenderItem(3, tileEntityIn,new Vec3(0.9375 - delta, y, 0.9375 - delta),new Quaternion(Vector3f.YP, 90.0f+rot-10, true), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);

            RenderItem(2, tileEntityIn,new Vec3(0.9375 - delta, y, 0.0625 + delta),new Quaternion(Vector3f.YP, 90.0f+rot+10, true), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);

            RenderItem(1, tileEntityIn,new Vec3(0.0625 + delta, y, 0.0625 + delta),new Quaternion(Vector3f.YP, 90.0f+rot+20, true), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);
        }


        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(BLOOD_TEXTURE);

        VertexConsumer builder = bufferIn.getBuffer(RenderType.translucent());

        float fill = (tileEntityIn.tank.getFluidAmount() / (float)tileEntityIn.tank.getCapacity());
        float step = 1 / 11.0f; //0.758f
        float level = 10.8f/16 * fill;

        if(fill != 0) {
            add(builder, matrixStackIn, 0f, level, 1f, sprite.getU0(), sprite.getV1(), combinedLightIn);
            add(builder, matrixStackIn, 1f, level, 1f, sprite.getU1(), sprite.getV1(),combinedLightIn);
            add(builder, matrixStackIn, 1f, level, 0f, sprite.getU1(), sprite.getV0(),combinedLightIn);
            add(builder, matrixStackIn, 0f, level, 0f, sprite.getU0(), sprite.getV0(),combinedLightIn);
        }

        double c_delta=0;
        if(tileEntityIn.isWorking()){
            c_delta = ((1-level)+0.3);
            c_delta *= progress;
        } else if(tileEntityIn.cool_down > 0){
            c_delta = ((1-level)+0.3);
            c_delta *= (tileEntityIn.cool_down/100f);
        }

        RenderItem(4, tileEntityIn,new Vec3(0.5, level+(itemScale/4)+c_delta, 0.5),null, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);

    }

    private void add(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, int light) {
        renderer.vertex(stack.last().pose(), x, y, z)
                .color(1.0f, 0.0f, 0.0f, 1.0f)
                .uv(u, v)
                .uv2(240)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .normal(1, 0, 0)
                .endVertex();
    }

    private void RenderItem(int slot_id, AltarTileEntity tileEntityIn, Vec3 pos, @Nullable Quaternion rot, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, boolean doAutoRot) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(slot_id);

        matrixStackIn.pushPose();
        matrixStackIn.translate(pos.x(), pos.y(), pos.z());
        matrixStackIn.scale(itemScale, itemScale, itemScale);

        if(rot != null)
            matrixStackIn.mulPose(rot);

        if(!(stack.getItem() instanceof BlockItem) && doAutoRot){
            matrixStackIn.translate(0,-itemScale/2,0);
            matrixStackIn.mulPose(new Quaternion(Vector3f.XP,90.0f,true));

        }
        //TODO: Make sure the `1` at the end is correct
        BakedModel ibakedmodel = itemRenderer.getModel(stack, tileEntityIn.getLevel(),null,1);
        itemRenderer.render(stack, ItemTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);

        matrixStackIn.popPose();
    }
}
