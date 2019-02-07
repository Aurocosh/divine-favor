package aurocosh.divinefavor.common.custom_data.player.data.curse.crawling_mist;

import net.minecraft.util.math.BlockPos;

// The default implementation of the capability. Holds all the logic.
public class CrawlingMistData {
    private BlockPos pos;

    public CrawlingMistData() {
        this.pos = BlockPos.ORIGIN;
    }

    public void setMistOrigin(BlockPos pos) {
        this.pos = pos;
    }

    public BlockPos getMistOrigin() {
        return pos;
    }
}
