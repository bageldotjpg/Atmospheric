package com.minecraftabnormals.atmospheric.common.block;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericBiomes;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class AridSandBlock extends FallingBlock implements IGrowable {
	private final int color;

	public AridSandBlock(int color, Properties properties) {
		super(properties);
		this.color = color;
	}

	@Override
	public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
		return this.color;
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader blockReader, BlockPos pos, Direction direction, IPlantable iPlantable) {
		final BlockPos plantPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
		final PlantType plantType = iPlantable.getPlantType(blockReader, plantPos);
		if (plantType == PlantType.DESERT) {
			return true;
		} else if (plantType == PlantType.WATER) {
			return blockReader.getBlockState(pos).getMaterial() == Material.WATER && blockReader.getBlockState(pos) == getDefaultState();
		} else if (plantType == PlantType.BEACH) {
			return ((blockReader.getBlockState(pos.east()).getMaterial() == Material.WATER || blockReader.getBlockState(pos.east()).hasProperty(BlockStateProperties.WATERLOGGED))
					|| (blockReader.getBlockState(pos.west()).getMaterial() == Material.WATER || blockReader.getBlockState(pos.west()).hasProperty(BlockStateProperties.WATERLOGGED))
					|| (blockReader.getBlockState(pos.north()).getMaterial() == Material.WATER || blockReader.getBlockState(pos.north()).hasProperty(BlockStateProperties.WATERLOGGED))
					|| (blockReader.getBlockState(pos.south()).getMaterial() == Material.WATER || blockReader.getBlockState(pos.south()).hasProperty(BlockStateProperties.WATERLOGGED)));
		} else {
			return false;
		}
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return worldIn.getBlockState(pos.up()).isAir();
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.up();
		BlockState blockstate = AtmosphericBlocks.ARID_SPROUTS.get().getDefaultState();

		label48:
		for(int i = 0; i < 128; ++i) {
			BlockPos blockpos1 = blockpos;

			for(int j = 0; j < i / 16; ++j) {
				blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
				if (!worldIn.getBlockState(blockpos1.down()).isIn(this) || worldIn.getBlockState(blockpos1).hasOpaqueCollisionShape(worldIn, blockpos1)) {
					continue label48;
				}
			}

			BlockState blockstate2 = worldIn.getBlockState(blockpos1);
			if (blockstate2.isAir()) {
				BlockState blockstate1;
				if (rand.nextInt(8) == 0) {
					ResourceLocation biome = worldIn.getBiome(blockpos1).getRegistryName();
					if (DataUtil.matchesKeys(biome, AtmosphericBiomes.FLOURISHING_DUNES.getKey()))
						blockstate1 = AtmosphericBlocks.GILIA.get().getDefaultState();
					else {
						blockstate1 = AtmosphericBlocks.YUCCA_FLOWER.get().getDefaultState();
					}
				} else {
					blockstate1 = blockstate;
				}

				if (blockstate1.isValidPosition(worldIn, blockpos1)) {
					worldIn.setBlockState(blockpos1, blockstate1, 3);
					if (blockstate1.isIn(AtmosphericBlocks.YUCCA_FLOWER.get()) && rand.nextInt(10) == 0) {
						((IGrowable)blockstate1.getBlock()).grow(worldIn, rand, blockpos1, blockstate1);
					}
				}
			}
		}

	}
}