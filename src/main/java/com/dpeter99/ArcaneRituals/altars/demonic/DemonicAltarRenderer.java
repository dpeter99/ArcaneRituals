package com.dpeter99.ArcaneRituals.altars.demonic;

import com.dpeter99.ArcaneRituals.ArcaneRituals;
import com.dpeter99.ArcaneRituals.ArcaneTileEntities;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class DemonicAltarRenderer extends TileEntityRenderer<DemonicAltarTileEntity> {

    public static final ResourceLocation BLOOD_TEXTURE = ArcaneRituals.location("fluid/blood");

    public DemonicAltarRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(DemonicAltarTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        float ItmeScale = 0.25f;

        matrixStackIn.push();
        matrixStackIn.translate(0.225, 1.23, 0.68);
        matrixStackIn.scale(ItmeScale,ItmeScale,ItmeScale);
        matrixStackIn.rotate(new Quaternion(Vector3f.YP,90.0f, true));
        RenderItem(0, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();

        matrixStackIn.push();
        matrixStackIn.translate(0.225, 1.23, 0.33);
        matrixStackIn.scale(ItmeScale,ItmeScale,ItmeScale);
        matrixStackIn.rotate(new Quaternion(Vector3f.YP,90.0f, true));
        RenderItem(3, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();

        matrixStackIn.push();
        matrixStackIn.translate(0.78, 1.23, 0.33);
        matrixStackIn.scale(ItmeScale,ItmeScale,ItmeScale);
        matrixStackIn.rotate(new Quaternion(Vector3f.YP,90.0f, true));
        RenderItem(2, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();

        matrixStackIn.push();
        matrixStackIn.translate(0.78, 1.23, 0.68);
        matrixStackIn.scale(ItmeScale,ItmeScale,ItmeScale);
        matrixStackIn.rotate(new Quaternion(Vector3f.YP,90.0f, true));
        RenderItem(1, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();


        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(BLOOD_TEXTURE);
        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getTranslucent());

        float fill = tileEntityIn.tank.getFluidAmount() / (float)tileEntityIn.tank.getCapacity();
        float level = 0.9f * fill;
        add(builder, matrixStackIn, 0f, level, 1f, sprite.getMinU(), sprite.getMaxV());
        add(builder, matrixStackIn, 1f, level, 1f, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStackIn, 1f, level, 0f, sprite.getMaxU(), sprite.getMinV());
        add(builder, matrixStackIn, 0f, level, 0f, sprite.getMinU(), sprite.getMinV());

        float floatScale = 0.35f;

        matrixStackIn.push();
        matrixStackIn.translate(0.5, level, 0.5);
        matrixStackIn.scale(floatScale,floatScale,floatScale);
        matrixStackIn.rotate(new Quaternion(Vector3f.XP,90.0f, true));
        RenderItem(4, tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();

    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    private void RenderItem(int slot_id, DemonicAltarTileEntity tileEntityIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(slot_id);
        IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
    }


    public static void register() {
        ClientRegistry.bindTileEntityRenderer(ArcaneTileEntities.demonic_altar, DemonicAltarRenderer::new);
    }
}
