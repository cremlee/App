package android.luna.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Lee.li on 2018/1/18.
 */

public class XmlPathParser {
    public static String getsvgPath(InputStream inStream) throws Exception {
        StringBuilder strb =new StringBuilder();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inStream);
        Element root = document.getDocumentElement();//返回文檔的根元素
        NodeList pathNodes = root.getElementsByTagName("path");
        String path;
        for (int i = 0; i < pathNodes.getLength(); i++) {
            Element pathElement = (Element) pathNodes.item(i);
            if (i!=pathNodes.getLength()-1) {
                 path = pathElement.getAttribute("android:pathData") + " ";
            }else
            {
                path = pathElement.getAttribute("android:pathData");
            }
            strb.append(path);
        }
        return strb.toString();
    }
}
