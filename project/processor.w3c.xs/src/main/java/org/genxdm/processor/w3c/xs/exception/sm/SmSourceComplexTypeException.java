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

import org.genxdm.exceptions.PreCondition;
import org.genxdm.xs.enums.ValidationOutcome;
import org.genxdm.xs.exceptions.SchemaException;
import org.genxdm.xs.resolve.LocationInSchema;

@SuppressWarnings("serial")
public abstract class SmSourceComplexTypeException extends SmLocationException
{
    public static final String PART_BASE_TYPE_MUST_BE_COMPLEX_TYPE = "1";
    public static final String PART_SIMPLE_CONTENT = "2";
    public static final String PART_SIMPLE_TYPE_AMONG_CHILDREN_OF_RESTRICTION = "2.2";
    public static final String PART_BASE_CONTENT_CANNOT_BE_SIMPLE = "?";

    public SmSourceComplexTypeException(final String partNumber, final LocationInSchema location)
    {
        super(ValidationOutcome.SRC_ComplexType, partNumber, location);
    }

    public SmSourceComplexTypeException(final String partNumber, final LocationInSchema location, final SchemaException cause)
    {
        super(ValidationOutcome.SRC_ComplexType, partNumber, location, PreCondition.assertArgumentNotNull(cause, "cause"));
    }
}
