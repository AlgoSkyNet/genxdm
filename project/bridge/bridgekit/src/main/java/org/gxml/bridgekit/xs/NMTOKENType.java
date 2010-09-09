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
package org.gxml.bridgekit.xs;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.gxml.typed.types.AtomBridge;
import org.gxml.xs.components.SmEnumeration;
import org.gxml.xs.enums.SmDerivationMethod;
import org.gxml.xs.enums.SmScopeExtent;
import org.gxml.xs.enums.SmWhiteSpacePolicy;
import org.gxml.xs.exceptions.SmDatatypeException;
import org.gxml.xs.facets.SmFacet;
import org.gxml.xs.facets.SmFacetKind;
import org.gxml.xs.facets.SmPattern;
import org.gxml.xs.resolve.SmPrefixResolver;
import org.gxml.xs.types.SmNativeType;
import org.gxml.xs.types.SmSequenceTypeVisitor;
import org.gxml.xs.types.SmSimpleType;

final class NMTOKENType<A> extends AbstractAtomType<A>
{
	public NMTOKENType(final QName name, final SmSimpleType<A> baseType, final AtomBridge<A> atomBridge)
	{
		super(name, baseType, atomBridge);
	}

	public void accept(SmSequenceTypeVisitor<A> visitor)
	{
		// TODO Auto-generated method stub
		throw new AssertionError("TODO");
	}

	public boolean derivedFrom(String namespace, String name, Set<SmDerivationMethod> derivationMethods)
	{
		// TODO Auto-generated method stub
		throw new AssertionError("TODO");
	}

	public Iterable<SmEnumeration<A>> getEnumerations()
	{
		// TODO Auto-generated method stub
		throw new AssertionError("TODO");
	}

	public SmFacet<A> getFacetOfKind(SmFacetKind facetKind)
	{
		// TODO Auto-generated method stub
		throw new AssertionError("TODO");
	}

	public Iterable<SmFacet<A>> getFacets()
	{
		return Collections.emptyList();
	}

	public Set<SmDerivationMethod> getFinal()
	{
		return Collections.emptySet();
	}

	public SmNativeType getNativeType()
	{
		return SmNativeType.NMTOKEN;
	}

	public Iterable<SmPattern> getPatterns()
	{
		// TODO Auto-generated method stub
		throw new AssertionError("TODO");
	}

	public SmScopeExtent getScopeExtent()
	{
		// TODO Auto-generated method stub
		throw new AssertionError("TODO");
	}

	public SmWhiteSpacePolicy getWhiteSpacePolicy()
	{
		return SmWhiteSpacePolicy.COLLAPSE;
	}

	public boolean hasEnumerations()
	{
		return false;
	}

	public boolean hasFacetOfKind(final SmFacetKind facetKind)
	{
		return false;
	}

	public boolean hasFacets()
	{
		return false;
	}

	public boolean hasPatterns()
	{
		return false;
	}

	public boolean isAbstract()
	{
		return false;
	}

	public boolean isID()
	{
		return false;
	}

	public boolean isIDREF()
	{
		return false;
	}

	public List<A> validate(final String initialValue) throws SmDatatypeException
	{
		final String normalized = normalize(initialValue);
		return atomBridge.wrapAtom(atomBridge.createStringDerived(castAsNMTOKEN(normalized, this), SmNativeType.NMTOKEN));
	}

	private static String castAsNMTOKEN(final String normalized, final SmSimpleType<?> type) throws SmDatatypeException
	{
		for (int i = 0, len = normalized.length(); i < len; i++)
		{
			if (!XmlCharacterSupport.isNameChar(normalized.charAt(i)))
			{
				// final char c = normalized.charAt(i);
				throw new SmDatatypeException(normalized, type);
				// throw new SmDattypeException(normalized, typeXsNamespace.NMTOKEN, Err.FORG0001.qname(), new
				// Exception("character '" + c + "' (#x" + Integer.toHexString(c) +
				// ") is not allowed by an NMTOKEN"));
			}
		}
		return normalized;
	}

	public List<A> validate(String initialValue, SmPrefixResolver resolver) throws SmDatatypeException
	{
		// TODO Auto-generated method stub
		throw new AssertionError("TODO");
	}
}
