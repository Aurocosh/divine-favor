package aurocosh.divinefavor.common.custom_data.player.crawling_mist;

import net.minecraft.util.math.BlockPos;

// The default implementation of the capability. Holds all the logic.
public class DefaultCrawlingMistHandler implements ICrawlingMistHandler {
    private BlockPos pos;

    public DefaultCrawlingMistHandler() {
        this.pos = BlockPos.ORIGIN;
    }

    @Override
    public void setMistOrigin(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public BlockPos getMistOrigin() {
        return pos;
    }
}
