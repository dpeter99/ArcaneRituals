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
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class AltarTileEntityRenderer extends TileEntityRenderer<AltarTileEntity> {

    public static final ResourceLocation BLOOD_TEXTURE = ArcaneRituals.location("fluid/blood");

    public AltarTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(AltarTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        float ItmeScale = 0.25f;

        matrixStackIn.pushPose();
        matrixStackIn.translate(0.225, 1.23, 0.68);
        matrixStackIn.scale(ItmeScale,ItmeScale,ItmeScale);
        matrixStackIn.mulPose(new Quaternion(Vector3f.YP,90.0f, true));
        RenderItem(0, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0.225, 1.23, 0.33);
        matrixStackIn.scale(ItmeScale,ItmeScale,ItmeScale);
        matrixStackIn.mulPose(new Quaternion(Vector3f.YP,90.0f, true));
        RenderItem(3, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0.78, 1.23, 0.33);
        matrixStackIn.scale(ItmeScale,ItmeScale,ItmeScale);
        matrixStackIn.mulPose(new Quaternion(Vector3f.YP,90.0f, true));
        RenderItem(2, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0.78, 1.23, 0.68);
        matrixStackIn.scale(ItmeScale,ItmeScale,ItmeScale);
        matrixStackIn.mulPose(new Quaternion(Vector3f.YP,90.0f, true));
        RenderItem(1, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();


        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(BLOOD_TEXTURE);
        IVertexBuilder builder = bufferIn.getBuffer(RenderType.solid());

        float fill = tileEntityIn.tank.getFluidAmount() / (float)tileEntityIn.tank.getCapacity();
        float step = 1 / 16.0f; //0.758f
        float level = ((1 - (step * 4.3f) - (step)) * fill) + (step * 1.5f);

        if(fill != 0) {
            add(builder, matrixStackIn, 0f, level, 1f, sprite.getU0(), sprite.getV1());
            add(builder, matrixStackIn, 1f, level, 1f, sprite.getU1(), sprite.getV1());
            add(builder, matrixStackIn, 1f, level, 0f, sprite.getU1(), sprite.getV0());
            add(builder, matrixStackIn, 0f, level, 0f, sprite.getU0(), sprite.getV0());
        }

    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.vertex(stack.last().pose(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                //.uv(u, v)
                //.overlayCoords(0, 240)
                //.normal(1, 0, 0)
                //.uv2(0,0)
                .endVertex();
    }

    private void RenderItem(int slot_id, AltarTileEntity tileEntityIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(slot_id);
        IBakedModel ibakedmodel = itemRenderer.getModel(stack, tileEntityIn.getLevel(), null);
        itemRenderer.render(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
    }
}
