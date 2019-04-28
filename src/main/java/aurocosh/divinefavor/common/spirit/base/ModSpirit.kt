package aurocosh.divinefavor.common.spirit.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.entries.SpiritConfig
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.custom_data.player.PlayerData
import aurocosh.divinefavor.common.global.dayClock.DayClock
import aurocosh.divinefavor.common.lib.TimePeriod
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry
import aurocosh.divinefavor.common.network.message.client.activity.MessageSpiritBecameActive
import aurocosh.divinefavor.common.network.message.client.activity.MessageSpiritBecameInactive
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.registry.ModRegistries
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import aurocosh.divinefavor.common.util.UtilDayTime
import aurocosh.divinefavor.common.util.UtilPlayer
import aurocosh.divinefavor.common.util.UtilWorld
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.registries.IForgeRegistryEntry

class ModSpirit(val name: String, val punishment: SpiritPunishment, config: SpiritConfig) : IForgeRegistryEntry.Impl<ModSpirit>(), IIndexedEntry {
    private val id: Int
    val offering: Item? = Item.getByNameOrId(config.offering)
    val offeringCount: Int
    val activityPeriod: TimePeriod

    val icon: ResourceLocation
    val symbol: ResourceLocation

    val favorMin: Int
    val favorMax: Int
    val favorRegen: Int

    val isActive: Boolean
        get() {
            val timeOfDay = DayClock.time
            return activityPeriod.isDayTimeInRange(timeOfDay)
        }

    val nameTranslationKey: String
        get() = registryName.toString() + ".name"

    init {
        if (offering == null)
            DivineFavor.logger.error("Spirit error: $name. Offering not found: ${config.offering}")
        offeringCount = config.offeringCount
        activityPeriod = UtilDayTime.fromHours(config.activityPeriod.start, config.activityPeriod.stop)
        favorMin = config.startingPlayerFavorMinimum
        favorMax = config.startingPlayerFavorMaximum
        favorRegen = config.startingPlayerFavorRegen
        registryName = ResourceNamer.getFullName("spirit", name)
        icon = ResourceLocation(ConstResources.PREFIX_SPIRIT_ICONS + name + ".png")
        symbol = ResourceLocation(ConstResources.PREFIX_SPIRIT_SYMBOLS + name + ".png")

        id = ModMappers.spirits.register(this)
        ModRegistries.spirits.register(this)
        registerActivityPeriod()
    }

    private fun becameActive() {
        val playerList = FMLCommonHandler.instance().minecraftServerInstance.playerList.players
        if (playerList.isEmpty())
            return
        if (playerList[0].world.isRemote)
            return
        regenerateFavor(playerList)
        informActivity(playerList)
    }

    private fun regenerateFavor(playerList: List<EntityPlayerMP>) {
        for (player in playerList) {
            val spiritData = PlayerData.get(player).spiritData
            if (spiritData.regenerateFavor(id))
                MessageSyncFavor(this, spiritData).sendTo(player)
        }
    }

    private fun informActivity(playerList: List<EntityPlayerMP>) {
        for (player in playerList) {
            val spiritData = PlayerData.get(player).spiritData
            if (spiritData.isInform(id))
                MessageSpiritBecameActive(id).sendTo(player)
        }
    }

    private fun becameInactive() {
        val playerList = FMLCommonHandler.instance().minecraftServerInstance.playerList.players
        if (playerList.isEmpty())
            return
        if (playerList[0].world.isRemote)
            return
        informInactivity(playerList)
    }

    private fun informInactivity(playerList: List<EntityPlayerMP>) {
        for (player in playerList) {
            val spiritData = PlayerData.get(player).spiritData
            if (spiritData.isInform(id))
                MessageSpiritBecameInactive(id).sendTo(player)
        }
    }

    private fun registerActivityPeriod() {
        DayClock.addAlarm(activityPeriod.start, { this.becameActive() }, true)
        DayClock.addAlarm(activityPeriod.stop, { this.becameInactive() }, true)
    }

    override fun getId(): Int {
        return id
    }
}
