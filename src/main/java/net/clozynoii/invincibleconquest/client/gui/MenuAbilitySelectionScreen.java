package net.clozynoii.invincibleconquest.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.clozynoii.invincibleconquest.world.inventory.MenuAbilitySelectionMenu;
import net.clozynoii.invincibleconquest.network.MenuAbilitySelectionButtonMessage;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class MenuAbilitySelectionScreen extends AbstractContainerScreen<MenuAbilitySelectionMenu> {
	private final static HashMap<String, Object> guistate = MenuAbilitySelectionMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	ImageButton imagebutton_icon_question;
	ImageButton imagebutton_icon_human;
	ImageButton imagebutton_icon_viltrumite;
	ImageButton imagebutton_icon_speedster;
	ImageButton imagebutton_icon_spider;
	ImageButton imagebutton_icon_clone;
	ImageButton imagebutton_icon_explode;
	ImageButton imagebutton_icon_portal;
	ImageButton imagebutton_icon_battlebeast;
	ImageButton imagebutton_icon_atomeve;
	ImageButton imagebutton_icon_robot;
	ImageButton imagebutton_icon_tech_jacket;

	public MenuAbilitySelectionScreen(MenuAbilitySelectionMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 0;
		this.imageHeight = 0;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("invincible_conquest:textures/screens/menu_ability_selection.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (mouseX > leftPos + -78 && mouseX < leftPos + -56 && mouseY > topPos + -47 && mouseY < topPos + -25)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_random"), mouseX, mouseY);
		if (mouseX > leftPos + -52 && mouseX < leftPos + -30 && mouseY > topPos + -47 && mouseY < topPos + -25)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_human"), mouseX, mouseY);
		if (mouseX > leftPos + -26 && mouseX < leftPos + -4 && mouseY > topPos + -47 && mouseY < topPos + -25)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_viltrumite"), mouseX, mouseY);
		if (mouseX > leftPos + 2 && mouseX < leftPos + 24 && mouseY > topPos + -47 && mouseY < topPos + -25)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_speedster"), mouseX, mouseY);
		if (mouseX > leftPos + 28 && mouseX < leftPos + 50 && mouseY > topPos + -47 && mouseY < topPos + -25)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_spider"), mouseX, mouseY);
		if (mouseX > leftPos + 54 && mouseX < leftPos + 76 && mouseY > topPos + -47 && mouseY < topPos + -25)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_cloning"), mouseX, mouseY);
		if (mouseX > leftPos + -78 && mouseX < leftPos + -56 && mouseY > topPos + -23 && mouseY < topPos + -1)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_explode"), mouseX, mouseY);
		if (mouseX > leftPos + -52 && mouseX < leftPos + -30 && mouseY > topPos + -23 && mouseY < topPos + -1)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_portal"), mouseX, mouseY);
		if (mouseX > leftPos + -26 && mouseX < leftPos + -4 && mouseY > topPos + -23 && mouseY < topPos + -1)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_beast"), mouseX, mouseY);
		if (mouseX > leftPos + 2 && mouseX < leftPos + 24 && mouseY > topPos + -23 && mouseY < topPos + -1)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_atom"), mouseX, mouseY);
		if (mouseX > leftPos + 28 && mouseX < leftPos + 50 && mouseY > topPos + -23 && mouseY < topPos + -1)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_robot"), mouseX, mouseY);
		if (mouseX > leftPos + 53 && mouseX < leftPos + 77 && mouseY > topPos + -25 && mouseY < topPos + -1)
			guiGraphics.renderTooltip(font, Component.translatable("gui.invincible_conquest.menu_ability_selection.tooltip_tech_jacket"), mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/menu_empty.png"), this.leftPos + -214, this.topPos + -120, 0, 0, 427, 240, 427, 240);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + -78, this.topPos + -47, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + -52, this.topPos + -47, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + -26, this.topPos + -47, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + 2, this.topPos + -47, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + 28, this.topPos + -47, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + 54, this.topPos + -47, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + -78, this.topPos + -23, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + -52, this.topPos + -23, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + -26, this.topPos + -23, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + 2, this.topPos + -23, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + 28, this.topPos + -23, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(ResourceLocation.parse("invincible_conquest:textures/screens/blank_slot.png"), this.leftPos + 54, this.topPos + -23, 0, 0, 22, 22, 22, 22);

		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.invincible_conquest.menu_ability_selection.label_ability_selection1"), -77, -68, -11252677, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.invincible_conquest.menu_ability_selection.label_ability_selection"), -78, -69, -1, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_icon_question = new ImageButton(this.leftPos + -75, this.topPos + -44, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_question.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_question.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(0, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_question", imagebutton_icon_question);
		this.addRenderableWidget(imagebutton_icon_question);
		imagebutton_icon_human = new ImageButton(this.leftPos + -49, this.topPos + -44, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_human.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_human.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(1, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 1, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_human", imagebutton_icon_human);
		this.addRenderableWidget(imagebutton_icon_human);
		imagebutton_icon_viltrumite = new ImageButton(this.leftPos + -23, this.topPos + -44, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_viltrumite.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_viltrumite.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(2, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_viltrumite", imagebutton_icon_viltrumite);
		this.addRenderableWidget(imagebutton_icon_viltrumite);
		imagebutton_icon_speedster = new ImageButton(this.leftPos + 5, this.topPos + -44, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_speedster.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_speedster.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(3, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 3, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_speedster", imagebutton_icon_speedster);
		this.addRenderableWidget(imagebutton_icon_speedster);
		imagebutton_icon_spider = new ImageButton(this.leftPos + 31, this.topPos + -44, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_spider.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_spider.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(4, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 4, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_spider", imagebutton_icon_spider);
		this.addRenderableWidget(imagebutton_icon_spider);
		imagebutton_icon_clone = new ImageButton(this.leftPos + 57, this.topPos + -44, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_clone.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_clone.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(5, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 5, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_clone", imagebutton_icon_clone);
		this.addRenderableWidget(imagebutton_icon_clone);
		imagebutton_icon_explode = new ImageButton(this.leftPos + -75, this.topPos + -20, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_explode.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_explode.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(6, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 6, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_explode", imagebutton_icon_explode);
		this.addRenderableWidget(imagebutton_icon_explode);
		imagebutton_icon_portal = new ImageButton(this.leftPos + -49, this.topPos + -20, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_portal.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_portal.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(7, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 7, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_portal", imagebutton_icon_portal);
		this.addRenderableWidget(imagebutton_icon_portal);
		imagebutton_icon_battlebeast = new ImageButton(this.leftPos + -23, this.topPos + -20, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_battlebeast.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_battlebeast.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(8, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 8, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_battlebeast", imagebutton_icon_battlebeast);
		this.addRenderableWidget(imagebutton_icon_battlebeast);
		imagebutton_icon_atomeve = new ImageButton(this.leftPos + 5, this.topPos + -20, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_atomeve.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_atomeve.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(9, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 9, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_atomeve", imagebutton_icon_atomeve);
		this.addRenderableWidget(imagebutton_icon_atomeve);
		imagebutton_icon_robot = new ImageButton(this.leftPos + 31, this.topPos + -20, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_robot.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_robot.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(10, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 10, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_robot", imagebutton_icon_robot);
		this.addRenderableWidget(imagebutton_icon_robot);
		imagebutton_icon_tech_jacket = new ImageButton(this.leftPos + 57, this.topPos + -20, 16, 16,
				new WidgetSprites(ResourceLocation.parse("invincible_conquest:textures/screens/icon_tech_jacket.png"), ResourceLocation.parse("invincible_conquest:textures/screens/icon_tech_jacket.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MenuAbilitySelectionButtonMessage(11, x, y, z));
						MenuAbilitySelectionButtonMessage.handleButtonAction(entity, 11, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_icon_tech_jacket", imagebutton_icon_tech_jacket);
		this.addRenderableWidget(imagebutton_icon_tech_jacket);
	}
}
