package aurocosh.divinefavor.common.block.rope

import aurocosh.divinefavor.common.block.base.ModBlockAir
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItemBlock
import aurocosh.divinefavor.common.state_mappers.InvisibleStateMapper
import aurocosh.divinefavor.common.state_mappers.common.ICustomStateMappedBlock
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.block.statemap.IStateMapper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class BlockRopeLight(name: String) : ModBlockAir(name, Material.AIR, ConstMainTabOrder.OTHER_BLOCKS), ICustomStateMappedBlock {
    init {
        this.tickRandomly = true
        this.lightValue = 6
    }

    public override fun getItemBlock(): ModItemBlock? {
        return null
    }

    @SideOnly(Side.CLIENT)
    override fun getCustomStateMapper(): IStateMapper {
        return InvisibleStateMapper()
    }
}