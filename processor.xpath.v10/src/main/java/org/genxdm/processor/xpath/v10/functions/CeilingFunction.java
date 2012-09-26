/*
 * Portions copyright (c) 1998-1999, James Clark : see copyingjc.txt for
 * license details
 * Portions copyright (c) 2002, Bill Lindsey : see copying.txt for license
 * details
 * 
 * Portions copyright (c) 2009-2011 TIBCO Software Inc.
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
package org.genxdm.processor.xpath.v10.functions;

import org.genxdm.Model;
import org.genxdm.nodes.TraversingInformer;
import org.genxdm.processor.xpath.v10.expressions.ConvertibleExprImpl;
import org.genxdm.processor.xpath.v10.expressions.ConvertibleNumberExpr;
import org.genxdm.xpath.v10.TraverserDynamicContext;
import org.genxdm.xpath.v10.NodeDynamicContext;
import org.genxdm.xpath.v10.StaticContext;
import org.genxdm.xpath.v10.ExprParseException;
import org.genxdm.xpath.v10.NumberExpr;
import org.genxdm.xpath.v10.extend.ConvertibleExpr;

/**
 * the XPath Function: number ceiling(number)
 * 
 * The ceiling function returns the smallest (closest to negative infinity) number that is not less than the argument and that is an integer.
 */
public final class CeilingFunction 
    extends Function1
{

	ConvertibleExprImpl makeCallExpr(final ConvertibleExpr e, final StaticContext statEnv) throws ExprParseException
	{
		final NumberExpr ne = e.makeNumberExpr(statEnv);

		return new ConvertibleNumberExpr()
		{
            @Override
			// oddly uses a double instead of an int
			public <N> double numberFunction(Model<N> model, final N contextNode, final NodeDynamicContext<N> dynEnv) {
				return Math.ceil(ne.numberFunction(model, contextNode, dynEnv));
			}

            @Override
            public double numberFunction(TraversingInformer contextNode, TraverserDynamicContext dynEnv) {
                return Math.ceil(ne.numberFunction(contextNode, dynEnv));
            }
		};
	}
}
