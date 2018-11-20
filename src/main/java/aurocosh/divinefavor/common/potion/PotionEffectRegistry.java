/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package aurocosh.divinefavor.common.potion;

import java.util.ArrayList;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.potion.effect.PotionBase;
import aurocosh.divinefavor.common.potion.effect.PotionWaterwalk;
import aurocosh.divinefavor.common.util.UtilChat;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionEffectRegistry {

  public static ArrayList<Potion> potions = new ArrayList<>();

  public static enum PotionSize {
    NORMAL, POWERED, LONG//, SPLASH, LINGER // todo: these last two
  }

  public static final PotionBase waterwalk = new PotionWaterwalk();
  public static ArrayList<PotionBase> potionEffects = new ArrayList<PotionBase>();

  public static void register() {
    //  PotionType t http://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/2842885-solved-how-can-i-add-my-own-potion-with-my-own
    /* Assuming you've created and registered the Potion instances, you then need to create and register a PotionType for each brewable potion. For most vanilla Potions there are one to three
     * PotionTypes: regular (e.g. Regeneration, 0:45) , long (e.g. Regeneration, 1:30) and strong (e.g. Regeneration II, 0:22).
     * 
     * Both Potion and PotionType are implementations of IForgeRegistryEntry, so they should be registered during the appropriate registry events.
     * 
     * Once you've created and registered the Potions and PotionTypes, use PotionHelper.addMix to add the brewing recipes (e.g. Awkward to Regular X, Regular X to Long X and Regular X to Strong X).
     * For more advanced brewing recipes, you can use Forge's BrewingRecipeRegistry. */

    registerPotionEffect(waterwalk);
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
    PotionEffectRegistry.register();
    for (Potion b : potions) {
      event.getRegistry().register(b);
    }
  }

  public static String getStrForLevel(int lvl) {
    return UtilChat.lang("enchantment.level." + lvl);
  }
}
