package net.clozynoii.invincibleconquest;

import net.clozynoii.invincibleconquest.procedures.SetupAnimationsProcedure;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.resources.ResourceLocation;

public class ClientOnlyHandler {
    public static void handleAnimation(SetupAnimationsProcedure.InvincibleConquestModAnimationMessage message, Player player) {
        if (player instanceof AbstractClientPlayer player_) {
            var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player_)
                .get(ResourceLocation.fromNamespaceAndPath("invincible_conquest", "player_animation"));

            if (message.getAnimation().isEmpty()) {
                if (animation != null && animation.isActive()) {
                    SetupAnimationsProcedure.stopAnimationClientside(player_);
                }
            } else {
                SetupAnimationsProcedure.setAnimationClientside(player, message.getAnimation(), message.isOverride());
            }
        }
    }
}
