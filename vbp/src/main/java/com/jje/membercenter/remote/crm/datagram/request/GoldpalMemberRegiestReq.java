package com.jje.membercenter.remote.crm.datagram.request;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.jje.membercenter.account.xsd.AccountActivationRequest.Body;
import com.jje.membercenter.remote.crm.datagram.response.GoldpalMemberRegiestRes;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
public class GoldpalMemberRegiestReq extends CrmRequest {

	
	public GoldpalMemberRegiestReq() {
		super(CrmTransCode.GOLDPAL_MEMBER_REGIEST);
		super.getHead().setSystype("JJ003");
		this.body = new RequestBody();
	}


	private RequestBody body;
	
	public RequestBody getBody() {
		
		return body;
	}


	public void setBody(RequestBody body) {
		this.body = body;
	}


	public GoldpalMemberRegiestRes send() throws Exception {
		return this.sendToType(GoldpalMemberRegiestRes.class);
	}
	
	public static class RequestBody extends Body{
		
		private Record record;

		public Record getRecord() {
			return record;
		}

		public void setRecord(Record record) {
			this.record = record;
		}
		
	}
	@XmlType(propOrder={"name","membtype","regidate","regichnl","membstat","rightflag","salesmanid","cddesp","retailer_id","listofcontact","listofmembercard"})
	public static class Record{
		private String name;
		private String membtype;
		private String regidate;
		private String regichnl;
		private String membstat;
		private String rightflag;
		private String salesmanid;
		private String cddesp;
		private String retailer_id;
		
		private Listofcontact listofcontact;
		private Listofmembercard listofmembercard;
		
		
		public Record() {
			
		}
		
		public Record(String name, String membtype,String regidate,String regichnl, String membstat,String rightflag,
				String salesmanid, String cddesp, String retailer_id,
				Listofcontact listofcontact, Listofmembercard listofmembercard) {
			super();
			this.rightflag=rightflag;
			this.regidate=regidate;
			this.regichnl=regichnl;
			this.name = name;
			this.membtype = membtype;
			this.membstat = membstat;
			this.salesmanid = salesmanid;
			this.cddesp = cddesp;
			this.retailer_id = retailer_id;
			this.listofcontact = listofcontact;
			this.listofmembercard = listofmembercard;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMembtype() {
			return membtype;
		}
		public void setMembtype(String membtype) {
			this.membtype = membtype;
		}
		public String getMembstat() {
			return membstat;
		}
		public void setMembstat(String membstat) {
			this.membstat = membstat;
		}
		public String getSalesmanid() {
			return salesmanid;
		}
		public void setSalesmanid(String salesmanid) {
			this.salesmanid = salesmanid;
		}
		public String getCddesp() {
			return cddesp;
		}
		public void setCddesp(String cddesp) {
			this.cddesp = cddesp;
		}
		public String getRetailer_id() {
			return retailer_id;
		}
		public void setRetailer_id(String retailer_id) {
			this.retailer_id = retailer_id;
		}
		public Listofcontact getListofcontact() {
			return listofcontact;
		}
		public void setListofcontact(Listofcontact listofcontact) {
			this.listofcontact = listofcontact;
		}
		public Listofmembercard getListofmembercard() {
			return listofmembercard;
		}
		public void setListofmembercard(Listofmembercard listofmembercard) {
			this.listofmembercard = listofmembercard;
		}
		public String getRegidate() {
			return regidate;
		}
		public void setRegidate(String regidate) {
			this.regidate = regidate;
		}
		public String getRegichnl() {
			return regichnl;
		}
		public void setRegichnl(String regichnl) {
			this.regichnl = regichnl;
		}

		@XmlElement(name="rightflag",defaultValue="")
		public String getRightflag() {
			return rightflag;
		}

		public void setRightflag(String rightflag) {
			this.rightflag = rightflag;
		}
		
		
		
	}
	
	
	public static class Listofmembercard{
		
		private List<Membercard> membercard=new ArrayList<Membercard>();

		public List<Membercard> getMembercard() {
			return membercard;
		}

		public void setMembercard(List<Membercard> membercard) {
			this.membercard = membercard;
		}
		
	}
	
	
	@XmlType(propOrder={"membcdid","membcdtype","memblevel","partnercard","startdate","enddate","membcdsour","membcdstat","accpatpflg"})
	public static class Membercard{	
		private String membcdid;
		private String membcdtype;
		private String memblevel;
		private String partnercard;
		private String startdate;
		private String enddate;
		private String membcdsour;
		private String membcdstat;
		private String accpatpflg;
		
		
		
		public Membercard() {
			super();
		}
		
		
		
		public Membercard(String membcdid, String membcdtype,String memblevel,
				String partnercard, String startdate, String enddate,String membcdsour,
				String membcdstat,String accpatpflg) {
			super();
			this.membcdid = membcdid;
			this.membcdtype = membcdtype;
			this.memblevel = memblevel;
			this.partnercard = partnercard;
			this.startdate = startdate;
			this.enddate = enddate;
			this.membcdsour = membcdsour;
			this.membcdstat=membcdstat;
			this.accpatpflg = accpatpflg;
		}

		public String getMemblevel() {
			return memblevel;
		}

		public void setMemblevel(String memblevel) {
			this.memblevel = memblevel;
		}

