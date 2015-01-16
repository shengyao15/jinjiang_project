package com.jje.membercenter.template;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.membercenter.ValidateNumCacheDto;
import com.jje.membercenter.persistence.cache.PwdValidateNumCache;

@Component
public class URLUtils {
	
	@Autowired
    private PwdValidateNumCache pValidateNumCache;
	
	public String buildAutoLoginUrl(String mcMemberCode, String email, String flamingoRedirectUrl) throws Exception {
		String ticket = UUID.randomUUID().toString();
		String code = EncryptionUtil.encryptDES(mcMemberCode + "---" + email);
        ValidateNumCacheDto cacheDto = new ValidateNumCacheDto();
        cacheDto.setMemberCode(mcMemberCode + "---" + email);
        cacheDto.setValidateNum(ticket);
        pValidateNumCache.putValueToCache(cacheDto.getValidateNum(), cacheDto.getMemberCode());
        String classBackUrl = addParameter(flamingoRedirectUrl, "vc", encodeUrl(ticket + "-" + code));
        return classBackUrl;
	}
	
	public String decodeAutoLoginUrl(String vc) throws Exception {
		@SuppressWarnings("deprecation")
		String validateNum = URLDecoder.decode(vc);
		String ticketStr = validateNum.substring(0,validateNum.lastIndexOf("-"));
		String code = validateNum.substring(validateNum.lastIndexOf("-") + 1);
		String memberCode = EncryptionUtil.decryptDES(code);
		String ticket = pValidateNumCache.getValueFromCache(memberCode);

		if (!ticketStr.equals(ticket)) {
			throw new Exception("验证不通过");
		}
		return memberCode.split("---")[0];
	}
	
	public static String buildLoginUrl(String baseUrl,String flamingoUrl, String desUrl) throws UnsupportedEncodingException {
		return addParameter(baseUrl, "redirectUrl" ,flamingoUrl + desUrl);
	}
	
	public static String addParameter(String URL, String name, String value)throws UnsupportedEncodingException {
		int qpos = URL.indexOf('?');
		int hpos = URL.indexOf('#');
		char sep = qpos == -1 ? '?' : '&';
		String seg = sep + encodeUrl(name) + '=' + encodeUrl(value);
		return hpos == -1 ? (URL + seg) : (URL.substring(0, hpos) + seg + URL.substring(hpos));
	}

	public static String encodeUrl(String url)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(url, "UTF-8");

	}

	public static String appendHttpPre(String url) {
		if (StringUtils.isEmpty(url))
			return "";
		boolean flag = false;
		flag = url.startsWith("http://") || url.startsWith("https://")
				|| url.startsWith("HTTP://") || url.startsWith("HTTPS://");
		if (flag) {
			return url;
		} else {
			return "http://" + url;
		}
	}
}
