/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import java.awt.image.BufferedImage;

import uk.co.eduardo.stereogram.MapResources;
import uk.co.eduardo.stereogram.StreamSource;
import uk.co.eduardo.stereogram.TextureResources;
import uk.co.eduardo.stereogram.map.MaximumDepthHeightMapTransformer;
import uk.co.eduardo.stereogram.texture.TextureOffsetFactory;
import uk.co.eduardo.stereogram.texture.ZeroTextureOffsetFactory;

/**
 * Defines a pipeline for creating stereograms.
 *
 * @author erodri02
 */
public class StereogramPipeline
{
   private final RootPipelineElement< StreamSource > heightMapSource = new RootPipelineElement<>( MapResources.SHARK );

   private final RootPipelineElement< Integer > maxDepth = new RootPipelineElement<>( MaximumDepthHeightMapTransformer.DEFAULT_MAXIMUM_DEPTH );

   private final RootPipelineElement< StreamSource > textureSource = new RootPipelineElement<>( TextureResources.TEXTURE_1 );

   private final RootPipelineElement< Integer > textureSize = new RootPipelineElement<>( 128 );

   private final RootPipelineElement< TextureOffsetFactory > textureOffset = new RootPipelineElement<>( new ZeroTextureOffsetFactory() );

   private final RootPipelineElement< Boolean > invert = new RootPipelineElement<>( false );

   private final HeightMapPipelineElement heightMap = new HeightMapPipelineElement();

   private final RescaledHeightMapPipelineElement rescaleHeightMap = new RescaledHeightMapPipelineElement();

   private final ImageTexturePipelineElement imageTexture = new ImageTexturePipelineElement();

   private final ArrayTexturePipelineElement arrayTexture = new ArrayTexturePipelineElement();

   private final Texture2DPipelineElement texture2D = new Texture2DPipelineElement();

   private final BufferedImagePipelineElement image = new BufferedImagePipelineElement();

   private final OutputPipelineElement output = new OutputPipelineElement();

   /**
    * Initializes a new StereogramPipeline object.
    */
   public StereogramPipeline()
   {
      this.heightMap.connectFirstInput( this.heightMapSource );
      this.rescaleHeightMap.connectFirstInput( this.heightMap );
      this.rescaleHeightMap.connectSecondInput( this.maxDepth );
      this.image.connectFirstInput( this.heightMap );

      this.imageTexture.connectFirstInput( this.textureSource );
      this.imageTexture.connectSecondInput( this.textureSize );
      this.arrayTexture.connectFirstInput( this.imageTexture );
      this.texture2D.connectFirstInput( this.arrayTexture );
      this.texture2D.connectSecondInput( this.rescaleHeightMap );
      this.texture2D.connectThirdInput( this.textureOffset );
      this.texture2D.connectFourthInput( this.invert );

      this.output.connectFirstInput( this.rescaleHeightMap );
      this.output.connectSecondInput( this.texture2D );
      this.output.connectThirdInput( this.image );
   }

   /**
    * Sets the resource for the height map.
    *
    * @param source the source of the height map.
    */
   public void setHeightMapSource( final StreamSource source )
   {
      this.heightMapSource.setValue( source );
   }

   /**
    * Gets the resource for the height map.
    *
    * @return the source of the height map.
    */
   public StreamSource getHeightMapSource()
   {
      return this.heightMapSource.getOutput();
   }

   /**
    * Sets the maximum apparent depth for the image.
    *
    * @param maxDepth the maximum apparent depth.
    */
   public void setMaximumDepth( final int maxDepth )
   {
      this.maxDepth.setValue( maxDepth );
   }

   /**
    * Gets the maximum apparent depth for the image.
    *
    * @return the maximum apparent depth.
    */
   public int getMaximumDepth()
   {
      return this.maxDepth.getOutput();
   }

   /**
    * Sets the resource of the texture.
    *
    * @param source the source of the texture.
    */
   public void setTextureSouce( final StreamSource source )
   {
      this.textureSource.setValue( source );
   }

   /**
    * Gets the resource of the texture.
    *
    * @return the source of the texture.
    */
   public StreamSource getTextureSource()
   {
      return this.textureSource.getOutput();
   }

   /**
    * Sets the size (width) of the texture in pixels.
    *
    * @param size the size (width) of the texture in pixels.
    */
   public void setTextureSize( final int size )
   {
      this.textureSize.setValue( size );
   }

   /**
    * Gets the size (width) of the texture in pixels.
    *
    * @return the size (width) of the texture in pixels.
    */
   public int getTextureSize()
   {
      return this.textureSize.getOutput();
   }

   /**
    * Sets the texture offset for texture correction.
    *
    * @param offsetFactory the texture offset factory.
    */
   public void setTextureOffsetFactory( final TextureOffsetFactory offsetFactory )
   {
      this.textureOffset.setValue( offsetFactory );
   }

   /**
    * Gets the texture offset for texture correction.
    *
    * @return the texture offset factory.
    */
   public TextureOffsetFactory getTextureOffsetFactory()
   {
      return this.textureOffset.getOutput();
   }

   /**
    * Sets whether to invert the depth.
    *
    * @param invert whether to invert the depth.
    */
   public void setInvertDepth( final boolean invert )
   {
      this.invert.setValue( invert );
   }

   /**
    * Gets whether the depth is inverted.
    *
    * @return whether the depth is inverted.
    */
   public boolean getInvertDepth()
   {
      return this.invert.getOutput();
   }

   /**
    * Gets the output buffered image containing the stereogram.
    *
    * @return the output computed stereogram.
    */
   public BufferedImage getOutput()
   {
      return this.output.getOutput();
   }
}
