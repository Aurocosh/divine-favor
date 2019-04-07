/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 *
 * Permission is hereby granted, free of favorCost, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to gainFavor, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.DivineFavor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class UtilTextureRender {

    @SideOnly(Side.CLIENT)
    public static void drawTextureSimple(ResourceLocation res, int x, int y, int w, int h) {
        if (res == null) {
            return;
        }
        try {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(res);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 0F, 0F, w, h, w, h);
        }
        catch (NullPointerException e) {
            DivineFavor.logger.error("Null pointer drawTexture;Simple " + res.getPath());
            DivineFavor.logger.error(e.getMessage());
            e.printStackTrace();
        }
        catch (net.minecraft.util.ReportedException e) {
            DivineFavor.logger.error("net.minecraft.util.ReportedException ");
            DivineFavor.logger.error(res.getNamespace() + ":" + res.getPath());
            DivineFavor.logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void drawTextureSquare(ResourceLocation img, int x, int y, int dim) {
        if (img == null) {
            return;
        }
        drawTextureSimple(img, x, y, dim, dim);
    }
}
