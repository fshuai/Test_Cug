package xml;

import java.util.List;

import model.NewsInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NewsInfoContentHandler extends DefaultHandler{
	private List<NewsInfo> infos;
	private NewsInfo newsInfo;
	private String tagName;
	
	public NewsInfoContentHandler(List<NewsInfo> infos) {
		super();
		this.infos = infos;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp=new String(ch,start,length);
		if(tagName.equals("title")){
			newsInfo.setTitle(temp);
		}
		if(tagName.equals("time")){
			newsInfo.setTime(temp);
		}
		if(tagName.equals("id")){
			newsInfo.setId(temp);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("item")){
			infos.add(newsInfo);
			//newsInfo=null;
		}
		tagName="";
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.tagName=localName;
		if(tagName.equals("item")){
			newsInfo=new NewsInfo();
		}
	}

}
