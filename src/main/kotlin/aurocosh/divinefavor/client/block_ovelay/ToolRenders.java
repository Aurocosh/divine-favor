package aurocosh.divinefavor.client.block_ovelay;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
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


    public static void renderBuilderOverlay(RenderWorldLastEvent evt, EntityPlayer player, IBlockState state, List<BlockPos> coordinates) {
        // Calculate the players current position, which is needed later

        if (state == Blocks.AIR.getDefaultState())
            return;
        if (coordinates.size() == 0)
            return;
//            coordinates = BuildingModes.collectPlacementPos(player.world, player, lookingAt.getBlockPos(), lookingAt.sideHit, heldItem, lookingAt.getBlockPos());

        // Figure out how many of the block we're rendering are in the player inventory.
//        ItemStack itemStack = ToolRenders.Utils.getSilkDropIfPresent(player.world, state, player);

        // Check if we have the blocks required
//        long hasBlocks = InventoryManipulation.INSTANCE.countItem(itemStack, player);
        long hasBlocks = Integer.MAX_VALUE;

        // Prepare the fake world -- using a fake world lets us render things properly, like fences connecting.
        Set<BlockPos> coords =  new HashSet<>(coordinates);
        fakeWorld.setWorldAndState(player.world, state, coords);

        GlStateManager.pushMatrix();
        ToolRenders.Utils.stateManagerPrepareBlend();

        List<BlockPos> sortedCoordinates = Sorter.Blocks.byDistance(coordinates, player); //Sort the coords by distance to player.

        Vec3d playerPos = ToolRenders.Utils.getPlayerTranslate(player, evt.getPartialTicks());
        for (BlockPos coordinate : sortedCoordinates) {
            GlStateManager.pushMatrix();
            ToolRenders.Utils.stateManagerPrepare(playerPos, coordinate, null);
            GL14.glBlendColor(1F, 1F, 1F, 0.55f); //Set the alpha of the blocks we are rendering

            if (fakeWorld.getWorldType() != WorldType.DEBUG_ALL_BLOCK_STATES)
                state = state.getActualState(fakeWorld, coordinate);

            mc.getBlockRendererDispatcher().renderBlockBrightness(state, 1f);//Render the defined block
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            ToolRenders.Utils.stateManagerPrepare(playerPos, coordinate, 0.01f);
            GlStateManager.scale(1.006f, 1.006f, 1.006f);
            GL14.glBlendColor(1F, 1F, 1F, 0.35f);

            hasBlocks--;

            if (hasBlocks < 0)
                mc.getBlockRendererDispatcher().renderBlockBrightness(stainedGlassRed, 1f);

            // Move the render position back to where it was
            GlStateManager.popMatrix();
        }

        //Set blending back to the default mode
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ForgeHooksClient.setRenderLayer(MinecraftForgeClient.getRenderLayer());
        //Disable blend
        GlStateManager.disableBlend();
        //Pop from the original push in this method
        GlStateManager.popMatrix();
    }
//
//    public static void renderExchangerOverlay(RenderWorldLastEvent evt, EntityPlayer player, ItemStack heldItem) {
//        // Calculate the players current position, which is needed later
//        Vec3d playerPos = ToolRenders.Utils.getPlayerTranslate(player, evt.getPartialTicks());
//
//        BlockRendererDispatcher dispatcher = mc.getBlockRendererDispatcher();
//
//        RayTraceResult lookingAt = VectorTools.getLookingAt(player, heldItem);
//        IBlockState state = Blocks.AIR.getDefaultState();
//        List<BlockPos> coordinates = getAnchor(heldItem);
//
//        if (lookingAt == null && coordinates.size() == 0)
//            return;
//
//        IBlockState renderBlockState = getToolBlock(heldItem);
//        Minecraft mc = Minecraft.getMinecraft();
//        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//        if (renderBlockState == Blocks.AIR.getDefaultState()) {//Don't render anything if there is no block selected (Air)
//            return;
//        }
//        if (coordinates.size() == 0 && lookingAt != null) { //Build a list of coordinates based on the tool mode and range
//            coordinates = ExchangingModes.collectPlacementPos(player.world, player, lookingAt.getBlockPos(), lookingAt.sideHit, heldItem, lookingAt.getBlockPos());
//        }
//
//        // Figure out how many of the block we're rendering we have in the inventory of the player.
//        ItemStack itemStack = ToolRenders.Utils.getSilkDropIfPresent(player.world, renderBlockState, player);
//
//        long hasBlocks = InventoryManipulation.countItem(itemStack, player, cacheInventory);
//        hasBlocks = hasBlocks + InventoryManipulation.countPaste(player);
//
//        // Prepare the fake world -- using a fake world lets us render things properly, like fences connecting.
//        Set<BlockPos> coords =  new HashSet<>(coordinates);
//        fakeWorld.setWorldAndState(player.world, renderBlockState, coords);
//
//        GlStateManager.pushMatrix();
//        ToolRenders.Utils.stateManagerPrepareBlend();
//
//        for (BlockPos coordinate : coordinates) {
//            GlStateManager.pushMatrix();
//            ToolRenders.Utils.stateManagerPrepare(playerPos, coordinate, 0.001f);
//            GL14.glBlendColor(1F, 1F, 1F, 0.55f); //Set the alpha of the blocks we are rendering
//
//            // Get the block state in the fake world
//            if (fakeWorld.getWorldType() != WorldType.DEBUG_ALL_BLOCK_STATES)
//                state = renderBlockState.getActualState(fakeWorld, coordinate);
//
//            if (renderBlockState.getRenderType() != EnumBlockRenderType.INVISIBLE) {
//                dispatcher.renderBlockBrightness(state, 1f);//Render the defined block
//                GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F); //Rotate it because i'm not sure why but we need to
//            }
//
//            GL14.glBlendColor(1F, 1F, 1F, 0.1f); //Set the alpha of the blocks we are rendering
//            dispatcher.renderBlockBrightness(stainedGlassWhite, 1f);//Render the defined block - White glass to show non-full block renders (Example: Torch)
//            GlStateManager.popMatrix();
//
//            GlStateManager.pushMatrix();
//            ToolRenders.Utils.stateManagerPrepare(playerPos, coordinate, 0.002f);
//
//            GlStateManager.scale(1.02f, 1.02f, 1.02f); //Slightly Larger block to avoid z-fighting.
//            GL14.glBlendColor(1F, 1F, 1F, 0.55f); //Set the alpha of the blocks we are rendering
//            hasBlocks--;
//
//            if (hasBlocks < 0)
//                dispatcher.renderBlockBrightness(stainedGlassRed, 1f);
//
//            // Move the render position back to where it was
//            GlStateManager.popMatrix();
//        }
//
//        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        ForgeHooksClient.setRenderLayer(MinecraftForgeClient.getRenderLayer());
//
//        GlStateManager.disableBlend();
//        GlStateManager.popMatrix();
//    }
//
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
