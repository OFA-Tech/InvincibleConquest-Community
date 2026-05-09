package net.clozynoii.invincibleconquest.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;

public class FlightMovementCheckProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double forward = 0;
		double strafe = 0;
		if (entity instanceof Player _player) {
			forward = _player.zza;
			strafe = _player.xxa;
		}
		if (Math.abs(forward) >= Math.abs(strafe) && forward > 0) {
			{
				InvincibleConquestModVariables.PlayerVariables _vars = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
				_vars.FlightDirection = "Forward";
				_vars.syncPlayerVariables(entity);
			}
		} else if (Math.abs(forward) >= Math.abs(strafe) && forward < 0) {
			{
				InvincibleConquestModVariables.PlayerVariables _vars = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
				_vars.FlightDirection = "Backward";
				_vars.syncPlayerVariables(entity);
			}
		} else if (Math.abs(strafe) > Math.abs(forward) && strafe < 0) {
			{
				InvincibleConquestModVariables.PlayerVariables _vars = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
				_vars.FlightDirection = "Left";
				_vars.syncPlayerVariables(entity);
			}
		} else if (Math.abs(strafe) > Math.abs(forward) && strafe > 0) {
			{
				InvincibleConquestModVariables.PlayerVariables _vars = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
				_vars.FlightDirection = "Right";
				_vars.syncPlayerVariables(entity);
			}
		} else {
			{
				InvincibleConquestModVariables.PlayerVariables _vars = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
				_vars.FlightDirection = "";
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
