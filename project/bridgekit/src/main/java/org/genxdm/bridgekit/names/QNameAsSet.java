/*
 * Copyright (c) 2009-2010 TIBCO Software Inc.
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
package org.genxdm.bridgekit.names;

import javax.xml.namespace.QName;

public class QNameAsSet
{
    public static final String ESCAPE = "\u001B";
    
    private QNameAsSet() {}
    
    private static boolean subset(final String lhs, final String rhs)
    {
        if (rhs != null)
        {
            if (lhs != null)
            {
                return lhs.equals(rhs);
            }
            return false;
        }
        return true;
    }

    /**
     * lhs <: rhs
     */
    public static boolean subset(final QName lhs, final QName rhs)
    {
        if (lhs != null)
        {
            if (rhs != null)
            {
                // first, call the necessary methods just once, replacing ESCAPE with null
                final String lhns = escapeToNull(lhs.getNamespaceURI());
                final String rhns = escapeToNull(rhs.getNamespaceURI());
                final String lhn = escapeToNull(lhs.getLocalPart());
                final String rhn = escapeToNull(rhs.getLocalPart());
                // then the return value is simple
                return subset(lhns, rhns) && subset(lhn, rhn);
            }
            return true;
        }
        if (rhs != null)
        {
            return subset(null, rhs.getNamespaceURI()) 
                   && subset(null, rhs.getLocalPart());
        }
        return true;
    }

    static private String escapeToNull(String input)
    {
        // if given the single character \u001B, return null, otherwise return input.
        return (input.equals(ESCAPE) ? null : input);
    }
}
