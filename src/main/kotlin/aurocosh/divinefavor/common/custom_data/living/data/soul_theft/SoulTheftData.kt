package aurocosh.divinefavor.common.custom_data.living.data.soul_theft

import net.minecraft.entity.player.EntityPlayer
import java.util.*

class SoulTheftData {
    private val thiefUUIDS: HashSet<UUID> = HashSet()

    val uuids: List<UUID>
        get() = ArrayList(thiefUUIDS)

    fun reset() {
        thiefUUIDS.clear()
    }

    fun isThief(player: EntityPlayer): Boolean {
        val uuid = player.gameProfile.id
        return thiefUUIDS.contains(uuid)
    }

    fun addThief(player: EntityPlayer) {
        val uuid = player.gameProfile.id
        thiefUUIDS.add(uuid)
    }

    fun settUUIDS(uuidList: List<UUID>) {
        thiefUUIDS.clear()
        thiefUUIDS.addAll(uuidList)
    }
}
