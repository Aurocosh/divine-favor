//package aurocosh.divinefavor.common.spirit;
//
//import aurocosh.divinefavor.common.lib.TickCounter;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.TickEvent;
//
//@Mod.EventBusSubscriber
//public class ActivityHandler {
//    private static int currentHour = 0;
//    private static TickCounter tickCounter = new TickCounter(1000);
//
//    @SubscribeEvent
//    public static void updateTickableSources(TickEvent.ServerTickEvent event) {
//        if (event.phase == TickEvent.Phase.END)
//            return;
//
//        if(!tickCounter.tick())
//            return;
//
//        World par3World = new World(); // Not sure about this, not needed if your using a method that has the World in it's perenthsis e.g onItemUse.
//        long i = par3World.getWorldTime();
//
//        if(i < 2400){
//            return VALUE;
//        }
//
//        int currentTicks =
//
//        for (TickableFavorSource favorSource : tickableFavorSources.keySet()) {
//            if(!favorSource.IsTickNeeded())
//                continue;
//
//            Collection<EntityPlayerMP> playerMPS = tickableFavorSources.get(favorSource);
//            for (EntityPlayerMP playerMP : playerMPS)
//                if (favorSource.IsFavorNeeded(playerMP))
//                    favorSourcesToProcess.add(new FavorToProcess(playerMP, favorSource));
//        }
//    }
//
//}
