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
package org.genxdm.processor.w3c.xs.regex.nfa;


import java.util.List;

import org.genxdm.processor.w3c.xs.regex.api.RegExDerivative;
import org.genxdm.processor.w3c.xs.regex.api.RegExMachine;


public final class LazyStepper<E,T> implements RegExMachine<E, T>
{
    private E m_expression;
    private final RegExDerivative<E, T> m_bridge;

    public LazyStepper(final E expression, final RegExDerivative<E, T> bridge)
    {
        m_expression = expression;
        m_bridge = bridge;
    }

    public boolean step(final T token, final List<? super E> matchers)
    {
        if (null != token)
        {
            // TODO: How do we get matches? It must be from the derivative.
            m_expression = m_bridge.residual(m_expression, token, matchers);
            return !m_bridge.empty(m_expression);
        }
        else
        {
            return m_bridge.delta(m_expression);
        }
    }
}
