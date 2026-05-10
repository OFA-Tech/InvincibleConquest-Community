package net.clozynoii.invincibleconquest.init;

import net.neoforged.neoforge.common.ModConfigSpec;

public class InvincibleConquestModConfig {

	public static final ModConfigSpec SPEC;

	public static final ModConfigSpec.BooleanValue ENABLE_RANDOM_SELECTION;
	public static final ModConfigSpec.BooleanValue FORCE_RANDOM_SELECTION;

	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

		builder
				.translation("invincible_conquest.configuration.random_power")
				.push("random_power");

		ENABLE_RANDOM_SELECTION = builder
				.comment("Allows the mod to randomly select a power.")
				.translation("invincible_conquest.configuration.random_power.enable_random_selection")
				.define("enable_random_selection", true);

		FORCE_RANDOM_SELECTION = builder
				.comment("Forces random power selection instead of allowing normal selection.")
				.translation("invincible_conquest.configuration.random_power.force_random_selection")
				.define("force_random_selection", false);

		builder.pop();

		SPEC = builder.build();
	}
}