package aurocosh.divinefavor.common.registry.content;

import aurocosh.divinefavor.IContent;
import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.potion.PotionEffectRegistry;
import aurocosh.divinefavor.common.potion.PotionTypeDivine;
import aurocosh.divinefavor.common.potion.PotionTypeRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration;

public class ItemPotionContent implements IContent {

  public static boolean enableWaterwalk;
  private PotionTypeDivine potionTypeWaterwalk;

  private final int SHORT = 1800;
  private final int NORMAL = 3600;

  @Override
  public void register() {
    if (ItemPotionContent.enableWaterwalk) {
      potionTypeWaterwalk = PotionTypeRegistry.addPotionType(new PotionEffect(PotionEffectRegistry.waterwalk, NORMAL), "waterwalk", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.COD.getMetadata()));
    }
  }

  @Override
  public void syncConfig(Configuration config) {
    String category = LibMisc.content;
    enableWaterwalk = true;
//    enableWaterwalk = config.getBoolean("PotionWaterwalk", category, true, Const.ConfigCategory.contentDefaultText);
  }

  @Override
  public boolean enabled() {
    return true;
  }
}
