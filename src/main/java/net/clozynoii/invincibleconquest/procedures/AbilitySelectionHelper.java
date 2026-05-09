package net.clozynoii.invincibleconquest.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import net.clozynoii.invincibleconquest.init.InvincibleConquestModConfig;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModGameRules;
import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class AbilitySelectionHelper {
	private static final List<String> MENU_ABILITIES = Arrays.asList("Human", "Viltrumite", "Speedster", "Spider", "Cloning", "Explode", "Portal", "Battle Beast", "Atom", "Robot", "Tech Jacket");
	private static final Set<String> DISABLED_ABILITY_IDS = Set.of("speedster", "cloning", "atom", "robot");
	private static final Map<String, String> COMMAND_POWER_ALIASES = createCommandAliases();

	private static Map<String, String> createCommandAliases() {
		Map<String, String> aliases = new LinkedHashMap<>();
		for (String ability : MENU_ABILITIES) {
			aliases.put(toCommandId(ability), ability);
		}
		aliases.put("beast", "Battle Beast");
		aliases.put("battlebeast", "Battle Beast");
		aliases.put("theimmortal", "The Immortal");
		return aliases;
	}

	public static String toCommandId(String ability) {
		if (ability == null) return "";
		return ability.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "");
	}

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

	public static boolean isPowerAvailable(String ability) {
		return resolvePowerIdOrAlias(ability) != null;
	}

	public static List<String> getAvailablePowers() {
		return MENU_ABILITIES.stream().filter(ability -> !DISABLED_ABILITY_IDS.contains(toCommandId(ability))).toList();
	}

	public static List<String> getRandomSelectablePowers() {
		return getAvailablePowers();
	}

	public static String resolvePowerIdOrAlias(String ability) {
		if (ability == null || ability.isBlank()) return null;
		String requestedAbility = ability.trim();
		String aliasResolved = COMMAND_POWER_ALIASES.get(toCommandId(requestedAbility));
		if (aliasResolved != null) {
			requestedAbility = aliasResolved;
		}
		String requestedAbilityFinal = requestedAbility;
		String resolvedAbility = getAvailablePowers().stream().filter(power -> power.equalsIgnoreCase(requestedAbilityFinal)).findFirst().orElse(null);
		return resolvedAbility;
	}

	public static List<String> getCommandPowerIds() {
		return getAvailablePowers().stream().map(AbilitySelectionHelper::toCommandId).distinct().toList();
	}

	public static boolean assignAbility(ServerPlayer player, String ability) {
		if (player == null)
			return false;
		String resolvedAbility = resolvePowerIdOrAlias(ability);
		if (resolvedAbility == null)
			return false;
		InvincibleConquestModVariables.PlayerVariables vars = player.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
		if (resolvedAbility.equals(vars.PlayerAbility)) return true;
		vars.PlayerAbility = resolvedAbility;
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
