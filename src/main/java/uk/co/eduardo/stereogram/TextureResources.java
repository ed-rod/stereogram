/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram;

import java.io.InputStream;

import uk.co.eduardo.stereogram.texture.Texture2D;

/**
 * Resources for textures.
 *
 * @author erodri02
 */
@SuppressWarnings( "nls" )
public enum TextureResources implements StreamSource
{
   /** Texture 1 */
   TEXTURE_1( "texture (1).jpg" ),

   /** Texture 2 */
   TEXTURE_2( "texture (2).jpg" ),

   /** Texture 3 */
   TEXTURE_3( "texture (3).jpg" ),

   /** Texture 4 */
   TEXTURE_4( "texture (4).jpg" ),

   /** Texture 5 */
   TEXTURE_5( "texture (5).jpg" ),

   /** Texture 6 */
   TEXTURE_6( "texture (6).jpg" ),

   /** Texture 7 */
   TEXTURE_7( "texture (7).jpg" ),

   /** Texture 8 */
   TEXTURE_8( "texture (8).jpg" ),

   /** Texture 9 */
   TEXTURE_9( "texture (9).jpg" ),

   /** Texture 10 */
   TEXTURE_10( "texture (10).jpg" ),

   /** Texture 11 */
   TEXTURE_11( "texture (11).jpg" ),

   /** Texture 12 */
   TEXTURE_12( "texture (12).jpg" ),

   /** Texture 13 */
   TEXTURE_13( "texture (13).jpg" );

   private final String resourceName;

   TextureResources( final String resourceName )
   {
      this.resourceName = resourceName;
   }

   /**
    * Gets the resourceName.
    *
    * @return the resourceName.
    */
   public String getResourceName()
   {
      return this.resourceName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public InputStream getStream()
   {
      return Texture2D.class.getResourceAsStream( this.resourceName );
   }
}
