/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Abstract implemenetation that has listener support.
 *
 * @author erodri02
 * @param <T> the type of output the element generates.
 */
public abstract class AbstractPipelineElement< T > implements PipelineElement< T >
{
   private boolean dirty = true;

   private final List< PipelineElementListener > listeners = new CopyOnWriteArrayList<>();

   private final String name;

   /**
    * Initializes a new AbstractPipelineElement object.
    *
    */
   public AbstractPipelineElement()
   {
      // Create a name;
      this.name = getClass().getSimpleName();
   }

   /**
    * Basic listener that will mark this element as dirty when one of the inputs becomes invalid
    */
   protected final PipelineElementListener defaultListener = new PipelineElementListener()
   {
      @Override
      public void inputInvalid()
      {
         setDirty( true );
      }
   };

   /**
    * Checks to see if his pipeline element has been marked as dirty.
    *
    * @return whether or not this element is dirty.
    */
   protected boolean isDirty()
   {
      return this.dirty;
   }

   /**
    * Mark this element as either dirty (<code>true</code>) or not (<code>false</code>).
    *
    * @param isDirty whether the element is dirty.
    */
   protected void setDirty( final boolean isDirty )
   {
      this.dirty = isDirty;
      if( isDirty )
      {
         notifyListeners();
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void addPipelineElementListener( final PipelineElementListener listener )
   {
      if( ( listener != null ) && !this.listeners.contains( listener ) )
      {
         this.listeners.add( listener );
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void removePipelineElementListener( final PipelineElementListener listener )
   {
      this.listeners.remove( listener );
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getName()
   {
      return this.name;
   }

   /**
    * Notifies all registered listeners that this element has been marked as invalid.
    */
   protected void notifyListeners()
   {
      for( final PipelineElementListener listener : this.listeners )
      {
         listener.inputInvalid();
      }
   }
}
