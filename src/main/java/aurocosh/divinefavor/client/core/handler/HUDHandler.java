package aurocosh.divinefavor.client.core.handler;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.ItemMysticArchitectStick;
import aurocosh.divinefavor.common.item.talismans.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.ModTalismans;
import aurocosh.divinefavor.common.item.talismans.Talisman;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber
public final class HUDHandler {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() == ElementType.ALL) {
            ScaledResolution resolution = event.getResolution();
            float partialTicks = event.getPartialTicks();

            renderSpellRequirements(resolution, partialTicks);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void renderWorldLastEvent(RenderWorldLastEvent evt) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer p = mc.player;
        ItemStack heldItem = p.getHeldItemMainhand();
        if (!(heldItem.getItem() instanceof ItemMysticArchitectStick))
            return;
        renderArchitectOverlay(evt, p, heldItem);
    }

    @SideOnly(Side.CLIENT)
    private void renderSpellRequirements(ScaledResolution res, float pticks) {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack stack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemTalisman))
            return;

        Talisman talisman = ModTalismans.getMetaContainer().getByMeta(stack.getMetadata());
        if(talisman == null)
            return;

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        int useCount = talisman.getUseCount(player);

        String description = useCount > 0 ? "Uses left: " + useCount : "Unusable";
        int alpha = 255;

        int color = (0 << 0) + (128 << 8) + (0 << 16) + (alpha << 24);

        int x = res.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(description) / 2;
        int y = res.getScaledHeight() - 71;
        if (mc.player.capabilities.isCreativeMode)
            y += 14;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.fontRenderer.drawStringWithShadow(description, x, y, color);

        int w = mc.fontRenderer.getStringWidth(description);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x - 20, y - 6, 0);
        GlStateManager.scale(alpha / 255F, 1F, 1);
        GlStateManager.color(1F, 1F, 1F);
        mc.getRenderItem().renderItemIntoGUI(stack, 0, 0);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }

    public static void renderArchitectOverlay(RenderWorldLastEvent evt, EntityPlayer player, ItemStack stack) {
        boolean positionsSet = UtilNbt.checkForTags(stack, ItemMysticArchitectStick.TAG_POS_FIRST, ItemMysticArchitectStick.TAG_POS_SECOND);
        if(!positionsSet)
            return;

        NBTTagCompound compound = stack.getTagCompound();
        BlockPos startPos = UtilNbt.getBlockPos(compound,ItemMysticArchitectStick.TAG_POS_FIRST);
        BlockPos endPos = UtilNbt.getBlockPos(compound,ItemMysticArchitectStick.TAG_POS_SECOND);

        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        //Calculate the players current position, which is needed later
        double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * evt.getPartialTicks();
        double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * evt.getPartialTicks();
        double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * evt.getPartialTicks();

        //We want to draw from the starting position to the (ending position)+1
        int x = (startPos.getX() <= endPos.getX()) ? startPos.getX() : endPos.getX();
        int y = (startPos.getY() <= endPos.getY()) ? startPos.getY() : endPos.getY();
        int z = (startPos.getZ() <= endPos.getZ()) ? startPos.getZ() : endPos.getZ();
        int dx = (startPos.getX() > endPos.getX()) ? startPos.getX() + 1 : endPos.getX() + 1;
        int dy = (startPos.getY() > endPos.getY()) ? startPos.getY() + 1 : endPos.getY() + 1;
        int dz = (startPos.getZ() > endPos.getZ()) ? startPos.getZ() + 1 : endPos.getZ() + 1;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-doubleX, -doubleY, -doubleZ);//The render starts at the player, so we subtract the player coords and move the render to 0,0,0

        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        renderBox(tessellator, bufferbuilder, x, y, z, dx, dy, dz, 255, 223, 127); // Draw the box around the validBlocks we've copied.

        GlStateManager.glLineWidth(1.0F);
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);

        GlStateManager.popMatrix();
    }

    private static void renderBox(Tessellator tessellator, BufferBuilder bufferBuilder, double startX, double startY, double startZ, double endX, double endY, double endZ, int R, int G, int B) {
        GlStateManager.glLineWidth(2.0F);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(startX, startY, startZ).color(G, G, G, 0.0F).endVertex();
        bufferBuilder.pos(startX, startY, startZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(endX, startY, startZ).color(G, B, B, R).endVertex();
        bufferBuilder.pos(endX, startY, endZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(startX, startY, endZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(startX, startY, startZ).color(B, B, G, R).endVertex();
        bufferBuilder.pos(startX, endY, startZ).color(B, G, B, R).endVertex();
        bufferBuilder.pos(endX, endY, startZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(endX, endY, endZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(startX, endY, endZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(startX, endY, startZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(startX, endY, endZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(startX, startY, endZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(endX, startY, endZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(endX, endY, endZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(endX, endY, startZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(endX, startY, startZ).color(G, G, G, R).endVertex();
        bufferBuilder.pos(endX, startY, startZ).color(G, G, G, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.glLineWidth(1.0F);
    }
}