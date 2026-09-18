package com.anhpls.progressionpls.client.trinket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class FaceAccessoryTrinketRenderer implements TrinketRenderer {

    @Override
    public void submit(ItemStack stack, TrinketSlotAccess slotReference,
                        EntityModel<? extends LivingEntityRenderState> contextModel,
                        PoseStack poseStack, SubmitNodeCollector collector, int light,
                        LivingEntityRenderState renderState, float limbSwing, float limbSwingAmount) {

        if (!(contextModel instanceof HeadedModel headedModel)) {
            return;
        }

        FaceAccessoryTuning.Values v = FaceAccessoryTuning.get(stack.getItem());

        poseStack.pushPose();
        TrinketRenderer.translateToHead(poseStack, headedModel);
        poseStack.translate(v.offsetX, v.offsetY, v.offsetZ);

        if (v.rotX != 0f) poseStack.mulPose(Axis.XP.rotationDegrees(v.rotX));
        if (v.rotY != 0f) poseStack.mulPose(Axis.YP.rotationDegrees(v.rotY));
        if (v.rotZ != 0f) poseStack.mulPose(Axis.ZP.rotationDegrees(v.rotZ));

        poseStack.scale(v.scaleX, v.scaleY, v.scaleZ);

        ItemStackRenderState itemRenderState = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver()
            .updateForTopItem(itemRenderState, stack, ItemDisplayContext.HEAD, null, null, 0);
        itemRenderState.submit(poseStack, collector, light, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }
}