/**
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
package org.genxdm.processor.w3c.xs.exception.scc;

import javax.xml.namespace.QName;

@SuppressWarnings("serial")
public final class SccItemTypeMustBeAtomicOrUnionException extends SccDerivationRestrictionSimpleException
{
    public SccItemTypeMustBeAtomicOrUnionException(final QName simpleType)
    {
        super(PART_ITEM_TYPE_MUST_BE_ATOMIC_OR_UNION, simpleType);
    }

    @Override
    public String getMessage()
    {
        return "The {item type definition} of the list type must have a {variety} of atomic or union.";
    }
}
