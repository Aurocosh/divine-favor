package aurocosh.divinefavor.common.other;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.base.IDescriptionProvider;
import aurocosh.divinefavor.common.item.base.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.darkhax.bookshelf.util.GameUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.oredict.OreDictionary;

@JEIPlugin
public class DivineFavorJEIPlugin implements IModPlugin {
    @Override
    public void register (IModRegistry registry) {

        for (final Item item : ModItems.getItems()) {
            if(!(item instanceof IDescriptionProvider))
                continue;
            IDescriptionProvider descriptionProvider = (IDescriptionProvider)item;
            final String key = "jei." + descriptionProvider.getTranslationKey();
            registry.addIngredientInfo(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), ItemStack.class, key);
            this.validateKey(key);
        }
    }

    private void validateKey (String key) {

        if (GameUtils.isClient())
            if (!I18n.canTranslate(key))
                DivineFavor.logger.info("Could not translate: " + key);
    }
}
