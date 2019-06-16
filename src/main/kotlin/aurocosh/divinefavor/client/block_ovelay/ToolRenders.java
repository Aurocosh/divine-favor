package aurocosh.divinefavor.client.block_ovelay;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.apache.commons.lang3.tuple.Triple;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static net.minecraft.block.BlockStainedGlass.COLOR;

/**
 * Parts of this class were adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

public class ToolRenders {
    private static final FakeBuilderWorld fakeWorld = new FakeBuilderWorld();

    private static Minecraft mc = Minecraft.getMinecraft();
    private static Cache<Triple<UniqueItemStack, BlockPos, Integer>, Integer> cacheDestructionOverlay = CacheBuilder.newBuilder().maximumSize(1).
            expireAfterWrite(1, TimeUnit.SECONDS).removalListener(removal -> GLAllocation.deleteDisplayLists((int) removal.getValue())).build();

    // We use these as highlighters
    private static final IBlockState stainedGlassYellow = Blocks.STAINED_GLASS.getDefaultState().withProperty(COLOR, EnumDyeColor.YELLOW);
    private static final IBlockState stainedGlassRed    = Blocks.STAINED_GLASS.getDefaultState().withProperty(COLOR, EnumDyeColor.RED);
    private static final IBlockState stainedGlassWhite  = Blocks.STAINED_GLASS.getDefaultState().withProperty(COLOR, EnumDyeColor.WHITE);


//    public static void renderDestructionOverlay(RenderWorldLastEvent evt, EntityPlayer player, ItemStack stack) {
//        RayTraceResult lookingAt = VectorTools.getLookingAt(player, stack);
//        if (lookingAt == null && GadgetDestruction.getAnchor(stack) == null) return;
//        World world = player.world;
//        BlockPos startBlock = (GadgetDestruction.getAnchor(stack) == null) ? lookingAt.getBlockPos() : GadgetDestruction.getAnchor(stack);
//        EnumFacing facing = (GadgetDestruction.getAnchorSide(stack) == null) ? lookingAt.sideHit : GadgetDestruction.getAnchorSide(stack);
//        if (startBlock == ModBlocks.effectBlock.getDefaultState()) return;
//
//        if (!GadgetDestruction.getOverlay(stack)) return;
//        GlStateManager.pushMatrix();
//        double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * evt.getPartialTicks();
//        double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * evt.getPartialTicks();
//        double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * evt.getPartialTicks();
//        GlStateManager.translate(-doubleX, -doubleY, -doubleZ);
//        try {
//            GlStateManager.callList(cacheDestructionOverlay.get(new ImmutableTriple<>(new UniqueItemStack(stack), startBlock, facing.ordinal()), () -> {
//                int displayList = GLAllocation.generateDisplayLists(1);
//                GlStateManager.glNewList(displayList, GL11.GL_COMPILE);
//                renderDestructionOverlay(player, world, startBlock, facing, stack);
//                GlStateManager.glEndList();
//                return displayList;
//            }));
//        } catch (ExecutionException e) {
//            BuildingGadgets.logger.error("Error encountered while rendering destruction gadget overlay", e);
//        }
//        GlStateManager.enableLighting();
//        GlStateManager.popMatrix();
//    }
//
//    private static void renderDestructionOverlay(EntityPlayer player, World world, BlockPos startBlock, EnumFacing facing, ItemStack heldItem) {
//        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//
//        SortedSet<BlockPos> coordinates = GadgetDestruction.getArea(world, startBlock, facing, player, heldItem);
//
//        GlStateManager.pushMatrix();
//        GlStateManager.enableBlend();
//        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//
//        List<BlockPos> sortedCoordinates = Sorter.Blocks.byDistance(coordinates, player); //Sort the coords by distance to player.
//
//        Tessellator t = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = t.getBuffer();
//
//        for (BlockPos coordinate : sortedCoordinates) {
//            boolean invisible = true;
//            IBlockState state = world.getBlockState(coordinate);
//            for (EnumFacing side : EnumFacing.values()) {
//                if (state.shouldSideBeRendered(world, coordinate, side)) {
//                    invisible = false;
//                    break;
//                }
//            }
//
//            if (invisible)
//                continue;
//
//            GlStateManager.pushMatrix();//Push matrix again just because
//            GlStateManager.translate(coordinate.getX(), coordinate.getY(), coordinate.getZ());//Now move the render position to the coordinates we want to render at
//            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F); //Rotate it because i'm not sure why but we need to
//            GlStateManager.translate(-0.005f, -0.005f, 0.005f);
//            GlStateManager.scale(1.01f, 1.01f, 1.01f);//Slightly Larger block to avoid z-fighting.
//
//            GlStateManager.disableLighting();
//            GlStateManager.disableTexture2D();
//
//            renderBoxSolid(t, bufferBuilder, 0, 0, -1, 1, 1, 0, 1, 0, 0, 0.5f);
//
//            GlStateManager.enableTexture2D();
//            GlStateManager.enableLighting();
//            GlStateManager.popMatrix();
//        }
//
//        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        ForgeHooksClient.setRenderLayer(MinecraftForgeClient.getRenderLayer());
//
//        GlStateManager.disableBlend();
//        GlStateManager.popMatrix();
//    }

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

    private static void renderBoxSolid(Tessellator tessellator, BufferBuilder bufferBuilder, double startX, double startY, double startZ, double endX, double endY, double endZ, float red, float green, float blue, float alpha) {
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);

        //down
        bufferBuilder.pos(startX, startY, startZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, startY, startZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, startY, endZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(startX, startY, endZ).color(red, green, blue, alpha).endVertex();

        //up
        bufferBuilder.pos(startX, endY, startZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(startX, endY, endZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, endY, endZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, endY, startZ).color(red, green, blue, alpha).endVertex();

        //east
        bufferBuilder.pos(startX, startY, startZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(startX, endY, startZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, endY, startZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, startY, startZ).color(red, green, blue, alpha).endVertex();

        //west
        bufferBuilder.pos(startX, startY, endZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, startY, endZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, endY, endZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(startX, endY, endZ).color(red, green, blue, alpha).endVertex();

        //south
        bufferBuilder.pos(endX, startY, startZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, endY, startZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, endY, endZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(endX, startY, endZ).color(red, green, blue, alpha).endVertex();

        //north
        bufferBuilder.pos(startX, startY, startZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(startX, startY, endZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(startX, endY, endZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(startX, endY, startZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
    }

    private static class Utils {

        /**
         * Returns a Vec3i of the players position based on partial tick.
         * Used for Render translation.
         */
        private static Vec3d getPlayerTranslate(EntityPlayer player, float partialTick) {

            return new Vec3d(
                    player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTick,
                    player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTick,
                    player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTick
            );


        }

        private static void stateManagerPrepareBlend() {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
        }

        /**
         * Prepares our render using base properties
         */
        private static void stateManagerPrepare(Vec3d playerPos, BlockPos blockPos, Float shift) {
            GlStateManager.translate(blockPos.getX()-playerPos.x, blockPos.getY() - playerPos.y, blockPos.getZ() - playerPos.z);//Now move the render position to the coordinates we want to render at
            // Rotate it because i'm not sure why but we need to
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(1f, 1f, 1f);

            // Slightly Larger block to avoid z-fighting.
            if( shift != null ) {
                GlStateManager.translate(-shift, -shift, shift);
                GlStateManager.scale(1.005f, 1.005f, 1.005f);
            }
        }
    }
}
