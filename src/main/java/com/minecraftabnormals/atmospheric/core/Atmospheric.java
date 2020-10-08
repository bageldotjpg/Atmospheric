package com.minecraftabnormals.atmospheric.core;

import com.minecraftabnormals.atmospheric.core.other.AtmosphericCompat;
import com.minecraftabnormals.atmospheric.core.other.AtmosphericRender;
import com.minecraftabnormals.atmospheric.core.other.AtmosphericVillagers;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericBiomes;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericEffects;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericFeatures;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericParticles;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Atmospheric.MODID)
@Mod.EventBusSubscriber(modid = Atmospheric.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Atmospheric
{
    public static final String MODID = "atmospheric";
	public static final AtmosphericRegistryHelper REGISTRY_HELPER = new AtmosphericRegistryHelper(MODID);

    public Atmospheric() {
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
    	REGISTRY_HELPER.getDeferredItemRegister().register(modEventBus);
    	REGISTRY_HELPER.getDeferredBlockRegister().register(modEventBus);
    	
    	AtmosphericBiomes.BIOMES.register(modEventBus);
    	AtmosphericParticles.PARTICLES.register(modEventBus);
        AtmosphericEffects.EFFECTS.register(modEventBus);
        AtmosphericEffects.POTIONS.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        modEventBus.addListener(this::setup);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
        	modEventBus.addListener(this::clientSetup);
        });
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AtmosphericConfig.COMMON_SPEC);
    }
    
	private void setup(final FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(() -> {
			AtmosphericFeatures.generateFeatures();
			AtmosphericFeatures.addCarvables();
			AtmosphericBiomes.addBiomeTypes();
			AtmosphericBiomes.registerBiomesToDictionary();
			AtmosphericVillagers.setupVillagerTypes();
			AtmosphericCompat.registerCompostables();
			AtmosphericCompat.registerFlammables();
			AtmosphericCompat.registerDispenserBehaviors();
			AtmosphericEffects.registerBrewingRecipes();			
		});
	}
    
    private void clientSetup(final FMLClientSetupEvent event) {
    	DeferredWorkQueue.runLater(() -> {
    		AtmosphericRender.registerBlockColors();
    		AtmosphericRender.setupRenderLayer();
    	});
    }
}
