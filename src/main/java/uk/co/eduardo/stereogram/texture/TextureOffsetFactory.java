/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.texture;

import uk.co.eduardo.stereogram.map.HeightMap;

/**
 * Creates texture offsets to overcome the progressive warping that occurrs across a stereogram image.
 *
 * @author erodri02
 */
public interface TextureOffsetFactory
{
   /**
    * In a standard stereogram, the textures are initially generated so the left-hand-side looks normal. They are then warped for
    * the height map so that the texture becomes more and more warped towards the right hand side of the image. To combat this, it
    * is possible to set initial offsets (an integer value for each row). This warps the texture on the left hand side such that it
    * should look normal again around the middle of the image.
    * <p>
    * This method returns an array of offsets (one ofr each row) for the given height map. Ie. the length of the returned array is
    * the same as {@link HeightMap#getHeight()}
    *
    * @param map the height map for which the texture offsets are to be calculated.
    * @param texture the texture to apply to the height map.
    * @return an array of texture offsets.
    */
   int[] getOffsets( final HeightMap map, Texture2D texture );
}
