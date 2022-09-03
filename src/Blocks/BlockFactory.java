package Blocks;

public class BlockFactory {
public static Block newBlock (BlockType blockType){
    switch (blockType){
        case I_Block ->{ return new IBlock();}
        case S_Block ->{ return new SBlock();}
        case Z_Block ->{ return new ZBlock();}
        case L_Block ->{ return new LBlock();}
        case J_Block ->{ return new JBlock();}
        case O_Block ->{ return new OBlock();}
        case T_Block ->{ return new TBlock();}
        default -> throw new RuntimeException("Error block type");
    }


}}
