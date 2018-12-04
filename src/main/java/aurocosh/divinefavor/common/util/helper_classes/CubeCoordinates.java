package aurocosh.divinefavor.common.util.helper_classes;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.util.math.BlockPos;

public class CubeCoordinates {
    private Vector3i lowerCorner;
    private Vector3i upperCorner;

    public CubeCoordinates(BlockPos firstCorner, BlockPos secondCorner) {
        this(Vector3i.fromBlockPos(firstCorner),Vector3i.fromBlockPos(secondCorner));
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

    public CubeCoordinates(BlockPos firstCorner, BlockPos secondCorner, BlockPos center) {
        this(Vector3i.fromBlockPos(firstCorner),Vector3i.fromBlockPos(secondCorner));
        setCenter(Vector3i.fromBlockPos(center));
    }

    public void setCenter(Vector3i center){
        lowerCorner = lowerCorner.subtract(center);
        upperCorner = upperCorner.subtract(center);
    }

    public Vector3i getLowerCorner() {
        return lowerCorner;
    }

    public Vector3i getUpperCorner() {
        return upperCorner;
    }

    public Vector3i getSizeVector(){
         return new Vector3i(getSizeX(),getSizeY(),getSizeZ());
    }

    public int getSizeX(){
        return upperCorner.x - lowerCorner.x + 1;
    }

    public int getSizeY(){
        return upperCorner.x - lowerCorner.x + 1;
    }

    public int getSizeZ(){
        return upperCorner.x - lowerCorner.x + 1;
    }

    public BlockPos[] getAllPositionsInside(){
        int xSize = getSizeX();
        int ySize = getSizeY();
        int zSize = getSizeZ();

        int i = 0;
        int positionCount = xSize * ySize * zSize;
        BlockPos[] positions = new BlockPos[positionCount];
        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                for (int z = 0; z < zSize; z++){
                    Vector3i shift = new Vector3i(x,y,z);
                    positions[i++] = Vector3i.toBlockPos(lowerCorner.add(shift));
                }
            }
        }
        return positions;
    }

    public void moveToCenter(){
        upperCorner = upperCorner.subtract(lowerCorner);
        lowerCorner = Vector3i.ZERO;
    }
}
