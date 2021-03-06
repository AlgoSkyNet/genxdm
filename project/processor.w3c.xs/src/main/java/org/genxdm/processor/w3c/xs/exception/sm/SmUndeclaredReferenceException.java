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
package org.genxdm.processor.w3c.xs.exception.sm;

import javax.xml.namespace.QName;

import org.genxdm.exceptions.PreCondition;
import org.genxdm.xs.enums.ValidationOutcome;
import org.genxdm.xs.resolve.LocationInSchema;


@SuppressWarnings("serial")
public final class SmUndeclaredReferenceException extends SmLocationException
{
    private final QName m_what;

    public SmUndeclaredReferenceException(final QName name, final LocationInSchema location)
    {
        super(ValidationOutcome.TODO, "?", location);
        m_what = PreCondition.assertArgumentNotNull(name, "name");
    }

    @Override
    public String getMessage()
    {
        return "Unresolved reference to " + m_what;
    }
}
