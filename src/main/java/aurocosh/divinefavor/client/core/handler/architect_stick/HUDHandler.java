package aurocosh.divinefavor.client.core.handler.architect_stick;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.mystic_architect_stick.ItemMysticArchitectStick;
import aurocosh.divinefavor.common.util.UtilGui;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public final class HUDHandler {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void renderWorldLastEvent(RenderWorldLastEvent event) {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ItemStack stack = player.getHeldItemMainhand();
        if (!(stack.getItem() instanceof ItemMysticArchitectStick))
            return;

        boolean positionsSet = UtilNbt.checkForTags(stack, ItemMysticArchitectStick.TAG_POS_FIRST, ItemMysticArchitectStick.TAG_POS_SECOND);
        if (!positionsSet)
            return;

        NBTTagCompound compound = stack.getTagCompound();
        BlockPos startPos = UtilNbt.getBlockPos(compound, ItemMysticArchitectStick.TAG_POS_FIRST, BlockPos.ORIGIN);
        BlockPos endPos = UtilNbt.getBlockPos(compound, ItemMysticArchitectStick.TAG_POS_SECOND, BlockPos.ORIGIN);

        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        //Calculate the players current position, which is needed later
        float partialTicks = event.getPartialTicks();
        double currentPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double currentPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double currentPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        //We want to draw from the starting position to the (ending position)+1
        // TODO
        int x = (startPos.getX() <= endPos.getX()) ? startPos.getX() : endPos.getX();
        int y = (startPos.getY() <= endPos.getY()) ? startPos.getY() : endPos.getY();
        int z = (startPos.getZ() <= endPos.getZ()) ? startPos.getZ() : endPos.getZ();
        int dx = (startPos.getX() > endPos.getX()) ? startPos.getX() + 1 : endPos.getX() + 1;
        int dy = (startPos.getY() > endPos.getY()) ? startPos.getY() + 1 : endPos.getY() + 1;
        int dz = (startPos.getZ() > endPos.getZ()) ? startPos.getZ() + 1 : endPos.getZ() + 1;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-currentPosX, -currentPosY, -currentPosZ);//The render starts at the player, so we subtract the player coords and move the render to 0,0,0

        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        UtilGui.renderBox(tessellator, bufferbuilder, new BlockPos(x, y, z), new BlockPos(dx, dy, dz), 255, 223, 127);

        GlStateManager.glLineWidth(1.0F);
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);

        GlStateManager.popMatrix();
    }
}