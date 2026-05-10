package net.clozynoii.invincibleconquest.client;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import net.clozynoii.invincibleconquest.InvincibleConquestMod;

@Mod(value = InvincibleConquestMod.MODID, dist = net.neoforged.api.distmarker.Dist.CLIENT)
public class InvincibleConquestModClient {

    public InvincibleConquestModClient(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}