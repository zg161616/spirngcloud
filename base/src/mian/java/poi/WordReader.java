package poi;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.util.List;

/**
 * @author bwh
 * @date 2020/1/16/016 - 10:18
 * @Description
 */
public class WordReader {
    public static void main(String[] args) {
        try {
            new WordReader().test4();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void test4() throws Exception {
        XWPFDocument word = new XWPFDocument(new FileInputStream("D:\\公式.docx"));
        try {
            List<XWPFParagraph> paragraphs = word.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {

                StringBuffer text = new StringBuffer();
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {

                    Node runNode = run.getCTR().getDomNode();
                    nodeInfo(runNode);
                    text.append(getText(runNode));
                    String math = getMath(run, runNode);
                    text.append(math);
                }
                System.out.println("段落内容：".concat(text.toString()));
            }
        } finally {
            word.close();
        }
    }

    public void nodeInfo(Node node){
        System.out.println(node.getNodeName()+":");
        NamedNodeMap attributes = node.getAttributes();
        if(attributes!=null){
            for (int i = 0; i < attributes.getLength(); i++) {
                Node item = attributes.item(i);
                System.out.println("   "+item.getNodeName()+":"+item.getNodeValue());
            }
        }
        if(!node.hasChildNodes()){
            return;
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            nodeInfo(childNode);

        }
    }


    private String getText(Node runNode) {
        Node textNode = getChildNode(runNode, "w:t");
        if (textNode == null) {
            return "";
        }
        return textNode.getFirstChild().getNodeValue();
    }

    private String getMath(XWPFRun run, Node runNode) throws Exception {
        Node objectNode = getChildNode(runNode, "w:object");
        if (objectNode == null) {
            return "";
        }
        Node shapeNode = getChildNode(objectNode, "v:shape");
        if (shapeNode == null) {
            return "";
        }

        Node imageNode = getChildNode(shapeNode, "v:imagedata");
        if (imageNode == null) {
            return "";
        }
        Node binNode = getChildNode(objectNode, "o:OLEObject");
        if (binNode == null) {
            return "";
        }

        XWPFDocument word = run.getDocument();

        NamedNodeMap shapeAttrs = shapeNode.getAttributes();
        // 图片在Word中显示的宽高
        String style = shapeAttrs.getNamedItem("style").getNodeValue();
        System.out.println("图片宽高：".concat(style));

        System.out.println("--------------");

        NamedNodeMap imageAttrs = imageNode.getAttributes();
        // 图片在Word中的ID
        String imageRid = imageAttrs.getNamedItem("r:id").getNodeValue();
        // 获取图片信息
        PackagePart imgPart = word.getPartById(imageRid);
        System.out.println("图片名称".concat(imgPart.getPartName().getName()));
        System.out.println(imgPart.getInputStream());

        System.out.println("--------------");

        NamedNodeMap binAttrs = binNode.getAttributes();
        // 公式二进制文件在Word中的ID
        String binRid = binAttrs.getNamedItem("r:id").getNodeValue();
        // 获取二进制文件
        PackagePart binPart = word.getPartById(binRid);
        System.out.println("二进制文件名称：".concat(binPart.getPartName().getName()));
        File binFile = new File("D://mt//"+binPart.getPartName().getName());
        File imageFile = new File("D://mt//"+imgPart.getPartName().getName());
        writeFile(binFile,binPart);
        writeFile(imageFile,imgPart);
        System.out.println(binPart.getInputStream());

        System.out.println("--------------");

        return "{公式#}";
    }


    private boolean writeFile(File binFile,PackagePart binPart){
        try {
            if (!binFile.exists()) {
                binFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(binFile);
            InputStream inputStream = binPart.getInputStream();
            if (inputStream != null & out != null) {
                int temp = 0;
                while ((temp = inputStream.read()) != -1) {
                    out.write(temp);
                }
            }
            inputStream.close();
            out.close();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    private Node getChildNode(Node node, String nodeName) {
        if (!node.hasChildNodes()) {
            return null;
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (nodeName.equals(childNode.getNodeName())) {
                return childNode;
            }
            childNode = getChildNode(childNode, nodeName);
            if (childNode != null) {
                return childNode;
            }
        }
        return null;
    }
}
