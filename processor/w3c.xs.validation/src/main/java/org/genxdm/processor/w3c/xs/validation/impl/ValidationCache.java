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
package org.genxdm.processor.w3c.xs.validation.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.namespace.QName;

import org.genxdm.names.NameSource;
import org.genxdm.processor.w3c.xs.validation.api.VxSchemaDocumentLocationStrategy;
import org.genxdm.processor.w3c.xs.validation.api.VxValidator;
import org.genxdm.processor.w3c.xs.validation.api.VxValidatorCache;
import org.genxdm.processor.w3c.xs.validation.regex.api.RegExBridge;
import org.genxdm.processor.w3c.xs.validation.regex.api.RegExFactory;
import org.genxdm.processor.w3c.xs.validation.regex.api.RegExMachine;
import org.genxdm.processor.w3c.xs.validation.regex.api.RegExPattern;
import org.genxdm.processor.w3c.xs.validation.regex.impl.nfa.NfaFactory;
import org.genxdm.typed.types.AtomBridge;
import org.genxdm.xs.components.ComponentProvider;
import org.genxdm.xs.components.ElementDefinition;
import org.genxdm.xs.components.ModelGroup;
import org.genxdm.xs.components.ParticleTerm;
import org.genxdm.xs.components.SchemaParticle;
import org.genxdm.xs.types.ComplexType;
import org.genxdm.xs.types.ContentType;


final class ValidationCache implements VxValidatorCache
{
	@SuppressWarnings("unused")
	private final ElementDefinition elementDeclaration;
	private final ComponentProvider provider;
	private final VxSchemaDocumentLocationStrategy sdl;

	private final ConcurrentHashMap<ComplexType, RegExPattern<ValidationExpr, QName>> m_patterns = new ConcurrentHashMap<ComplexType, RegExPattern<ValidationExpr, QName>>();

	private final RegExBridge<ValidationExpr, QName> m_regexb;

	ValidationCache(final ElementDefinition elementDeclaration, final ComponentProvider provider, final VxSchemaDocumentLocationStrategy sdl)
	{
		this.elementDeclaration = elementDeclaration;
		this.provider = provider;
		m_regexb = new ValidationRegExBridge();
		this.sdl = sdl;
	}

	@Override
	public <A> VxValidator<A> newValidator(AtomBridge<A> bridge)
	{
		return new ValidationKernel<A>(provider, bridge, this, sdl);
	}

	SmContentFiniteStateMachine getMachine(final ComplexType complexType)
	{
		final ComplexType itemType = (ComplexType)complexType;
		final RegExPattern<ValidationExpr, QName> pattern = ensurePattern(itemType);

		final List<ValidationExpr> expectedFollowers = new LinkedList<ValidationExpr>();
		final RegExMachine<ValidationExpr, QName> regexm = PreCondition.assertArgumentNotNull(pattern.createRegExMachine(expectedFollowers), "createRegExMachine");

		return new SmMachineImpl(regexm);
	}

	private RegExPattern<ValidationExpr, QName> ensurePattern(final ComplexType complexType)
	{
		final RegExPattern<ValidationExpr, QName> cachedPattern = m_patterns.get(complexType);
		if (null != cachedPattern)
		{
			return cachedPattern;
		}
		else
		{
			final ContentType contentType = complexType.getContentType();

			final SchemaParticle contentModel = contentType.getContentModel();
			final ValidationExpr expression = expression(contentModel);
			final RegExFactory<ValidationExpr, QName> factory = new NfaFactory<ValidationExpr, QName>();
			final RegExPattern<ValidationExpr, QName> pattern = factory.newPattern(expression, m_regexb);
			m_patterns.put(complexType, pattern);
			return pattern;
		}
	}

	private ValidationExpr expression(final SchemaParticle particle)
	{
		final ParticleTerm term = particle.getTerm();
		if (term instanceof ModelGroup)
		{
			final ModelGroup group = (ModelGroup)term;
			return new ModelGroupExpression(particle, group);
		}
		else
		{
			return new ContentModelExpression(particle);
		}
	}
}