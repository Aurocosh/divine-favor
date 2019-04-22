package aurocosh.divinefavor.common.network.message.client.config;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.network.base.ConfigSyncClientMessage;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageConfigOreBlocks extends ConfigSyncClientMessage {
    public ArrayList<String> oreBlocks;

    public MessageConfigOreBlocks() {
    }

    @Override
    protected void getServerConfigs() {
        oreBlocks = new ArrayList<>();
        oreBlocks.addAll(Arrays.asList(ConfigGeneral.oreBlocks));
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        ConfigGeneral.ORE_BLOCKS.clear();
        for (String oreBlock : oreBlocks) {
            Block block = Block.getBlockFromName(oreBlock);
            if (block != null)
                ConfigGeneral.ORE_BLOCKS.add(block);
        }
    }
}
