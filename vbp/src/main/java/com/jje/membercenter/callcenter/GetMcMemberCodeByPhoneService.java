package com.jje.membercenter.callcenter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://siebel.com/asi/", name = "GetMcMemberCodeByPhoneService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface GetMcMemberCodeByPhoneService {

    @WebResult(name = "strMemberNumber", targetNamespace = "http://siebel.com/asi/", partName = "strMemberNumber")
    @WebMethod(operationName = "QueryForCC", action = "rpc/http://siebel.com/asi/:QueryForCC")
    public java.lang.String queryForCC(
        @WebParam(partName = "strCell", name = "strCell")
        java.lang.String strCell
    );
}
