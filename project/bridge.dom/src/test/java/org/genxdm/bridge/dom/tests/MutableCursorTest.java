package org.genxdm.bridge.dom.tests;

import org.genxdm.bridge.dom.DomProcessingContext;
import org.genxdm.bridgetest.mutable.MutableCursorBase;
import org.w3c.dom.Node;

public class MutableCursorTest
    extends MutableCursorBase<Node>
{
    @Override
    public DomProcessingContext newProcessingContext()
    {
        return new DomProcessingContext();
    }
}
