package aurocosh.divinefavor.common.effect;

import java.util.ArrayList;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.effect.effect.PotionLavawalk;
import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.effect.effect.PotionBase;
import aurocosh.divinefavor.common.effect.effect.PotionWaterwalk;
import aurocosh.divinefavor.common.util.UtilChat;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModPotionEffects {
    public static ArrayList<Potion> potions = new ArrayList<>();
    public static ArrayList<PotionBase> potionEffects = new ArrayList<>();

    public static final PotionBase waterwalk = new PotionWaterwalk();
    public static final PotionBase lavawalk = new PotionLavawalk();

    public static void register() {
        registerPotionEffect(waterwalk);
        registerPotionEffect(lavawalk);
    }

    private static void registerPotionEffect(PotionBase effect) {
        effect.setIcon(effect.getIcon());
        effect.setRegistryName(new ResourceLocation(LibMisc.MOD_ID, effect.getName()));
        potions.add(effect);
        potionEffects.add(effect);
        DivineFavor.instance.events.register(effect);
    }

    @SubscribeEvent
    public static void onRegistryEvent(RegistryEvent.Register<Potion> event) {
        ModPotionEffects.register();
        for (Potion b : potions) {
            event.getRegistry().register(b);
        }
    }
}
