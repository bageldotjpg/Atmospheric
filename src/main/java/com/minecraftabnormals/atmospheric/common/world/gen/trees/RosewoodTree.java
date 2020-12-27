package com.minecraftabnormals.atmospheric.common.world.gen.trees;

import com.minecraftabnormals.atmospheric.core.registry.AtmosphericFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class RosewoodTree extends Tree {
	
	@Nullable
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean beehive) {
		return AtmosphericFeatures.ROSEWOOD_TREE.get().withConfiguration(beehive ? AtmosphericFeatures.Configs.ROSEWOOD_TREE_WITH_MORE_BEEHIVES_CONFIG : AtmosphericFeatures.Configs.ROSEWOOD_TREE_CONFIG);
	}
}
