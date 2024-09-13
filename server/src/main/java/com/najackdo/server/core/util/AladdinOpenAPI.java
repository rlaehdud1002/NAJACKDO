package com.najackdo.server.core.util;

import java.util.*;
import com.najackdo.server.core.util.AladdinOpenAPIHandler;
import com.najackdo.server.domain.book.entity.Book;
import lombok.extern.slf4j.Slf4j;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.ParserAdapter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j

class AladdinOpenAPIHandler extends DefaultHandler {
    public List<BookData> Items;
    private BookData currentItem;
    private boolean inItemElement = false;
    private String tempValue;

    public AladdinOpenAPIHandler() {
        Items = new ArrayList<BookData>();
    }

    public void startElement(String namespace, String localName, String qName, Attributes atts) {
        if (localName.equals("item")) {
            currentItem = new BookData();
            inItemElement = true;
        } else if (localName.equals("title")) {
            tempValue = "";
        } else if (localName.equals("categoryName")) {
            tempValue = "";
        } else if (localName.equals("author")) {
            tempValue = "";
        } else if (localName.equals("description")) {
            tempValue = "";
        } else if (localName.equals("cover")) {
            tempValue = "";
        } else if (localName.equals("isbn13")) {
            tempValue = "";
        } else if (localName.equals("itemPage")) {
            tempValue = "";
        } else if (localName.equals("pubDate")) {
            tempValue = "";
        } else if (localName.equals("priceStandard")) {
            tempValue = "";
        } else if (localName.equals("adult")) {
            tempValue = "";
        } else if (localName.equals("publisher")) {
            tempValue = "";
        } else if (localName.equals("ratingScore")) {
            tempValue = "";
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        tempValue = tempValue + new String(ch, start, length);
    }
    public void endElement(String namespaceURI, String localName, String qName) {
        if (inItemElement) {
            if (localName.equals("item")) {
                Items.add(currentItem);
                currentItem = null;
                inItemElement = false;
            } else if (localName.equals("title")) {
                currentItem.setTitle(tempValue);
            } else if (localName.equals("categoryName")) {
                currentItem.setCategoryName(tempValue);
            } else if (localName.equals("author")) {
                currentItem.setAuthor(tempValue);
            } else if (localName.equals("description")) {
                currentItem.setDescription(tempValue);
            } else if (localName.equals("cover")) {
                currentItem.setCover(tempValue);
            } else if (localName.equals("isbn13")) {
                if(tempValue.length()>0) {
                    currentItem.setIsbn(Long.parseLong(tempValue));
                }
            } else if (localName.equals("itemPage")) {
                currentItem.setItemPage(Integer.parseInt(tempValue));
            } else if (localName.equals("pubDate")) {
                if(tempValue.length()>0) {
                    currentItem.setPubDate(LocalDate.parse(tempValue));
                }
            } else if (localName.equals("priceStandard")) {
                log.info(tempValue);
                if(tempValue.length()>0) {
                    currentItem.setPriceStandard(Integer.parseInt(tempValue));
                }
            } else if (localName.equals("adult")) {
                //log.info(tempValue);
                currentItem.setAdult(tempValue);
            } else if (localName.equals("publisher")) {
                //log.info(tempValue);
                currentItem.setPublisher(tempValue);
            } else if (localName.equals("ratingScore")) {
//                log.info(tempValue);
                if(tempValue.length()>0) {
                    currentItem.setStarPoint(Double.parseDouble(tempValue));
                }
            }
        }
    }

    public void parseXml(String xmlUrl) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        ParserAdapter pa = new ParserAdapter(sp.getParser());
        pa.setContentHandler(this);
        pa.parse(xmlUrl);
    }
}

@Slf4j
@Component
public class AladdinOpenAPI {

    public String GetUrl(String index,String BASE_URL,String Results) throws Exception {
        Map<String, String> hm = new HashMap<String, String>();
        hm.put("ttbkey", "ttbbeomsu46390952001");
        //hm.put("Query", URLEncoder.encode(searchWord, "UTF-8"));
        hm.put("QueryType", "Bestseller");
        hm.put("MaxResults", Results);
        hm.put("start", index);
        hm.put("SearchTarget", "Book");
        hm.put("output", "xml");
        hm.put("Version", "20131101");
        StringBuffer sb = new StringBuffer();
        Iterator<String> iter = hm.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String val = hm.get(key);
            sb.append(key).append("=").append(val).append("&");
        }

        return BASE_URL + sb.toString();
    }

    public String GetUrlDetail(String index,String BASE_URL,String searchISBN) throws Exception {
        Map<String, String> hm = new HashMap<String, String>();
        hm.put("ttbkey", "ttbbeomsu46390952001");
        hm.put("ItemId", searchISBN);
        hm.put("ItemIdType", "ISBN13");
        hm.put("Cover", "Big");
        hm.put("SearchTarget", "Book");
        hm.put("output", "xml");
        hm.put("Version", "20131101");
        hm.put("OptResult","ratingInfo,Story");
        StringBuffer sb = new StringBuffer();
        Iterator<String> iter = hm.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String val = hm.get(key);
            sb.append(key).append("=").append(val).append("&");
        }
        log.info(BASE_URL + sb.toString());
        return BASE_URL + sb.toString();
    }

    public List<Book> addBooks(int pages, int bookNum) throws Exception {
        List<Book> response = new ArrayList<Book>();
        String BASE_URL = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?";
        String BASE_URL_DETAIL = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?";
        for(int i=1;i<=pages;i++){
            String url = GetUrl(String.valueOf(i),BASE_URL, String.valueOf(bookNum));
            //System.out.println(url);
            AladdinOpenAPIHandler api = new AladdinOpenAPIHandler();
            api.parseXml(url);
            //System.out.println(api.Items);
            int count=1;
            for (BookData item : api.Items) {
                //System.out.println(item.toString());
                //System.out.println(count++);
                String urlDetail = GetUrlDetail(item.getTitle(),BASE_URL_DETAIL, String.valueOf(item.getIsbn()));
                AladdinOpenAPIHandler api_detail = new AladdinOpenAPIHandler();
                //System.out.println(api_detail);
                api_detail.parseXml(urlDetail);
                for (BookData item_detail : api_detail.Items) {
                    response.add(Book.BookfromBookData(item_detail));
                }
            }
        }
        return response;
    }

//    public static void main(String[] args) throws Exception {
//        List<BookData> list = addBooks(1,5);
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream("customer.txt");
//            for(BookData customer : list) {
//                out.write(customer.toString().getBytes());
//                out.write("\n".getBytes());
//            }
//            System.out.println("파일이 저장되었습니다. 프로젝트를 새로고침(F5)하세요.");
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try { out.close(); } catch (IOException e) { }
//        }
//    }
}