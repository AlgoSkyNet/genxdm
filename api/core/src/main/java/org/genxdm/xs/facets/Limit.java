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
package org.genxdm.xs.facets;

import org.genxdm.typed.types.AtomBridge;
import org.genxdm.xs.exceptions.FacetMinMaxException;
import org.genxdm.xs.types.SimpleType;

/**
 * One of the xs:maxInclusive, xs:minInclusive, xs:maxExclusive and xs:minExclusive facets.
 * 
 */
public interface Limit extends Facet
{
    /**
     * The value of the facet.
     */
    <A> A getLimit(AtomBridge<A> bridge);

    /**
     * Validates the specified atom, with the specified type against this facet.
     * 
     * @param atom
     *            The atom to be validated.
     * @param simpleType
     *            The type of the atom.
     * @throws FacetMinMaxException
     *             if the atom does not comply with the facet.
     */
    <A> void validate(A atom, SimpleType simpleType, AtomBridge<A> bridge) throws FacetMinMaxException;
}
