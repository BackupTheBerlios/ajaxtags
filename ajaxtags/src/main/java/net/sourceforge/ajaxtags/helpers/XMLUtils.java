/**
 * Copyright 2009 Jens Kapitza
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
 * some helper functions for xml
 * 
 * @author jenskapitza
 * 
 */
public final class XMLUtils {

    private static ThreadLocal<TransformerFactory> transformerFactory = new ThreadLocal<TransformerFactory>() {

        @Override
        protected TransformerFactory initialValue() {
            return TransformerFactory.newInstance();
        }
    };
    private static ThreadLocal<DocumentBuilderFactory> docFactory = new ThreadLocal<DocumentBuilderFactory>() {

        @Override
        protected DocumentBuilderFactory initialValue() {
            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
            df.setValidating(false);
            df.setIgnoringElementContentWhitespace(true);
            return df;
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


    public static NodeList evaluateXPathExpression(final String expression, final Node node)
                    throws XPathExpressionException {
        return (NodeList) evaluateXPathExpression(expression, node, XPathConstants.NODESET);
    }


    public static Object evaluateXPathExpression(final String expression, final Node node,
                    final QName returnValue) throws XPathExpressionException {
        return xPathFactory.get().newXPath().evaluate(expression, node, returnValue);
    }


    public static Document getXMLDocument(String xml) throws SAXException {
        try {
            return docFactory.get().newDocumentBuilder().parse(
                            new InputSource(new StringReader(xml)));
        }
        catch (IOException e) {
            throw new SAXException(e);
        }
        catch (ParserConfigurationException e) {
            throw new SAXException(e);
        }
    }


    /**
     * create a new document
     * 
     * @return an empty document
     * @throws ParserConfigurationException
     */
    public static Document createDocument() throws ParserConfigurationException {
        return docFactory.get().newDocumentBuilder().newDocument();
    }


    public static String format(String xml) throws TransformerException, SAXException {
        return toString(getXMLDocument(xml));
    }


    public static String toString(Document document) throws TransformerException {
        StringWriter stringWriter = new StringWriter();
        StreamResult streamResult = new StreamResult(stringWriter);
        final Transformer transformer = transformerFactory.get().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.transform(new DOMSource(document.getDocumentElement()), streamResult);
        return stringWriter.toString();
    }
}
