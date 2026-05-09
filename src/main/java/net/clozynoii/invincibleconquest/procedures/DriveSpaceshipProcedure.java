package net.clozynoii.invincibleconquest.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.clozynoii.invincibleconquest.entity.SpaceshipEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class DriveSpaceshipProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity.isPassenger()) {
			if ((entity.getVehicle()) instanceof SpaceshipEntity) {
				if (((entity.getVehicle()) instanceof SpaceshipEntity _datEntI ? _datEntI.getEntityData().get(SpaceshipEntity.DATA_Fuel) : 0) > 0) {
					double forward = 0;
					if (entity instanceof Player _player) {
						forward = _player.zza;
					}
					if (forward > 0) {
						(entity.getVehicle()).setDeltaMovement(new Vec3(((entity.getVehicle()).getDeltaMovement().x()), ((entity.getVehicle()).getDeltaMovement().y() + 0.2), ((entity.getVehicle()).getDeltaMovement().z())));
					}
					if (entity.isShiftKeyDown()) {
						(entity.getVehicle()).setDeltaMovement(new Vec3(((entity.getVehicle()).getDeltaMovement().x()), ((entity.getVehicle()).getDeltaMovement().y() - 0.2), ((entity.getVehicle()).getDeltaMovement().z())));
					}
				}
			}
		}
	}
}
