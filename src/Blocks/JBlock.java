package Blocks;

import java.awt.*;

public class JBlock extends Block {
    public JBlock(){
        blockRotations = new int[][][]{
                {{0, 1}, {0, 0}, {0, -1}, {-1, -1}},
                {{1, -1}, {0, -1}, {-1, -1}, {-1, 0}},
                {{-1, -2}, {-1, -1}, {-1, 0}, {0, 0}},
                {{-2, 0}, {-1, 0}, {0, 0}, {0, -1}}
        };
        blockType = BlockType.J_Block;
        color =new Color(50,50,200);
    }
}
