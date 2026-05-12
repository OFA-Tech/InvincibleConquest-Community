package net.clozynoii.invincibleconquest.client;

import net.clozynoii.invincibleconquest.InvincibleConquestMod;
import net.clozynoii.invincibleconquest.procedures.SetupAnimationsProcedure;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.IPlayable;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = "invincible_conquest", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientAnimationHandler {
	private static final ResourceLocation PLAYER_ANIMATION_LAYER_ID = ResourceLocation.fromNamespaceAndPath("invincible_conquest", "player_animation");
	private static final Set<String> MISSING_ANIMATION_LOG_KEYS = ConcurrentHashMap.newKeySet();

	private ClientAnimationHandler() {
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register((player, animationStack) -> {
			ModifierLayer<IAnimation> layer = new ModifierLayer<>();
			animationStack.addAnimLayer(69, layer);
			PlayerAnimationAccess.getPlayerAssociatedData(player).set(PLAYER_ANIMATION_LAYER_ID, layer);
		});
	}

	@OnlyIn(Dist.CLIENT)
	public static void handleAnimation(SetupAnimationsProcedure.InvincibleConquestModAnimationMessage message, Player player) {
		if (player instanceof AbstractClientPlayer player_) {
			var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player_).get(PLAYER_ANIMATION_LAYER_ID);

			if (message.getAnimation().isEmpty()) {
				if (animation != null && animation.isActive()) {
					stopAnimationClientside(player_);
				}
			} else {
				setAnimationClientside(player_, message.getAnimation(), message.isOverride(), "network:setup_animations");
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void setAnimationClientside(Player player, String anim, boolean override) {
		setAnimationClientside(player, anim, override, "direct");
	}

	@OnlyIn(Dist.CLIENT)
	public static void setAnimationClientside(Player player, String anim, boolean override, String context) {
		if (player instanceof AbstractClientPlayer player_) {
			var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player_).get(PLAYER_ANIMATION_LAYER_ID);

			if (anim.isEmpty()) {
				if (animation == null || !animation.isActive()) {
					return;
				}
			}

			if (animation != null && (override || !animation.isActive())) {
				ResourceLocation animationId = ResourceLocation.fromNamespaceAndPath("invincible_conquest", anim);
				IPlayable playable = getPlayableAnimationOrNull(animationId, player_, context);
				if (playable == null) {
					return;
				}

				animation.replaceAnimationWithFade(
						AbstractFadeModifier.functionalFadeIn(5, (modelName, type, value) -> value),
						playable.playAnimation()
								.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL)
								.setFirstPersonConfiguration(new FirstPersonConfiguration().setShowRightArm(true).setShowLeftItem(false))
				);
			}
		}
	}

	private static IPlayable getPlayableAnimationOrNull(ResourceLocation animationId, Player player, String context) {
		IPlayable playable = PlayerAnimationRegistry.getAnimation(animationId);
		if (playable == null) {
			logMissingAnimationOnce(animationId, player, context);
		}
		return playable;
	}

	private static void logMissingAnimationOnce(ResourceLocation animationId, Player player, String context) {
		String key = animationId + "|" + context;
		if (MISSING_ANIMATION_LOG_KEYS.add(key)) {
			InvincibleConquestMod.LOGGER.warn("Missing player animation '{}' (context='{}', player='{}', uuid='{}', entityId={}). Animation was skipped.",
					animationId,
					context,
					player.getName().getString(),
					player.getUUID(),
					player.getId());
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void stopAnimationClientside(Player player) {
		if (player instanceof AbstractClientPlayer player_) {
			var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player_).get(PLAYER_ANIMATION_LAYER_ID);

			if (animation != null && animation.isActive()) {
				animation.setAnimation(null);
			}
		}
	}
}
