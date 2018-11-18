/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Jan 19, 2014, 5:40:38 PM (GMT)]
 */
package vazkii.arl.util;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class RenderHelper {

	public static void renderTooltip(int x, int y, List<String> tooltipData) {
		int color = 0x505000ff;
		int color2 = 0xf0100010;

		renderTooltip(x, y, tooltipData, color, color2);
	}

	public static void renderTooltipOrange(int x, int y, List<String> tooltipData) {
		int color = 0x50a06600;
		int color2 = 0xf01e1200;

		renderTooltip(x, y, tooltipData, color, color2);
	}

	public static void renderTooltipGreen(int x, int y, List<String> tooltipData) {
		int color = 0x5000a000;
		int color2 = 0xf0001e00;

		renderTooltip(x, y, tooltipData, color, color2);
	}

	public static void renderTooltip(int x, int y, List<String> tooltipData, int color, int color2) {
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting)
			net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

		if (!tooltipData.isEmpty()) {
			int var5 = 0;
			int var6;
			int var7;
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			for (var6 = 0; var6 < tooltipData.size(); ++var6) {
				var7 = fontRenderer.getStringWidth(tooltipData.get(var6));
				if (var7 > var5)
					var5 = var7;
			}
			var6 = x + 12;
			var7 = y - 12;
			int var9 = 8;
			if (tooltipData.size() > 1)
				var9 += 2 + (tooltipData.size() - 1) * 10;

			ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
			int right = var6 + var5 + 5;
			int swidth = res.getScaledWidth();
			if(right > swidth) {
				int diff = right - swidth;
				var6 -= diff;
			}

			int bottom = var7 + var9 + 5;
			int sheight = res.getScaledHeight();
			if(bottom > sheight) {
				int diff = bottom - sheight;
				var7 -= diff;
			}

			float z = 300F;
			drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
			drawGradientRect(var6 - 3, var7 + var9 + 3, z, var6 + var5 + 3, var7 + var9 + 4, color2, color2);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var9 + 3, color2, color2);
			int var12 = (color & 0xFFFFFF) >> 1 | color & -16777216;
			drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var9 + 3 - 1, color, var12);
			drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var9 + 3 - 1, color, var12);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, color, color);
			drawGradientRect(var6 - 3, var7 + var9 + 2, z, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

			GlStateManager.disableDepth();
			for (int var13 = 0; var13 < tooltipData.size(); ++var13) {
				String var14 = tooltipData.get(var13);
				fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
				if (var13 == 0)
					var7 += 2;
				var7 += 10;
			}
			GlStateManager.enableDepth();
		}
		if(!lighting)
			net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		GlStateManager.color(1F, 1F, 1F, 1F);
	}

	public static void drawGradientRect(int par1, int par2, float z, int par3, int par4, int par5, int par6) {
		float var7 = (par5 >> 24 & 255) / 255F;
		float var8 = (par5 >> 16 & 255) / 255F;
		float var9 = (par5 >> 8 & 255) / 255F;
		float var10 = (par5 & 255) / 255F;
		float var11 = (par6 >> 24 & 255) / 255F;
		float var12 = (par6 >> 16 & 255) / 255F;
		float var13 = (par6 >> 8 & 255) / 255F;
		float var14 = (par6 & 255) / 255F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		Tessellator var15 = Tessellator.getInstance();
		BufferBuilder buff = var15.getBuffer();
		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		buff.pos(par3, par2, z).color(var8, var9, var10, var7).endVertex();
		buff.pos(par1, par2, z).color(var8, var9, var10, var7).endVertex();
		buff.pos(par1, par4, z).color(var12, var13, var14, var11).endVertex();
		buff.pos(par3, par4, z).color(var12, var13, var14, var11).endVertex();
		var15.draw();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawTexturedModalRect(int par1, int par2, float z, int par3, int par4, int par5, int par6) {
		drawTexturedModalRect(par1, par2, z, par3, par4, par5, par6, 0.00390625F, 0.00390625F);
	}

	public static void drawTexturedModalRect(int par1, int par2, float z, int par3, int par4, int par5, int par6, float f, float f1) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();
		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(par1 + 0, par2 + par6, z).tex((par3 + 0) * f, (par4 + par6) * f1).endVertex();
		buff.pos(par1 + par5, par2 + par6, z).tex((par3 + par5) * f, (par4 + par6) * f1).endVertex();
		buff.pos(par1 + par5, par2 + 0, z).tex((par3 + par5) * f, (par4 + 0) * f1).endVertex();
		buff.pos(par1 + 0, par2 + 0, z).tex((par3 + 0) * f, (par4 + 0) * f1).endVertex();
		tessellator.draw();
	}
	
	public static void renderStar(int color, float scale, long seed) {
		renderStar(color, scale, scale, scale, seed);
	}
	
	public static void renderStar(int color, float xScale, float yScale, float zScale, long seed) {
		Tessellator tessellator = Tessellator.getInstance();

		float ticks = (ClientTicker.ticksInGame % 200) + ClientTicker.partialTicks;
		if (ticks >= 100)
			ticks = 200 - ticks - 1;

		float f1 = ticks / 200F;
		float f2 = 0F;
		if (f1 > 0.7F)
			f2 = (f1 - 0.7F) / 0.2F;
		Random random = new Random(seed);

		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GlStateManager.disableAlpha();
		GlStateManager.enableCull();
		GlStateManager.depthMask(false);
		GlStateManager.scale(xScale, yScale, zScale);

		for (int i = 0; i < (f1 + f1 * f1) / 2F * 90F + 30F; i++) {
			GlStateManager.rotate(random.nextFloat() * 360F, 1F, 0F, 0F);
			GlStateManager.rotate(random.nextFloat() * 360F, 0F, 1F, 0F);
			GlStateManager.rotate(random.nextFloat() * 360F, 0F, 0F, 1F);
			GlStateManager.rotate(random.nextFloat() * 360F, 1F, 0F, 0F);
			GlStateManager.rotate(random.nextFloat() * 360F, 0F, 1F, 0F);
			GlStateManager.rotate(random.nextFloat() * 360F + f1 * 90F, 0F, 0F, 1F);
			tessellator.getBuffer().begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
			float f3 = random.nextFloat() * 20F + 5F + f2 * 10F;
			float f4 = random.nextFloat() * 2F + 1F + f2 * 2F;
			float r = ((color & 0xFF0000) >> 16) / 255F;
			float g = ((color & 0xFF00) >> 8) / 255F;
			float b = (color & 0xFF) / 255F;
			tessellator.getBuffer().pos(0, 0, 0).color(r, g, b, 1F - f2).endVertex();
			tessellator.getBuffer().pos(-0.866D * f4, f3, -0.5F * f4).color(0, 0, 0, 0).endVertex();
			tessellator.getBuffer().pos(0.866D * f4, f3, -0.5F * f4).color(0, 0, 0, 0).endVertex();
			tessellator.getBuffer().pos(0, f3, 1F * f4).color(0, 0, 0, 0).endVertex();
			tessellator.getBuffer().pos(-0.866D * f4, f3, -0.5F * f4).color(0, 0, 0, 0).endVertex();
			tessellator.draw();
		}

		GlStateManager.depthMask(true);
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableTexture2D();
		GlStateManager.enableAlpha();
		GlStateManager.popMatrix();
	}

	public static String getKeyDisplayString(String keyName) {
		String key = null;
		KeyBinding[] keys = Minecraft.getMinecraft().gameSettings.keyBindings;
		for(KeyBinding otherKey : keys)
			if(otherKey.getKeyDescription().equals(keyName)) {
				key = Keyboard.getKeyName(otherKey.getKeyCode());
				break;
			}

		return key;
	}
}