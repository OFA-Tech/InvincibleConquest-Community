package net.clozynoii.invincibleconquest.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
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
														if (success) changed++;
													}
													if (changed == 0) {
														ctx.getSource().sendFailure(Component.literal("Invalid/disabled/WIP power: " + input));
														return 0;
													}
													ctx.getSource().sendSuccess(() -> Component.literal("Updated power for " + changed + " player(s)."), true);
													return changed;
												}))))
								.then(Commands.literal("clear").then(Commands.argument("target", EntityArgument.players()).executes(ctx -> {
									int changed = 0;
									for (ServerPlayer target : EntityArgument.getPlayers(ctx, "target")) {
										if (AbilitySelectionHelper.assignAbility(target, "Human")) changed++;
									}
									ctx.getSource().sendSuccess(() -> Component.literal("Cleared power for " + changed + " player(s)."), true);
									return changed;
								})))
								.then(Commands.literal("list").executes(ctx -> {
									List<String> ids = new ArrayList<>(AbilitySelectionHelper.getCommandPowerIds());
									ctx.getSource().sendSuccess(() -> Component.literal("Available powers: " + String.join(", ", ids)), false);
									return ids.size();
								}))))));
	}

	private static CompletableFuture<Suggestions> suggestPowers(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
		List<String> suggestions = new ArrayList<>(AbilitySelectionHelper.getCommandPowerIds());
		suggestions.add("random");
		return SharedSuggestionProvider.suggest(suggestions, builder);
	}
}
