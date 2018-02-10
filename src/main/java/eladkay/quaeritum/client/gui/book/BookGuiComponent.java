package eladkay.quaeritum.client.gui.book;

import com.teamwizardry.librarianlib.core.client.ClientTickHandler;
import com.teamwizardry.librarianlib.features.gui.component.GuiComponent;
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents;
import com.teamwizardry.librarianlib.features.gui.components.ComponentText;
import com.teamwizardry.librarianlib.features.gui.components.ComponentVoid;
import com.teamwizardry.librarianlib.features.sprite.Sprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Property of Demoniaque.
 * All rights reserved.
 */
public abstract class BookGuiComponent extends GuiComponent {

	public BookGuiComponent(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
	}

	public BookGuiComponent(int posX, int posY, int width) {
		super(posX, posY, width);
	}

	public BookGuiComponent(int posX, int posY) {
		super(posX, posY);
	}

	public abstract String getTitle();

	public abstract String getDescription();

	@Nonnull
	public abstract GuiBook getBook();

	@Nullable
	public abstract String getIcon();

	public abstract void update();

	public GuiComponent getOrMakeIndexButton(int indexID, GuiBook book, GuiComponent caller) {
		ComponentVoid indexButton = new ComponentVoid(0, 16 * indexID, getSize().getXi(), 16);

		indexButton.BUS.hook(GuiComponentEvents.MouseClickEvent.class, event -> {
			setVisible(true);
			update();
			caller.setVisible(false);
			book.FOCUSED_COMPONENT = this;
		});

		// SUB INDEX PLATE RENDERING
		{
			ComponentText textComponent = new ComponentText(20, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2, ComponentText.TextAlignH.LEFT, ComponentText.TextAlignV.TOP);
			textComponent.getUnicode().setValue(true);
			textComponent.getText().setValue(getTitle());
			indexButton.add(textComponent);

			indexButton.BUS.hook(GuiComponentEvents.MouseInEvent.class, (event) -> {
				textComponent.getText().setValue(" " + TextFormatting.ITALIC.toString() + getTitle());
			});

			indexButton.BUS.hook(GuiComponentEvents.MouseOutEvent.class, (event) -> {
				textComponent.getText().setValue(TextFormatting.RESET.toString() + getTitle());
			});

			if (getIcon() == null) return indexButton;

			Sprite iconSprite = null;
			ItemStack stackIcon = ItemStack.EMPTY;
			{
				ResourceLocation iconLocation = new ResourceLocation(getIcon());
				if (ForgeRegistries.ITEMS.containsKey(iconLocation)) {
					Item itemIcon = ForgeRegistries.ITEMS.getValue(iconLocation);
					if (itemIcon != null) stackIcon = new ItemStack(itemIcon);
				} else iconSprite = new Sprite(iconLocation);
			}

			Sprite finalIconSprite = iconSprite;
			ItemStack finalStackIcon = stackIcon;
			indexButton.BUS.hook(GuiComponentEvents.PostDrawEvent.class, (event) -> {
				if (finalIconSprite != null) {

					GlStateManager.pushMatrix();
					GlStateManager.color(1, 1, 1, 1);
					GlStateManager.enableBlend();
					GlStateManager.enableRescaleNormal();

					finalIconSprite.getTex().bind();
					finalIconSprite.draw((int) ClientTickHandler.getPartialTicks(), 0, 0, 16, 16);

					GlStateManager.popMatrix();

				} else if (finalStackIcon != null && !finalStackIcon.isEmpty()) {

					GlStateManager.pushMatrix();
					GlStateManager.enableBlend();
					GlStateManager.enableRescaleNormal();
					RenderHelper.enableGUIStandardItemLighting();

					RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
					itemRender.renderItemAndEffectIntoGUI(finalStackIcon, 0, 0);
					itemRender.renderItemOverlays(Minecraft.getMinecraft().fontRenderer, finalStackIcon, 0, 0);

					RenderHelper.disableStandardItemLighting();
					GlStateManager.popMatrix();
				}
			});
		}

		return indexButton;
	}

}
