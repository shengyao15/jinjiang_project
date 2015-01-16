package com.jje.membercenter.remote.crm.datagram.request;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.cardbind.PartnerCard;
import com.jje.dto.membercenter.cardbind.PartnerCardsListDto;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

@XmlRootElement(name = "response")
public class MemberPartnerCardRes extends CrmResponse {
	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public static class ResponseBody extends Body {
		Listofcard listofcard;

		public Listofcard getListofcard() {
			return listofcard;
		}

		public void setListofcard(Listofcard listofcard) {
			this.listofcard = listofcard;
		}

		public PartnerCardsListDto toPartnerCardsListDto(){
			PartnerCardsListDto dto = new PartnerCardsListDto();
			List<PartnerCard> list = new ArrayList<PartnerCard>();
			if(this.getListofcard()==null || this.getListofcard().getMembcd().size()==0){
				return dto;
			}
			for(Membcd m : this.getListofcard().getMembcd()){
				PartnerCard card = new PartnerCard();
				card.setPartnerCardNo(m.getMemcardno());
				card.setPartnerCode(MemberAirLineCompany.getMemberAirLineCompany(m.getMembcdtype()));
				if("Y".equals(m.getConvertflg())){
					card.setUse(true);
				}
				list.add(card);
			}
			dto.setCardList(list);
			return dto;
		}
		public static class Listofcard{
			List<Membcd> membcd;

			
			
			public List<Membcd> getMembcd() {
				return membcd;
			}



			public void setMembcd(List<Membcd> membcd) {
				this.membcd = membcd;
			}

		}

		public static class Membcd{
			String membcdstat;
			String convertflg;
			String name;
			String cardtier;
			String memcardno;
			String membcdtype;
			String startdate;
			String enddate;
			String membcdsour;
			String activeflg;
			public String getMembcdstat() {
				return membcdstat;
			}
			public void setMembcdstat(String membcdstat) {
				this.membcdstat = membcdstat;
			}
			public String getConvertflg() {
				return convertflg;
			}
			public void setConvertflg(String convertflg) {
				this.convertflg = convertflg;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getCardtier() {
				return cardtier;
			}
			public void setCardtier(String cardtier) {
				this.cardtier = cardtier;
			}
			public String getMemcardno() {
				return memcardno;
			}
			public void setMemcardno(String memcardno) {
				this.memcardno = memcardno;
			}
			public String getMembcdtype() {
				return membcdtype;
			}
			public void setMembcdtype(String membcdtype) {
				this.membcdtype = membcdtype;
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
			public String getMembcdsour() {
				return membcdsour;
			}
			public void setMembcdsour(String membcdsour) {
				this.membcdsour = membcdsour;
			}
			public String getActiveflg() {
				return activeflg;
			}
			public void setActiveflg(String activeflg) {
				this.activeflg = activeflg;
			}
		}

	}

}
