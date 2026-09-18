package com.anhpls.progressionpls.client.mixin;

import com.anhpls.progressionpls.client.vfx.ParticleToggle;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenParticleToggleMixin extends AbstractContainerScreen<InventoryMenu> {

    // Never actually called — Mixin merges this class's members directly into the
    // real InventoryScreen instance. It only needs to exist so this class compiles
    // as a legal subtype of AbstractContainerScreen.
    public InventoryScreenParticleToggleMixin(InventoryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void progressionpls$addParticleToggleButton(CallbackInfo ci) {
        this.addRenderableWidget(Button.builder(
                        progressionpls$buttonLabel(),
                        btn -> {
                            ParticleToggle.toggle();
                            btn.setMessage(progressionpls$buttonLabel());
                        })
                .bounds(this.leftPos + this.imageWidth - 22, this.topPos + 4, 18, 18)
                .build());
    }

    private static Component progressionpls$buttonLabel() {
        return Component.literal(ParticleToggle.isEnabled() ? "✨" : "🚫");
    }
}