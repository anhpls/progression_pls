package com.anhpls.progressionpls.client.mixin;

import java.util.LinkedHashSet;
import java.util.Set;

import com.anhpls.progressionpls.client.vfx.AuraVfx;
import com.anhpls.progressionpls.client.vfx.ParticleToggle;
import com.anhpls.progressionpls.client.vfx.VfxItemToggles;

import eu.pb4.trinkets.api.TrinketAttachment;
import eu.pb4.trinkets.api.TrinketsApi;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
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
                        progressionpls$masterButtonLabel(),
                        btn -> {
                            ParticleToggle.toggle();
                            btn.setMessage(progressionpls$masterButtonLabel());
                        })
                .bounds(this.leftPos + this.imageWidth - 22, this.topPos + 4, 18, 18)
                .build());

        progressionpls$addPerItemToggleButtons();
    }

    // One small row per currently-equipped item that has an aura VFX registered to it,
    // so a player wearing several VFX trinkets at once can quiet down the ones they
    // don't want to see without losing that item's stats/functionality. Separate from
    // the master toggle above (which is all-or-nothing) and from /vfxtune's `enabled`
    // (a dev/balance flag, not meant for players to fiddle with every session).
    //
    // Positioned OFF the inventory panel entirely (unlike the master toggle, which sits
    // in the panel's corner) — hugging whichever screen edge has more open space, so it
    // never overlaps the panel itself, the hotbar, or vanilla's potion-effect icons
    // (which claim whichever side has room). If the window is too narrow for a readable
    // button on either side, the column is skipped rather than drawn over something else.
    private void progressionpls$addPerItemToggleButtons() {
        Player player = this.minecraft.player;
        if (player == null) return;

        TrinketAttachment attachment = TrinketsApi.getAttachment(player);

        Set<Item> equippedVfxItems = new LinkedHashSet<>();
        for (Item item : AuraVfx.allRegisteredItems()) {
            if (attachment.isEquipped(item)) {
                equippedVfxItems.add(item);
            }
        }
        if (equippedVfxItems.isEmpty()) return;

        int margin = 4;
        int leftGap = this.leftPos - margin * 2;
        int rightGap = this.width - (this.leftPos + this.imageWidth) - margin * 2;
        boolean useLeftSide = leftGap >= rightGap;
        int availableGap = Math.max(leftGap, rightGap);
        if (availableGap < 40) return;

        int buttonWidth = Math.min(100, availableGap);
        int x = useLeftSide ? margin : this.width - buttonWidth - margin;
        int y = this.topPos;

        for (Item item : equippedVfxItems) {
            if (y + 16 > this.height - margin) break;
            this.addRenderableWidget(Button.builder(
                            progressionpls$itemButtonLabel(item),
                            btn -> {
                                VfxItemToggles.toggle(item);
                                btn.setMessage(progressionpls$itemButtonLabel(item));
                            })
                    .bounds(x, y, buttonWidth, 16)
                    .build());
            y += 18;
        }
    }

    private static Component progressionpls$masterButtonLabel() {
        return Component.literal(ParticleToggle.isEnabled() ? "✨" : "🚫");
    }

    private static Component progressionpls$itemButtonLabel(Item item) {
        boolean enabled = VfxItemToggles.isEnabled(item);
        return Component.translatable(item.getDescriptionId())
                .copy()
                .append(Component.literal(enabled ? " ✨" : " 🚫"));
    }
}
