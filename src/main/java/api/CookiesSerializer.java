package main.java.api;

import com.google.appengine.labs.repackaged.com.google.common.io.BaseEncoding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Map;

public final class CookiesSerializer {
    public static String serialize(Map<String, String> raw) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(raw);
            so.flush();
            return BaseEncoding.base64().encode(bo.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Map<String, String> deserialize(String serializedBase64) {
        try {
            byte b[] = BaseEncoding.base64().decode(serializedBase64);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            return (Map<String, String>) si.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }
}
