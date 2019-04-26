package com.jzy.api.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataUtils {

    /**
     * 读取xml文件
     */
    public static String readXmlFile(String filePath) {
        SAXReader sax = new SAXReader();
        String formatXml = null;
        try {
            Document doc = sax.read(new File(filePath));
            formatXml = formatXml(doc.asXML());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return formatXml;
    }

    /**
     * 格式化输出xml
     */
    public static String formatXml(String xmlStr) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return formatXml(document);
    }

    /**
     * 将document写入到输出流
     */
    public static String formatXml(Document document) {
        StringWriter writer = new StringWriter();
        XMLWriter xmlWriter;
        try {
            OutputFormat format = OutputFormat.createPrettyPrint(); // 格式化输出格式
            // format.setEncoding("UTF-8");
            format.setIndentSize(4);
            xmlWriter = new XMLWriter(writer, format);
            xmlWriter.write(document); // 将document写入到输出流
            xmlWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static String mapToxml(Map<String, String> map) {
        StringWriter sw = null;
        try {
            Document d = DocumentHelper.createDocument();
            Element root = d.addElement("xml");
            Set<String> keys = map.keySet();
            for (String key : keys) {
                root.addElement(key).addText(map.get(key));
            }
            sw = new StringWriter();
            XMLWriter xw = new XMLWriter(sw);
            xw.setEscapeText(false);
            xw.write(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    public static Map<String, String> xmlTomap(String strXML) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            DocumentBuilder documentBuilder = WXPayXmlUtil.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    map.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    /**
     * 对象转换xml字符串
     */
    public static String objectToXml(Object obj, Class<?> cls) {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(cls);
            Marshaller marshaller = context.createMarshaller();

//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 格式化xml字符串
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);// 是否省略xml头声明信息
            marshaller.marshal(obj, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

}
