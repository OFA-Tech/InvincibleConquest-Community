
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
import net.clozynoii.invincibleconquest.procedures.SelectAbilityViltrumiteProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilityTechJacketProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilitySpiderProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilitySpeedsterProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilityRobotProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilityPortalProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilityHumanProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilityExplodeProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilityCloningProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilityBeastProcedure;
import net.clozynoii.invincibleconquest.procedures.SelectAbilityAtomProcedure;
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
		if (entity instanceof ServerPlayer serverPlayer && buttonID == 0) {
			if (!AbilitySelectionHelper.isRandomSelectionAllowed(entity)) {
				serverPlayer.displayClientMessage(Component.literal("Random selection is disabled."), false);
				return;
			}
			if (!AbilitySelectionHelper.assignRandomPower(serverPlayer, serverPlayer.getRandom())) {
				return;
			}
		}
		if (buttonID == 1) {

			SelectAbilityHumanProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 2) {

			SelectAbilityViltrumiteProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 3) {

			SelectAbilitySpeedsterProcedure.execute(entity);
		}
		if (buttonID == 4) {

			SelectAbilitySpiderProcedure.execute(entity);
		}
		if (buttonID == 5) {

			SelectAbilityCloningProcedure.execute(entity);
		}
		if (buttonID == 6) {

			SelectAbilityExplodeProcedure.execute(entity);
		}
		if (buttonID == 7) {

			SelectAbilityPortalProcedure.execute(entity);
		}
		if (buttonID == 8) {

			SelectAbilityBeastProcedure.execute(entity);
		}
		if (buttonID == 9) {

			SelectAbilityAtomProcedure.execute(entity);
		}
		if (buttonID == 10) {

			SelectAbilityRobotProcedure.execute(entity);
		}
		if (buttonID == 11) {

			SelectAbilityTechJacketProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		InvincibleConquestMod.addNetworkMessage(MenuAbilitySelectionButtonMessage.TYPE, MenuAbilitySelectionButtonMessage.STREAM_CODEC, MenuAbilitySelectionButtonMessage::handleData);
	}
}
