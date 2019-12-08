package aurocosh.divinefavor.common.custom_data.player.capability

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.custom_data.CustomDataStorage
import aurocosh.divinefavor.common.custom_data.player.data.aura.arboreal.ArborealAuraDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.aura.calling.CallingAuraDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.aura.charred.CharredAuraDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.aura.distorted.DistortedAuraDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.aura.energetic.EnergeticAuraDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.aura.frosty.FrostyAuraDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.aura.hunters.HuntersAuraDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.aura.visceral.VisceralAuraDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.curse.corrosion.ArmorCorrosionDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.curse.crawling_mist.CrawlingMistDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye.EvilEyeDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.curse.red_fury.RedFuryDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.materia.MaterialPresenceDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.presence.energetic.EnergeticPresenceDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.presence.furious.FuriousPresenceDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.presence.manipulative.ManipulativePresenceDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.presence.predatory.PredatoryPresenceDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.presence.scorching.ScorchingPresenceDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.presence.towering.ToweringPresenceDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.spell.escape_plan.EscapePlanDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.spell.focused_fury.FocusedFuryDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.spell.gills.GillsDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.spell.grudge.GrudgeDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.spell.molten_skin.MoltenSkinDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.spell.pearl_crumbs.PearlCrumbsDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.spell.vengeful_blade.VengfulBladeDataSerializer
import aurocosh.divinefavor.common.custom_data.player.data.templates.TemplateDataSerializer
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class PlayerDataDataHandler {

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    fun attachCapabilities(event: AttachCapabilitiesEvent<Entity>) {
        if (event.getObject() is EntityPlayer)
            event.addCapability(ResourceNamer.getFullName("capability_player_data"), PlayerDataProvider())
    }

    companion object {
        // The Capability field. Used for checks and references.
        // Initialized when forge registers the capability.
        @CapabilityInject(IPlayerDataHandler::class)
        val capability: Capability<IPlayerDataHandler>? = null

        val CAPABILITY_PLAYER_DATA: Capability<IPlayerDataHandler>
            get() = capability as Capability<IPlayerDataHandler>

        // Handles all of the required registration for the capability.
        fun register() {
            CapabilityManager.INSTANCE.register(IPlayerDataHandler::class.java, getStorage(), DefaultPlayerDataHandler::class.java)
            MinecraftForge.EVENT_BUS.register(PlayerDataDataHandler())
        }

        private fun getStorage(): Capability.IStorage<IPlayerDataHandler> {
            val storage = CustomDataStorage<IPlayerDataHandler>()
            storage.addSerializer(SpiritDataSerializer(), IPlayerDataHandler::spiritData)

            storage.addSerializer(ArmorCorrosionDataSerializer(), IPlayerDataHandler::armorCorrosionData)
            storage.addSerializer(CrawlingMistDataSerializer(), IPlayerDataHandler::crawlingMistData)
            storage.addSerializer(EvilEyeDataSerializer(), IPlayerDataHandler::evilEyeData)
            storage.addSerializer(RedFuryDataSerializer(), IPlayerDataHandler::redFuryData)

            storage.addSerializer(EscapePlanDataSerializer(), IPlayerDataHandler::escapePlanData)
            storage.addSerializer(FocusedFuryDataSerializer(), IPlayerDataHandler::focusedFuryData)
            storage.addSerializer(GillsDataSerializer(), IPlayerDataHandler::gillsData)
            storage.addSerializer(GrudgeDataSerializer(), IPlayerDataHandler::grudgeData)
            storage.addSerializer(InteractionDataSerializer(), IPlayerDataHandler::interactionData)
            storage.addSerializer(MoltenSkinDataSerializer(), IPlayerDataHandler::moltenSkinData)
            storage.addSerializer(PearlCrumbsDataSerializer(), IPlayerDataHandler::pearlCrumbsData)
            storage.addSerializer(TemplateDataSerializer(), IPlayerDataHandler::templateData)
            storage.addSerializer(VengfulBladeDataSerializer(), IPlayerDataHandler::vengefulBladeData)

            storage.addSerializer(ArborealAuraDataSerializer(), IPlayerDataHandler::arborealAuraData)
            storage.addSerializer(CallingAuraDataSerializer(), IPlayerDataHandler::callingAuraData)
            storage.addSerializer(CharredAuraDataSerializer(), IPlayerDataHandler::charredAuraData)
            storage.addSerializer(DistortedAuraDataSerializer(), IPlayerDataHandler::distortedAuraData)
            storage.addSerializer(EnergeticAuraDataSerializer(), IPlayerDataHandler::energeticAuraData)
            storage.addSerializer(FrostyAuraDataSerializer(), IPlayerDataHandler::frostyAuraData)
            storage.addSerializer(HuntersAuraDataSerializer(), IPlayerDataHandler::huntersAuraData)
            storage.addSerializer(VisceralAuraDataSerializer(), IPlayerDataHandler::visceralAuraData)

            storage.addSerializer(EnergeticPresenceDataSerializer(), IPlayerDataHandler::energeticPresenceData)
            storage.addSerializer(FuriousPresenceDataSerializer(), IPlayerDataHandler::furiousPresenceData)
            storage.addSerializer(ManipulativePresenceDataSerializer(), IPlayerDataHandler::manipulativePresenceData)
            storage.addSerializer(MaterialPresenceDataSerializer(), IPlayerDataHandler::materialPresenceData)
            storage.addSerializer(PredatoryPresenceDataSerializer(), IPlayerDataHandler::predatoryPresenceData)
            storage.addSerializer(ScorchingPresenceDataSerializer(), IPlayerDataHandler::scorchingPresenceData)
            storage.addSerializer(ToweringPresenceDataSerializer(), IPlayerDataHandler::toweringPresenceData)

            return storage
        }
    }
}