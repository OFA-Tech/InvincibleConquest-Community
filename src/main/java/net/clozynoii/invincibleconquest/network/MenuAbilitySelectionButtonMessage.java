
package net.clozynoii.invincibleconquest.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.clozynoii.invincibleconquest.world.inventory.MenuAbilitySelectionMenu;
import net.clozynoii.invincibleconquest.InvincibleConquestMod;
import net.clozynoii.invincibleconquest.procedures.AbilitySelectionHelper;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record MenuAbilitySelectionButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<MenuAbilitySelectionButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(InvincibleConquestMod.MODID, "menu_ability_selection_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, MenuAbilitySelectionButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, MenuAbilitySelectionButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new MenuAbilitySelectionButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<MenuAbilitySelectionButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final MenuAbilitySelectionButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				Player entity = context.player();
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleButtonAction(entity, buttonID, x, y, z);
			}).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = MenuAbilitySelectionMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (!(entity instanceof ServerPlayer serverPlayer)) {
			return;
		}
		if (buttonID == 0) {
			if (!AbilitySelectionHelper.isRandomSelectionAllowed(entity)) {
				serverPlayer.displayClientMessage(Component.literal("Random selection is disabled."), false);
				return;
			}
			AbilitySelectionHelper.assignRandomPower(serverPlayer, serverPlayer.getRandom());
			return;
		}
		String requestedPower = switch (buttonID) {
			case 1 -> "Human";
			case 2 -> "Viltrumite";
			case 3 -> "Speedster";
			case 4 -> "Spider";
			case 5 -> "Cloning";
			case 6 -> "Explode";
			case 7 -> "Portal";
			case 8 -> "Beast";
			case 9 -> "Atom";
			case 10 -> "Robot";
			case 11 -> "Tech Jacket";
			default -> null;
		};
		if (requestedPower != null && !AbilitySelectionHelper.assignAbility(serverPlayer, requestedPower, x, y, z)) {
			serverPlayer.displayClientMessage(Component.literal("That power is unavailable."), false);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		InvincibleConquestMod.addNetworkMessage(MenuAbilitySelectionButtonMessage.TYPE, MenuAbilitySelectionButtonMessage.STREAM_CODEC, MenuAbilitySelectionButtonMessage::handleData);
	}
}
