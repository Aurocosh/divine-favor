/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 *
 * Permission is hereby granted, free of favorCost, to any person obtaining a copy
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
package aurocosh.divinefavor.common.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@SuppressWarnings("deprecation")
public class UtilChat {

    public static void addChatMessage(EntityPlayer player, String text) {
        player.sendMessage(new TextComponentTranslation(lang(text)));
    }

    public static void addChatMessage(ICommandSender sender, String text) {
        sender.sendMessage(new TextComponentTranslation(lang(text)));
    }

    public static String blockPosToString(BlockPos pos) {
        return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    }

    public static String lang(String string) {
        //if we use the clientside one, it literally does not work & crashes on serverside run
        return I18n.translateToLocal(string);
    }

    public static void addChatMessage(World worldObj, ITextComponent textComponentTranslation) {
        if (worldObj.getMinecraftServer() != null) {
            worldObj.getMinecraftServer().sendMessage(textComponentTranslation);
        }
    }

    public static void addChatMessage(World worldObj, String s) {
        addChatMessage(worldObj, new TextComponentTranslation(s));
    }

    public static List<String> splitIntoEqualLengths(FontRenderer fr, String input, int lineWidth) {
        List<String> lines = new ArrayList<String>();
        String aLine = "";
        for (char chr : input.toCharArray()) {
            if (fr.getCharWidth(chr) + fr.getStringWidth(aLine) < lineWidth) {
                //we have room on this line for this char
                aLine = aLine + chr;
            }
            else {
                lines.add(aLine);
                //then the current character has to be pushed to next line
                aLine = "" + chr;
            }
        }
        if (!aLine.isEmpty()) {
            // the last line did not hit max length so add it now
            lines.add(aLine);
        }
        return lines;
    }

    public static String[] splitIntoLine(String input, int maxCharInLine) {
        // https://stackoverflow.com/questions/7528045/large-string-split-into-lines-with-maximum-length-in-java
        // better than spell.getInfo().split("(?<=\\G.{25})")
        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();
            while (word.length() > maxCharInLine) {
                if (maxCharInLine - lineLen < 0) {
                    break;
                }
                output.append(word.substring(0, maxCharInLine - lineLen) + "\n");
                word = word.substring(maxCharInLine - lineLen);
                lineLen = 0;
            }
            if (lineLen + word.length() > maxCharInLine) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word + " ");
            lineLen += word.length() + 1;
        }
        return output.toString().split("\n");
    }

    public static String getDirectionsString(ICommandSender player, BlockPos pos) {
        //https://github.com/LothrazarMinecraftMods/MinecraftSearchCommands/blob/master/src/main/java/com/lothrazar/searchcommands/command/CommandSearchItem.java
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int xDist, yDist, zDist;
        xDist = player.getPosition().getX() - x;
        yDist = player.getPosition().getY() - y;
        zDist = player.getPosition().getZ() - z;
        //in terms of directon copmass:
        //North is -z;  south is +z
        //east is +x, west is -x
        //so for Distances:
        boolean isNorth = (zDist > 0);
        boolean isSouth = (zDist < 0);
        boolean isWest = (xDist > 0);
        boolean isEast = (xDist < 0);
        boolean isUp = (yDist < 0);
        boolean isDown = (zDist > 0);
        String xStr = "";
        String yStr = "";
        String zStr = "";
        if (isWest)
            xStr = Math.abs(xDist) + " west ";
        if (isEast)
            xStr = Math.abs(xDist) + " east ";
        if (isNorth)
            zStr = Math.abs(zDist) + " north ";
        if (isSouth)
            zStr = Math.abs(zDist) + " south ";
        if (isUp)
            yStr = Math.abs(yDist) + " up ";
        if (isDown)
            yStr = Math.abs(yDist) + " down ";
        return xStr + yStr + zStr;
    }

    public static void sendStatusMessage(EntityPlayer player, String string) {
        player.sendStatusMessage(new TextComponentTranslation(string), true);
    }

    public static String formatSecondsToMinutes(int secontsTotal) {
        if (secontsTotal < 0) {
            return "";
        }
        int minutes = secontsTotal / 60;
        int secs = secontsTotal % 60;
        return minutes + ":" + String.format("%02d", secs);
    }
}
