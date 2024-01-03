package com.nguyenhoangbaophuc.newsapp.RSS;

import android.util.Log;

import com.nguyenhoangbaophuc.newsapp.Model.Item;

import org.jsoup.Jsoup;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MySaxHandler extends DefaultHandler {
    public List<Item> list;
    private Item item;
    public String temp;
    // co de gap the bat dau: "<item>"
    private boolean flagStart = false;

    public MySaxHandler(){
        list = new ArrayList<>();
    }

    public List<Item> getItems(){
        return list;
    }

    // lay duoc cai bat dau tu the nao, lay gi, do dai bao nhieu,..
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if(flagStart){
            temp = new String(ch,start,length);
        }
    }

    // bat dau the
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(qName.equalsIgnoreCase("item")){
            item = new Item();
            flagStart = true;
        }
    }

    // ket thuc the
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equalsIgnoreCase("item")) {
            if (item != null) {
                list.add(item);
            }
        } else if (qName.equalsIgnoreCase("title")) {
            if (item != null) {
                item.setTitle(temp);
            }
        } else if (qName.equalsIgnoreCase("description")) {
            if (item != null) {
                String descriptionWithoutHtml = Jsoup.parse(temp.toString()).text();
                item.setDescription(descriptionWithoutHtml);
                if (temp != null && temp.contains("<img src=")) {
                    int startIndex = temp.indexOf("<img src=") + "<img src=".length() + 1;
                    int endIndex = temp.indexOf("\"", startIndex);

                    if (startIndex >= 0 && endIndex >= 0) {
                        String imageUrl = temp.substring(startIndex, endIndex);
                        item.setImageUrl(imageUrl);
                    }
                }
            }
        } else if (qName.equalsIgnoreCase("pubDate")) {
            if (item != null) {
                item.setPubDate(temp);
            }
        } else if (qName.equalsIgnoreCase("link")) {
            if (item != null) {
                item.setLink(temp);
            }
        }
    }
}
