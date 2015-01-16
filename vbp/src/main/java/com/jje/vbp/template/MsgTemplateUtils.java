package com.jje.vbp.template;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.utils.DateUtils;
import com.jje.membercenter.template.URLUtils;
import com.jje.vbp.regist.domain.WebMemberInfo;

@Component
public class MsgTemplateUtils {
	
	@Value(value = "${flamingo.url}")
	private String flamingoUrl;
	
	@Value(value = "${vbp.template.path}")
	private String templatePath;
	
	@Value(value = "${static.resource.url}/cms/mms/email/register/")
	private String resourceUrl;
	
	@Autowired
	private URLUtils urlUtils;
	
	private static final String REDIRECT_URL = "/redirect/";
	
	public String merge(String templateName, Map<String, Object> msgArgs) {
		init(msgArgs);
		try {
			initForRegister(msgArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return renderTemplate(templateName, msgArgs);
	}
	
	private void init(Map<String, Object> tools){
		tools.put("date", DateUtils.class);
		tools.put("resourceUrl", resourceUrl);
		tools.put("stringUtils", StringUtils.class);
		tools.put("flamingoUrl", flamingoUrl);
	}
		
	private void initForRegister(Map<String, Object> msgArgs) throws Exception {
		Object obj =  msgArgs.get("webMember");
		if (obj != null) {
			WebMemberInfo member = (WebMemberInfo) obj;
			String flamingoRedirectUrl = null;
			flamingoRedirectUrl = urlUtils.buildAutoLoginUrl(member.getMcMemberCode(), member.getEmail(), flamingoUrl + REDIRECT_URL);
			msgArgs.put("flamingoRedirectUrl", flamingoRedirectUrl);
			msgArgs.put("urlUtils", URLUtils.class);
		}
	}
	
	private String renderTemplate(String templateName, Map<String, Object> arguments) {
		Properties p = new Properties();
		p.setProperty("resource.loader", "class");
		p.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		VelocityEngine ve = new VelocityEngine();
		ve.init(p);
		Template template = ve.getTemplate(templatePath + templateName, "UTF-8");
		String result = null;
		VelocityContext context = new VelocityContext();
		for (String key : arguments.keySet()) {
			context.put(key, arguments.get(key));
		}
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		result = writer.toString();
		return result;
	}	
	
	public static String buildAutoLoginUrl(String url) {
		return null;
	}
}