		public String getMembcdsour() {
			return membcdsour;
		}

		public void setMembcdsour(String membcdsour) {
			this.membcdsour = membcdsour;
		}

		public String getMembcdstat() {
			return membcdstat;
		}

		public void setMembcdstat(String membcdstat) {
			this.membcdstat = membcdstat;
		}

		public String getMembcdid() {
			return membcdid;
		}
		public void setMembcdid(String membcdid) {
			this.membcdid = membcdid;
		}
		public String getMembcdtype() {
			return membcdtype;
		}
		public void setMembcdtype(String membcdtype) {
			this.membcdtype = membcdtype;
		}
		public String getPartnercard() {
			return partnercard;
		}
		public void setPartnercard(String partnercard) {
			this.partnercard = partnercard;
		}
		public String getStartdate() {
			return startdate;
		}
		public void setStartdate(String startdate) {
			this.startdate = startdate;
		}
		public String getEnddate() {
			return enddate;
		}
		public void setEnddate(String enddate) {
			this.enddate = enddate;
		}
		public String getAccpatpflg() {
			return accpatpflg;
		}
		public void setAccpatpflg(String accpatpflg) {
			this.accpatpflg = accpatpflg;
		}
		
		
	}
	
	
	public static class Listofcontact {

		private List<Contact> contact = new ArrayList<Contact>();

		public List<Contact> getContact() {
			return contact;
		}

		public void setContact(List<Contact> contact) {
			this.contact = contact;
		}

	}

	@XmlType(propOrder={"conpriflag","sex","title","nation","cardtype","cardid","email","mobile","courcmp","postflg","listofpersonaladdress"})
	public static class Contact {
		private String conpriflag;
		private String sex;
		private String title;
		private String nation;
		private String cardtype;
		private String cardid;
		private String email;
		private String mobile;
		private String courcmp;
		private String postflg;
		
		private Listofpersonaladdress listofpersonaladdress;
		
		

		public Contact() {
		}

		public Contact(String conpriflag, String sex, String title,
				String nation, String cardtype, String cardid,
				String email, String mobile, String postflg,
				String courcmp, Listofpersonaladdress listofpersonaladdress) {
			this.conpriflag = conpriflag;
			this.sex = sex;
			this.title = title;
			this.nation = nation;
			this.cardtype = cardtype;
			this.cardid = cardid;
			this.email = email;
			this.mobile = mobile;
			this.postflg = postflg;
			this.courcmp = courcmp;
			this.listofpersonaladdress = listofpersonaladdress;
		}

		public String getConpriflag() {
			return conpriflag;
		}

		public void setConpriflag(String conpriflag) {
			this.conpriflag = conpriflag;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getNation() {
			return nation;
		}

		public void setNation(String nation) {
			this.nation = nation;
		}

		public String getCardtype() {
			return cardtype;
		}

		public void setCardtype(String cardtype) {
			this.cardtype = cardtype;
		}

		public String getCardid() {
			return cardid;
		}

		public void setCardid(String cardid) {
			this.cardid = cardid;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getPostflg() {
			return postflg;
		}

		public void setPostflg(String postflg) {
			this.postflg = postflg;
		}

		public String getCourcmp() {
			return courcmp;
		}

		public void setCourcmp(String courcmp) {
			this.courcmp = courcmp;
		}

		public Listofpersonaladdress getListofpersonaladdress() {
			return listofpersonaladdress;
		}

		public void setListofpersonaladdress(Listofpersonaladdress listofpersonaladdress) {
			this.listofpersonaladdress = listofpersonaladdress;
		}
	}
	
	public static class Listofpersonaladdress{
		private List<Personaladdress> personaladdress=new ArrayList<Personaladdress>();

		public List<Personaladdress> getPersonaladdress() {
			return personaladdress;
		}

		public void setPersonaladdress(List<Personaladdress> personaladdress) {
			this.personaladdress = personaladdress;
		}
		
	}
	
	@XmlType(propOrder={"addpriflag","addrtype","nation","province","city","area","streetaddr","postcode"})
	public static class Personaladdress {
		private String addpriflag;
		private String addrtype;
		private String province;
		private String city;
		private String area;
		private String streetaddr;
		private String postcode;
		private String nation;
		
		public Personaladdress() {
		}

		
		public Personaladdress(String addpriflag, String addrtype,
				String province, String city, String area, String streetaddr,
				String postcode,String nation) {
			super();
			this.addpriflag = addpriflag;
			this.addrtype = addrtype;
			this.province = province;
			this.city = city;
			this.area = area;
			this.streetaddr = streetaddr;
			this.postcode = postcode;
			this.nation = nation;
		}




		public String getAddpriflag() {
			return addpriflag;
		}

		public void setAddpriflag(String addpriflag) {
			this.addpriflag = addpriflag;
		}

		public String getAddrtype() {
			return addrtype;
		}

		public void setAddrtype(String addrtype) {
			this.addrtype = addrtype;
		}
	

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getStreetaddr() {
			return streetaddr;
		}

		public void setStreetaddr(String streetaddr) {
			this.streetaddr = streetaddr;
		}

		public String getPostcode() {
			return postcode;
		}

		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}


		public String getNation() {
			return nation;
		}


		public void setNation(String nation) {
			this.nation = nation;
		}
	}	

}
