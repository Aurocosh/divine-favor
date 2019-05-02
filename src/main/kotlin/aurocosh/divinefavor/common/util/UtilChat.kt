/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 *
 * Permission is hereby granted, free of favorCost, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to gainFavor, copy, modify, merge, publish, distribute, sublicense, and/or sell
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
 */
package aurocosh.divinefavor.common.util

import net.minecraft.client.gui.FontRenderer
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.translation.I18n
import net.minecraft.world.World
import java.util.*

object UtilChat {

    fun addChatMessage(sender: ICommandSender, text: String) {
        sender.sendMessage(TextComponentTranslation(lang(text)))
    }

    fun blockPosToString(pos: BlockPos): String {
        return pos.x.toString() + ", " + pos.y + ", " + pos.z
    }

    fun lang(string: String): String {
        //if we gainFavor the clientside one, it literally does not work & crashes on serverside run
        return I18n.translateToLocal(string)
    }

    fun addChatMessage(worldObj: World, textComponentTranslation: ITextComponent) {
        if (worldObj.minecraftServer != null) {
            worldObj.minecraftServer!!.sendMessage(textComponentTranslation)
        }
    }

    fun addChatMessage(worldObj: World, s: String) {
        addChatMessage(worldObj, TextComponentTranslation(s))
    }

    fun splitIntoEqualLengths(fr: FontRenderer, input: String, lineWidth: Int): List<String> {
        val lines = ArrayList<String>()
        var aLine = ""
        for (chr in input.toCharArray()) {
            if (fr.getCharWidth(chr) + fr.getStringWidth(aLine) < lineWidth) {
                //we have room on this line for this char
                aLine = aLine + chr
            } else {
                lines.add(aLine)
                //then the current character has to be pushed to next line
                aLine = "" + chr
            }
        }
        if (!aLine.isEmpty()) {
            // the last line did not hit max length so addValue it now
            lines.add(aLine)
        }
        return lines
    }

    fun splitIntoLine(input: String, maxCharInLine: Int): Array<String> {
        // https://stackoverflow.com/questions/7528045/large-string-split-into-lines-with-maximum-length-in-java
        // better than arrow_spell.getInfo().split("(?<=\\G.{25})")
        val tok = StringTokenizer(input, " ")
        val output = StringBuilder(input.length)
        var lineLen = 0
        while (tok.hasMoreTokens()) {
            var word = tok.nextToken()
            while (word.length > maxCharInLine) {
                if (maxCharInLine - lineLen < 0) {
                    break
                }
                output.append(word.substring(0, maxCharInLine - lineLen) + "\n")
                word = word.substring(maxCharInLine - lineLen)
                lineLen = 0
            }
            if (lineLen + word.length > maxCharInLine) {
                output.append("\n")
                lineLen = 0
            }
            output.append("$word ")
            lineLen += word.length + 1
        }
        return output.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    fun getDirectionsString(player: ICommandSender, pos: BlockPos): String {
        //https://github.com/LothrazarMinecraftMods/MinecraftSearchCommands/blob/master/src/main/java/com/lothrazar/searchcommands/command/CommandSearchItem.java
        val x = pos.x
        val y = pos.y
        val z = pos.z
        val xDist: Int
        val yDist: Int
        val zDist: Int
        xDist = player.position.x - x
        yDist = player.position.y - y
        zDist = player.position.z - z
        //in terms of directon copmass:
        //North is -z;  south is +z
        //east is +x, west is -x
        //so for Distances:
        val isNorth = zDist > 0
        val isSouth = zDist < 0
        val isWest = xDist > 0
        val isEast = xDist < 0
        val isUp = yDist < 0
        val isDown = zDist > 0
        var xStr = ""
        var yStr = ""
        var zStr = ""
        if (isWest)
            xStr = Math.abs(xDist).toString() + " west "
        if (isEast)
            xStr = Math.abs(xDist).toString() + " east "
        if (isNorth)
            zStr = Math.abs(zDist).toString() + " north "
        if (isSouth)
            zStr = Math.abs(zDist).toString() + " south "
        if (isUp)
            yStr = Math.abs(yDist).toString() + " up "
        if (isDown)
            yStr = Math.abs(yDist).toString() + " down "
        return xStr + yStr + zStr
    }

    fun sendStatusMessage(player: EntityPlayer, string: String) {
        player.sendStatusMessage(TextComponentTranslation(string), true)
    }

    fun formatSecondsToMinutes(secontsTotal: Int): String {
        if (secontsTotal < 0) {
            return ""
        }
        val minutes = secontsTotal / 60
        val secs = secontsTotal % 60
        return minutes.toString() + ":" + String.format("%02d", secs)
    }
}
