package aurocosh.divinefavor.common.integrations.patchouli.processors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.common.util.ItemStackUtil;

public class SpotlightIntegrationProcessor implements IComponentProcessor {
    ModItem item;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String itemName = variables.get("item");
        item = ModRegistries.items.getValue(new ResourceLocation(itemName));
        if (item == null) {
            DivineFavor.logger.error("Item not found:" + itemName);
            return;
        }
    }

    @Override
    public String process(String key) {
        if (key.startsWith("item") && item != null) {
            ItemStack stack = new ItemStack(item);
            return ItemStackUtil.serializeStack(stack);
        }
        else if (key.equals("name"))
            return I18n.format(item.getNameKey());
        else if (key.equals("text"))
            return I18n.format(item.getDescriptionKey());
        return null;
    }

}