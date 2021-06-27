package com.dpeter99.arcanerituals.tileentities;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import org.jetbrains.annotations.Nullable;

public class AltarTileEntityRenderer extends TileEntityRenderer<AltarTileEntity> {

    public static final ResourceLocation BLOOD_TEXTURE = ArcaneRituals.location("fluid/blood");

    public AltarTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    float progress_base = 100;

    float itemScale = 0.45f;

    @Override
    public void render(AltarTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

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
            RenderItem(0, tileEntityIn,new Vector3d(0.0625 + delta, y, 0.9375 - delta),new Quaternion(Vector3f.YP, 90.0f+rot, true), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);

            RenderItem(3, tileEntityIn,new Vector3d(0.9375 - delta, y, 0.9375 - delta),new Quaternion(Vector3f.YP, 90.0f+rot-10, true), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);

            RenderItem(2, tileEntityIn,new Vector3d(0.9375 - delta, y, 0.0625 + delta),new Quaternion(Vector3f.YP, 90.0f+rot+10, true), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);

            RenderItem(1, tileEntityIn,new Vector3d(0.0625 + delta, y, 0.0625 + delta),new Quaternion(Vector3f.YP, 90.0f+rot+20, true), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);
        }


        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(BLOOD_TEXTURE);

        IVertexBuilder builder = bufferIn.getBuffer(RenderType.translucent());

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

        RenderItem(4, tileEntityIn,new Vector3d(0.5, level+(itemScale/4)+c_delta, 0.5),null, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, true);

    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v, int light) {
        renderer.vertex(stack.last().pose(), x, y, z)
                .color(1.0f, 0.0f, 0.0f, 1.0f)
                .uv(u, v)
                .uv2(240)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .normal(1, 0, 0)
                .endVertex();
    }

    private void RenderItem(int slot_id, AltarTileEntity tileEntityIn, Vector3d pos, @Nullable Quaternion rot, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, boolean doAutoRot) {

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
        IBakedModel ibakedmodel = itemRenderer.getModel(stack, tileEntityIn.getLevel(), null);
        itemRenderer.render(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);

        matrixStackIn.popPose();
    }
}
