package aurocosh.divinefavor.common.block.base

import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable
import net.minecraft.world.World

abstract class TickableTileEntity(private val tickOnClient: Boolean, private val tickOnServer: Boolean) : TileEntity(), ITickable {
    private var tickRate: Int = 0
    private var tickCounter: Int = 0

    private var tickingOnThisSideEnabled: Boolean = false

    init {
        tickRate = 40
        tickCounter = 0
        tickingOnThisSideEnabled = false
    }

    override fun setWorld(worldIn: World) {
        super.setWorld(worldIn)
        val isClient = world.isRemote
        tickingOnThisSideEnabled = isClient && tickOnClient || !isClient && tickOnServer
    }

    fun setTickRateInSeconds(seconds: Int) {
        this.tickRate = seconds * 20
    }

    fun setTickRate(tickRate: Int) {
        this.tickRate = tickRate
    }

    override fun update() {
        if (!tickingOnThisSideEnabled)
            return
        tickCounter++
        if (tickCounter < tickRate)
            return
        tickCounter = 0
        updateFiltered()
    }

    protected abstract fun updateFiltered()
}