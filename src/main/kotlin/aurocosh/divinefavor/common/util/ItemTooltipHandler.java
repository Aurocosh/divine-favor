package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
public class ItemTooltipHandler {
    public static final DecimalFormat COMPOST_AMOUNT_FORMAT = new DecimalFormat("#.##");

    static {
        COMPOST_AMOUNT_FORMAT.setRoundingMode(RoundingMode.CEILING);
    }

//    @SubscribeEvent
//    public static void onItemTooltip(ItemTooltipEvent event) {
//        EntityPlayer player = event.getEntityPlayer();
//        if (player == null)
//            return;
//
//        List<String> toolTip = event.getToolTip();
//        ItemStack stack = event.getItemStack();
//    }

    /**
     * Splits a tooltip into multiple lines
     *
     * @param tooltip
     * @param indent
     * @return
     */
    public static List<String> splitTooltip(String tooltip, int indent) {
        String indentStr = new String(new char[indent]).replace('\0', ' ');
        List<String> lines = new ArrayList<>();
        String[] splits = tooltip.split("\\\\n");
        for (int i = 0; i < splits.length; i++) {
            splits[i] = indentStr + splits[i];
        }
        lines.addAll(Arrays.asList(splits));
        return lines;
    }
}