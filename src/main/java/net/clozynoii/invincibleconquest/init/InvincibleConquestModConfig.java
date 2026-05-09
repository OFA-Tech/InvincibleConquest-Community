package net.clozynoii.invincibleconquest.init;

import net.neoforged.neoforge.common.ModConfigSpec;

public class InvincibleConquestModConfig {
	public static final ModConfigSpec SPEC;
	public static final ModConfigSpec.BooleanValue ENABLE_RANDOM_SELECTION;
	public static final ModConfigSpec.BooleanValue FORCE_RANDOM_SELECTION;

	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
		builder.push("random_power");
		ENABLE_RANDOM_SELECTION = builder.define("enable_random_selection", true);
		FORCE_RANDOM_SELECTION = builder.define("force_random_selection", false);
		builder.pop();
		SPEC = builder.build();
	}
}
