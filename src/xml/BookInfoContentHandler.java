package xml;

import java.util.List;

import model.BookInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BookInfoContentHandler extends DefaultHandler{
	
	private List<BookInfo> infos;
	private BookInfo bookInfo;
	private String tagName;
	
	public BookInfoContentHandler(List<BookInfo> infos){
		super();
		this.infos=infos;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp=new String(ch,start,length);
		if(tagName.equals("id")){
			bookInfo.setId(temp);
		}
		else if(tagName.equals("bookName")){
			bookInfo.setBookName(temp);
		}
		else if(tagName.equals("writer")){
			bookInfo.setWriter(temp);
		}
		else if(tagName.equals("publish")){
			bookInfo.setPublish(temp);
		}
		else if(tagName.equals("place")){
			bookInfo.setPlace(temp);
		}
		else if(tagName.equals("condition")){
			bookInfo.setCondition(temp);
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
		if(qName.equals("bookresource")){
			infos.add(bookInfo);
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
		if(tagName.equals("bookresource")){
			bookInfo=new BookInfo();
		}
	}
}
