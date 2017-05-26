package xml;

import java.util.List;

import model.LectureInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LectureInfoContentHandler extends DefaultHandler {
    private LectureInfo info;
    private List<LectureInfo> infos;
    private String tagName;
    
    
	public LectureInfoContentHandler(List<LectureInfo> infos) {
		super();
		this.infos = infos;
	}
	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp=new String(ch,start,length);
		if(tagName.equals("id")){
			info.setId(temp);
		}
		if(tagName.equals("title")){
			info.setTitle(temp);
		}
		if(tagName.equals("time")){
			info.setTime(temp);
		}
		if(tagName.equals("place")){
			info.setPlace(temp);
		}
		if(tagName.equals("master")){
			info.setMaster(temp);
		}
		if(tagName.equals("masterid")){
			info.setMasterid(temp);
		}
		if(tagName.equals("level")){
			info.setLevel(temp);
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
		if(qName.equals("lecture")){
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
		if(tagName.equals("lecture")){
			info=new LectureInfo();
		}
	}
	

}
