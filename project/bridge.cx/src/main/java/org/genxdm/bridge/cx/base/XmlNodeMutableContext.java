/*
 * Copyright (c) 2010-2011 TIBCO Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.genxdm.bridge.cx.base;

import org.genxdm.bridge.cx.tree.XmlNode;
import org.genxdm.bridge.cx.tree.XmlNodeFactory;
import org.genxdm.bridge.cx.tree.XmlNodeMutator;
import org.genxdm.bridgekit.tree.MutableCursorOnMutableModel;
import org.genxdm.exceptions.PreCondition;
import org.genxdm.mutable.MutableContext;
import org.genxdm.mutable.MutableCursor;
import org.genxdm.mutable.MutableModel;
import org.genxdm.mutable.NodeFactory;

public class XmlNodeMutableContext
    implements MutableContext<XmlNode>
{
    
    XmlNodeMutableContext(XmlNodeContext context)
    {
        this.context = PreCondition.assertNotNull(context, "context");
    }

    public MutableModel<XmlNode> getModel()
    {
        return model;
    }

    public NodeFactory<XmlNode> getNodeFactory()
    {
        return factory;
    }

    public XmlNodeContext getProcessingContext()
    {
        return context;
    }

    public MutableCursor<XmlNode> newCursor(XmlNode node)
    {
        return new MutableCursorOnMutableModel<XmlNode>(node, model);
    }

    private final XmlNodeFactory factory = new XmlNodeFactory();
    private final XmlNodeContext context;
    private final XmlNodeMutator model = new XmlNodeMutator();
}
