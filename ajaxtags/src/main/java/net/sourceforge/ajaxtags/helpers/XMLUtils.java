/*
 * Copyright 2009 AjaxTags-Team
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sourceforge.ajaxtags.helpers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Some helper functions for XML.
 *
 * @author jenskapitza
 * @version $Revision$ $Date$ $Author$
 */
public final class XMLUtils {

    private static final String TRANSFORMER_YES = "yes";

    private static ThreadLocal<TransformerFactory> transformerFactory = new ThreadLocal<TransformerFactory>() {
        @Override
        protected TransformerFactory initialValue() {
            return TransformerFactory.newInstance();
        }
    };

    private static ThreadLocal<DocumentBuilderFactory> docFactory = new ThreadLocal<DocumentBuilderFactory>() {
        @Override
        protected DocumentBuilderFactory initialValue() {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            dbf.setIgnoringElementContentWhitespace(true);
            return dbf;
        };
    };

    private static ThreadLocal<XPathFactory> xPathFactory = new ThreadLocal<XPathFactory>() {
        @Override
        protected XPathFactory initialValue() {
            return XPathFactory.newInstance();
        }
    };

    private XMLUtils() {
    }

    /**
     * Evaluate XPath expression and return list of nodes.
     *
     * @param expression
     *            XPath expression
     * @param node
     *            DOM node
     * @return list of DOM nodes
     * @throws XPathExpressionException
     *             if expression cannot be evaluated
     */
    public static NodeList evaluateXPathExpression(final String expression, final Node node)
            throws XPathExpressionException {
        return (NodeList) evaluateXPathExpression(expression, node, XPathConstants.NODESET);
    }

    /**
     * Evaluate XPath expression.
     *
     * @param expression
     *            XPath expression
     * @param node
     *            DOM node
     * @param returnValue
     *            the desired return type
     * @return result of evaluating an XPath expression as an Object of returnType
     * @throws XPathExpressionException
     *             if expression cannot be evaluated
     */
    public static Object evaluateXPathExpression(final String expression, final Node node,
            final QName returnValue) throws XPathExpressionException {
        return xPathFactory.get().newXPath().evaluate(expression, node,
                returnValue == null ? XPathConstants.NODE : returnValue);
    }

    /**
     * @return DocumentBuilder
     * @throws ParserConfigurationException
     *             if a DocumentBuilder cannot be created which satisfies the configuration
     *             requested
     */
    private static DocumentBuilder getNewDocumentBuilder() throws ParserConfigurationException {
        return docFactory.get().newDocumentBuilder();
    }

    /**
     * Parse string with XML content to {@link org.w3c.dom.Document}.
     *
     * @param xml
     *            string with XML content
     * @return Document
     * @throws SAXException
     *             if any parse errors occur
     */
    public static Document getXMLDocument(final String xml) throws SAXException {
        try {
            return getNewDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        } catch (IOException e) {
            throw new SAXException(e);
        } catch (ParserConfigurationException e) {
            throw new SAXException(e);
        }
    }

    /**
     * Create a new {@link org.w3c.dom.Document}.
     *
     * @return an empty document
     * @throws ParserConfigurationException
     *             if a DocumentBuilder cannot be created
     */
    public static Document createDocument() throws ParserConfigurationException {
        return getNewDocumentBuilder().newDocument();
    }

    /**
     * Parse string as XML document and return string with reformatted document.
     *
     * @param xml
     *            string with XML content
     * @return reformatted content
     * @throws TransformerException
     *             if it is not possible to transform document to string
     * @throws SAXException
     *             if any parse errors occur
     */
    public static String format(final String xml) throws TransformerException, SAXException {
        return toString(getXMLDocument(xml));
    }

    /**
     * Transform document to string representation.
     *
     * @param document
     *            XHTML document
     * @return string representation of document
     * @throws TransformerException
     *             if it is not possible to create a Transformer instance or to transform document
     */
    public static String toString(final Document document) throws TransformerException {
        final StringWriter stringWriter = new StringWriter();
        final StreamResult streamResult = new StreamResult(stringWriter);
        final Transformer transformer = transformerFactory.get().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, TRANSFORMER_YES);
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        // transformer.setOutputProperty(OutputKeys.METHOD, "html");
        // html method transforms <br/> into <br>, which cannot be re-parsed
        // transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");
        // xhtml method does not work for my xalan transformer
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, TRANSFORMER_YES);
        transformer.transform(new DOMSource(document.getDocumentElement()), streamResult);
        return stringWriter.toString();
    }
}
