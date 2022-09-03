package Blocks;

import java.awt.*;

public class LBlock extends Block {
    public LBlock(){
        blockRotations = new int[][][]{
                {{0, 2}, {0, 1}, {0, 0}, {1, 0}},
                {{2, 1}, {1, 1}, {0, 1}, {0, 0}},
                {{0, 1}, {1, 1}, {1, 0}, {1, -1}},
                {{1, 1}, {1, 0}, {0, 0}, {-1, 0}}
        };
        blockType = BlockType.I_Block;
        color = new Color(250,100,100);
    }
}
