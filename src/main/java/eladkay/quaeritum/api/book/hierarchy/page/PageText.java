package eladkay.quaeritum.api.book.hierarchy.page;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.teamwizardry.librarianlib.features.gui.component.GuiComponent;
import com.teamwizardry.librarianlib.features.gui.components.ComponentText;
import com.teamwizardry.librarianlib.features.math.Vec2d;
import eladkay.quaeritum.api.book.hierarchy.entry.Entry;
import eladkay.quaeritum.client.gui.book.GuiBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PageText implements Page {

    private final String key;
    private final Entry entry;

    public PageText(Entry entry, JsonObject jsonElement) {
        this.entry = entry;
        key = jsonElement.getAsJsonPrimitive("value").getAsString();
    }

    @Override
    public @NotNull Entry getEntry() {
        return entry;
    }

    @Override
    public Collection<String> getSearchableKeys() {
        return Lists.newArrayList(key);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public List<GuiComponent> createBookComponents(GuiBook book, Vec2d size) {
        List<GuiComponent> pages = new ArrayList<>();

        String text = I18n.format(key);

        List<String> list = Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(text, (size.getXi() + 16) * 18);

        for (String section : list) {
            if (section.trim().isEmpty()) continue;

            ComponentText sectionComponent = new ComponentText(0, 0, ComponentText.TextAlignH.LEFT, ComponentText.TextAlignV.TOP);
            sectionComponent.getText().setValue(section);
            sectionComponent.getWrap().setValue(size.getXi());
            sectionComponent.getUnicode().setValue(true);

            pages.add(sectionComponent);
        }
        return pages;
    }
}
