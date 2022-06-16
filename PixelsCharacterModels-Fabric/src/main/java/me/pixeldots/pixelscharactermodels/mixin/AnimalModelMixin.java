package me.pixeldots.pixelscharactermodels.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.pixeldots.pixelscharactermodels.PCMClient;
import me.pixeldots.pixelscharactermodels.files.AnimationPlayer;
import me.pixeldots.scriptedmodels.ScriptedModels;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(AnimalModel.class)
public class AnimalModelMixin {
    
    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo info) {
        UUID uuid = ScriptedModels.Rendering_Entity.getUuid();
        if (PCMClient.EntityAnimationList.containsKey(uuid)) {
            AnimationPlayer player = PCMClient.EntityAnimationList.get(uuid);
            player.frame += player.animation.getFPSDifference(((IMinecraftClientMixin)PCMClient.minecraft).getFPS());
            player.updateCurrent(ScriptedModels.Rendering_Entity, (EntityModel<?>)(Object)this, player.frame, player.index);
        }
    }

}
