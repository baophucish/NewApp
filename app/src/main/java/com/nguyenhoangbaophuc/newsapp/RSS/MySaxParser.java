package com.nguyenhoangbaophuc.newsapp.RSS;

import android.util.Log;

import com.nguyenhoangbaophuc.newsapp.Model.Item;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class MySaxParser {
    public static List<Item> xmlParser(InputStream is) {
        List<Item> list = null;
        try {
            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            MySaxHandler handler = new MySaxHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(is));
            list = handler.getItems();

        } catch (Exception e) {
            Log.e("Loi: ", e.getMessage());
        }
        return (list != null) ? list : new ArrayList<>(); // tranh tra ve null, thay the danh sach rong
    }
}
