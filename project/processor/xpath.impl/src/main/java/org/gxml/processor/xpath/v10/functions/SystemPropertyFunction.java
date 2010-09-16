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
package org.gxml.processor.xpath.v10.functions;

import org.gxml.xpath.v10.Function;
import org.gxml.xpath.v10.expressions.ConvertibleExpr;
import org.gxml.xpath.v10.expressions.ConvertibleVariantExpr;
import org.gxml.xpath.v10.expressions.ExprContextDynamic;
import org.gxml.xpath.v10.expressions.ExprContextStatic;
import org.gxml.xpath.v10.expressions.ExprException;
import org.gxml.xpath.v10.expressions.ExprParseException;
import org.gxml.xpath.v10.variants.Variant;
import org.gxml.base.Model;

/**
 * implements the system-property() function, XSLT 1.0, section 12.4
 */
public final class SystemPropertyFunction 
    implements Function
{

	public ConvertibleExpr makeCallExpr(final ConvertibleExpr e[], final ExprContextStatic statEnv) throws ExprParseException
	{
		if (e.length != 1)
		{
			throw new ExprParseException("expected one argument");
		}

		// final StringExpr<N> se = e[0].makeStringExpr();

		return new ConvertibleVariantExpr()
		{
			public <N> Variant<N> evaluateAsVariant(Model<N> model, final N contextNode, final ExprContextDynamic<N> dynEnv) throws ExprException
			{
				return null;
				// final String qname = se.eval(node, context);
				// final QName name = QNameExpander.expandElementName(qname, statEnv.getInScopeNamespaces());
				// return context.getSystemProperty(name);
			}
		};
	}
}