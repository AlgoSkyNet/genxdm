/**
 * Copyright (c) 2010 TIBCO Software Inc.
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
package org.gxml.bridge.cx.tree;

import java.util.List;

import org.gxml.bridgekit.atoms.XmlAtom;
import org.gxml.NodeKind;
import org.gxml.xs.types.SmType;

public final class XmlTextNode
    extends XmlLeafNode
{

    XmlTextNode(final XmlRootNode document, List<? extends XmlAtom> data)
    {
        super(NodeKind.TEXT, document, null, data);
    }
    
    XmlTextNode(final XmlRootNode document, String value)
    {
        super(NodeKind.TEXT, document, value);
    }
    
    public SmType<XmlAtom> getType()
    {
        if ( (parent != null) && parent.isElement() )
            return ((XmlElementNode)parent).getType();
        return null;
    }

    public boolean isId()
    {
        return parent.isId();
    }

    public boolean isIdRefs()
    {
        return parent.isIdRefs();
    }

    public boolean isText()
    {
        return true;
    }
}
