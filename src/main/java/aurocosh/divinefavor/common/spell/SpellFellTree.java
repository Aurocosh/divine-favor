package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.Spell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.events.TalismanEvent;
import aurocosh.divinefavor.common.constants.LibSpellNames;
import aurocosh.divinefavor.common.spell.base.SpellType;
import aurocosh.divinefavor.common.util.UtilBlock;
import com.google.common.collect.Lists;
import gnu.trove.set.hash.THashSet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class SpellFellTree extends Spell {
    public SpellFellTree() {
        super(SpellType.STONEBALL_THROW);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        ItemStack talisman = context.playerIn.getHeldItem(context.hand);

        if(detectTree(context.worldIn, context.pos)) {
            return fellTree(talisman, context.pos, context.playerIn);
        }
        return true;
    }

    @Override
    protected boolean claimCost( SpellContext context) {
        return true;
    }

    protected static boolean claimSubCost() {
        return true;
    }

    public static boolean detectTree(World world, BlockPos origin) {
        BlockPos pos = null;
        Stack<BlockPos> candidates = new Stack<>();
        candidates.add(origin);

        while(!candidates.isEmpty()) {
            BlockPos candidate = candidates.pop();
            if((pos == null || candidate.getY() > pos.getY()) && isLog(world, candidate)) {
                pos = candidate.up();
                // go up
                while(isLog(world, pos)) {
                    pos = pos.up();
                }
                // check if we still have a way diagonally up
                candidates.add(pos.north());
                candidates.add(pos.east());
                candidates.add(pos.south());
                candidates.add(pos.west());
            }
        }

        // not even one match, so there were no logs.
        if(pos == null) {
            return false;
        }

        // check if there were enough leaves around the last position
        // pos now contains the block above the topmost log
        // we want at least 5 leaves in the surrounding 26 blocks
        int d = 3;
        int o = -1; // -(d-1)/2
        int leaves = 0;
        for(int x = 0; x < d; x++) {
            for(int y = 0; y < d; y++) {
                for(int z = 0; z < d; z++) {
                    BlockPos leaf = pos.add(o + x, o + y, o + z);
                    IBlockState state = world.getBlockState(leaf);
                    if(state.getBlock().isLeaves(state, world, leaf)) {
                        if(++leaves >= 5) {
                            return true;
                        }
                    }
                }
            }
        }

        // not enough leaves. sorreh
        return false;
    }

    private static boolean isLog(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isWood(world, pos);
    }

    public static boolean fellTree(ItemStack itemstack, BlockPos start, EntityPlayer player) {
        if(player.getEntityWorld().isRemote) {
            return true;
        }
        TalismanEvent.ExtraBlockBreak event = TalismanEvent.ExtraBlockBreak.fireEvent(itemstack, player, player.getEntityWorld().getBlockState(start), 3, 3, 3, -1);
        int speed = Math.round((event.width * event.height * event.depth) / 27f);
        if(event.distance > 0) {
            speed = event.distance + 1;
        }

        MinecraftForge.EVENT_BUS.register(new TreeChopTask(itemstack, start, player, speed));
        return true;
    }

    public static class TreeChopTask {

        public final World world;
        public final EntityPlayer player;
        public final ItemStack tool;
        public final int blocksPerTick;

        public Queue<BlockPos> blocks = Lists.newLinkedList();
        public Set<BlockPos> visited = new THashSet<>();

        public TreeChopTask(ItemStack tool, BlockPos start, EntityPlayer player, int blocksPerTick) {
            this.world = player.getEntityWorld();
            this.player = player;
            this.tool = tool;
            this.blocksPerTick = blocksPerTick;

            this.blocks.add(start);
        }

        @SubscribeEvent
        public void chopChop(TickEvent.WorldTickEvent event) {
            if(event.side.isClient()) {
                finish();
                return;
            }
            // only if same dimension
            if(event.world.provider.getDimension() != world.provider.getDimension()) {
                return;
            }

            // setup
            int left = blocksPerTick;

            // continue running
            BlockPos pos;
            while(left > 0) {
                // completely done or can't do our job anymore?!
                if(blocks.isEmpty() || !claimSubCost()) {
                    finish();
                    return;
                }

                pos = blocks.remove();
                if(!visited.add(pos)) {
                    continue;
                }

                // can we harvest the block and is effective?
                if(!isLog(world, pos)) {
                    continue;
                }

                // save its neighbours
                for(EnumFacing facing : new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST }) {
                    BlockPos pos2 = pos.offset(facing);
                    if(!visited.contains(pos2)) {
                        blocks.add(pos2);
                    }
                }

                // also add the layer above.. stupid acacia trees
                for(int x = 0; x < 3; x++) {
                    for(int z = 0; z < 3; z++) {
                        BlockPos pos2 = pos.add(-1 + x, 1, -1 + z);
                        if(!visited.contains(pos2)) {
                            blocks.add(pos2);
                        }
                    }
                }

                // break it, wooo!
                UtilBlock.removeBlockWithDrops(player, world, tool, pos, false);
                left--;
            }
        }

        private void finish() {
            // goodbye cruel world
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
