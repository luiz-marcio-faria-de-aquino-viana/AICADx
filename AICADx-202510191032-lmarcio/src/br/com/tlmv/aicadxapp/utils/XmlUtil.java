/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * XmlUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil 
{

	public static Node getAttrByName(Node nItem, String attrName)
	{
		NamedNodeMap map = nItem.getAttributes();

		Node nAttr = map.getNamedItem(attrName);
		return nAttr;
	}

	public static String getAttrAsStringByName(Node nItem, String attrName)
	{
		String strAttr = "";
		
		NamedNodeMap map = nItem.getAttributes();
		Node nAttr = map.getNamedItem(attrName);
		if(nAttr != null)
			strAttr = nAttr.getNodeValue();
		return strAttr;
	}

	public static int getAttrAsIntByName(Node nItem, String attrName)
	{
		int iAttr = -1;
		
		NamedNodeMap map = nItem.getAttributes();
		Node nAttr = map.getNamedItem(attrName);
		if(nAttr != null) {
			String strAttr = nAttr.getNodeValue();
			iAttr = StringUtil.safeInt(strAttr);
		}
		return iAttr;
	}

	public static long getAttrAsLongByName(Node nItem, String attrName)
	{
		long lAttr = -1L;
		
		NamedNodeMap map = nItem.getAttributes();
		Node nAttr = map.getNamedItem(attrName);
		if(nAttr != null) {
			String strAttr = nAttr.getNodeValue();
			lAttr = StringUtil.safeLng(strAttr);
		}
		return lAttr;
	}

	public static double getAttrAsDoubleByName(NumberFormat nf, Node nItem, String attrName)
	{
		double dAttr = 0.0;
		
		NamedNodeMap map = nItem.getAttributes();
		Node nAttr = map.getNamedItem(attrName);
		if(nAttr != null) {
			String strAttr = nAttr.getNodeValue();
			dAttr = StringUtil.safeDbl(nf, strAttr);
		}
		return dAttr;
	}
	
	public static Node getChildNodeByName(NodeList lsChild, String name)
	{
		for(int i = 0; i < lsChild.getLength(); i++)
		{
			Node nNode = lsChild.item(i);
			
			String tk = nNode.getNodeName();
			if( name.equalsIgnoreCase(tk) )
				return nNode;
		}
		return null;
	}

	public static Node getChildNodeByName(Node oNode, String name)
	{
		NodeList lsChild = oNode.getChildNodes();
		return getChildNodeByName(lsChild, name);
	}

	public static ArrayList<Node> getListChildNodeByName(NodeList lsChild, String name)
	{
		ArrayList<Node> lsResult = new ArrayList<Node>();
		
		for(int i = 0; i < lsChild.getLength(); i++)
		{
			Node nNode = lsChild.item(i);
			
			String tk = nNode.getNodeName();
			if( name.equalsIgnoreCase(tk) )
				lsResult.add(nNode);
		}
		return lsResult;
	}

	public static ArrayList<Node> getListChildNodeByName(Node oNode, String name)
	{
		if(oNode == null) return new ArrayList<Node>();
		
		NodeList lsChild = oNode.getChildNodes();
		return getListChildNodeByName(lsChild, name);
	}
	
	public static String getChildNodeValueByName(NodeList lsChild, String name, boolean withEncode, String defaultVal)
	{
		String result = "";

		try
		{
			for(int i = 0; i < lsChild.getLength(); i++)
			{
				Node nNode = lsChild.item(i);
				if( name.equalsIgnoreCase(nNode.getNodeName()) )
				{
					try
					{
						if( withEncode )
							result = EncodeUtil.decode(nNode.getFirstChild().getTextContent());
						else
							result = nNode.getFirstChild().getTextContent();
					}
					catch(Exception e)
					{
						if(defaultVal != null)
							result = defaultVal;
						else
							result = "";
					}
					
					
					return result;
				}
			}			
		}
		catch(Exception e)
		{
			
		}

		if(defaultVal != null)
			result = defaultVal;
		else
			result = "";
		
		return result;
	}

	public static String getChildNodeValueByName(Node oNode, String name, boolean withEncode, String defaultVal)
	{
		NodeList lsChild = oNode.getChildNodes();
		return getChildNodeValueByName(lsChild, name, withEncode, defaultVal);
	}
	
//	// Create XML Element
//	
//	public static String createXmlElem(String tagname, String val)
//	{
//		String strVal = EncodeUtil.encode(val);
//
//		String str = String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//	
//	public static String createXmlElem(String tagname, int val)
//	{
//		String strVal = EncodeUtil.encode(Integer.toString(val));
//
//		String str = String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//	
//	public static String createXmlElem(String tagname, long val)
//	{
//		String strVal = EncodeUtil.encode(Long.toString(val));
//
//		String str = String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//		
//	public static String createXmlElem(NumberFormat nf, String tagname, double val)
//	{
//		String strVal = EncodeUtil.encode(nf.format(val));
//
//		String str = String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//	
//	public static String createXmlElem(String tagname, boolean val)
//	{
//		String strVal = ( val ) ? "S" : "N";
//
//		String str = String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//	
//	public static String createXmlElem(DateFormat df, String tagname, Date val)
//	{
//		String strVal = EncodeUtil.encode(df.format(val));
//
//		String str = String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//	
//	// Create XML Element Without Encode
//	
//	public static String createXmlElemWithoutEncode(String tagname, String val)
//	{
//		String str = String.format("<%s>%s</%s>\n", tagname, val, tagname);
//		return str;
//	}
//	
//	public static String createXmlElemWithoutEncode(String tagname, int val)
//	{
//		String strVal = Integer.toString(val);
//	
//		String str = String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//	
//	public static String createXmlElemWithoutEncode(String tagname, long val)
//	{
//		String strVal = Long.toString(val);
//
//		String str = String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//	
//	public static String createXmlElemWithoutEncode(NumberFormat nf, String tagname, double val)
//	{
//		String strVal = nf.format(val);
//
//		String str = String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//	
//	public static String createXmlElemWithoutEncode(String tagname, boolean val)
//	{
//		String strVal = ( val ) ? "S" : "N";
//
//		String str = String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
//		return str;
//	}
//	
//	public static String createXmlElemWithoutEncode(DateFormat df, String tagname, Date val)
//	{
//		String strVal = df.format(val);
//
//		String str = String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
//		return str;
//	}
	
	// Create XML Element With Level
	
	public static String createXmlElem(String tagname, String val, int level)
	{
		String strVal = EncodeUtil.encode(val);

		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
		
		str += String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	public static String createXmlElem(String tagname, int val, int level)
	{
		String strVal = EncodeUtil.encode(Integer.toString(val));
		
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";

		str += String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	public static String createXmlElem(String tagname, long val, int level)
	{
		String strVal = EncodeUtil.encode(Long.toString(val));
		
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";		

		str += String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
		return str;
	}
		
	public static String createXmlElem(NumberFormat nf, String tagname, double val, int level)
	{
		String strVal = EncodeUtil.encode(nf.format(val));


		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
				
		str += String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	public static String createXmlElem(String tagname, boolean val, int level)
	{
		String strVal = ( val ) ? "S" : "N";
		
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";		

		str += String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	public static String createXmlElem(DateFormat df, String tagname, Date val, int level)
	{
		String strVal = EncodeUtil.encode(df.format(val));

		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
				
		str += String.format("<%s><![CDATA[%s]]></%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	// Create XML Element Without Encode
	
	public static String createXmlElemWithoutEncode(String tagname, String val, int level)
	{
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
				
		str += String.format("<%s>%s</%s>\n", tagname, val, tagname);
		return str;
	}
	
	public static String createXmlElemWithoutEncode(String tagname, int val, int level)
	{
		String strVal = Integer.toString(val);

		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
			
		str += String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	public static String createXmlElemWithoutEncode(String tagname, long val, int level)
	{
		String strVal = Long.toString(val);

		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
				
		str += String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	public static String createXmlElemWithoutEncode(NumberFormat nf, String tagname, double val, int level)
	{
		String strVal = nf.format(val);

		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
				
		str += String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	public static String createXmlElemWithoutEncode(String tagname, boolean val, int level)
	{
		String strVal = ( val ) ? "S" : "N";

		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
				
		str += String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	public static String createXmlElemWithoutEncode(DateFormat df, String tagname, Date val, int level)
	{
		String strVal = df.format(val);
		
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";		

		str += String.format("<%s>%s</%s>\n", tagname, strVal, tagname);
		return str;
	}
	
	// Create XML Element START/END Tags
	
	public static String createXmlElemStartEndTags(String tagname, String val, int level)
	{
		String strTab = "";
		for(int i = 0; i < level; i++)
			strTab += "\t";
				
		String str = String.format("%s<%s>\n%s%s</%s>\n", strTab, tagname, val, strTab, tagname);
		return str;
	}	
	
	// Create XML Document Element
	
	public static String createXmlDocElem(String tagname, String val)
	{
		String str = String.format("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<%s>\n%s</%s>\n", tagname, val, tagname);
		return str;
	}	
	
}
