package net.clozynoii.invincibleconquest.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import net.clozynoii.invincibleconquest.procedures.AbilitySelectionHelper;

@EventBusSubscriber
public class InvincibleConquestPowerCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("invincibleconquest").requires(s -> s.hasPermission(2)).then(Commands.literal("power")
				.then(Commands.literal("set").then(Commands.argument("target", EntityArgument.player()).then(Commands.argument("power", StringArgumentType.greedyString()).executes(ctx -> {
					ServerPlayer target;
					try {
						target = EntityArgument.getPlayer(ctx, "target");
					} catch (CommandSyntaxException e) {
						return 0;
					}
					String ability = StringArgumentType.getString(ctx, "power");
					if (!AbilitySelectionHelper.assignAbility(target, ability)) {
						ctx.getSource().sendFailure(Component.literal("Invalid/disabled/WIP power: " + ability));
						return 0;
					}
					target.displayClientMessage(Component.literal("Your power was set to " + ability + " by an admin."), false);
					ctx.getSource().sendSuccess(() -> Component.literal("Set " + target.getScoreboardName() + " power to " + ability), true);
					return 1;
				})))).then(Commands.literal("random").then(Commands.argument("target", EntityArgument.player()).executes(ctx -> {
					ServerPlayer target;
					try {
						target = EntityArgument.getPlayer(ctx, "target");
					} catch (CommandSyntaxException e) {
						return 0;
					}
					if (!AbilitySelectionHelper.assignRandomPower(target, target.getRandom())) {
						ctx.getSource().sendFailure(Component.literal("No valid random powers are available."));
						return 0;
					}
					ctx.getSource().sendSuccess(() -> Component.literal("Randomized power for " + target.getScoreboardName()), true);
					return 1;
				})))));
	}
}
