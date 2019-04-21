package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.lib.math.Vector2i;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UtilGui {
    @SideOnly(Side.CLIENT)
    public static void drawPolyline(List<Vector2i> points, float red, float green, float blue, float alpha) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GlStateManager.disableDepth();
        GL11.glLineWidth(2);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glColor4f(red, green, blue, alpha);

        for (int i = 0; i < points.size(); i++) {
            Vector2i point = points.get(i);
            GlStateManager.glVertex3f((float) point.x, (float) point.y, 150);
        }

        GL11.glEnd();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    @SideOnly(Side.CLIENT)
    public static void renderBox(Tessellator tessellator, BufferBuilder bufferBuilder, BlockPos start, BlockPos end, int red, int green, int blue) {
        GlStateManager.glLineWidth(2.0F);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(start.getX(), start.getY(), start.getZ()).color(green, green, green, 0.0F).endVertex();
        bufferBuilder.pos(start.getX(), start.getY(), start.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(end.getX(), start.getY(), start.getZ()).color(green, blue, blue, red).endVertex();
        bufferBuilder.pos(end.getX(), start.getY(), end.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(start.getX(), start.getY(), end.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(start.getX(), start.getY(), start.getZ()).color(blue, blue, green, red).endVertex();
        bufferBuilder.pos(start.getX(), end.getY(), start.getZ()).color(blue, green, blue, red).endVertex();
        bufferBuilder.pos(end.getX(), end.getY(), start.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(end.getX(), end.getY(), end.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(start.getX(), end.getY(), end.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(start.getX(), end.getY(), start.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(start.getX(), end.getY(), end.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(start.getX(), start.getY(), end.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(end.getX(), start.getY(), end.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(end.getX(), end.getY(), end.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(end.getX(), end.getY(), start.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(end.getX(), start.getY(), start.getZ()).color(green, green, green, red).endVertex();
        bufferBuilder.pos(end.getX(), start.getY(), start.getZ()).color(green, green, green, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.glLineWidth(1.0F);
    }

    public static Vector2i findClosestPoint(Vector2i target, Collection<Vector2i> points, Vector2i defaultValue) {
        Vector2i result = defaultValue;
        int maxDistanceSq = Integer.MAX_VALUE;
        for (Vector2i point : points) {
            int distanceSq = target.subtract(point).magnitudeSquare();
            if (distanceSq < maxDistanceSq) {
                maxDistanceSq = distanceSq;
                result = point;
            }
        }
        return result;
    }

    public static int findClosestPointIndex(Vector2i target, List<Vector2i> points, int defaultValue) {
        int result = defaultValue;
        int maxDistanceSq = Integer.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            int distanceSq = target.subtract(points.get(i)).magnitudeSquare();
            if (distanceSq < maxDistanceSq) {
                maxDistanceSq = distanceSq;
                result = i;
            }
        }
        return result;
    }

    public static List<Vector2i> generateSpiral(int pointCount, int pointsToSkip, int scalar, double a, double aChange, double xScale, double yScale) {
        List<Vector2i> points = new ArrayList<>(pointCount);
        for (int i = 0; i < pointCount; ++i) {
            //find `ln(d*r)/a` for both points
            double lni1 = Math.log(a * scalar * (i + pointsToSkip)) / a;
            //Calculate the points and convert to rectangular coordinates, zooming in with 7x magnification
            double logSpiral = Math.exp(lni1 * a);
            double pointX = xScale * logSpiral * Math.cos(lni1);
            double pointY = yScale * logSpiral * Math.sin(lni1);

            points.add(new Vector2i((int) pointX, (int) pointY));

            a += aChange;
        }
        return points;
    }
}
