package aurocosh.divinefavor.common.network.message.client.activity

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.network.base.WrappedClientMessage
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import net.minecraft.client.resources.I18n
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSpiritBecameActive : WrappedClientMessage {
    var spiritId: Int = 0

    constructor() {}

    constructor(spiritId: Int) {
        this.spiritId = spiritId
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        val spirit = ModMappers.spirits.get(spiritId)
        val name = I18n.format(spirit.nameTranslationKey)
        val message = I18n.format("divinefavor:spirit_became_active", name)
        player.sendMessage(TextComponentString(message))
    }
}
