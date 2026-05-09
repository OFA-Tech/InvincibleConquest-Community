package net.clozynoii.invincibleconquest.client;

import net.clozynoii.invincibleconquest.procedures.SetupAnimationsProcedure;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;

@EventBusSubscriber(modid = "invincible_conquest", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientAnimationHandler {
	private ClientAnimationHandler() {
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register((player, animationStack) -> {
			ModifierLayer<IAnimation> layer = new ModifierLayer<>();
			animationStack.addAnimLayer(69, layer);
			PlayerAnimationAccess.getPlayerAssociatedData(player).set(ResourceLocation.fromNamespaceAndPath("invincible_conquest", "player_animation"), layer);
		});
	}

	@OnlyIn(Dist.CLIENT)
	public static void handleAnimation(SetupAnimationsProcedure.InvincibleConquestModAnimationMessage message, Player player) {
		if (player instanceof AbstractClientPlayer player_) {
			var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player_)
					.get(ResourceLocation.fromNamespaceAndPath("invincible_conquest", "player_animation"));

			if (message.getAnimation().isEmpty()) {
				if (animation != null && animation.isActive()) {
					stopAnimationClientside(player_);
				}
			} else {
				setAnimationClientside(player_, message.getAnimation(), message.isOverride());
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void setAnimationClientside(Player player, String anim, boolean override) {
		if (player instanceof AbstractClientPlayer player_) {
			var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player_)
					.get(ResourceLocation.fromNamespaceAndPath("invincible_conquest", "player_animation"));

			if (anim.isEmpty()) {
				if (animation == null || !animation.isActive()) {
					return;
				}
			}

			if (animation != null && (override || !animation.isActive())) {
				var key = ResourceLocation.fromNamespaceAndPath("invincible_conquest", anim);
				var registered = PlayerAnimationRegistry.getAnimation(key);
				if (registered == null) {
					return;
				}
				animation.replaceAnimationWithFade(
						AbstractFadeModifier.functionalFadeIn(5, (modelName, type, value) -> value),
						registered.playAnimation()
								.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL)
								.setFirstPersonConfiguration(new FirstPersonConfiguration().setShowRightArm(true).setShowLeftItem(false))
				);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void stopAnimationClientside(Player player) {
		if (player instanceof AbstractClientPlayer player_) {
			var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player_)
					.get(ResourceLocation.fromNamespaceAndPath("invincible_conquest", "player_animation"));

			if (animation != null && animation.isActive()) {
				animation.setAnimation(null);
			}
		}
	}
}

