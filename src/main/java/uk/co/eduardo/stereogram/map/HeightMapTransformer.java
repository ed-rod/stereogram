/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.map;

/**
 * Transforms a height map.
 *
 * @author erodri02
 */
public interface HeightMapTransformer
{
   /**
    * Transforms an input height map.
    *
    * @param input the height map to transform.
    * @return a transformed height map.
    */
   HeightMap transform( HeightMap input );
}
