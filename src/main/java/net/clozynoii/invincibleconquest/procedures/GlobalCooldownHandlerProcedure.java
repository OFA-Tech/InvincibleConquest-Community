package net.clozynoii.invincibleconquest.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModGameRules;

import javax.annotation.Nullable;

import java.util.ArrayList;

@EventBusSubscriber
public class GlobalCooldownHandlerProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).AgeTimer > 0) {
				{
					InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
					_vars.AgeTimer = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).AgeTimer - 1;
					_vars.syncPlayerVariables(entityiterator);
				}
			} else if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).AgeTimer == 0) {
				if (entityiterator instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("Age Up " + new java.text.DecimalFormat("##").format(entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAge) + " > "
							+ new java.text.DecimalFormat("##").format(entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAge + 1))), true);
				{
					InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
					_vars.PlayerAge = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAge + 1;
					_vars.syncPlayerVariables(entityiterator);
				}
				{
					InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
					_vars.AgeTimer = (world.getLevelData().getGameRules().getInt(InvincibleConquestModGameRules.BIRTHDAY_TIME));
					_vars.syncPlayerVariables(entityiterator);
				}
			}
			if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).RegenTimer > 0) {
				{
					InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
					_vars.RegenTimer = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).RegenTimer - 1;
					_vars.syncPlayerVariables(entityiterator);
				}
			} else if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).RegenTimer == 0) {
				if (entityiterator.isAlive()) {
					if ((entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Viltrumite")) {
						if (entityiterator instanceof LivingEntity _entity)
							_entity.setHealth((float) ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
									+ Math.round((entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerVitality + entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).AgeBoost)
											/ (100 / (world.getLevelData().getGameRules().getInt(InvincibleConquestModGameRules.VITALITY_REGENERATION_RATE))))));
					} else {
						if (entityiterator instanceof LivingEntity _entity)
							_entity.setHealth((float) ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + Math
									.round(entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerVitality / (100 / (world.getLevelData().getGameRules().getInt(InvincibleConquestModGameRules.VITALITY_REGENERATION_RATE))))));
					}
				}
				{
					InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
					_vars.RegenTimer = 20;
					_vars.syncPlayerVariables(entityiterator);
				}
			}
			if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).StaminaTimer > 0) {
				{
					InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
					_vars.StaminaTimer = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).StaminaTimer - 1;
					_vars.syncPlayerVariables(entityiterator);
				}
			} else if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).StaminaTimer == 0) {
				if ((entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("Viltrumite")) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.PlayerCurrentStamina = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerCurrentStamina + 10
								+ Math.round((entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerStamina + entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).AgeBoost)
										/ (100 / (world.getLevelData().getGameRules().getInt(InvincibleConquestModGameRules.STM_STAMINA_DRAIN))));
						_vars.syncPlayerVariables(entityiterator);
					}
				} else {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.PlayerCurrentStamina = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerCurrentStamina + 5
								+ Math.round(entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerStamina / (100 / (world.getLevelData().getGameRules().getInt(InvincibleConquestModGameRules.STM_STAMINA_DRAIN))));
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				{
					InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
					_vars.StaminaTimer = 20;
					_vars.syncPlayerVariables(entityiterator);
				}
			}
			if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown1a > 0 || entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown2a > 0
					|| entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown3a > 0 || entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown4a > 0
					|| entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown5a > 0 || entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown1b > 0
					|| entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown2b > 0 || entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown3b > 0
					|| entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown4b > 0 || entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cooldown5b > 0) {
				InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
				_vars.tickAbilityCooldowns();
				_vars.syncPlayerVariables(entityiterator);
			}
			if (InvincibleConquestModVariables.MapVariables.get(world).WageTimer > 0) {
				InvincibleConquestModVariables.MapVariables.get(world).WageTimer = InvincibleConquestModVariables.MapVariables.get(world).WageTimer - 1;
				InvincibleConquestModVariables.MapVariables.get(world).syncData(world);
			} else if (InvincibleConquestModVariables.MapVariables.get(world).WageTimer == 0) {
				if (entityiterator instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("\u00A7aYou've Been Paid!"), false);
				if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation < 50) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 100;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation >= 50 && entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation < 100) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 250;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation >= 100 && entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation < 250) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 300;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation >= 250 && entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation < 400) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 350;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation >= 400 && entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation < 500) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 400;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerReputation >= 500) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 550;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if ((InvincibleConquestModVariables.MapVariables.get(world).EarthOwner).equals(entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerFaction)) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 500;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if ((InvincibleConquestModVariables.MapVariables.get(world).MoonOwner).equals(entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerFaction)) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 500;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if ((InvincibleConquestModVariables.MapVariables.get(world).MarsOwner).equals(entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerFaction)) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 500;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if ((InvincibleConquestModVariables.MapVariables.get(world).ViltrumOwner).equals(entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerFaction)) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 1500;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				if ((InvincibleConquestModVariables.MapVariables.get(world).TalescriaOwner).equals(entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerFaction)) {
					{
						InvincibleConquestModVariables.PlayerVariables _vars = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
						_vars.Balance = entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance + 500;
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				InvincibleConquestModVariables.MapVariables.get(world).WageTimer = 24000;
				InvincibleConquestModVariables.MapVariables.get(world).syncData(world);
			}
		}
	}
}
