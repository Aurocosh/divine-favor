package aurocosh.divinefavor.common.integrations.patchouli.processors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;

public class ItemDescriptionProcessor implements IComponentProcessor {
    String text;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String itemName = variables.get("talisman");
        Item item = Item.REGISTRY.getObject(new ResourceLocation(itemName));
        if (item instanceof ModItem) {
            ModItem talisman = (ModItem) item;
            text = I18n.format(talisman.getDescriptionKey());
        }
        else
            DivineFavor.logger.error("Item not found:" + itemName);
    }

    @Override
    public String process(String key) {
        if (key.equals("text"))
            return text;
        return null;
    }
}