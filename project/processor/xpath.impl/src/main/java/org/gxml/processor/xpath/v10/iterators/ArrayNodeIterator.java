/**
 * Portions copyright (c) 1998-1999, James Clark : see copyingjc.txt for
 * license details
 * Portions copyright (c) 2002, Bill Lindsey : see copying.txt for license
 * details
 * 
 * Portions copyright (c) 2009-2010 TIBCO Software Inc.
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
package org.gxml.processor.xpath.v10.iterators;

import org.gxml.xpath.v10.iterators.NodeIterator;

public final class ArrayNodeIterator<N> implements NodeIterator<N>
{
	private int i;
	private final int len;
	private final N[] nodes;

	public ArrayNodeIterator(final N[] nodes, final int start, final int end)
	{
		this.nodes = nodes;
		this.len = end;
		this.i = start;
	}

	public N next()
	{
		if (i == len)
		{
			return null;
		}
		return nodes[i++];
	}
}
