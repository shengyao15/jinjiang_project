package com.jje.data.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	public static String objectToJson(Object obj)  {
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter writer = new StringWriter();
			JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
			mapper.writeValue(gen, obj);
			gen.close();
			String json = writer.toString();
			writer.close();
			return json;
		} catch (Exception e) {
			logger.error("objectToJson({}) error!", obj, e);
		}
		return null;
	}
	
	public static <T> T jsonToObject(String objStr, Class<T> type)  {
		try {
			ObjectMapper mapper = new ObjectMapper();
			T t = mapper.readValue(objStr, type);
			return t;
		} catch (Exception e) {
			logger.error(String.format("jsonToObject(%s, %s) error!", objStr, type), e);
		}
		return null;
	}
	
	public static Map jsonToMap(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}
	
}
