package org.genxdm.bridge.cx.tests;

import org.genxdm.bridge.cx.base.XmlNodeContext;
import org.genxdm.bridge.cx.tree.XmlNode;
import org.genxdm.processor.io.tests.RoundTripBase;

public class RoundTripTest
    extends RoundTripBase<XmlNode>
{

    @Override
    public XmlNodeContext newProcessingContext()
    {
        return new XmlNodeContext();
    }

}