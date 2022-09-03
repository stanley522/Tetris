package Blocks;

import java.awt.*;

public class OBlock extends Block {
    public OBlock(){
        blockRotations = new int[][][]{
                {{0, 0}, {0, 1}, {1, 0}, {1, 1}}
        };
        blockType = BlockType.O_Block;
        color = new Color(200,180,80);
    }
}
