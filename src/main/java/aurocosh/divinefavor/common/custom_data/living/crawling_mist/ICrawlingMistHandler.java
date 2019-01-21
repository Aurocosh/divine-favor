package aurocosh.divinefavor.common.custom_data.living.crawling_mist;

import net.minecraft.util.math.BlockPos;

public interface ICrawlingMistHandler {
    void setMistOrigin(BlockPos pos);
    BlockPos getMistOrigin();
}
