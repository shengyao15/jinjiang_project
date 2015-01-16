package com.jje.membercenter.persistence;

import java.util.List;

import com.jje.membercenter.domain.BaseData;



public interface BaseDataMapper {

	List<BaseData> listMemberType();

	List<BaseData> listCertificateTypes();

	List<BaseData> listRemindQuestionTypes();

	List<BaseData> listAddressTypes();

	List<BaseData> listInvoiceTypes();

	List<BaseData> listEduTypes();
	
	List<BaseData> listTitleTypes();

	List<BaseData> listProvinceTypes();

	List<BaseData> listCityTypes(String parRowId);

	List<BaseData> listTownTypes(String parRowId);

	List<BaseData> getBaseDataById(BaseData basedata);

	List<BaseData> getPrice(String memberType);
	
	List<BaseData> listHierarchyTypes();
	
	String getCardLevel(String name);

}