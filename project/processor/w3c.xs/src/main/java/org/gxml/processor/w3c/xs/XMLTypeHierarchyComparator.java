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
package org.gxml.processor.w3c.xs;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import javax.xml.namespace.QName;

import org.gxml.exceptions.PreCondition;

final class XMLTypeHierarchyComparator<A> implements Comparator<XMLType<A>>
{
	private final XMLSchemaCache<A> m_cache;
	private final HashMap<QName, HashSet<QName>> m_targets = new HashMap<QName, HashSet<QName>>();
	private final boolean m_reverse;

	public XMLTypeHierarchyComparator(final XMLSchemaCache<A> cache, boolean reverse)
	{
		m_cache = PreCondition.assertArgumentNotNull(cache, "cache");
		m_reverse = reverse;
	}

	public int compare(final XMLType<A> t1, final XMLType<A> t2)
	{
		if (t1 == t2 || t1.getName().equals(t2.getName()))
		{
			return 0;
		}
		else
		{
			if (references(t1, t2))
			{
				return m_reverse ? -1 : +1;
			}
			else if (references(t2, t1))
			{
				return m_reverse ? +1 : -1;
			}
			else
			{
				if (m_reverse)
				{
					return t2.getName().toString().compareToIgnoreCase(t1.getName().toString());
				}
				else
				{
					return t1.getName().toString().compareToIgnoreCase(t2.getName().toString());
				}
			}
		}
	}

	/**
	 * Does t1 reference t2?
	 */
	private boolean references(final XMLType<A> t1, final XMLType<A> t2)
	{
		final HashSet<QName> targets = computeReferences(t1);

		return targets.contains(t2.getName());
	}

	private HashSet<QName> computeReferences(final XMLType<A> type)
	{
		if (m_targets.containsKey(type.getName()))
		{
			return m_targets.get(type.getName());
		}
		else
		{
			final HashSet<QName> targets = new HashSet<QName>();
			final Stack<XMLType<A>> stack = new Stack<XMLType<A>>();
			{
				final XMLTypeRef<A> baseType = type.getBaseRef();
				if (baseType.isGlobal())
				{
					final QName baseName = baseType.getName();
					if (m_cache.m_globalTypes.containsKey(baseName))
					{
						stack.push(m_cache.m_globalTypes.get(baseName));
					}
					else
					{
						// A dangling reference.
					}
				}
			}

			while (!stack.isEmpty())
			{
				final XMLType<A> popped = stack.pop();

				targets.add(popped.getName());

				final XMLTypeRef<A> baseType = popped.getBaseRef();
				if (baseType.isGlobal())
				{
					final QName baseName = baseType.getName();
					if (m_cache.m_globalTypes.containsKey(baseName))
					{
						stack.push(m_cache.m_globalTypes.get(baseName));
					}
					else
					{
						// A dangling reference.
					}
				}
			}

			// System.out.println(StripQualifiers.strip(getClass().getName()) + ", targets(" + type.getName() + ")->" +
			// targets);

			m_targets.put(type.getName(), targets);

			return targets;
		}
	}
}
