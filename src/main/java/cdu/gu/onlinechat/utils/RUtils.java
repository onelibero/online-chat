package cdu.gu.onlinechat.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class RUtils extends HashMap<String, Object> {

    public RUtils() {
        put("code", HttpStatus.SC_OK);
        put("msg", "success");
    }

    public RUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static RUtils ok() {
        return new RUtils();
    }

    public static RUtils ok(String msg) {
        RUtils r = new RUtils();
        r.put("msg", msg);
        return r;
    }

    public static RUtils ok(Map<String, Object> map) {
        RUtils r = new RUtils();
        r.putAll(map);
        return r;
    }

    public static RUtils error(int code, String msg) {
        RUtils r = new RUtils();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static RUtils error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static RUtils error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

}