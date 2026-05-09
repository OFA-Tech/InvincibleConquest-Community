package net.clozynoii.invincibleconquest.procedures;

import net.minecraft.world.entity.Entity;

import net.clozynoii.invincibleconquest.network.InvincibleConquestModVariables;

import java.util.HashMap;

public class GDASoldierGUIWhileThisGUIIsOpenTickProcedure {
	public static void execute(Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		{
			InvincibleConquestModVariables.PlayerVariables _vars = entity.getData(InvincibleConquestModVariables.PLAYER_VARIABLES);
			_vars.Cost = new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(getText(guistate, "text:gdasoldieramount")) * 150;
			_vars.syncPlayerVariables(entity);
		}
		if (new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(getText(guistate, "text:gdasoldieramount")) > 100) {
			Object field = guistate.get("text:gdasoldieramount");
			if (field != null) {
				try {
					java.lang.reflect.Method method = field.getClass().getMethod("setValue", String.class);
					method.invoke(field, "100");
				} catch (Exception e) {
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
