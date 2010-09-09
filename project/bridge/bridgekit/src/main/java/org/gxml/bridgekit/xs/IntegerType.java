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

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.gxml.bridgekit.misc.UnaryIterable;
import org.gxml.exceptions.PreCondition;
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

final class IntegerType<A> extends AbstractAtomType<A>
{
	private final SmFacet<A> FRACTION_DIGITS;
	private final Iterable<SmFacet<A>> FACETS;

	public IntegerType(final QName name, final SmSimpleType<A> baseType, final AtomBridge<A> atomBridge)
	{
		super(name, baseType, atomBridge);
		FRACTION_DIGITS = new FacetFractionDigitsImpl<A>(0, true, atomBridge);
		FACETS = new UnaryIterable<SmFacet<A>>(FRACTION_DIGITS);
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

	public SmFacet<A> getFacetOfKind(final SmFacetKind facetKind)
	{
		PreCondition.assertArgumentNotNull(facetKind, "facetKind");
		switch (facetKind)
		{
			case MinInclusive:
			case MaxInclusive:
			case MinExclusive:
			case MaxExclusive:
			case TotalDigits:
			{
				return null;
			}
			case FractionDigits:
			{
				return FRACTION_DIGITS;
			}
			default:
			{
				throw new AssertionError("TODO: getFacetOfKind(" + facetKind + ")");
			}
		}
	}

	public Iterable<SmFacet<A>> getFacets()
	{
		return FACETS;
	}

	public Set<SmDerivationMethod> getFinal()
	{
		return Collections.emptySet();
	}

	public SmNativeType getNativeType()
	{
		return SmNativeType.INTEGER;
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
		switch (facetKind)
		{
			case FractionDigits:
			{
				return true;
			}
			default:
			{
				return false;
			}
		}
	}

	public boolean hasFacets()
	{
		return true;
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
		try
		{
			// Note that trimming eliminates a leading plus-sign, but leaves leading minus-sign.
			final String trimmed = trim(initialValue);
			return atomBridge.wrapAtom(atomBridge.createInteger(new BigInteger(trimmed)));
		}
		catch (final NumberFormatException e)
		{
			throw new SmDatatypeException(initialValue, this);
		}
	}

	public List<A> validate(String initialValue, SmPrefixResolver resolver) throws SmDatatypeException
	{
		// TODO Auto-generated method stub
		throw new AssertionError("TODO");
	}
}
