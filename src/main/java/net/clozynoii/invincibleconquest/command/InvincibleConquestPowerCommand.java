package net.clozynoii.invincibleconquest.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.stream.Stream;

import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import net.clozynoii.invincibleconquest.procedures.AbilitySelectionHelper;

@EventBusSubscriber
public class InvincibleConquestPowerCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("invincibleconquest").then(Commands.literal("admin").requires(s -> s.hasPermission(2)).then(Commands.literal("power")
				.then(Commands.literal("set").then(Commands.argument("target", EntityArgument.player()).then(Commands.argument("power", StringArgumentType.word()).suggests((ctx, builder) -> {
					return SharedSuggestionProvider.suggest(Stream.concat(AbilitySelectionHelper.getAvailablePowers().stream().map(String::toLowerCase), Stream.of("random")).toList(), builder);
				}).executes(ctx -> {
					ServerPlayer target;
					try {
						target = EntityArgument.getPlayer(ctx, "target");
					} catch (CommandSyntaxException e) {
						return 0;
					}
					String ability = StringArgumentType.getString(ctx, "power");
					if ("random".equalsIgnoreCase(ability)) {
						if (!AbilitySelectionHelper.assignRandomPower(target, target.getRandom())) {
							ctx.getSource().sendFailure(Component.literal("No available powers found for random selection."));
							return 0;
						}
						ctx.getSource().sendSuccess(() -> Component.literal("Randomized power for " + target.getScoreboardName()), true);
						return 1;
					}
					if (AbilitySelectionHelper.isPowerDisabled(ability)) {
						ctx.getSource().sendFailure(Component.literal("Power is disabled/WIP and cannot be assigned: " + ability));
						return 0;
					}
					if (!AbilitySelectionHelper.assignAbility(target, ability)) {
						ctx.getSource().sendFailure(Component.literal("Unknown or unavailable power: " + ability));
						return 0;
					}
					target.displayClientMessage(Component.literal("Your power was set to " + ability + " by an admin."), false);
					ctx.getSource().sendSuccess(() -> Component.literal("Set " + target.getScoreboardName() + " power to " + ability), true);
					return 1;
				})))));
	}
}
