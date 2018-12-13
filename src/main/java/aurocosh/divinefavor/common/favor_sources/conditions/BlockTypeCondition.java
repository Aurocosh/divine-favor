package aurocosh.divinefavor.common.favor_sources.conditions;

import aurocosh.divinefavor.common.favor_sources.conditions.base.FavorCondition;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.world.BlockEvent;

import javax.annotation.CheckForNull;
import java.util.Set;

public class BlockTypeCondition extends FavorCondition {
    private final Set<Block> acceptableBlocks;

    public BlockTypeCondition(Set<Block> acceptableBlocks) {
        this.acceptableBlocks = acceptableBlocks;
    }

    @Override
    public boolean isMet(EntityPlayer player, @CheckForNull Object context) {
        assert context instanceof BlockEvent;
        BlockEvent event = (BlockEvent) context;
        Block block = event.getState().getBlock();
        return acceptableBlocks.contains(block);
    }
}
