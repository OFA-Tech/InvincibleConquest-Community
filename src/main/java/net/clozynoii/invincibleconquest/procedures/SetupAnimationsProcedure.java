package net.clozynoii.invincibleconquest.procedures;

import net.clozynoii.invincibleconquest.InvincibleConquestMod;
import net.clozynoii.invincibleconquest.client.ClientAnimationHandler;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import javax.annotation.Nullable;

public class SetupAnimationsProcedure {
  @EventBusSubscriber(modid = "invincible_conquest", bus = EventBusSubscriber.Bus.MOD)
  public static record InvincibleConquestModAnimationMessage(String animation, int target, boolean override) implements CustomPacketPayload {

    public static final Type<InvincibleConquestModAnimationMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(InvincibleConquestMod.MODID, "setup_animations"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InvincibleConquestModAnimationMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, InvincibleConquestModAnimationMessage message) -> {
      buffer.writeUtf(message.animation);
      buffer.writeInt(message.target);
      buffer.writeBoolean(message.override);
    }, (RegistryFriendlyByteBuf buffer) -> new InvincibleConquestModAnimationMessage(buffer.readUtf(), buffer.readInt(), buffer.readBoolean()));

    @Override
    public Type<InvincibleConquestModAnimationMessage> type() {
      return TYPE;
    }

    public String getAnimation() {
      return animation;
    }

    public boolean isOverride() {
      return override;
    }

    public int getTarget() {
      return target;
    }

    public static void handleData(final InvincibleConquestModAnimationMessage message, final IPayloadContext context) {
      if (context.flow() == PacketFlow.CLIENTBOUND) {
        context.enqueueWork(() -> {
          Level level = context.player().level();
          if (level.getEntity(message.target) instanceof Player player) {
            if (FMLEnvironment.dist == Dist.CLIENT) {
              ClientAnimationHandler.handleAnimation(message, player);
            }
          }
        }).exceptionally(e -> {
          context.connection().disconnect(Component.literal(e.getMessage()));
          return null;
        });
      }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
      InvincibleConquestMod.addNetworkMessage(InvincibleConquestModAnimationMessage.TYPE, InvincibleConquestModAnimationMessage.STREAM_CODEC, InvincibleConquestModAnimationMessage::handleData);
    }
  }

  public static void setAnimationClientside(Player player, String anim, boolean override) {
    if (player == null) {
      return;
    }
    if (FMLEnvironment.dist == Dist.CLIENT) {
      ClientAnimationHandler.setAnimationClientside(player, anim, override);
    }
  }



  public static void setAnimationClientside(Player player, String anim, boolean override, String context) {
    if (player == null) {
      return;
    }
    if (FMLEnvironment.dist == Dist.CLIENT) {
      ClientAnimationHandler.setAnimationClientside(player, anim, override, context);
    }
  }
  public static void stopAnimationClientside(Player player) {
    if (player == null) {
      return;
    }
    if (FMLEnvironment.dist == Dist.CLIENT) {
      ClientAnimationHandler.stopAnimationClientside(player);
    }
  }

  public static void execute() {
    execute(null);
  }

  private static void execute(@Nullable Event event) {
  }
}

