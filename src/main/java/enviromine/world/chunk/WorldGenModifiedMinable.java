package enviromine.world.chunk;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenModifiedMinable extends WorldGenerator
{
    private Block minableBlock;
    /** The number of blocks to generate. */
    private int numberOfBlocks;
    private Block targetBlock;
    private int targetMeta;
    private int mineableBlockMeta;

    public WorldGenModifiedMinable(Block block, int number)
    {
        this(block, number, Blocks.stone);
    }

    public WorldGenModifiedMinable(Block block, int number, Block target)
    {
        this(block, 0, number, target, 0);
    }

    public WorldGenModifiedMinable(Block block, int meta, int number, Block target)
    {
        this(block, meta, number, target, 0);
    }
    
    public WorldGenModifiedMinable(Block block, int meta, int number, Block target, int targetMeta)
    {
    	this.minableBlock = block;
    	this.mineableBlockMeta = meta;
    	this.numberOfBlocks = number;
    	this.targetBlock = target;
    	this.targetMeta = targetMeta;
    }

    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        //float f = rand.nextFloat() * (float)Math.PI;
        float mh_s_ = MathHelper.sin(rand.nextFloat() * (float)Math.PI);
        float mh_c_ = (float)Math.sqrt(1 - mh_s_*mh_s_);
        float d0 = ((float)(x + 8) + mh_s_ * (float)this.numberOfBlocks / 8.0F);
        float d1 = ((float)(x + 8) - mh_s_ * (float)this.numberOfBlocks / 8.0F) - d0;
        float d2 = ((float)(z + 8) + mh_c_ * (float)this.numberOfBlocks / 8.0F);
        float d3 = ((float)(z + 8) - mh_c_ * (float)this.numberOfBlocks / 8.0F) - d2;
        float d4 = (y + rand.nextInt(3) - 2);
        float d5 = (y + rand.nextInt(3) - 2) - d4;

        for (int l = 0; l <= this.numberOfBlocks; ++l)
        {
            float d6 = d0 + d1 * l / (float)this.numberOfBlocks;
            float d7 = d4 + d5 * l / (float)this.numberOfBlocks;
            float d8 = d2 + d3 * l / (float)this.numberOfBlocks;
            //float d9 = rand.nextFloat() * (float)this.numberOfBlocks / 16.0F;
            float d10 = ((MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * (rand.nextFloat() * (float)this.numberOfBlocks / 16.0F) + 1.0F) / 2.0F;
            //float d11 = (MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_float(d6 - d10);
            int j1 = MathHelper.floor_float(d7 - d10);
            int k1 = MathHelper.floor_float(d8 - d10);
            int l1 = MathHelper.floor_float(d6 + d10);
            int i2 = MathHelper.floor_float(d7 + d10);
            int j2 = MathHelper.floor_float(d8 + d10);
            float d6_05 = 0.5F - d6;
            float d7_05 = 0.5F - d7;
            float d8_05 = 0.5F - d8;

            for (int k2 = i1; k2 <= l1; ++k2)
            {
                float d12 = ((float)k2 + d6_05) / (d10);d12*=d12;

                if (d12 < 1.0F)
                {
                    for (int l2 = j1; l2 <= i2; ++l2)
                    {
                        float d13 = ((float)l2 + d7_05) / (d10);d13=d13 * d13 + d12;

                        if (d13 < 1.0F)
                        {
                            for (int i3 = k1; i3 <= j2; ++i3)
                            {
                                float d14 = ((float)i3 + d8_05) / (d10);

                                if (d13 + d14 * d14 < 1.0F && world.getBlock(k2, l2, i3).equals(targetBlock) && (targetMeta <= -1 || world.getBlockMetadata(k2, l2, i3) == targetMeta))
                                {
                                    world.setBlock(k2, l2, i3, this.minableBlock, mineableBlockMeta, 2);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}