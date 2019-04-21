package aurocosh.divinefavor.common.block.rope;

import aurocosh.divinefavor.common.block.base.ModBlockAir;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.item.base.ModItemBlock;
import aurocosh.divinefavor.common.state_mappers.InvisibleStateMapper;
import aurocosh.divinefavor.common.state_mappers.common.ICustomStateMappedBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRopeLight extends ModBlockAir implements ICustomStateMappedBlock {
    public BlockRopeLight(String name) {
        super(name, Material.AIR, ConstMainTabOrder.OTHER_BLOCKS);
        this.setTickRandomly(true);
        this.lightValue = 6;
    }

    @Override
    public ModItemBlock getItemBlock() {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IStateMapper getCustomStateMapper() {
        return new InvisibleStateMapper();
    }
}