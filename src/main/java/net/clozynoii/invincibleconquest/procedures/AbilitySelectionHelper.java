package net.clozynoii.invincibleconquest.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import net.clozynoii.invincibleconquest.init.InvincibleConquestModConfig;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModGameRules;
import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbilitySelectionHelper {
	private static final List<String> MENU_ABILITIES = Arrays.asList("Human", "Viltrumite", "Speedster", "Spider", "Cloning", "Explode", "Portal", "Battle Beast", "Atom Eve", "Robot", "Tech Jacket");
	private static final Set<String> DISABLED_ABILITIES = new HashSet<>(Arrays.asList("Portal"));

	public static boolean isRandomSelectionAllowed(Entity entity) {
		if (entity == null || entity.level().isClientSide())
			return false;
		boolean configEnabled = InvincibleConquestModConfig.ENABLE_RANDOM_SELECTION.get();
		boolean gameruleEnabled = entity.level().getLevelData().getGameRules().getBoolean(InvincibleConquestModGameRules.INVINCIBLE_CONQUEST_RANDOM_SELECTION);
		return configEnabled || gameruleEnabled;
	}

	public static boolean isForceRandomSelection(Entity entity) {
		if (!isRandomSelectionAllowed(entity))
			return false;
		boolean configForced = InvincibleConquestModConfig.FORCE_RANDOM_SELECTION.get();
		boolean gameruleForced = entity.level().getLevelData().getGameRules().getBoolean(InvincibleConquestModGameRules.INVINCIBLE_CONQUEST_FORCE_RANDOM_SELECTION);
		return configForced || gameruleForced;
	}

	public static boolean isSelectableForRandom(String ability) {
		return ability != null && !ability.isBlank() && MENU_ABILITIES.contains(ability) && !DISABLED_ABILITIES.contains(ability);
	}

	public static List<String> getRandomSelectablePowers() {
		return MENU_ABILITIES.stream().filter(AbilitySelectionHelper::isSelectableForRandom).toList();
	}

	public static boolean assignAbility(ServerPlayer player, String ability) {
		if (player == null || !isSelectableForRandom(ability))
			return false;
		InvincibleConquestModVariables.PlayerVariables vars = player.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
		vars.PlayerAbility = ability;
		vars.syncPlayerVariables(player);
		return true;
	}

	public static boolean assignRandomPower(ServerPlayer player, RandomSource random) {
		List<String> powers = getRandomSelectablePowers();
		if (powers.isEmpty()) {
			player.displayClientMessage(Component.literal("No valid random powers are available."), false);
			return false;
		}
		String ability = powers.get(random.nextInt(powers.size()));
		return assignAbility(player, ability);
	}
}
