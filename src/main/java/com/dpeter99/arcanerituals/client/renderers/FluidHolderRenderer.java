
/*
package com.dpeter99.arcanerituals.client.renderers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    public FluidHolderRenderer withFluid(Fluid newFluid)
    {
        return new FluidHolderRenderer(fluids, newFluid);
    }

    @Override
    public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {

        Material particleLocation = owner.resolveTexture("particle");
        if (MissingTextureAtlasSprite.getLocation().equals(particleLocation.atlasLocation()))
        {
            particleLocation = null;
        }
        TextureAtlasSprite particleSprite = particleLocation != null ? spriteGetter.apply(particleLocation) : null;


        ModelState transformsFromModel = owner.getCombinedTransform();

        ImmutableMap<ItemTransforms.TransformType, Transformation> transformMap = transformsFromModel != null ?
                PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(transformsFromModel, modelTransform)) :
                PerspectiveMapWrapper.getTransforms(modelTransform);

        Transformation transform = modelTransform.getRotation();

        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        String texturePath = fluids.get(active_fuid);
        ResourceLocation texture_loc = ResourceLocation.tryParse(texturePath);
        Material fluidMaterial = ModelLoaderRegistry.blockMaterial(texture_loc);
        TextureAtlasSprite fluidSprite = spriteGetter.apply(fluidMaterial);

        if (fluidSprite != null)
        {
            builder.addAll(ItemLayerModel.getQuadsForSprite(0,fluidSprite,transform));
        }

        return new BakedModel(bakery, owner, this, builder.build(), particleSprite, Maps.immutableEnumMap(transformMap), Maps.newHashMap(), transform.isIdentity(), modelTransform, owner.isSideLit());

    }

    @Override
    public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        Set<Material> texs = Sets.newHashSet();

        //owner.resolveTexture()

        fluids.forEach((key,val)->{
            texs.add(new Material(TextureAtlas.LOCATION_BLOCKS,ResourceLocation.tryParse(val)));
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
        public void onResourceManagerReload(ResourceManager resourceManager)
        {
            // no need to clear cache since we create a new model instance
        }

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager, Predicate<IResourceType> resourcePredicate)
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


    private static final class ContainedFluidOverrideHandler extends ItemOverrides
    {
        private final ModelBakery bakery;

        private ContainedFluidOverrideHandler(ModelBakery bakery)
        {
            this.bakery = bakery;
        }

        @Override
        public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity livingEntity)
        {
            return FluidUtil.getFluidContained(stack)
                    .map(fluidStack -> {
                        BakedModel model = (BakedModel)originalModel;

                        Fluid fluid = fluidStack.getFluid();
                        String name = fluid.getRegistryName().toString();

                        if (!model.cache.containsKey(name))
                        {
                            FluidHolderRenderer parent = model.parent.withFluid(fluid);
                            BakedModel bakedModel = parent.bake(model.owner, bakery, ModelLoader.defaultTextureGetter(), model.originalTransform, model.getOverrides(), new ResourceLocation("forge:bucket_override"));
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
        private final Map<String, BakedModel> cache; // contains all the baked models since they'll never change
        private final ModelState originalTransform;
        private final boolean isSideLit;

        BakedModel(ModelBakery bakery,
                   IModelConfiguration owner,
                   FluidHolderRenderer parent,
                   ImmutableList<BakedQuad> quads,
                   TextureAtlasSprite particle,
                   ImmutableMap<ItemTransforms.TransformType, Transformation> transforms,
                   Map<String, BakedModel> cache,
                   boolean untransformed,
                   ModelState originalTransform, boolean isSideLit)
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
*/