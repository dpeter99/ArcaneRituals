package com.dpeter99.ArcaneRituals.client.renderer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.types.Func;
import com.mojang.datafixers.util.Pair;
//import net.minecraft.block.material.Material;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.ItemRenderer;
//import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.client.model.obj.MaterialLibrary;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class FluidHolderRenderer implements IModelGeometry<FluidHolderRenderer> {

    Map<Fluid,String> fluids;

    Fluid active_fuid;

    public FluidHolderRenderer(Map<Fluid,String> fluids) {
        this.fluids = fluids;
        this.active_fuid = Fluids.EMPTY;
    }

    public FluidHolderRenderer(Map<Fluid,String> fluids, Fluid active) {
        this.fluids = fluids;
        this.active_fuid = active;
    }

    /**
     * Returns a new ModelDynBucket representing the given fluid, but with the same
     * other properties (flipGas, tint, coverIsMask).
     */
    public FluidHolderRenderer withFluid(Fluid newFluid)
    {
        return new FluidHolderRenderer(fluids, newFluid);
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {

        RenderMaterial particleLocation = owner.resolveTexture("particle");
        if (MissingTextureSprite.getLocation().equals(particleLocation.getTextureLocation()))
        {
            particleLocation = null;
        }
        TextureAtlasSprite particleSprite = particleLocation != null ? spriteGetter.apply(particleLocation) : null;


        IModelTransform transformsFromModel = owner.getCombinedTransform();

        ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> transformMap = transformsFromModel != null ?
                PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(transformsFromModel, modelTransform)) :
                PerspectiveMapWrapper.getTransforms(modelTransform);

        TransformationMatrix transform = modelTransform.getRotation();

        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        String texturePath = fluids.get(active_fuid);
        ResourceLocation texture_loc = ResourceLocation.tryCreate(texturePath);
        RenderMaterial fluidMaterial = ModelLoaderRegistry.blockMaterial(texture_loc);
        TextureAtlasSprite fluidSprite = spriteGetter.apply(fluidMaterial);

        if (fluidSprite != null)
        {
            builder.addAll(ItemLayerModel.getQuadsForSprite(0,fluidSprite,transform));
        }

        return new BakedModel(bakery, owner, this, builder.build(), particleSprite, Maps.immutableEnumMap(transformMap), Maps.newHashMap(), transform.isIdentity(), modelTransform, owner.isSideLit());

    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        Set<RenderMaterial> texs = Sets.newHashSet();

        //owner.resolveTexture()

        fluids.forEach((key,val)->{
            texs.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE,ResourceLocation.tryCreate(val)));
        });

        //String texturePath = fluids.get(active_fuid);
        //texs.add(new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE,ResourceLocation.tryCreate(texturePath)));

        return texs;
    }

    public enum Loader implements IModelLoader<FluidHolderRenderer>
    {
        INSTANCE;

        @Override
        public IResourceType getResourceType()
        {
            return VanillaResourceType.MODELS;
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager)
        {
            // no need to clear cache since we create a new model instance
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate)
        {
            // no need to clear cache since we create a new model instance
        }

        @Override
        public FluidHolderRenderer read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
        {
            if (!modelContents.has("fluid_variants"))
                throw new RuntimeException("Bucket model requires 'fluid' value.");

            JsonObject fluid_variants = modelContents.get("fluid_variants").getAsJsonObject();

            Set<Map.Entry<String, JsonElement>> a = fluid_variants.entrySet();
            Map<Fluid,String> fluids = new HashMap<>();
            for (Map.Entry<String, JsonElement> item : a) {
                ResourceLocation fluidName = new ResourceLocation(item.getKey());
                Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidName);
                fluids.put(fluid,item.getValue().getAsString());
            }



            boolean flip = false;
            if (modelContents.has("flipGas"))
            {
                flip = modelContents.get("flipGas").getAsBoolean();
            }

            boolean tint = true;
            if (modelContents.has("applyTint"))
            {
                tint = modelContents.get("applyTint").getAsBoolean();
            }

            // create new model with correct liquid
            return new FluidHolderRenderer(fluids);
        }
    }


    private static final class ContainedFluidOverrideHandler extends ItemOverrideList
    {
        private final ModelBakery bakery;

        private ContainedFluidOverrideHandler(ModelBakery bakery)
        {
            this.bakery = bakery;
        }

        @Override
        public IBakedModel getOverrideModel(IBakedModel originalModel, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity livingEntity)
        {
            return FluidUtil.getFluidContained(stack)
                    .map(fluidStack -> {
                        BakedModel model = (BakedModel)originalModel;

                        Fluid fluid = fluidStack.getFluid();
                        String name = fluid.getRegistryName().toString();

                        if (!model.cache.containsKey(name))
                        {
                            FluidHolderRenderer parent = model.parent.withFluid(fluid);
                            IBakedModel bakedModel = parent.bake(model.owner, bakery, ModelLoader.defaultTextureGetter(), model.originalTransform, model.getOverrides(), new ResourceLocation("forge:bucket_override"));
                            model.cache.put(name, bakedModel);
                            return bakedModel;
                        }

                        return model.cache.get(name);
                    })
                    // not a fluid item apparently
                    .orElse(originalModel); // empty bucket
        }
    }

    // the dynamic bucket is based on the empty bucket
    private static final class BakedModel extends BakedItemModel
    {
        private final IModelConfiguration owner;
        private final FluidHolderRenderer parent;
        private final Map<String, IBakedModel> cache; // contains all the baked models since they'll never change
        private final IModelTransform originalTransform;
        private final boolean isSideLit;

        BakedModel(ModelBakery bakery,
                   IModelConfiguration owner,
                   FluidHolderRenderer parent,
                   ImmutableList<BakedQuad> quads,
                   TextureAtlasSprite particle,
                   ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> transforms,
                   Map<String, IBakedModel> cache,
                   boolean untransformed,
                   IModelTransform originalTransform, boolean isSideLit)
        {
            super(quads, particle, transforms, new ContainedFluidOverrideHandler(bakery), untransformed, isSideLit);
            this.owner = owner;
            this.parent = parent;
            this.cache = cache;
            this.originalTransform = originalTransform;
            this.isSideLit = isSideLit;
        }
    }

}
