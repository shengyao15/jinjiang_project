package com.jje.vbp.taobao.proxy;

import java.util.Date;

import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.utils.ObjectUtils;
import com.jje.dto.member.taobao.TaobaoNotifyDto;
import com.taobao.api.ApiException;
import com.taobao.api.AutoRetryTaobaoClient;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TripffaBindRequest;
import com.taobao.api.request.TripffaExchangeMileageRequest;
import com.taobao.api.response.TripffaBindResponse;
import com.taobao.api.response.TripffaExchangeMileageResponse;

@Component
public class TaobaoProxy {
    private Logger logger = LoggerFactory.getLogger(TaobaoProxy.class);

    TaobaoClient taobaoClient = null;
    
    @Value(value = "${taobao.server.url}")
    private String serverUrl;
    @Value(value = "${taobao.app.key}")
	private String appKey;
    @Value(value = "${taobao.app.secret}")
	private String appSecret;
    @Value(value = "${taobao.app.partnersId}")
    private String partnersId;

    public static void main(String[] args) throws ApiException {
/*        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest","1023012663","sandbox96a841554cfe2fe68b6871420");
        TripffaBindRequest request = new TripffaBindRequest();
        request.setCardNo("card0001");
        request.setGrade("v1");
        request.setPartnersId(123456l);
        request.setScore(1000L);
        request.setTaobaoId(3640178250l);
        request.setTimestamp(new Date().getTime());
        TripffaBindResponse response = client.execute(request);
        System.out.println(ObjectUtils.toMap(response));*/

    	//TaobaoClient client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest","1023011938","sandbox96a841554cfe2fe68b6871420");
    	//TaobaoClient client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest","1023012663","sandbox96a841554cfe2fe68b6871420");
    	TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest","23012663","68e4f5e96a841554cfe2fe68b6871420");
    	TripffaExchangeMileageRequest request = new TripffaExchangeMileageRequest();
    	request.setUserId(50183221l);  //3633856222l  2050424590l
    	request.setPartId(1034991800l);
    	request.setMileage(2000l);
    	request.setScore(2000l);
    	Date d = new Date();
    	request.setBizId(d.getTime());
    	TripffaExchangeMileageResponse response = client.execute(request);
    	System.out.println(response.isSuccess());
    	System.out.println(response.getSucc());
    	System.out.println(ObjectUtils.toMap(response));
    	
/*    	TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
    	TripffaExchangeMileageRequest req=new TripffaExchangeMileageRequest();
    	req.setUserId("974554406");
    	req.setMileage("10");
    	req.setPartId("1034991800");
    	req.setScore("10");
    	req.setBizId("111122223344");
    	TripffaExchangeMileageResponse response = client.execute(req , sessionKey);*/
    	
    	
    }

    public boolean notify(TaobaoNotifyDto notifyDto) {
        if(taobaoClient == null){
        	taobaoClient = new AutoRetryTaobaoClient(serverUrl,appKey,appSecret);
        }
    	
        TripffaBindRequest request = buildTripffaBindRequest(notifyDto);
        try {
            TripffaBindResponse response = taobaoClient.execute(request);
            if (response != null && response.getIsSuccess() != null && response.getIsSuccess()) {
                return true;
            }
            logger.error("send taobao bind error with request:{},response:{}", request,ObjectUtils.toMap(response));
        } catch (ApiException e) {
            logger.error("send taobao bind error with request:{}", request,e);
        }
        return false;
    }

    private TripffaBindRequest buildTripffaBindRequest(TaobaoNotifyDto notifyDto) {
        TripffaBindRequest request = new TripffaBindRequest();
        request.setCardNo(notifyDto.getCardNo());
        request.setGrade(notifyDto.getGrade());
        request.setPartnersId(Long.parseLong(partnersId));
        request.setScore(notifyDto.getScore());
        request.setTaobaoId(notifyDto.getTaobaoId());
        request.setTimestamp(new Date().getTime());
        
        return request;
    }

    
    public boolean taobaoAddPoints(String taobaoID, String points) {
    	if(taobaoClient == null){
        	taobaoClient = new AutoRetryTaobaoClient(serverUrl,appKey,appSecret);
        }
        TripffaExchangeMileageRequest request = buildExchangeMileageRequest(taobaoID, points);
        try {
        	TripffaExchangeMileageResponse response = taobaoClient.execute(request);
            if (response != null && response.getSucc()) {
                return true;
            }
            logger.error("taobaoAddPoints error request:{},response:{}", request,ObjectUtils.toMap(response));
        } catch (ApiException e) {
            logger.error("taobaoAddPoints error request:{}", request,e);
        }
        return false;
    }

	private TripffaExchangeMileageRequest buildExchangeMileageRequest(
			String taobaoID, String points) {
	 	TripffaExchangeMileageRequest request = new TripffaExchangeMileageRequest();
    	request.setUserId(Long.valueOf(taobaoID));  //3633856222l
    	request.setPartId(1034991800l);
    	request.setMileage(Long.valueOf(points));
    	request.setScore(Long.valueOf(points));
    	Date d = new Date();
    	request.setBizId(d.getTime());
		return request;
	}
}
