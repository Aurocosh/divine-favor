package aurocosh.divinefavor.common.block.rope

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.base.ModBlockAir
import aurocosh.divinefavor.common.config.common.ConfigRope
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItemBlock
import aurocosh.divinefavor.common.particles.ModParticles
import aurocosh.divinefavor.common.particles.particles.EtherealParticle
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.state_mappers.InvisibleStateMapper
import aurocosh.divinefavor.common.state_mappers.common.ICustomStateMappedBlock
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.statemap.IStateMapper
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class BlockLuminousRopeSource(name: String) : ModBlockAir(name, Material.AIR, ConstMainTabOrder.OTHER_BLOCKS), ICustomStateMappedBlock {
    init {
        lightValue = ConfigRope.luminousRope.lightLevel
    }

    public override fun getItemBlock(): ModItemBlock? {
        return null
    }

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) {
        if (ConfigRope.luminousRope.doLightsTimeOut)
            world.scheduleUpdate(pos, this, ConfigRope.luminousRope.lightSourceDuration)
    }

    override fun updateTick(world: World, pos: BlockPos, state: IBlockState, rand: Random) {
        world.setBlockToAir(pos)
    }

    @SideOnly(Side.CLIENT)
    override fun randomDisplayTick(stateIn: IBlockState, worldIn: World, pos: BlockPos, rand: Random) {
        val player = DivineFavor.proxy.clientPlayer
        if (player.isPotionActive(ModPotions.prismatic_eyes))
            spawnParticles(worldIn, pos)
    }

    @SideOnly(Side.CLIENT)
    private fun spawnParticles(worldIn: World, pos: BlockPos) {
        val random = worldIn.rand
        for (i in 0..5) {
            var x = (pos.x.toFloat() + random.nextFloat()).toDouble()
            var y = (pos.y.toFloat() + random.nextFloat()).toDouble()
            var z = (pos.z.toFloat() + random.nextFloat()).toDouble()

            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube)
                y = pos.y.toDouble() + 0.0625 + 1.0

            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube)
                y = pos.y.toDouble() - 0.0625

            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube)
                z = pos.z.toDouble() + 0.0625 + 1.0

            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube)
                z = pos.z.toDouble() - 0.0625

            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube)
                x = pos.x.toDouble() + 0.0625 + 1.0

            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube)
                x = pos.x.toDouble() - 0.0625

            if (x < pos.x.toDouble() || x > (pos.x + 1).toDouble() || y < 0.0 || y > (pos.y + 1).toDouble() || z < pos.z.toDouble() || z > (pos.z + 1).toDouble()) {
                val position = Vec3d(x, y, z)
                ModParticles.normal.createParticle(position) { EtherealParticle(worldIn, position, 1f, 1f) }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override fun getCustomStateMapper(): IStateMapper {
        return InvisibleStateMapper()
    }
}