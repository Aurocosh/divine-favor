package aurocosh.divinefavor.client.gui.text;

import aurocosh.divinefavor.client.gui.IGuiSimpleElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.min;

public class GuiTextBlock extends Gui implements IGuiSimpleElement {
    public int x;
    public int y;
    public int width;
    public int height;

    private static final int lineSpacing = 2;
    private List<String> description;

    public GuiTextBlock(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setText(text);
    }

    public void setText(String text) {
        List<String> descriptionLines = Arrays.asList(text);
        descriptionLines = expandNewlines(descriptionLines);
        descriptionLines = wrapDescriptionLines(descriptionLines);

        int maxLines = height / 20;
        this.description = descriptionLines.subList(0, min(descriptionLines.size(), maxLines));
    }

    private List<String> expandNewlines(List<String> descriptionLines) {
        List<String> descriptionLinesExpanded = new ArrayList<>();
        for (String descriptionLine : descriptionLines) {
            String[] descriptionLineExpanded = descriptionLine.split("\\\\n");
            Collections.addAll(descriptionLinesExpanded, descriptionLineExpanded);
        }
        return descriptionLinesExpanded;
    }

    private List<String> wrapDescriptionLines(List<String> descriptionLines) {
        Minecraft minecraft = Minecraft.getMinecraft();
        List<String> descriptionLinesWrapped = new ArrayList<>();
        for (String descriptionLine : descriptionLines) {
            List<String> textLines = minecraft.fontRenderer.listFormattedStringToWidth(descriptionLine, width);
            descriptionLinesWrapped.addAll(textLines);
        }
        return descriptionLinesWrapped;
    }

    @Override
    public void draw(Minecraft minecraft, int guiLeft, int guiTop) {
        int xPos = guiLeft + x;
        int yPos = guiTop + y +  4;

        for (String descriptionLine : description) {
            minecraft.fontRenderer.drawString(descriptionLine, xPos, yPos, Color.black.getRGB());
            yPos += minecraft.fontRenderer.FONT_HEIGHT + lineSpacing;
        }
    }

    public List<String> getDescription() {
        return description;
    }

}
