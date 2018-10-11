/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram;

import java.io.InputStream;

/**
 * Source for an input stream.
 *
 * @author erodri02
 */
public interface StreamSource
{
   /**
    * Gets an input stream.
    *
    * @return an input stream.
    */
   InputStream getStream();
}
