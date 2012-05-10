package org.genxdm.bridgetest.typed;

import javax.xml.namespace.QName;

import org.genxdm.bridgekit.xs.ComponentBagImpl;
import org.genxdm.bridgekit.xs.complex.ComplexTypeImpl;
import org.genxdm.bridgekit.xs.complex.ContentTypeImpl;
import org.genxdm.bridgekit.xs.complex.ElementDeclTypeImpl;
import org.genxdm.bridgetest.TestBase;
import org.genxdm.typed.TypedContext;
import org.genxdm.typed.io.SequenceBuilder;
import org.genxdm.typed.types.TypesBridge;
import org.genxdm.xs.Schema;
import org.genxdm.xs.components.ElementDefinition;
import org.genxdm.xs.enums.ScopeExtent;
import org.genxdm.xs.types.ComplexType;

public abstract class TypedTestBase<N, A>
    extends TestBase<N>
{
    protected static enum Doctype
    { 
        SIMPLEST
        {
            public void initializeSchema(Schema schema)
            {
                // dead simple: it has a single empty element called "doc"
                // of course, that turns out not to be so simple, because an
                // element definition has a type.  And that type has a base type, and a content model,
                // and more *crap* than can be easily imagined.
                QName typeName = new QName(NSANON, "type-".concat(Integer.toString(nextType++)));
                ComplexType docElementType = new ComplexTypeImpl(typeName, 
                        /*native*/false, 
                        /*anonymous*/true, 
                        ScopeExtent.Local, 
                        /*base type*/schema.getComponentProvider().getComplexUrType(), 
                        /*derivation method*/null, 
                        /*attribute uses*/null, 
                        /*content type*/new ContentTypeImpl()/*this means empty*/, 
                        /*block*/null, 
                        /*atoms*/null);
                
                QName name = new QName(NSCOM, "doc");
                ElementDefinition docElement = new ElementDeclTypeImpl(name, ScopeExtent.Global, docElementType);
                // and you can't register components individually, either.
                ComponentBagImpl bag = new ComponentBagImpl();
                bag.add(docElementType);
                bag.add(docElement);
                schema.register(bag);
            }
            public <N, A> N buildTypedDocument(SequenceBuilder<N, A> builder, TypesBridge schema)
            {
                return null;
            }
        },
        
        TEXTMARKUP
        {
            public void initializeSchema(Schema schema)
            {
                // still very simple, basically a simple structured document.
                // doc { 
                //          title {text}, 
                //          para {mixed: text, em{text}, bold{text}, link{@href, text}, fnref{@idref}}+, 
                //          fn{ mixed: text, link{@href, text}* 
                // }
                // define link globally, refer to it.
            }
            public <N, A> N buildTypedDocument(SequenceBuilder<N, A> builder, TypesBridge schema)
            {
                return null;
            }
        },
        
        PO
        {
            public void initializeSchema(Schema schema)
            {
                // more complex, and using some basic types.
                // purchaseorder {
                //     shipto[addr],
                //     billto[addr],
                //     orderdate[datetime]
                //     items {
                //         item { name, sku, quantity }
                //     }
                // }
            }
            public <N, A> N buildTypedDocument(SequenceBuilder<N, A> builder, TypesBridge schema)
            {
                return null;
            }
        }, 
        
        SOAPYMESS
        {
            public void initializeSchema(Schema schema)
            {
                // dunno about doing a genuine soap message; that really would
                // be a mess.  let's try for just the body, or something.
                // the big deal here is that we want to have multiple
                // namespaces involved.
            }
            public <N, A> N buildTypedDocument(SequenceBuilder<N, A> builder, TypesBridge schema)
            {
                return null;
            }
        };
        
        public abstract void initializeSchema(Schema schema);
        
        public abstract <N, A> N buildTypedDocument(SequenceBuilder<N, A> builder, TypesBridge schema);
    };
    
    // convenience method for testing typed context variants
    protected TypedContext<N, A> getTypedContext(TypesBridge bridge)
    {
        return newProcessingContext().getTypedContext(bridge);
    }
    
    // to use this, specify one of the enums
    // then we'll build a document that has appropriate annotations.
    // then the actual test code can verify things about the well-known
    // schema components and documents that are created.
    protected N createValidTypedDocument(TypedContext<N, A> schemaContext, Doctype dc)
    {
        // create/register the schema components
        dc.initializeSchema(schemaContext.getTypesBridge());
        // build the typed document
        N document = dc.buildTypedDocument(schemaContext.newSequenceBuilder(), schemaContext.getTypesBridge());
        // return it
        return document;
    }

    static int nextType = 0;
    
    protected static final String NSCOM = "http://www.example.com/namespace";
    protected static final String NSORG = "http://www.example.org/namespace";
    protected static final String NSNET = "http://www.example.net/namespace";
    protected static final String NSANON = "http://www.example.anon/namespace";
}
