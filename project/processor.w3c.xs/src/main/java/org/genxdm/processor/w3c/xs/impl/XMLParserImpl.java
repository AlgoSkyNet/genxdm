/*
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
package org.genxdm.processor.w3c.xs.impl;

import java.io.InputStream;

import org.genxdm.Cursor;
import org.genxdm.bridgekit.xs.ComponentBagImpl;
import org.genxdm.exceptions.PreCondition;
import org.genxdm.processor.w3c.xs.LoadOptions;
import org.genxdm.processor.w3c.xs.xmlrep.XMLSchemaCache;
import org.genxdm.processor.w3c.xs.xmlrep.XMLSchemaModule;
import org.genxdm.processor.w3c.xs.xmlrep.exceptions.XMLSccExceptionAdapter;
import org.genxdm.processor.w3c.xs.xmlrep.util.XMLComponentLocator;
import org.genxdm.xs.ComponentBag;
import org.genxdm.xs.ComponentProvider;
import org.genxdm.xs.SchemaLoadOptions;
import org.genxdm.xs.SchemaParser;
import org.genxdm.xs.exceptions.AbortException;
import org.genxdm.xs.exceptions.SchemaException;
import org.genxdm.xs.exceptions.SchemaExceptionCatcher;
import org.genxdm.xs.exceptions.SchemaExceptionHandler;
import org.genxdm.xs.facets.SchemaRegExCompiler;
import org.genxdm.xs.resolve.CatalogResolver;
import org.genxdm.xs.resolve.SchemaCatalog;

final public class XMLParserImpl implements SchemaParser, LoadOptions
{
    public XMLParserImpl(final ComponentProvider cache)
    {
        this.cache = PreCondition.assertArgumentNotNull(cache, "cache");
    }

    @Override
    public ComponentBag parse(final String schemaLocation, final Cursor tree,
            final String systemId, final SchemaExceptionHandler errors)
        throws AbortException
    {
        PreCondition.assertNotNull(tree, "tree");
        CursoryInputStream stream = new CursoryInputStream(tree, "UTF-8");
        return parse(schemaLocation, stream, systemId, errors);
    }
    
    @Override
    public ComponentBag parse(final String schemaLocation, final InputStream istream, final String systemId, final SchemaExceptionHandler errors) 
        throws AbortException
    {
        PreCondition.assertArgumentNotNull(istream, "istream");
        // PreCondition.assertArgumentNotNull(systemId, "systemId");
        // PreCondition.assertArgumentNotNull(errors, "errors");

        // The cache holds an in-memory model of the XML representation.
        final XMLSchemaCache schemaCache = new XMLSchemaCache();

        // The top-level module acts as a parent for includes, imports and redefines.
        final XMLSchemaModule module = new XMLSchemaModule(null, schemaLocation, systemId);

        // Catch the reported exceptions in order to maximize the amount of feedback.
        final SchemaExceptionCatcher caught = new SchemaExceptionCatcher();

        // Delegate the parsing into an XML representation
        final XMLSchemaParser parser = new XMLSchemaParser(this.cache, caught, m_catalog, m_resolver, m_processRepeatedNamespaces);

        parser.parse(systemId, istream, schemaCache, module);
        
        return resolve(schemaCache, caught, errors);

    }

    @Override
    public void setCatalogResolver(final CatalogResolver resolver, final SchemaCatalog catalog)
    {
        m_resolver = resolver;
        m_catalog = catalog;
    }

    @Override
    public void setComponentProvider(final ComponentProvider provider)
    {
        this.cache = provider;
    }
    
    @Override
    public void setRegExCompiler(final SchemaRegExCompiler regexc)
    {
        m_regexc = regexc;
    }

    @Override
    public void setSchemaLoadOptions(final SchemaLoadOptions options)
    {
        m_options = options;
        if (options != null)
        {
            String prn = m_options.getOption(OPTION_REPEAT_NS);
            if (prn != null)
            {
                if (prn.trim().equals(TRUE) || prn.trim().equals(YES))
                    m_processRepeatedNamespaces = true;
                else
                    m_processRepeatedNamespaces = false;
            }
            String liw = m_options.getOption(OPTION_LAST_IN_WINS);
            if (liw != null)
            {
                if (liw.trim().equals(TRUE) || liw.trim().equals(YES))
                    m_lastInWins = true;
                else
                    m_lastInWins = false;
            }
        }
    }

    /**
     * Converts the XML cache into a compiled schema.
     */
    private Pair<ComponentBagImpl, XMLComponentLocator> convert(final XMLSchemaCache cache, final SchemaExceptionHandler errors) throws AbortException
    {
        // Convert the schema before checking for unresolved references.  That way, during the
        // conversion from XML model to SCHEMA model, we can peer into the SmComponentProvider to
        // see if any of the desired components are there.
        Pair<ComponentBagImpl, XMLComponentLocator> retval = XMLSchemaConverter.convert(m_regexc, this.cache, cache, errors, m_lastInWins);      
        try
        {
            cache.checkReferences();
        }
        catch (final SchemaException e)
        {
            errors.error(e);
            return null;
        }

        return retval;
    }

    private void reportErrors(final SchemaExceptionCatcher caught, final SchemaExceptionHandler errors) throws AbortException
    {
        for (final SchemaException error : caught)
        {
            if (null != errors)
            {
                errors.error(error);
            }
            else
            {
                throw new AbortException(error);
            }
        }
    }
    
    private ComponentBag resolve(XMLSchemaCache schemaCache, SchemaExceptionCatcher catcher, SchemaExceptionHandler errors)
        throws AbortException
    {
        if (!catcher.isEmpty())
        {
            reportErrors(catcher, errors);
            return null;
        }

        // Convert the XML representation into the compiled schema.
        final Pair<ComponentBagImpl, XMLComponentLocator> converted = convert(schemaCache, catcher);

        if (catcher.isEmpty())
        {
            final XMLSccExceptionAdapter scc = new XMLSccExceptionAdapter(catcher, converted.getSecond());

            SchemaConstraintChecker checker = new SchemaConstraintChecker(converted.getFirst(), cache);
            checker.checkSchemaComponentConstraints(scc);

            if (catcher.isEmpty())
                return converted.getFirst();
        }
        // implicit else for the last two conditionals, because they test the
        // same thing, and the inner one has a return.
        reportErrors(catcher, errors);
        return null;
    }

    /**
     * Injected during initialization, already contains native and xsi schema components.
     */
    private ComponentProvider cache;

    private SchemaCatalog m_catalog;
    private CatalogResolver m_resolver;
    private SchemaRegExCompiler m_regexc;
    private SchemaLoadOptions m_options;
    private boolean m_processRepeatedNamespaces = false;
    private boolean m_lastInWins = false;

}
