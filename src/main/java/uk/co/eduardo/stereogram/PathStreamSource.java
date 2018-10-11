/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gets an input stream from a path.
 *
 * @author erodri02
 */
public class PathStreamSource implements StreamSource
{
   private static final Logger LOGGER = Logger.getLogger( PathStreamSource.class.getName() );

   private final Path path;

   /**
    * Initializes a new PathStreamSource object.
    *
    * @param path the path to the file.
    */
   public PathStreamSource( final Path path )
   {
      this.path = path;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public InputStream getStream()
   {
      try
      {
         return Files.newInputStream( this.path, StandardOpenOption.READ );
      }
      catch( final IOException exception )
      {
         LOGGER.log( Level.SEVERE, exception.getMessage(), exception );
      }
      return null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals( final Object obj )
   {
      if( obj instanceof PathStreamSource )
      {
         final PathStreamSource other = (PathStreamSource) obj;
         return Objects.equals( this.path, other.path );
      }
      return false;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return Objects.hash( this.path );
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return this.path.getFileName().toString();
   }
}
