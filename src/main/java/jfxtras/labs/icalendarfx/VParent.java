package jfxtras.labs.icalendarfx;

import java.util.List;

/**
 * <p>Interface for parent calendar components.  Parent components can have children.</p>
 * 
 * <p>Adding children is not exposed by the implementation, but rather handled internally when a calendar 
 * component has a property change.</p>
 * 
 * @author David Bal
 */
public interface VParent extends VElement
{
    /** 
     * <p>Returns unmodifiable list of {@link VChild} elements.</p>
     * 
     * @return  unmodifiable list of children
     */
    List<VChild> childrenUnmodifiable();
    
    /**
     * Copy {@link VChild} elements from source into this {@link VParent}
     */
//    void copyChildrenFrom(VParent source);
    
    /**
     * Copy this {@link VParent} into destination {@link VParent}
     */
    void copyInto(VParent destination);
}
