package eladkay.quaeritum.client.gui.book;

import com.teamwizardry.librarianlib.features.animator.Animator;
import com.teamwizardry.librarianlib.features.gui.EnumMouseButton;
import com.teamwizardry.librarianlib.features.gui.component.GuiComponent;
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents;
import com.teamwizardry.librarianlib.features.math.Vec2d;
import eladkay.quaeritum.api.structure.CachedStructure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.math.MathHelper;

public class ComponentStructure extends GuiComponent {

	private static Animator animator = new Animator();
	public double zoom = 0;
	private boolean dragging = false;
	private Vec2d prevPos = Vec2d.ZERO;
	private Vec2d panVec = Vec2d.ZERO;
	private Vec2d rotVec = Vec2d.ZERO;

	public ComponentStructure(int x, int y, int width, int height, CachedStructure structure) {
		super(x, y, width, height);

		//ComponentStructureList list = new ComponentStructureList(structure.getStructure());
		//ComponentBookmarkSwitch bookmark = new ComponentBookmarkSwitch(new Vec2d(-getSize().getXi() - 35, 0), bookGui, this, list, bookGui.bookmarkIndex, "Materials", true, false, true, true);
		//add(bookmark);

		BUS.hook(GuiComponentEvents.MouseWheelEvent.class, event -> {
			if (event.component.hasTag("switched") || !event.component.isVisible()) return;
			double zoom = this.zoom;
			if (event.getDirection() == GuiComponentEvents.MouseWheelDirection.UP) zoom += 3;
			else zoom -= 3;

			this.zoom = MathHelper.clamp(zoom, 1, 30);

			//BasicAnimation mouseOutAnim = new BasicAnimation<>(this, "zoom");
			//mouseOutAnim.setDuration(10);
			//mouseOutAnim.setEasing(Easing.easeOutExpo);
			//mouseOutAnim.setTo(zoom);
			//animator.add(mouseOutAnim);
		});

		BUS.hook(GuiComponentEvents.MouseDragEvent.class, event -> {
			if (event.component.hasTag("switched") || !event.component.isVisible()) return;
			Vec2d untransform = event.getMousePos();
			Vec2d diff;
			if (dragging) diff = untransform.sub(prevPos).mul(1 / 2.0);
			else diff = event.getMousePos().mul(1 / 100.0);

			if (event.getButton() == EnumMouseButton.RIGHT) rotVec = rotVec.add(diff);
			else if (event.getButton() == EnumMouseButton.LEFT) panVec = panVec.add(diff.mul(2));

			prevPos = untransform;
			dragging = true;
		});

		BUS.hook(GuiComponentEvents.MouseUpEvent.class, event -> {
			if (event.component.hasTag("switched") || !event.component.isVisible()) return;
			prevPos = Vec2d.ZERO;
			dragging = false;
		});

		clipping.setClipToBounds(true);
		BUS.hook(GuiComponentEvents.PostDrawEvent.class, event -> {
			if (event.component.hasTag("switched") || !event.component.isVisible()) return;
			if (structure == null || structure.perfectCenter == null) {
				GlStateManager.pushMatrix();
				GuiBook.ERROR.bind();
				GuiBook.ERROR.draw((int) event.getPartialTicks(),
						(int) ((getSize().getX() / 2.0) - GuiBook.ERROR.getWidth() / 2.0),
						(int) ((getSize().getY() / 2.0) - GuiBook.ERROR.getHeight()));

				GuiBook.FOF.bind();
				GuiBook.FOF.draw((int) event.getPartialTicks(),
						(int) ((getSize().getX() / 2.0) - GuiBook.FOF.getWidth() / 2.0),
						(int) ((getSize().getY() / 2.0) - GuiBook.FOF.getHeight() / 2.0));

				GlStateManager.popMatrix();
				return;
			}

			GlStateManager.pushMatrix();

			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();

			GlStateManager.translate(getSize().getX() / 2.0, (getSize().getY() / 2.0), 500);

			GlStateManager.translate(panVec.getX(), panVec.getY(), 0);
			GlStateManager.rotate((float) (35 + rotVec.getY()), -1, 0, 0);
			GlStateManager.rotate((float) ((45 + rotVec.getX())), 0, 1, 0);
			GlStateManager.scale(5 + zoom, -5 - zoom, 5 + zoom);
			GlStateManager.translate(-structure.perfectCenter.x - 0.5, -structure.perfectCenter.y - 0.5, -structure.perfectCenter.z - 0.5);

			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			structure.draw();

			GlStateManager.popMatrix();
		});
	}

	@Override
	public void drawComponent(Vec2d mousePos, float partialTicks) {

	}
}
