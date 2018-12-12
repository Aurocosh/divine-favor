package aurocosh.divinefavor.common.favor_sources.common;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.EventFavorSource;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.TickableFavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.MessagePartialDataSync;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.*;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

@Mod.EventBusSubscriber
public class FavorSourceManager {
    public static final AdvancementList ADVANCEMENT_LIST = ReflectionHelper.getPrivateValue(AdvancementManager.class, null, 2);

    private static List<FavorSource> favorSources = new ArrayList<>();
    private static Multimap<EntityPlayer, FavorSource> blockBreakSourcesMultimap = MultimapBuilder.hashKeys().arrayListValues().build();
    private static Multimap<EntityPlayer, FavorSource> blockPlaceSourcesMultimap = MultimapBuilder.hashKeys().arrayListValues().build();

    private static Multimap<TickableFavorSource,EntityPlayerMP> tickableFavorSources = MultimapBuilder.hashKeys().arrayListValues().build();

    public static void addFavorSource(FavorSource favorSource){
        favorSources.add(favorSource);
    }

    private static List<FavorGain> favorSourcesToProcess = new ArrayList<>();

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.player instanceof EntityPlayerMP))
            return;

        EntityPlayerMP playerMP = (EntityPlayerMP) event.player;
        PlayerAdvancements advancements = playerMP.getAdvancements();

        for (FavorSource favorSource : favorSources) {
            boolean unlocked = isDone(favorSource.unlockAdvancements, true, advancements);
            boolean locked = isDone(favorSource.lockAdvancement, false, advancements);
            if(!unlocked || locked)
                continue;

            if(favorSource instanceof TickableFavorSource)
                tickableFavorSources.put((TickableFavorSource) favorSource,playerMP);
            else if(favorSource instanceof EventFavorSource){
                EventFavorSource eventFavorSource = (EventFavorSource) favorSource;
                switch (eventFavorSource.eventType){
                    case BLOCK_BREAK:
                        blockBreakSourcesMultimap.put(playerMP, favorSource);
                        break;
                    case BLOCK_PLACE:
                        blockPlaceSourcesMultimap.put(playerMP, favorSource);
                        break;
                }
            }
        }
    }

    private static boolean isDone(ResourceLocation advancementName, boolean isUnlock, PlayerAdvancements advancements) {
        if (advancementName == null)
            return isUnlock;
        Advancement advancement = ADVANCEMENT_LIST.getAdvancement(advancementName);
        return advancements.getProgress(advancement).isDone();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        for (FavorSource favorSource : blockBreakSourcesMultimap.get(player))
            if (favorSource.isConditionsMet(player, event))
                favorSourcesToProcess.add(new FavorGain(player,favorSource));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlaceEvent(BlockEvent.PlaceEvent event) {
        EntityPlayer player = event.getPlayer();
        for (FavorSource favorSource : blockPlaceSourcesMultimap.get(player))
            if (favorSource.isConditionsMet(player, event))
                favorSourcesToProcess.add(new FavorGain(player,favorSource));
    }

    @SubscribeEvent
    public static void updateTickableSources(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            return;

        for (TickableFavorSource favorSource : tickableFavorSources.keySet()) {
            if(!favorSource.IsTickNeeded())
                continue;
            Collection<EntityPlayerMP> playerMPS = tickableFavorSources.get(favorSource);
            for (EntityPlayerMP playerMP : playerMPS)
                if (favorSource.isConditionsMet(playerMP, null))
                    favorSourcesToProcess.add(new FavorGain(playerMP, favorSource));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void processFavorSources(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        Multimap<EntityPlayer, ModFavor> favorsToUpdate = MultimapBuilder.hashKeys().hashSetValues().build();
        for (FavorGain favorGain : favorSourcesToProcess) {
            EntityPlayerMP playerMP = (EntityPlayerMP) favorGain.player;
            FavorSource favorSource = favorGain.favorSource;

            IFavorHandler favorHandler = playerMP.getCapability(CAPABILITY_FAVOR, null);
            if (favorHandler == null)
                continue;
            favorHandler.addFavor(favorSource.favor.id, favorSource.favorCount);
            favorsToUpdate.put(playerMP,favorSource.favor);
        }
        favorSourcesToProcess.clear();

        for (Map.Entry<EntityPlayer, Collection<ModFavor>> entry : favorsToUpdate.asMap().entrySet()) {
            EntityPlayer player = entry.getKey();
            Collection<ModFavor> favors = entry.getValue();

            MessagePartialDataSync message = new MessagePartialDataSync(player, favors);
            NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) player);
        }
    }
}
