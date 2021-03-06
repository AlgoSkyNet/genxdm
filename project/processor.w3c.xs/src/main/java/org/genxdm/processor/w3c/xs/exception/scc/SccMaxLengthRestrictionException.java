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

import org.genxdm.xs.enums.ValidationOutcome;
import org.genxdm.xs.exceptions.ComponentConstraintException;

/**
 * Date: Mar 11, 2008
 */
@SuppressWarnings("serial")
final public class SccMaxLengthRestrictionException extends ComponentConstraintException
{
    private final int m_maxLength;
    private final int m_restrictedMaxLength;

    public SccMaxLengthRestrictionException(final int maxLength, final int restrictedMaxLength)
    {
        super(ValidationOutcome.SCC_FractionDigitsValidRestriction, "4.3.3.4");
        m_maxLength = maxLength;
        m_restrictedMaxLength = restrictedMaxLength;
    }
    @Override
    public String getMessage()
    {
        return "The {value}, " + m_maxLength + ", of maxLength must be less than or equal to the {value}, " + m_restrictedMaxLength + ", of its inherited maxLength.";
    }
}
