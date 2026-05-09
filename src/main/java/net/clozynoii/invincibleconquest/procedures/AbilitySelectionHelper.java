package net.clozynoii.invincibleconquest.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import net.clozynoii.invincibleconquest.init.InvincibleConquestModConfig;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModGameRules;
import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Locale;

public class AbilitySelectionHelper {
	private static final List<String> MENU_ABILITIES = Arrays.asList("Human", "Viltrumite", "Speedster", "Spider", "Cloning", "Explode", "Portal", "Battle Beast", "Atom Eve", "Robot", "Tech Jacket");
	private static final Set<String> DISABLED_POWER_IDS = Set.of("speedster", "cloning", "atom", "robot");
	private static final Map<String, String> COMMAND_POWER_TO_DISPLAY = new HashMap<>();

	static {
		for (String ability : MENU_ABILITIES) {
			COMMAND_POWER_TO_DISPLAY.put(normalizePowerId(ability), ability);
		}
		COMMAND_POWER_TO_DISPLAY.put("theimmortal", "The Immortal");
	}

	private static String normalizePowerId(String ability) {
		if (ability == null) {
			return "";
		}
		String normalized = ability.trim().toLowerCase(Locale.ROOT).replace(" ", "");
		normalized = normalized.replace("_", "");
		if (normalized.equals("atomeve")) {
			return "atom";
		}
		return normalized;
	}

	public static String resolvePowerDisplayName(String powerInput) {
		return COMMAND_POWER_TO_DISPLAY.get(normalizePowerId(powerInput));
	}

	public static List<String> getAvailablePowerCommandIds() {
		List<String> available = new ArrayList<>();
		for (Map.Entry<String, String> entry : COMMAND_POWER_TO_DISPLAY.entrySet()) {
			if (isPowerAvailable(entry.getValue())) {
				available.add(entry.getKey());
			}
		}
		return available.stream().distinct().sorted().toList();
	}

	public static boolean isPowerDisabled(String ability) {
		return DISABLED_POWER_IDS.contains(normalizePowerId(ability));
	}

	public static boolean isPowerAvailable(String ability) {
		if (ability == null || ability.isBlank()) {
			return false;
		}
		String resolvedDisplay = resolvePowerDisplayName(ability);
		if (resolvedDisplay != null) {
			ability = resolvedDisplay;
		}
		for (String menuAbility : MENU_ABILITIES) {
			if (menuAbility.equalsIgnoreCase(ability)) {
				return !isPowerDisabled(menuAbility);
			}
		}
		return false;
	}

	public static List<String> getAvailablePowers() {
		List<String> available = new ArrayList<>();
		for (String menuAbility : MENU_ABILITIES) {
			if (isPowerAvailable(menuAbility)) {
				available.add(menuAbility);
			}
		}
		return available;
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

	public static boolean isSelectableForRandom(String ability) {
		return isPowerAvailable(ability);
	}

	public static List<String> getRandomSelectablePowers() {
		return getAvailablePowers();
	}

	public static boolean assignAbility(ServerPlayer player, String ability) {
		if (player == null)
			return false;
		String resolvedDisplay = resolvePowerDisplayName(ability);
		if (resolvedDisplay != null) {
			ability = resolvedDisplay;
		}
		if (!isPowerAvailable(ability))
			return false;
		String requestedAbility = ability;
		String resolvedAbility = getAvailablePowers().stream().filter(power -> power.equalsIgnoreCase(requestedAbility)).findFirst().orElse(requestedAbility);
		InvincibleConquestModVariables.PlayerVariables vars = player.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
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
