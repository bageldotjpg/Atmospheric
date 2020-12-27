package com.minecraftabnormals.atmospheric.common.world.biome;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.minecraftabnormals.atmospheric.core.Atmospheric;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericBiomes;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericFeatures;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Atmospheric.MODID)
public class AtmosphericBiomeFeatures {

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		BiomeGenerationSettingsBuilder builder = event.getGeneration();

		Set<Block> allBlocksToCarve = new HashSet<>();
		allBlocksToCarve.add(AtmosphericBlocks.ARID_SAND.get());
		allBlocksToCarve.add(AtmosphericBlocks.ARID_SANDSTONE.get());
		allBlocksToCarve.add(AtmosphericBlocks.RED_ARID_SAND.get());
		allBlocksToCarve.add(AtmosphericBlocks.RED_ARID_SANDSTONE.get());

		for (GenerationStage.Carving carverStage : GenerationStage.Carving.values()) {
			for (Supplier<ConfiguredCarver<?>> carver : event.getGeneration().getCarvers(carverStage)) {
				allBlocksToCarve.addAll(carver.get().carver.carvableBlocks);
				carver.get().carver.carvableBlocks = allBlocksToCarve;
			}
		}

		if (biome != null) {
			if(DataUtil.matchesKeys(biome, AtmosphericBiomes.RAINFOREST.getKey(), AtmosphericBiomes.RAINFOREST_PLATEAU.getKey())) withRainforestFeatures(builder);
			if(DataUtil.matchesKeys(biome, AtmosphericBiomes.RAINFOREST_MOUNTAINS.getKey())) withRainforestMountainsFeatures(builder);
			if(DataUtil.matchesKeys(biome, AtmosphericBiomes.SPARSE_RAINFOREST_PLATEAU.getKey())) withSparseRainforestPlateauFeatures(builder);
			if(DataUtil.matchesKeys(biome, AtmosphericBiomes.RAINFOREST_BASIN.getKey())) withRainforestBasinFeatures(builder);
			if(DataUtil.matchesKeys(biome, AtmosphericBiomes.SPARSE_RAINFOREST_BASIN.getKey())) withSparseRainforestBasinFeatures(builder);

			if(DataUtil.matchesKeys(biome, AtmosphericBiomes.DUNES.getKey(), AtmosphericBiomes.DUNES_HILLS.getKey())) withDunesFeatures(builder);
			if(DataUtil.matchesKeys(biome, AtmosphericBiomes.ROCKY_DUNES.getKey(), AtmosphericBiomes.ROCKY_DUNES_HILLS.getKey())) withRockyDunesFeatures(builder);
			if(DataUtil.matchesKeys(biome, AtmosphericBiomes.PETRIFIED_DUNES.getKey())) withPetrifiedDunesFeatures(builder);
			if(DataUtil.matchesKeys(biome, AtmosphericBiomes.FLOURISHING_DUNES.getKey())) withFlourishingDunesFeatures(builder);

			if (DataUtil.matchesKeys(biome, Biomes.DESERT, Biomes.DESERT_HILLS))
				builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.YUCCA_TREE_DESERT);
			if (DataUtil.matchesKeys(biome, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.WOODED_BADLANDS_PLATEAU))
				builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.YUCCA_TREE);
			if (DataUtil.matchesKeys(biome, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU))
				builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.YUCCA_TREE_SAVANNA);
		}
	}

	public static void withBaseRainforestFeatures(BiomeGenerationSettingsBuilder builder) {
		DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
		DefaultBiomeFeatures.withCavesAndCanyons(builder);
		DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
		DefaultBiomeFeatures.withMonsterRoom(builder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
		DefaultBiomeFeatures.withOverworldOres(builder);
		DefaultBiomeFeatures.withDisks(builder);
		DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
		DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
		DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
		DefaultBiomeFeatures.withFrozenTopLayer(builder);
		DefaultBiomeFeatures.withForestRocks(builder);
		DefaultBiomeFeatures.withLargeFern(builder);
		AtmosphericBiomeFeatures.withRainforestFoliage(builder);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PODZOL);
	}

	public static void withRainforestFeatures(BiomeGenerationSettingsBuilder builder) {
		AtmosphericBiomeFeatures.withBaseRainforestFeatures(builder);
		AtmosphericBiomeFeatures.withRainforestWaterFoliage(builder);
		AtmosphericBiomeFeatures.withRainforestTrees(builder);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.ROSEWOOD_TREE);
	}

	public static void withRainforestMountainsFeatures(BiomeGenerationSettingsBuilder builder) {
		AtmosphericBiomeFeatures.withBaseRainforestFeatures(builder);
		AtmosphericBiomeFeatures.withRainforestWaterFoliage(builder);
		AtmosphericBiomeFeatures.withRainforestTrees(builder);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.ROSEWOOD_TREE_EXTRA);
	}

	public static void withSparseRainforestPlateauFeatures(BiomeGenerationSettingsBuilder builder) {
		AtmosphericBiomeFeatures.withBaseRainforestFeatures(builder);
		AtmosphericBiomeFeatures.withRainforestWaterFoliage(builder);
		AtmosphericBiomeFeatures.withSparseRainforestPlateauTrees(builder);
	}

	public static void withRainforestBasinFeatures(BiomeGenerationSettingsBuilder builder) {
		AtmosphericBiomeFeatures.withBaseRainforestFeatures(builder);
		AtmosphericBiomeFeatures.withRainforestBasinWaterFoliage(builder);
		AtmosphericBiomeFeatures.withRainforestBasinTrees(builder);
	}

	public static void withSparseRainforestBasinFeatures(BiomeGenerationSettingsBuilder builder) {
		AtmosphericBiomeFeatures.withBaseRainforestFeatures(builder);
		AtmosphericBiomeFeatures.withSparseRainforestBasinWaterFoliage(builder);
		AtmosphericBiomeFeatures.withSparseRainforestBasinTrees(builder);
	}

	public static void withBaseDunesFeatures(BiomeGenerationSettingsBuilder builder) {
		DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
		DefaultBiomeFeatures.withCavesAndCanyons(builder);
		DefaultBiomeFeatures.withMonsterRoom(builder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
		DefaultBiomeFeatures.withOverworldOres(builder);
		DefaultBiomeFeatures.withDisks(builder);
		DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
		DefaultBiomeFeatures.withFrozenTopLayer(builder);
		DefaultBiomeFeatures.withFossils(builder);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH);

	}

	public static void withDunesFeatures(BiomeGenerationSettingsBuilder builder) {
		withBaseDunesFeatures(builder);
		builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, AtmosphericFeatures.Configured.DUNE_ROCK);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.YUCCA_TREE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_YUCCA_FLOWER);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_ALOE_VERA);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_BARREL_CACTUS_DUNES);
	}

	public static void withFlourishingDunesFeatures(BiomeGenerationSettingsBuilder builder) {
		withBaseDunesFeatures(builder);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_MELON);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_GILIA);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_DUNE_GRASS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.YUCCA_TREE_BEEHIVE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.YUCCA_TREE_BABY);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_YUCCA_FLOWER_EXTRA);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_ALOE_VERA_EXTRA);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_BARREL_CACTUS_FLOURISHING_DUNES);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.DUNE_ROCK_EXTRA);
	}

	public static void withRockyDunesFeatures(BiomeGenerationSettingsBuilder builder) {
		withBaseDunesFeatures(builder);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.YUCCA_TREE_ROCKY_DUNES);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_ALOE_VERA);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_BARREL_CACTUS_ROCKY_DUNES);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.DUNE_ROCK_MANY);
	}

	public static void withPetrifiedDunesFeatures(BiomeGenerationSettingsBuilder builder) {
		withBaseDunesFeatures(builder);
		builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, AtmosphericFeatures.Configured.DUNE_ROCK);
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, AtmosphericFeatures.Configured.FOSSIL_SURFACE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.YUCCA_TREE_PETRIFIED);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_BARREL_CACTUS_PETRIFIED_DUNES);
	}

	public static void withRainforestFoliage(BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_TALL_GRASS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_JUNGLE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.VINES);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PASSION_VINES);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FOREST_FLOWER_VEGETATION_COMMON);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_WARM_MONKEY_BRUSH);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_HOT_MONKEY_BRUSH);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_SCALDING_MONKEY_BRUSH);
	}

	public static void withRainforestWaterFoliage(BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_WATER_HYACINTH);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_WATERLILLY_RAINFOREST);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
	}

	public static void withRainforestTrees (BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.OAK_TREE_RAINFOREST);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.MORADO_TREE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.RAINFOREST_BUSH);
	}

	public static void withRainforestBasinTrees(BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.OAK_TREE_RAINFOREST);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.RAINFOREST_BUSH);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.ROSEWOOD_WATER_TREE);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.MORADO_WATER_TREE);
    }

	public static void withSparseRainforestBasinTrees(BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.OAK_TREE_RAINFOREST);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.RAINFOREST_BUSH);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.ROSEWOOD_WATER_TREE_SPARSE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.MORADO_WATER_TREE_SPARSE);
	}

	public static void withSparseRainforestPlateauTrees(BiomeGenerationSettings.Builder builder) {
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.ROSEWOOD_TREE_SPARSE);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.MORADO_TREE_SPARSE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.RAINFOREST_BUSH);
	}

	public static void withRainforestBasinWaterFoliage(BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_WATER_HYACINTH);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_DEEP_WARM);
    }

	public static void withSparseRainforestBasinWaterFoliage(BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AtmosphericFeatures.Configured.PATCH_WATER_HYACINTH_SPARSE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_DEEP_WARM);
    }
}
