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
package org.gxml.processor.w3c.xs.exception;

import java.util.Set;

import org.gxml.xs.enums.SmDerivationMethod;
import org.gxml.xs.types.SmType;


@SuppressWarnings("serial")
public final class SccComplexTypeDerivationHierarchyException extends SccTypeDerivationOKComplexException
{
	public SccComplexTypeDerivationHierarchyException(final SmType<?> typeName, final SmType<?> baseName, final Set<SmDerivationMethod> subset)
	{
		super(PART_HIERARCHY, typeName, baseName, subset);
	}

	@Override
	public String getMessage()
	{
		return "The complex type definition " + getDerivedType().getName() + " is not validly derived from " + getBaseName() + " given the subset {" + derivations(getSubset()) + "}.";
	}
}
