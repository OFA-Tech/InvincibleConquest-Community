
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.clozynoii.invincibleconquest.init;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.GameRules;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class InvincibleConquestModGameRules {
	public static GameRules.Key<GameRules.BooleanValue> SPAWN_INVINCIBLE_MOBS;
	public static GameRules.Key<GameRules.BooleanValue> INVINCIBLE_ABILITY_DESTRUCTION;
	public static GameRules.Key<GameRules.IntegerValue> FLAXAN_TIME_DILATION;
	public static GameRules.Key<GameRules.BooleanValue> INVINCIBLE_ABILITY_BLOCK_DROP;
	public static GameRules.Key<GameRules.BooleanValue> SERVER_FRIENDLY;
	public static GameRules.Key<GameRules.IntegerValue> EXP_PER_LEVEL;
	public static GameRules.Key<GameRules.IntegerValue> SP_PER_LEVEL;
	public static GameRules.Key<GameRules.IntegerValue> BIRTHDAY_TIME;
	public static GameRules.Key<GameRules.IntegerValue> STRENGTH_ATTACK_DAMAGE;
	public static GameRules.Key<GameRules.IntegerValue> STRENGTH_KNOCKBACK;
	public static GameRules.Key<GameRules.IntegerValue> DURABILITY_DAMAGE_RESISTANCE;
	public static GameRules.Key<GameRules.IntegerValue> DURABILITY_KNOCKBACK_RESISTANCE;
	public static GameRules.Key<GameRules.IntegerValue> AGILITY_MOVEMENT_SPEED;
	public static GameRules.Key<GameRules.IntegerValue> AGILITY_ATTACK_SPEED;
	public static GameRules.Key<GameRules.IntegerValue> STM_STAMINA_BONUS;
	public static GameRules.Key<GameRules.IntegerValue> STM_STAMINA_DRAIN;
	public static GameRules.Key<GameRules.IntegerValue> VITALITY_HEALTH_BOOST;
	public static GameRules.Key<GameRules.IntegerValue> VITALITY_REGENERATION_RATE;
	public static GameRules.Key<GameRules.IntegerValue> FOC_COOLDOWN_REDUCTION;
	public static GameRules.Key<GameRules.IntegerValue> FOC_MOVE_DURATION;
	public static GameRules.Key<GameRules.IntegerValue> INTEXP_BONUS;
	public static GameRules.Key<GameRules.IntegerValue> VILTRUMITE_AGE_BOOST;
	public static GameRules.Key<GameRules.IntegerValue> VILTRUMITE_MAX_AGE_BONUS;
	public static GameRules.Key<GameRules.BooleanValue> SERVER_REP_LEVELING;
	public static GameRules.Key<GameRules.BooleanValue> CANNIBALISM;
	public static GameRules.Key<GameRules.BooleanValue> INVINCIBLE_CONQUEST_RANDOM_SELECTION;
	public static GameRules.Key<GameRules.BooleanValue> INVINCIBLE_CONQUEST_FORCE_RANDOM_SELECTION;

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		SPAWN_INVINCIBLE_MOBS = GameRules.register("spawnInvincibleMobs", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
		INVINCIBLE_ABILITY_DESTRUCTION = GameRules.register("invincibleAbilityDestruction", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
		FLAXAN_TIME_DILATION = GameRules.register("flaxanTimeDilation", GameRules.Category.UPDATES, GameRules.IntegerValue.create(2));
		INVINCIBLE_ABILITY_BLOCK_DROP = GameRules.register("invincibleAbilityBlockDrop", GameRules.Category.DROPS, GameRules.BooleanValue.create(false));
		SERVER_FRIENDLY = GameRules.register("serverFriendly", GameRules.Category.MISC, GameRules.BooleanValue.create(false));
		EXP_PER_LEVEL = GameRules.register("expPerLevel", GameRules.Category.PLAYER, GameRules.IntegerValue.create(25));
		SP_PER_LEVEL = GameRules.register("sPPerLevel", GameRules.Category.PLAYER, GameRules.IntegerValue.create(5));
		BIRTHDAY_TIME = GameRules.register("birthdayTime", GameRules.Category.PLAYER, GameRules.IntegerValue.create(24000));
		STRENGTH_ATTACK_DAMAGE = GameRules.register("strengthAttackDamage", GameRules.Category.PLAYER, GameRules.IntegerValue.create(25));
		STRENGTH_KNOCKBACK = GameRules.register("strengthKnockback", GameRules.Category.PLAYER, GameRules.IntegerValue.create(15));
		DURABILITY_DAMAGE_RESISTANCE = GameRules.register("durabilityDamageResistance", GameRules.Category.PLAYER, GameRules.IntegerValue.create(20));
		DURABILITY_KNOCKBACK_RESISTANCE = GameRules.register("durabilityKnockbackResistance", GameRules.Category.PLAYER, GameRules.IntegerValue.create(1));
		AGILITY_MOVEMENT_SPEED = GameRules.register("agilityMovementSpeed", GameRules.Category.PLAYER, GameRules.IntegerValue.create(1));
		AGILITY_ATTACK_SPEED = GameRules.register("agilityAttackSpeed", GameRules.Category.PLAYER, GameRules.IntegerValue.create(2));
		STM_STAMINA_BONUS = GameRules.register("sTMStaminaBonus", GameRules.Category.PLAYER, GameRules.IntegerValue.create(400));
		STM_STAMINA_DRAIN = GameRules.register("sTMStaminaDrain", GameRules.Category.PLAYER, GameRules.IntegerValue.create(25));
		VITALITY_HEALTH_BOOST = GameRules.register("vitalityHealthBoost", GameRules.Category.PLAYER, GameRules.IntegerValue.create(80));
		VITALITY_REGENERATION_RATE = GameRules.register("vitalityRegenerationRate", GameRules.Category.PLAYER, GameRules.IntegerValue.create(4));
		FOC_COOLDOWN_REDUCTION = GameRules.register("fOCCooldownReduction", GameRules.Category.PLAYER, GameRules.IntegerValue.create(25));
		FOC_MOVE_DURATION = GameRules.register("fOCMoveDuration", GameRules.Category.PLAYER, GameRules.IntegerValue.create(5));
		INTEXP_BONUS = GameRules.register("iNTEXPBonus", GameRules.Category.PLAYER, GameRules.IntegerValue.create(25));
		VILTRUMITE_AGE_BOOST = GameRules.register("viltrumiteAgeBoost", GameRules.Category.PLAYER, GameRules.IntegerValue.create(1));
		VILTRUMITE_MAX_AGE_BONUS = GameRules.register("viltrumiteMaxAgeBonus", GameRules.Category.PLAYER, GameRules.IntegerValue.create(100));
		SERVER_REP_LEVELING = GameRules.register("serverRepLeveling", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
		CANNIBALISM = GameRules.register("cannibalism", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
		INVINCIBLE_CONQUEST_RANDOM_SELECTION = GameRules.register("invincibleConquestRandomSelection", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
		INVINCIBLE_CONQUEST_FORCE_RANDOM_SELECTION = GameRules.register("invincibleConquestForceRandomSelection", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
	}
}
