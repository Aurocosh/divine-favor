package aurocosh.divinefavor.common.patchouli.processors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.EnumSet;

public class ArrowTalismanStatProcessor implements IComponentProcessor {
    ItemArrowTalisman arrowTalisman;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String talismanName = variables.get("talisman");
        Item item = Item.REGISTRY.getObject(new ResourceLocation(talismanName));
        if (item instanceof ItemArrowTalisman) {
            arrowTalisman = (ItemArrowTalisman) item;


        }
        else
            DivineFavor.logger.error("Arrow talisman not found:" + talismanName);
    }

    @Override
    public String process(String key) {
        if (key.startsWith("talisman")) {
            ItemStack stack = new ItemStack(arrowTalisman, 1);
            return ItemStackUtil.serializeStack(stack);
        }
        else if (key.equals("favor_icon")) {
            ModFavor favor = arrowTalisman.getFavor();
            return favor.getIcon().toString();
        }
        else if (key.equals("favor")) {
            ModFavor favor = arrowTalisman.getFavor();
            return "Favor: " + favor.getName();
        }
        else if (key.equals("cost")) {
            int favorCost = arrowTalisman.getFavorCost();
            if (favorCost == 0)
                return "No cost";
            else
                return "Favor cost: " + favorCost;
        }
        else if (key.equals("damage")) {
            double arrowDamage = arrowTalisman.getArrowDamage();
            if (arrowDamage == 0)
                return "Arrow deals no damage";
            else {
                NumberFormat formatter = new DecimalFormat("#0.00");
                return "Arrow damage: " + formatter.format(arrowDamage);
            }
        }
        else if (key.equals("options")) {
            EnumSet<ArrowOptions> options = arrowTalisman.getOptions();
            if (options.equals(ArrowOptions.NORMAL))
                return "No extra properties";
            String result = "Extra properties: ";
            if (options.contains(ArrowOptions.BreakOnHit))
                result += "Will break on hit. ";
            if (options.contains(ArrowOptions.RequiresTarget))
                result += "Needs target.";
            return result;
        }
        return null;
    }

}