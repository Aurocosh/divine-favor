package aurocosh.divinefavor.common.lib.math;

import aurocosh.divinefavor.common.util.helper_classes.IDeepCopy;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class CubeCoordinates implements IDeepCopy<CubeCoordinates> {
    public final Vector3i lowerCorner;
    public final Vector3i upperCorner;

    public CubeCoordinates() {
        lowerCorner = Vector3i.ZERO;
        upperCorner = Vector3i.ZERO;
    }

    public CubeCoordinates(BlockPos firstCorner, BlockPos secondCorner) {
        this(Vector3i.convert(firstCorner),Vector3i.convert(secondCorner));
    }

    public CubeCoordinates(Vector3i firestCorner, Vector3i secondCorner) {
        int lowerX = Math.min(firestCorner.x,secondCorner.x);
        int lowerY = Math.min(firestCorner.y,secondCorner.y);
        int lowerZ = Math.min(firestCorner.z,secondCorner.z);

        int upperX = Math.max(firestCorner.x,secondCorner.x);
        int upperY = Math.max(firestCorner.y,secondCorner.y);
        int upperZ = Math.max(firestCorner.z,secondCorner.z);

        this.lowerCorner = new Vector3i(lowerX,lowerY,lowerZ);
        this.upperCorner = new Vector3i(upperX,upperY,upperZ);
    }

    public CubeCoordinates add(Vector3i vector){
        Vector3i lower = lowerCorner.add(vector);
        Vector3i upper = upperCorner.add(vector);
        return new CubeCoordinates(lower,upper);
    }

    public CubeCoordinates subtract(Vector3i vector){
        Vector3i lower = lowerCorner.subtract(vector);
        Vector3i upper = upperCorner.subtract(vector);
        return new CubeCoordinates(lower,upper);
    }

    public Vector3i getSizeVector(){
         return new Vector3i(getSizeX(),getSizeY(),getSizeZ());
    }

    public int getSizeX(){
        return upperCorner.x - lowerCorner.x + 1;
    }

    public int getSizeY(){
        return upperCorner.y - lowerCorner.y + 1;
    }

    public int getSizeZ(){
        return upperCorner.z - lowerCorner.z + 1;
    }

    public Vector3i[] getAllPositionsInside(){
        int xSize = getSizeX();
        int ySize = getSizeY();
        int zSize = getSizeZ();

        int i = 0;
        int positionCount = xSize * ySize * zSize;
        Vector3i[] positions = new Vector3i[positionCount];
        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                for (int z = 0; z < zSize; z++){
                    Vector3i shift = new Vector3i(x,y,z);
                    positions[i++] = lowerCorner.add(shift);
                }
            }
        }
        return positions;
    }

    @Override
    public CubeCoordinates deepCopy() {
        return new CubeCoordinates(lowerCorner,upperCorner);
    }

    public boolean isCoordinateInside(Vector3i coordinate){
        return lowerCorner.x <= coordinate.x && coordinate.x <= upperCorner.x
                && lowerCorner.y <= coordinate.y && coordinate.y <= upperCorner.y
                && lowerCorner.z <= coordinate.z && coordinate.z <= upperCorner.z;
    }

    public static CubeCoordinates getBoundingBox(List<Vector3i> positions){
        if(positions.size() == 0)
            return new CubeCoordinates();

        Vector3i min = Vector3i.MAX;
        Vector3i max = Vector3i.MIN;

        for (Vector3i vector3i : positions) {
            min = min.getMinCoordinates(vector3i);
            max = max.getMaxCoordinates(vector3i);
        }
        return new CubeCoordinates(min,max);
    }


    public CubeCoordinates expandBoundingBox(List<Vector3i> positions){
        if(positions.size() == 0)
            return this;

        Vector3i min = lowerCorner;
        Vector3i max = upperCorner;

        for (Vector3i vector3i : positions) {
            min = min.getMinCoordinates(vector3i);
            max = max.getMaxCoordinates(vector3i);
        }
        return new CubeCoordinates(min,max);
    }
}
