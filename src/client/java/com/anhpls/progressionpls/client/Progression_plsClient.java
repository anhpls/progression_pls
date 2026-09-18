package com.anhpls.progressionpls.client;

import com.anhpls.progressionpls.client.trinket.FaceAccessoryTrinketRenderer;
import com.anhpls.progressionpls.client.trinket.FaceAccessoryTuning;
import com.anhpls.progressionpls.client.vfx.AuraVfx;
import com.anhpls.progressionpls.client.vfx.VfxTuningCommands;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import com.anhpls.progressionpls.item.ModItems;
import com.anhpls.progressionpls.item.ModParticleTypes;
import com.anhpls.progressionpls.client.vfx.HeartAuraParticle;

import eu.pb4.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;

public class Progression_plsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FaceAccessoryTrinketRenderer faceRenderer = new FaceAccessoryTrinketRenderer();
		AuraVfx.register();
		VfxTuningCommands.register();
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.HEART_AURA, HeartAuraParticle.Provider::new);
		TrinketRendererRegistry.registerRenderer(ModItems.EGG_TOAST, faceRenderer);
		TrinketRendererRegistry.registerRenderer(ModItems.BROKEN_GLASSES, faceRenderer);
		TrinketRendererRegistry.registerRenderer(ModItems.SPROUT, faceRenderer);

		FaceAccessoryTuning.setDefault(ModItems.EGG_TOAST, new FaceAccessoryTuning.Values(
		    0.0f, 0.2f, -0.28f,
		    -65f, 0f, 0f,
		    0.3f, 0.3f, 1f
		));

		FaceAccessoryTuning.setDefault(ModItems.SPROUT, new FaceAccessoryTuning.Values(
		    -0.04f, -0.44f, -0.03f,
		    0f, 180f, 180f,
		    0.6f, 0.6f, 1.0f
		));

		TuningCommands.register();
	}
}