package net.clozynoii.invincibleconquest.procedures;

import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.clozynoii.invincibleconquest.world.inventory.MenuAbilitySelectionMenu;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModItems;

import io.netty.buffer.Unpooled;

public class OpenSelectionGUIProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof ServerPlayer _ent) {
			if (AbilitySelectionHelper.isForceRandomSelection(entity) && (entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerAbility).equals("None")) {
				if (AbilitySelectionHelper.assignRandomPower(_ent, _ent.getRandom())) {
					_ent.displayClientMessage(Component.literal("Random power selection is forced on this server/world."), false);
				}
				return;
			}
			BlockPos _bpos = BlockPos.containing(x, y, z);
			_ent.openMenu(new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return Component.literal("MenuAbilitySelection");
				}

				@Override
				public boolean shouldTriggerClientSideContainerClosingOnOpen() {
					return false;
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					return new MenuAbilitySelectionMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
				}
			}, _bpos);
		}
		if (!(new Object() {
 			public boolean checkGamemode(Entity _ent) {
 				if (_ent instanceof ServerPlayer _serverPlayer) {
 					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
 				}
 				return false;
 			}
 		}.checkGamemode(entity))) {
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(InvincibleConquestModItems.SELECTION_BOOK.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
		}
	}
}
