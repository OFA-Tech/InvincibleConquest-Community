package net.clozynoii.invincibleconquest.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import net.clozynoii.invincibleconquest.init.InvincibleConquestModConfig;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModGameRules;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AbilitySelectionHelper {
	public record AbilityDefinition(String displayName, List<String> aliases, boolean workInProgress, boolean randomSelectable) {
	}

	private static final List<AbilityDefinition> ABILITY_DEFINITIONS = Arrays.asList(
			new AbilityDefinition("Human", List.of("human", "none", "clear"), false, true),
			new AbilityDefinition("Viltrumite", List.of("viltrumite", "viltrum"), false, true),
			new AbilityDefinition("Speedster", List.of("speedster"), true, true),
			new AbilityDefinition("Spider", List.of("spider"), true, true),
			new AbilityDefinition("Cloning", List.of("cloning", "clone"), true, true),
			new AbilityDefinition("Explode", List.of("explode"), false, true),
			new AbilityDefinition("Portal", List.of("portal"), false, true),
			new AbilityDefinition("Beast", List.of("beast", "battlebeast"), false, true),
			new AbilityDefinition("Atom", List.of("atom", "atomeve"), true, true),
			new AbilityDefinition("Robot", List.of("robot"), true, true),
			new AbilityDefinition("Tech Jacket", List.of("techjacket", "tech_jacket"), false, true));
	private static final Map<String, AbilityDefinition> COMMAND_POWER_ALIASES = createCommandAliases();

	private static Map<String, AbilityDefinition> createCommandAliases() {
		Map<String, AbilityDefinition> aliases = new LinkedHashMap<>();
		for (AbilityDefinition definition : ABILITY_DEFINITIONS) {
			aliases.put(toCommandId(definition.displayName()), definition);
			for (String alias : definition.aliases()) {
				aliases.put(toCommandId(alias), definition);
			}
		}
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

	public static AbilityDefinition resolveAbilityDefinition(String ability) {
		if (ability == null || ability.isBlank()) return null;
		return COMMAND_POWER_ALIASES.get(toCommandId(ability.trim()));
	}

	public static boolean isPowerAvailable(String ability) {
		AbilityDefinition definition = resolveAbilityDefinition(ability);
		return definition != null && !definition.workInProgress();
	}

	public static List<AbilityDefinition> getSelectableDefinitions() {
		return ABILITY_DEFINITIONS.stream().filter(def -> !def.workInProgress()).toList();
	}

	public static List<String> getAvailablePowers() {
		return getSelectableDefinitions().stream().map(AbilityDefinition::displayName).toList();
	}

	public static List<String> getRandomSelectablePowers() {
		return getSelectableDefinitions().stream().filter(AbilityDefinition::randomSelectable).map(AbilityDefinition::displayName).toList();
	}

	public static String resolvePowerIdOrAlias(String ability) {
		AbilityDefinition definition = resolveAbilityDefinition(ability);
		if (definition == null || definition.workInProgress()) return null;
		return definition.displayName();
	}

	public static List<String> getCommandPowerIds() {
		return getSelectableDefinitions().stream().map(AbilityDefinition::displayName).map(AbilitySelectionHelper::toCommandId).distinct().toList();
	}

	public static boolean assignAbility(ServerPlayer player, String ability) {
		return assignAbility(player, ability, player.getX(), player.getY(), player.getZ());
	}

	public static boolean assignAbility(ServerPlayer player, String ability, double x, double y, double z) {
		if (player == null)
			return false;
		String resolvedAbility = resolvePowerIdOrAlias(ability);
		if (resolvedAbility == null)
			return false;
		return executeSelectorBookFlow(player, resolvedAbility, x, y, z);
	}

	private static boolean executeSelectorBookFlow(ServerPlayer player, String resolvedAbility, double x, double y, double z) {
		switch (Objects.requireNonNull(resolvedAbility)) {
			case "Human" -> SelectAbilityHumanProcedure.execute(player.level(), x, y, z, player);
			case "Viltrumite" -> SelectAbilityViltrumiteProcedure.execute(player.level(), x, y, z, player);
			case "Speedster" -> SelectAbilitySpeedsterProcedure.execute(player);
			case "Spider" -> SelectAbilitySpiderProcedure.execute(player);
			case "Cloning" -> SelectAbilityCloningProcedure.execute(player);
			case "Explode" -> SelectAbilityExplodeProcedure.execute(player);
			case "Portal" -> SelectAbilityPortalProcedure.execute(player);
			case "Beast" -> SelectAbilityBeastProcedure.execute(player);
			case "Atom" -> SelectAbilityAtomProcedure.execute(player);
			case "Robot" -> SelectAbilityRobotProcedure.execute(player);
			case "Tech Jacket" -> SelectAbilityTechJacketProcedure.execute(player);
			default -> {
				return false;
			}
		}
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
