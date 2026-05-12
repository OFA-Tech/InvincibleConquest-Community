package net.clozynoii.invincibleconquest.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;
import net.clozynoii.invincibleconquest.procedures.AbilitySelectionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class InvincibleConquestPowerCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("invincibleconquest")
				.then(Commands.literal("admin").requires(s -> s.hasPermission(2))
						.then(Commands.literal("power")
								.then(Commands.literal("set")
										.then(Commands.argument("target", EntityArgument.players())
												.then(Commands.argument("power", StringArgumentType.word()).suggests(InvincibleConquestPowerCommand::suggestPowers).executes(ctx -> {
													String input = StringArgumentType.getString(ctx, "power");
													int changed = 0;
													for (ServerPlayer target : EntityArgument.getPlayers(ctx, "target")) {
														boolean success = "random".equalsIgnoreCase(input) ? AbilitySelectionHelper.assignRandomPower(target, target.getRandom()) : AbilitySelectionHelper.assignAbility(target, input);
														if (success)
															changed++;
													}
													if (changed == 0) {
														ctx.getSource().sendFailure(Component.literal("Power is invalid or marked work-in-progress: " + input));
														return 0;
													}
													int finalChanged = changed;
													ctx.getSource().sendSuccess(() -> Component.literal("Updated power for " + finalChanged + " player(s)."), true);
													return changed;
												}))))
								.then(Commands.literal("clear").then(Commands.argument("target", EntityArgument.players()).executes(ctx -> {
									int changed = (int) EntityArgument.getPlayers(ctx, "target").stream().filter(target -> AbilitySelectionHelper.assignAbility(target, "Human")).count();
									ctx.getSource().sendSuccess(() -> Component.literal("Cleared power for " + changed + " player(s)."), true);
									return changed;
								})))
								.then(Commands.literal("list").executes(ctx -> {
									List<String> ids = new ArrayList<>(AbilitySelectionHelper.getCommandPowerIds());
									ctx.getSource().sendSuccess(() -> Component.literal("Available powers: " + String.join(", ", ids)), false);
									return ids.size();
								})))
						.then(Commands.literal("cooldown")
								.then(Commands.literal("reset").then(Commands.argument("target", EntityArgument.players()).executes(ctx -> resetCooldowns(ctx, "target"))))
								.then(Commands.literal("clear").then(Commands.argument("target", EntityArgument.players()).executes(ctx -> resetCooldowns(ctx, "target")))))));
	}

	private static int resetCooldowns(CommandContext<CommandSourceStack> ctx, String targetArg) throws CommandSyntaxException {
		int affected = 0;
		for (ServerPlayer target : EntityArgument.getPlayers(ctx, targetArg)) {
			InvincibleConquestModVariables.PlayerVariables vars = target.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
			vars.resetAbilityCooldowns();
			vars.syncPlayerVariables(target);
			affected++;
		}
		int finalAffected = affected;
		ctx.getSource().sendSuccess(() -> Component.literal("Reset ability cooldowns for " + finalAffected + " player(s)."), true);
		return affected;
	}

	private static CompletableFuture<Suggestions> suggestPowers(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
		List<String> suggestions = new ArrayList<>(AbilitySelectionHelper.getCommandPowerIds());
		suggestions.add("random");
		return SharedSuggestionProvider.suggest(suggestions, builder);
	}
}
