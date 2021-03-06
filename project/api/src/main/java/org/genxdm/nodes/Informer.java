/*
 * Copyright (c) 2009-2011 TIBCO Software Inc.
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
package org.genxdm.nodes;

import java.net.URI;

import org.genxdm.NodeKind;
import org.genxdm.io.Reader;
import org.genxdm.names.NamespaceBinding;

/**
 * A stateful abstraction that provides information about the &lt;N>ode which is
 * its state. 
 */
public interface Informer
    extends Reader
{
    /**
     * Returns the base URI of the current node, per the XML:Base
     * specification.
     * <p>Corresponds to the dm:base-uri accessor in the XDM.  Defined
     * for all node types except namespace.</p>
     * 
     * @return the absolute value of the base-uri property, if it is available,
     * or null if it is not.
     */
    URI getBaseURI();
    
    /**
     * Returns the absolute URI of the resource from which the Document Node was
     * constructed.
     * <p>Corresponds to the dm:document-uri accessor in the XDM.</p>
     * 
     * @return the absolute URI of the resource from which the Document Node was
     *         constructed, if the absolute URI is available; if there is no URI
     *         available, or if it cannot be made absolute when the Document
     *         Node is constructed, or if it is used on a node other than a
     *         Document Node, returns null
     */
    URI getDocumentURI();

    /**
     * Returns the namespace bindings associated with the node as a set or prefix/URI pairs.
     * 
     * <p>Only includes prefix mappings which are explicit and local to the node.</p>
     * 
     * @return an iterable of {@link NamespaceBinding}s for declared namespaces.
     */
    Iterable<NamespaceBinding> getNamespaceBindings();
    
    /**
     * Return an object which obeys the contract for equals() and hashCode();
     * it may also identify the node via object identity (==), but this is not
     * guaranteed.  Implementations often return the node itself, but some bridges
     * are not able to do so.  The object returned from this method is guaranteed
     * to obey the equals()/hashCode() contract even when the node the object identifies
     * does not.
     * 
     * <p>Conforms to the contract specified in section 2.3 of the XDM specification
     * for node identity.  Nodes in an instance are equal to themselves and to no
     * other node; they are never equal across instances.</p>
     * 
     * @return a bridge-defined object that obeys the constraints specified,
     * for any given node supported by the bridge.
     */
    Object getNodeId();

    /**
     * Returns the dm:node-kind property of the XDM as an enumeration in {@link NodeKind}.
     * 
     * @return the {@link NodeKind} of this node.
     */
    NodeKind getNodeKind();

    /**
     * Test whether this node contains child nodes.
     * 
     * <p>Never true for non-container nodes, and may be false for empty
     * container nodes.</p>
     * 
     * @return <code>true</code> if the node has children, otherwise <code>false</code>.
     */
    boolean hasChildren();
    
    /**
     * Test whether this node has a following sibling.
     * 
     * <p>Never true for non-child nodes, and may be false for the last child
     * in a container (or have no parent).  Note that attribute and namespace nodes always return
     * false, as do document nodes.</p>
     * 
     * @return <code>true</code> if the node has a following sibling, otherwise <code>false</code>.
     */
    boolean hasNextSibling();

    /**
     * Test whether this node has a parent.
     * 
     * <p>Never true for non-child nodes; child nodes may return false if they
     * are not currently contained by a container node.</p>
     * 
     * @return <code>true</code> if the node has a parent, otherwise <code>false</code>.
     */
    boolean hasParent();

    /**
     * Test whether this node has a previous sibling.
     * 
     * <p>Never true for non-child nodes; child nodes may return false if they
     * are the first child in a container (or have no parent).</p>
     * 
     * @return <code>true</code> if the node has a preceding sibling, otherwise <code>false</code>.
     */
    boolean hasPreviousSibling();

    /**
     * Test whether this node is an attribute node.
     * 
     * @return true for attribute nodes, false for all other node types.
     */
    boolean isAttribute();

    /**
     * Test whether this node is an element node.
     * 
     * @return true for element nodes, false for all other node types.
     */
    boolean isElement();
    
    /**
     * Test whether this node is an ID node.
     * <p>Corresponds to the dm:is-id accessor.  Valid for element and attribute nodes.</p>
     * 
     * @return true if the node is an attribute named xml:id, if it has a PSVI
     * type derived from xs:ID, or if it is an attribute with a DTD-defined type of ID,
     * or if it is an element that contains such an attribute, otherwise false.
     */
    boolean isId();
    
    /**
     * Test whether this node contains one or more IDREFs.
     * <p>Corresponds to the dm:is-idrefs accessor.  Valid for element and attribute nodes.</p>
     * 
     * @return true if the node is an element or attribute with at least one atomic value
     * derived from xs:IDREF or xs:IDREFS, or if it is an attribute with a DTD-defined
     * type of IDREF or IDREFS. Returns false for an element that contains an attribute
     * of the designated type.
     */
    boolean isIdRefs();

    /**
     * Test whether this node is a namespace node.
     * 
     * @return true for namespace nodes, false for all other kinds.
     */
    boolean isNamespace();
    
    /**
     * Test whether this node is a text node.
     * 
     * @return true for text nodes, false for all other kinds.
     */
    boolean isText();

    /**
     * Test whether this node matches the arguments.
     * 
     * @param nodeKind
     *            The node kind to match; if null, match regardless of node kind.
     * @param namespaceURI
     *            The namespace-uri to match; if null, match regardless of namespace-uri.
     * @param localName
     *            The local-name to match; if null, match regardless of name.
     * @return <code>true</code> if the cursor matches the specified arguments.
     */
    boolean matches(NodeKind nodeKind, String namespaceURI, String localName);

    /**
     * Determines whether the current node matches in name.
     * 
     * <p>Equivalent to the {@link #matches(NodeKind, String, String)} method
     * with a <code>null</code> first argument.</p>
     * 
     * @param namespaceURI
     *            The namespace-uri part of the name; if null, match regardless of namespace-uri.
     * @param localName
     *            The local-name part of the name; if null, match regardless of name.
     * @return <code>true</code> if the node matches the arguments specified, otherwise <code>false</code>.
     */
    boolean matches(String namespaceURI, String localName);
}
