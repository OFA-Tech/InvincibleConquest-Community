package net.clozynoii.invincibleconquest.procedures;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;

import net.clozynoii.invincibleconquest.init.InvincibleConquestModMobEffects;

public class TitanLeapEffectOnEffectActiveTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world.isClientSide()) {
			SetupAnimationsProcedure.setAnimationClientside((Player) entity, "chargejump", true, "titan_leap_effect_tick");
		}
		if (!world.isClientSide()) {
			if (entity instanceof Player)
				PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.InvincibleConquestModAnimationMessage("chargejump", entity.getId(), true));
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 2, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 1, false, false));
		entity.getPersistentData().putDouble("titanleap", (entity.getPersistentData().getDouble("titanleap") + 1));
		entity.getPersistentData().putDouble("titanleapfx", (entity.getPersistentData().getDouble("titanleapfx") + 1));
		entity.getPersistentData().putDouble("titanleapfx", (entity.getPersistentData().getDouble("titanleapfx") + 1));
		if (entity.getPersistentData().getDouble("titanleap") >= 200) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(InvincibleConquestModMobEffects.TITAN_LEAP_EFFECT);
			TitanLeapEffectEffectExpiresProcedure.execute(world, x, y, z, entity);
			LeapProcedureProcedure.execute(entity);
			entity.getPersistentData().putDouble("titanleap", 0);
		}
		if (entity.getPersistentData().getDouble("titanleapfx") >= 10) {
			entity.getPersistentData().putDouble("titanleapfx", 0);
		}
		if (!(entity instanceof LivingEntity _livEnt14 && _livEnt14.hasEffect(InvincibleConquestModMobEffects.CLOUD_CIRCLE))) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(InvincibleConquestModMobEffects.CLOUD_CIRCLE, 15, 0, false, false));
		}
	}
}
