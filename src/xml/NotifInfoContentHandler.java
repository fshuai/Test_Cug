package xml;

import java.util.List;

import model.NotificationInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NotifInfoContentHandler extends DefaultHandler{
    private List<NotificationInfo> infos;
    private NotificationInfo info;
    private String tagName;
    
    
	
	public NotifInfoContentHandler(List<NotificationInfo> infos) {
		super();
		this.infos = infos;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp=new String(ch,start,length);
		if(tagName.equals("college")){
			info.setCollege(temp);
		}
		else if(tagName.equals("title")){
			info.setTitle(temp);
		}
		else if(tagName.equals("time")){
			info.setTime(temp);
		}
		else if(tagName.equals("id")){
			info.setId(temp);
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
		if(qName.equals("notification")){
			infos.add(info);
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
		if(tagName.equals("notification")){
			info=new NotificationInfo();
			
		}
	}
}
