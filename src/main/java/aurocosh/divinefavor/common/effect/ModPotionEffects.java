package aurocosh.divinefavor.common.effect;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.effect.effect.DivineFavorPotion;
import aurocosh.divinefavor.common.effect.effect.PotionEmpowerAxe;
import aurocosh.divinefavor.common.effect.effect.PotionLavawalk;
import aurocosh.divinefavor.common.effect.effect.PotionWaterwalk;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class ModPotionEffects {
    public static ArrayList<Potion> potions = new ArrayList<>();
    public static ArrayList<DivineFavorPotion> potionEffects = new ArrayList<>();

    public static final DivineFavorPotion waterwalk = new PotionWaterwalk();
    public static final DivineFavorPotion lavawalk = new PotionLavawalk();
    public static final DivineFavorPotion empower_axe = new PotionEmpowerAxe();

    public static void register() {
        registerPotionEffect(waterwalk);
        registerPotionEffect(lavawalk);
        registerPotionEffect(empower_axe);
    }

    private static void registerPotionEffect(DivineFavorPotion effect) {
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
