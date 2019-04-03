package aurocosh.divinefavor.common.patchouli.processors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;

public class TalismanDescriptionProcessor implements IComponentProcessor {
    String text;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String talismanName = variables.get("talisman");
        Item item = Item.REGISTRY.getObject(new ResourceLocation(talismanName));
        if (item instanceof ItemTalisman) {
            ItemTalisman talisman = (ItemTalisman) item;
            text = I18n.format(talisman.getDescriptionKey());
        }
        else
            DivineFavor.logger.error("Talisman not found:" + talismanName);
    }

    @Override
    public String process(String key) {
        if (key.equals("text"))
            return text;
        return null;
    }

}