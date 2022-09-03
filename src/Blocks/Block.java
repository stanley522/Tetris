package Blocks;

import java.awt.*;

public class Block {

    public int[][][] blockRotations;
    public BlockType blockType;
    public int[] position;
    public Color color;

    public int[][] getInitialRotation() {
        return blockRotations[0];
    }

    ;
    public int currentRotation = 0;

    public int[][] occupiedBlocks() {
        var occupiedBlocks = new int[4][];
        int row = position[1];
        int column = position[0];
        for (int i = 0; i < 4; i++) {
            occupiedBlocks[i] = new int[]{column + blockRotations[currentRotation][i][0], row + blockRotations[currentRotation][i][1]};
        }
        return occupiedBlocks;
    }

    public int[][] occupiedBlocksAfterDrop() {
        var beforeDrop = occupiedBlocks();
        for (int[] block : beforeDrop) {
            block[1] -= 1;
        }
        return beforeDrop;
    }

    public int[][] occupiedBlocksAfterLeft() {
        var beforeDrop = occupiedBlocks();
        for (int[] block : beforeDrop) {
            block[0] -= 1;
        }
        return beforeDrop;
    }
    public int[][] occupiedBlocksAfterRight() {
        var beforeDrop = occupiedBlocks();
        for (int[] block : beforeDrop) {
            block[0] += 1;
        }
        return beforeDrop;
    }

    public int[][] occupiedBlocksAfterRotate() {

        var occupiedBlocks = new int[4][];
        int row = position[1];
        int column = position[0];
        for (int i = 0; i < 4; i++) {
            occupiedBlocks[i] = new int[]{
                    column + blockRotations[(currentRotation + 1) % blockRotations.length][i][0],
                    row + blockRotations[(currentRotation + 1) % blockRotations.length][i][1]};
        }
        return occupiedBlocks;
    }

    public void rotate() {
        currentRotation = (currentRotation+1)%blockRotations.length;
    }
}
