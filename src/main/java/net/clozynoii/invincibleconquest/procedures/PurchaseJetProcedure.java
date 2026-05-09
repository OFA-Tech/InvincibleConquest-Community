package net.clozynoii.invincibleconquest.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;
import net.clozynoii.invincibleconquest.init.InvincibleConquestModEntities;
import net.clozynoii.invincibleconquest.entity.FighterJetEntity;

import java.util.HashMap;
import java.util.Comparator;
import java.util.ArrayList;

public class PurchaseJetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		if (entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance >= entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cost) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = InvincibleConquestModEntities.FIGHTER_JET.get().spawn(_level, BlockPos.containing(x, y + 100, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
				}
			}
			if (!world.getEntitiesOfClass(FighterJetEntity.class, AABB.ofSize(new Vec3(x, y, z), 1500, 1500, 1500), e -> true).isEmpty()) {
				if (((Entity) world.getEntitiesOfClass(FighterJetEntity.class, AABB.ofSize(new Vec3(x, y, z), 1500, 1500, 1500), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null)) instanceof TamableAnimal _toTame && entity instanceof Player _owner)
					_toTame.tame(_owner);
				if (((Entity) world.getEntitiesOfClass(FighterJetEntity.class, AABB.ofSize(new Vec3(x, y, z), 1500, 1500, 1500), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null)) instanceof FighterJetEntity _datEntSetI)
					_datEntSetI.getEntityData().set(FighterJetEntity.DATA_XTarget, (int) new Object() {
						double convert(String s) {
							try {
 								return Double.parseDouble(s.trim());
 							} catch (Exception e) {
 							}
 							return 0;
 						}
					}.convert(getText(guistate, "text:XValue")));
				if (((Entity) world.getEntitiesOfClass(FighterJetEntity.class, AABB.ofSize(new Vec3(x, y, z), 1500, 1500, 1500), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null)) instanceof FighterJetEntity _datEntSetI)
					_datEntSetI.getEntityData().set(FighterJetEntity.DATA_ZTarget, (int) new Object() {
						double convert(String s) {
							try {
 								return Double.parseDouble(s.trim());
 							} catch (Exception e) {
 							}
 						 return 0;
 						}
					}.convert(getText(guistate, "text:ZValue")));
			}
			if (entity instanceof Player _player)
				_player.closeContainer();
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.experience_orb.pickup")), SoundSource.NEUTRAL, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.experience_orb.pickup")), SoundSource.NEUTRAL, 1, 1, false);
				}
			}
			{
				InvincibleConquestModVariables.PlayerVariables _vars = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
				_vars.Balance = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance - entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cost;
				_vars.syncPlayerVariables(entity);
			}
			for (Entity entityiterator : new ArrayList<>(world.players())) {
				if ((entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerFactionRank).equals("Director")
						|| (entityiterator.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).PlayerFactionRank).equals("Deputy Director")) {
					if (entityiterator instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((entity.getDisplayName().getString() + " Spent \u00A7a$" + new java.text.DecimalFormat("#").format(entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cost))), false);
				}
			}
			{
				InvincibleConquestModVariables.PlayerVariables _vars = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
				_vars.Cost = 0;
				_vars.syncPlayerVariables(entity);
			}
		} else if (entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Balance < entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES).Cost) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.experience_orb.pickup")), SoundSource.NEUTRAL, 1, (float) 0.1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.experience_orb.pickup")), SoundSource.NEUTRAL, 1, (float) 0.1, false);
				}
			}
		}
	}

	private static String getText(HashMap guistate, String key) {
		Object value = guistate.get(key);
		if (value == null) {
			return "";
		}
		try {
			java.lang.reflect.Method method = value.getClass().getMethod("getValue");
			Object result = method.invoke(value);
			return result != null ? result.toString() : "";
		} catch (Exception e) {
			return value.toString();
		}
	}
}
