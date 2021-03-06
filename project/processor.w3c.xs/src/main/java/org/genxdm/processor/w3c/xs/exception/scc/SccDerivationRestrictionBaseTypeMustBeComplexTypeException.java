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
public final class SccDerivationRestrictionBaseTypeMustBeComplexTypeException extends SccDerivationRestrictionException
{
    public SccDerivationRestrictionBaseTypeMustBeComplexTypeException(final QName complexTypeName)
    {
        super(PART_BASE_TYPE_MUST_BE_COMPLEX_TYPE_AND_ALLOWED_BY_FINAL, complexTypeName);
    }

    @Override
    public String getMessage()
    {
        return "The {base type definition} of the complex type " + getComplexTypeName() + " must be a complex type.";
    }
}
