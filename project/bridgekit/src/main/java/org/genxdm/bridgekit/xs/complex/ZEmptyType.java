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
package org.genxdm.bridgekit.xs.complex;

import org.genxdm.bridgekit.xs.ForeignAttributesImpl;
import org.genxdm.typed.types.Quantifier;
import org.genxdm.xs.types.EmptyType;
import org.genxdm.xs.types.PrimeType;
import org.genxdm.xs.types.PrimeTypeKind;
import org.genxdm.xs.types.SequenceType;
import org.genxdm.xs.types.SequenceTypeVisitor;

public final class ZEmptyType
    extends ForeignAttributesImpl
    implements EmptyType
{
    public ZEmptyType()
    {
    }

    public PrimeType prime()
    {
        return new NoneTypeImpl();
    }

    public Quantifier quantifier()
    {
        return Quantifier.OPTIONAL;
    }

    public boolean isNone()
    {
        return false;
    }

    public boolean subtype(final PrimeType rhs)
    {
        return rhs.quantifier().contains(Quantifier.EMPTY);
    }

    public PrimeTypeKind getKind()
    {
        return PrimeTypeKind.EMPTY;
    }

    public boolean isNative()
    {
        return false;
    }

    public boolean isChoice()
    {
        return false;
    }

    public SequenceType atomSet()
    {
        return this;
    }

    @Override
    public String toString()
    {
        return "empty";
    }

    public void accept(final SequenceTypeVisitor visitor)
    {
        visitor.visit(this);
    }
}
