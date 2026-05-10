package net.clozynoii.invincibleconquest.procedures;

import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModParticleTypes;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModMobEffects;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModGameRules;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerFlyingTickProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean creativeFlight = false;
		double flightSpeed = 0;
		double outputModifier = 0;
		outputModifier = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerOutput / 100;
		flightSpeed = 1 + ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAgility + entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).AgeBoost)
				/ (100 / (world.getLevelData().getGameRules().getInt(InvincibleConquestModGameRules.AGILITY_MOVEMENT_SPEED)))) * 2 * outputModifier;
		if (!(entity == null)) {
			if (entity instanceof Player) {
				if (entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerFlying == true) {
					if (entity.isSprinting()) {
						if ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Viltrumite") || (entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Atom")
								|| (entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Tech Jacket")) {
							creativeFlight = false;
							if (entity instanceof Player _plr && !(_plr.isFallFlying())) {
								_plr.startFallFlying();
							}
							if (!(entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Tech Jacket")) {
								if (!world.isClientSide()) {
									if (entity instanceof Player)
										PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.InvincibleConquestModAnimationMessage("fly_fast", entity.getId(), true));
								}
							} else if ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Tech Jacket")) {
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.POOF, x, y, z, 3, 0.1, 0.1, 0.1, 0.2);
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.FLAME, x, y, z, 2, 0.1, 0.1, 0.1, 0);
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 1, 0.1, 0.1, 0.1, 0.2);
								if (world instanceof ServerLevel _level)
									_level.sendParticles((SimpleParticleType) (InvincibleConquestModParticleTypes.ELECTRICITY_SMALL.get()), x, y, z, 1, 0.1, 0.1, 0.1, 0.2);
							}
							if ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Atom")) {
								if (world instanceof ServerLevel _level)
									_level.getServer().getCommands().performPrefixedCommand(
											new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
											"particle dust{color:[0.94,0.58,0.99],scale:2} ~ ~ ~ 0.5 0.5 0.5 0 5");
							}
							if (entity instanceof LivingEntity _livEnt11 && _livEnt11.hasEffect(InvincibleConquestModMobEffects.DESTRUCTIVE_FLIGHT)) {
								flightSpeed = flightSpeed * 2.25;
							}
							if (entity instanceof Player player) {
								player.getAbilities().flying = (creativeFlight == true);
								player.onUpdateAbilities();
								if (player.isFallFlying()) {
									Vec3 lookVec = player.getLookAngle();
									Vec3 flightVelocity = new Vec3(lookVec.x * flightSpeed, lookVec.y * flightSpeed, lookVec.z * flightSpeed);
									player.setDeltaMovement(flightVelocity);
								}
							}
						}
					} else {
						creativeFlight = true;
						if (entity.isShiftKeyDown()) {
										if ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Portal")) {
											if (!world.isClientSide()) {
												if (entity instanceof Player)
													PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.InvincibleConquestModAnimationMessage("angstromfly", entity.getId(), true));
											}
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.FIREWORK, x, y, z, 5, 0.1, 0.1, 0.1, 0);
							} else if ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Atom")) {
											if (!world.isClientSide()) {
												if (entity instanceof Player)
													PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.InvincibleConquestModAnimationMessage("fly_down", entity.getId(), true));
											}
								if (world instanceof ServerLevel _level)
									_level.getServer().getCommands().performPrefixedCommand(
											new CommandSourceStack(CommandSource.NULL, new Vec3(x, (y - 1), z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
											"particle dust{color:[0.94,0.58,0.99],scale:2} ~ ~ ~ 0.5 0.5 0.5 0 5");
							} else if ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Tech Jacket")) {
											if (!world.isClientSide()) {
												if (entity instanceof Player)
													PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.InvincibleConquestModAnimationMessage("fly_down", entity.getId(), true));
											}
								if (world instanceof ServerLevel _level)
									_level.sendParticles((SimpleParticleType) (InvincibleConquestModParticleTypes.DIRTY_SMOKE.get()), x, y, z, 1, 0.1, 0.1, 0.1, 0.1);
							}
						} else {
							if ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Portal")) {
											if (!world.isClientSide()) {
												if (entity instanceof Player)
													PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.InvincibleConquestModAnimationMessage("angstromfly", entity.getId(), true));
											}
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.FIREWORK, x, y, z, 5, 0.1, 0.1, 0.1, 0);
								if (entity instanceof LivingEntity _livEnt21 && _livEnt21.hasEffect(InvincibleConquestModMobEffects.DESTRUCTIVE_FLIGHT)) {
									if (entity instanceof LivingEntity _entity)
										_entity.removeEffect(InvincibleConquestModMobEffects.DESTRUCTIVE_FLIGHT);
								}
							} else if ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Tech Jacket")) {
											if (!world.isClientSide()) {
												if (entity instanceof Player)
													PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.InvincibleConquestModAnimationMessage(getNormalFlightAnimation(entity), entity.getId(), true));
											}
								world.addParticle(ParticleTypes.POOF, x, y, z, 0, (-0.2), 0);
								world.addParticle(ParticleTypes.FLAME, x, y, z, 0, (-0.1), 0);
								world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 0, (-0.05), 0);
								if (entity instanceof LivingEntity _livEnt27 && _livEnt27.hasEffect(InvincibleConquestModMobEffects.DESTRUCTIVE_FLIGHT)) {
									if (entity instanceof LivingEntity _entity)
										_entity.removeEffect(InvincibleConquestModMobEffects.DESTRUCTIVE_FLIGHT);
								}
							} else if ((entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Atom")) {
											if (!world.isClientSide()) {
												if (entity instanceof Player)
													PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.InvincibleConquestModAnimationMessage(getNormalFlightAnimation(entity), entity.getId(), true));
											}
								if (world instanceof ServerLevel _level)
									_level.getServer().getCommands().performPrefixedCommand(
											new CommandSourceStack(CommandSource.NULL, new Vec3(x, (y - 1), z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
											"particle dust{color:[0.94,0.58,0.99],scale:2} ~ ~ ~ 0.5 0.5 0.5 0 5");
								if (entity instanceof LivingEntity _livEnt31 && _livEnt31.hasEffect(InvincibleConquestModMobEffects.DESTRUCTIVE_FLIGHT)) {
									if (entity instanceof LivingEntity _entity)
										_entity.removeEffect(InvincibleConquestModMobEffects.DESTRUCTIVE_FLIGHT);
								}
							} else {
											if (!world.isClientSide()) {
												if (entity instanceof Player)
													PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.InvincibleConquestModAnimationMessage(getNormalFlightAnimation(entity), entity.getId(), true));
											}
								if (entity instanceof LivingEntity _livEnt34 && _livEnt34.hasEffect(InvincibleConquestModMobEffects.DESTRUCTIVE_FLIGHT)) {
									if (entity instanceof LivingEntity _entity)
										_entity.removeEffect(InvincibleConquestModMobEffects.DESTRUCTIVE_FLIGHT);
								}
							}
						}
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(InvincibleConquestModMobEffects.NO_FALL, 40, 0, false, false));
					if (entity instanceof Player _player) {
						_player.getAbilities().flying = (creativeFlight == true);
						_player.onUpdateAbilities();
					}
					if (entity instanceof Player _player) {
						_player.getAbilities().mayfly = (creativeFlight == true);
						_player.onUpdateAbilities();
					}
				} else {
					creativeFlight = false;
				}
				if (new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayer _serverPlayer) {
							return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
						}
						return false;
					}
				}.checkGamemode(entity) || new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayer _serverPlayer) {
							return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.ADVENTURE;
						}
						return false;
					}
				}.checkGamemode(entity)) {
					if (entity instanceof Player _player) {
						_player.getAbilities().flying = (creativeFlight == true);
						_player.onUpdateAbilities();
					}
					if (entity instanceof Player _player) {
						_player.getAbilities().mayfly = (creativeFlight == true);
						_player.onUpdateAbilities();
					}
				}
			}
		}
	}

	private static String getNormalFlightAnimation(Entity entity) {
		String direction = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).FlightDirection;
		if ("Forward".equals(direction))
			return "fly_forward";
		if ("Backward".equals(direction))
			return "fly_back";
		if ("Left".equals(direction))
			return "fly_left";
		if ("Right".equals(direction))
			return "fly_right";
		return "fly_forward";
	}
}
