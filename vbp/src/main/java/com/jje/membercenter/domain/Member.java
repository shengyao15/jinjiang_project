package com.jje.membercenter.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

import com.jje.dto.membercenter.MemType;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.MemberVerfyDto;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.membercenter.buyCard.BuyCardRequest;
import com.jje.dto.membercenter.loginMerge.MemberMergeDto;

public class Member {
	
	private static final String channelSeparator = "|";

	private long id;
	
	private String memberID;//CRM会员ID

	private String userName; 

	private String fullName;
    @Deprecated
	private String memberCode;//会员编号
	
	private String activateCode;

	private String cardNo;

	private String cardType;

	private String cardLevel;

	private String memberType;

	private String memberHierarchy;

	private String registerSource;

    private String registTag;

	private Date registerDate;

	private String remindQuestion;

	private String remindAnswer;

	private String status;

	private String password;

	private String cellPhone;

	private String email;
	
	private String title;
	
	private String identityType;
	
	private String identityNo;

	private List<MemberMemCard> cardList = new ArrayList<MemberMemCard>();
	
	private List<MemberVerfy> memberVerfyList;
	
	private Date lastUpd;
	
	private boolean isWebMember;
	
	private String mcMemberCode;
	
	private String memType;
	
	private String ipAddress;
	
	private Date activeDate;
	
	private String activeChannel;

    private String activeTag;
	
	//合作类型
    private String thirdpartyType;

    private String mCustomerId;

    //渠道
    private Set<String> channels = new HashSet<String>();
    
    //新会员层级 add by Carter
    private String newMemberHierarchy;

    // 酒店渠道
    private String hotelChannel;
    
    
		public Member() {
		super();
	}

	public Member(MemberDto memberDto) {
		super();
		this.id = memberDto.getId();
		this.memberID = memberDto.getMemberID();
		this.userName = memberDto.getUserName();
		this.fullName = memberDto.getFullName();
		this.memberCode = memberDto.getMemberCode();
		this.activateCode = memberDto.getActivateCode();
		this.cardNo = memberDto.getCardNo();
		this.cardType = memberDto.getCardType();
		this.cardLevel = memberDto.getCardLevel();
		this.memberType = memberDto.getMemberType();
		this.memberHierarchy = memberDto.getMemberHierarchy();
		this.newMemberHierarchy = memberDto.getNewMemberHierarchy();
		this.registerSource = memberDto.getRegisterSource();
		this.registerDate = memberDto.getRegisterDate();
		this.remindQuestion = memberDto.getRemindQuestion();
		this.remindAnswer = memberDto.getRemindAnswer();
		this.status = memberDto.getStatus();
		this.password = memberDto.getPassword();
		this.cellPhone = memberDto.getCellPhone();
		this.email = memberDto.getEmail();
		this.title = memberDto.getTitle();
		this.identityNo = memberDto.getIdentityNo();
		this.identityType = memberDto.getIdentityType();
		this.lastUpd = memberDto.getLastUpd();
		this.mcMemberCode=memberDto.getMcMemberCode();
		this.memType=memberDto.getMemType();
		this.ipAddress=memberDto.getIpAddress();
		this.activeDate = memberDto.getActiveDate();
		this.activeChannel = memberDto.getActiveChannel();
		this.thirdpartyType = memberDto.getThirdpartyType();
        this.registTag = memberDto.getRegisterTag();
        this.activeTag = memberDto.getActiveTag();
        if(memberDto.getCardList() != null && !memberDto.getCardList().isEmpty()){
           for(MemberMemCardDto cardDto : memberDto.getCardList()){
               this.cardList.add(new MemberMemCard(cardDto));
           }
        }
	}
	
	public Member(String memberID, String userName, String fullName,
			String memberCode, String cardNo, String cardType,
			String cardLevel, String memberType, String memberHierarchy,
			String registerSource, Date registerDate, String remindQuestion,
			String remindAnswer, String status, String password, String cellPhone, String email,String title,String identityType,String identityNo,String activateCode,String mcMemberCode,String memType,String ipAddress) {
		super();
		this.memberID = memberID;
		this.userName = userName;
		this.fullName = fullName;
		this.memberCode = memberCode;
		this.cardNo = cardNo;
		this.cardType = cardType;
		this.cardLevel = cardLevel;
		this.memberType = memberType;
		this.memberHierarchy = memberHierarchy;
		this.registerSource = registerSource;
		this.registerDate = registerDate;
		this.remindQuestion = remindQuestion;
		this.remindAnswer = remindAnswer;
		this.status = status;
		this.password = password;
		this.cellPhone = cellPhone;
		this.email = email;
		this.title = title;
		this.identityNo = identityNo;
		this.identityType = identityType;
		this.activateCode = activateCode;
		this.memType=memType;
		this.mcMemberCode=mcMemberCode;
		this.ipAddress=ipAddress;
	}
    
     public Member(WebMember webMember) {
        this();
        this.id = webMember.getId();
        this.memType = webMember.getMemType();
        this.memberID = null;
		this.userName = webMember.getUserName();
		this.fullName = webMember.getPhone();
		this.memberCode = webMember.getMemberCode();
		this.cardNo = webMember.getTempCardNo();
		this.cardType = null;
		this.cardLevel = null;
		this.memberType = null;
		this.memberHierarchy = null;
		this.registerSource = null;
		this.registerDate = webMember.getRegistTime();
		this.remindQuestion = webMember.getQuestion();
		this.remindAnswer = webMember.getAnswer();
		this.status = null;
		this.password = webMember.getPwd();
		this.cellPhone = webMember.getPhone();
		this.email = webMember.getEmail();
		this.title = null;
		this.identityNo = webMember.getIdentityNo();
		this.identityType = webMember.getIdentityType();
		this.activateCode = webMember.getActivityCode();
		this.mcMemberCode=webMember.getMcMemberCode();
		this.memType=webMember.getMemType();
		this.ipAddress=webMember.getIpAddress();
		
    }
    
	public Member(AppMember tempmember,BuyCardRequest buyCardRequest) {
		this.setCardLevel(tempmember.getCardLevel());
		this.setCardList(tempmember.getCardList());
		this.setEmail(tempmember.getEmail());
		this.setFullName(tempmember.getFullName());
		this.setMcMemberCode(tempmember.getMcMemberCode());
		this.setMemberID(tempmember.getMemberId());
		this.setMemberType(tempmember.getMemType());
		this.setCellPhone(tempmember.getPhone());
		this.setIdentityType(buyCardRequest.getCertificateType());
		this.setIdentityNo(buyCardRequest.getCertificateNo());
		this.setUserName(buyCardRequest.getUserName());
		this.setCardType("JJ Card");	//转正成礼卡用户
		this.setFullName(buyCardRequest.getUserName());
        this.setRegisterTag(buyCardRequest.getActiveTag());
        this.setRegisterSource(buyCardRequest.getChannel());
	}

	public Member(String identityType, String identityNo, String fullName) {
	   this.identityType=identityType;
	   this.identityNo=identityNo;
	   this.fullName=fullName;
	}

    public String getActiveTag() {
        return activeTag;
    }

    public void setActiveTag(String activeTag) {
        this.activeTag = activeTag;
    }

    public boolean getIsWebMember() {
//		if (StringUtils.isNotEmpty(memType)) {
//			isWebMember= MemType.valueOf(memberType).equals(MemType.QUICK_REGIST);
//		}
//		return isWebMember;
    	return MemType.QUICK_REGIST.getTypeCode().equals(memberType);
	}

	public String getChannels() {
		return com.jje.common.utils.StringUtils.join(channels, channelSeparator);
	}

    public String getRegisterTag() {
        return registTag;
    }

    public void setRegisterTag(String registTag) {
        this.registTag = registTag;
    }

    public void setChannels(String channels) {
		this.channels = new HashSet<String>();
		addChannels(channels);
	}

	public void addChannels(String channels) {
		String[] split = com.jje.common.utils.StringUtils.split(channels, channelSeparator);
		if (split == null || split.length == 0) {
			return;
		}
		for (String channel : split) {
			if(StringUtils.isBlank(channel))continue;
			this.channels.add(channel.trim());
		}
	}
	
	public void addChannels(Collection<String> channels) {
		if(channels == null || channels.isEmpty()){
			return;
		}
		for (String channel : channels) {
			if(StringUtils.isBlank(channel))continue;
			this.channels.add(channel.trim());
		}
	}

    public String getUserName() {
		if (userName == null || "".equals(userName)) {
			if (this.getMemberVerfyList() != null && this.getMemberVerfyList().size() >0) {
				return this.getMemberVerfyList().get(0).getMenName();
			}
		}
		return userName;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
    @Deprecated
	public String getMemberCode() {
		return memberCode;
	}
    @Deprecated
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberHierarchy() {
		return memberHierarchy;
	}

	public void setMemberHierarchy(String memberHierarchy) {
		this.memberHierarchy = memberHierarchy;
	}

	public String getRegisterSource() {
		return registerSource;
	}

	public void setRegisterSource(String registerSource) {
		this.registerSource = registerSource;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getRemindQuestion() {
		return remindQuestion;
	}

	public void setRemindQuestion(String remindQuestion) {
		this.remindQuestion = remindQuestion;
	}

	public String getRemindAnswer() {
		return remindAnswer;
	}

	public void setRemindAnswer(String remindAnswer) {
		this.remindAnswer = remindAnswer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		if (password == null || "".equals(password)) {
			if (this.getMemberVerfyList() != null && this.getMemberVerfyList().size() >0) {
				return this.getMemberVerfyList().get(0).getPassword();
			}
		}
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean validate(String password) {
		return this.getPassword().equals(password);
	}

    public String getRegistTag() {
        return registTag;
    }

    public void setRegistTag(String registTag) {
        this.registTag = registTag;
    }

    public MemberDto toDto() {
		MemberDto memberDto = new MemberDto(memberID, userName, fullName,
				memberCode, cardNo, cardType, cardLevel, memberType,
				memberHierarchy, newMemberHierarchy,registerSource, registerDate, remindQuestion,
				remindAnswer, status, getPassword(), cellPhone, email,title,identityType,identityNo,activateCode,mcMemberCode,memType,ipAddress);
		memberDto.setId(id);
		memberDto.setLastUpd(this.getLastUpd());
		memberDto.setIsWebMember(this.getIsWebMember());
		// 取得最高的卡级别和实体卡号
		String maxCardType = null;
		String maxEentityCardNo = null;
		Date validateDate = null;
		Date dueDate = null;
		String cardStatus = null;
		boolean isFind = false;
		// 设置最高权益卡信息
		if(this.getCardList()!=null){
			List<MemberMemCardDto> cardDtoList = new ArrayList<MemberMemCardDto>();
            for (MemberMemCard card : this.getCardList()) {
                cardDtoList.add(card.toDto());
                if ("Silver".equals(card.getCardTypeCd())
                        || "Gold".equals(card.getCardTypeCd())
                        || "Platinum".equals(card.getCardTypeCd())
                        || "Black".equals(card.getCardTypeCd())) {
                    // 初始化数据
                    if (maxCardType == null) {
                        maxCardType = card.getCardTypeCd();
                        maxEentityCardNo = card.getxCardNum();
                        validateDate = card.getValidDate();
                        dueDate = card.getDueDate();
                        cardStatus = card.getStatus();
                        continue;
                    }
                    // 还没 找到 最高 级别卡信息
                    if (!isFind) {
                        // 卡级别有更高的
                        if (isCardTypeHigher(maxCardType, card.getCardTypeCd())) {
                            // 更新卡级别
                            maxCardType = card.getCardTypeCd();
                            maxEentityCardNo = card.getxCardNum();
                            validateDate = card.getValidDate();
                            dueDate = card.getDueDate();
                            cardStatus = card.getStatus();
                            if (cardNo.equals(card.getxCardNum())) {
                                isFind = true;
                            }
                            // 卡级别一样
                        } else if (maxCardType.equals(card.getCardTypeCd())) {
                            // 如果是电商的享计划卡
                            if (cardNo.equals(card.getxCardNum()) && isCardTypeHigher("Silver", card.getCardTypeCd())) {
                                // 更新卡信息
                                maxCardType = card.getCardTypeCd();
                                maxEentityCardNo = card.getxCardNum();
                                validateDate = card.getValidDate();
                                dueDate = card.getDueDate();
                                cardStatus = card.getStatus();
                                isFind = true;
                            } else {
                                // 如果有效期更久
                                if (dueDate != null && card.getDueDate() != null && card.getDueDate().after(dueDate)) {
                                    // 更新卡信息
                                    maxCardType = card.getCardTypeCd();
                                    maxEentityCardNo = card.getxCardNum();
                                    validateDate = card.getValidDate();
                                    dueDate = card.getDueDate();
                                    cardStatus = card.getStatus();
                                }
                            }
                        }
                    }
                }
            }
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			memberDto.setCardType(maxCardType);
			memberDto.setEntityCardNo(maxEentityCardNo);
			if (validateDate != null) {
				memberDto.setValidStartDate(df.format(validateDate));
			}
			if (dueDate != null) {
				memberDto.setValidEndDate(df.format(dueDate));
			}
			memberDto.setCardStatus(cardStatus);
			memberDto.setCardList(cardDtoList);
			//合作类型
			memberDto.setThirdpartyType(this.thirdpartyType);
		}
		if(this.getMemberVerfyList()!=null) {
			List<MemberVerfyDto> verfyDtoList = new ArrayList<MemberVerfyDto>();
			for(MemberVerfy verfy:this.getMemberVerfyList()){
				verfyDtoList.add(verfy.toDto());
			}
			memberDto.setMemberVerfyList(verfyDtoList);
		}

		memberDto.setChannels(getChannels());
        memberDto.setActiveTag(activeTag);
        memberDto.setmCustomerId(mCustomerId);
		return memberDto;
	}
	
    public MemberMergeDto toMergeDto() {
    	MemberMergeDto memberDto = new MemberMergeDto(memberID, userName, fullName,
				memberCode, cardNo, cardType, cardLevel, memberType,
				memberHierarchy, newMemberHierarchy,registerSource, registerDate, remindQuestion,
				remindAnswer, status, getPassword(), cellPhone, email,title,identityType,identityNo,activateCode,mcMemberCode,memType,ipAddress);
		memberDto.setId(id);
		memberDto.setLastUpd(this.getLastUpd());
		memberDto.setIsWebMember(this.getIsWebMember());
		// 取得最高的卡级别和实体卡号
		String maxCardType = null;
		String maxEentityCardNo = null;
		Date validateDate = null;
		Date dueDate = null;
		String cardStatus = null;
		boolean isFind = false;
		// 设置最高权益卡信息
		if(this.getCardList()!=null){
			List<MemberMemCardDto> cardDtoList = new ArrayList<MemberMemCardDto>();
            for (MemberMemCard card : this.getCardList()) {
                cardDtoList.add(card.toDto());
                if ("Silver".equals(card.getCardTypeCd())
                        || "Gold".equals(card.getCardTypeCd())
                        || "Platinum".equals(card.getCardTypeCd())
                        || "Black".equals(card.getCardTypeCd())) {
                    // 初始化数据
                    if (maxCardType == null) {
                        maxCardType = card.getCardTypeCd();
                        maxEentityCardNo = card.getxCardNum();
                        validateDate = card.getValidDate();
                        dueDate = card.getDueDate();
                        cardStatus = card.getStatus();
                        continue;
                    }
                    // 还没 找到 最高 级别卡信息
                    if (!isFind) {
                        // 卡级别有更高的
                        if (isCardTypeHigher(maxCardType, card.getCardTypeCd())) {
                            // 更新卡级别
                            maxCardType = card.getCardTypeCd();
                            maxEentityCardNo = card.getxCardNum();
                            validateDate = card.getValidDate();
                            dueDate = card.getDueDate();
                            cardStatus = card.getStatus();
                            if (cardNo.equals(card.getxCardNum())) {
                                isFind = true;
                            }
                            // 卡级别一样
                        } else if (maxCardType.equals(card.getCardTypeCd())) {
                            // 如果是电商的享计划卡
                            if (cardNo.equals(card.getxCardNum()) && isCardTypeHigher("Silver", card.getCardTypeCd())) {
                                // 更新卡信息
                                maxCardType = card.getCardTypeCd();
                                maxEentityCardNo = card.getxCardNum();
                                validateDate = card.getValidDate();
                                dueDate = card.getDueDate();
                                cardStatus = card.getStatus();
                                isFind = true;
                            } else {
                                // 如果有效期更久
                                if (dueDate != null && card.getDueDate() != null && card.getDueDate().after(dueDate)) {
                                    // 更新卡信息
                                    maxCardType = card.getCardTypeCd();
                                    maxEentityCardNo = card.getxCardNum();
                                    validateDate = card.getValidDate();
                                    dueDate = card.getDueDate();
                                    cardStatus = card.getStatus();
                                }
                            }
                        }
                    }
                }
            }
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			memberDto.setCardType(maxCardType);
			memberDto.setEntityCardNo(maxEentityCardNo);
			if (validateDate != null) {
				memberDto.setValidStartDate(df.format(validateDate));
			}
			if (dueDate != null) {
				memberDto.setValidEndDate(df.format(dueDate));
			}
			memberDto.setCardStatus(cardStatus);
			memberDto.setCardList(cardDtoList);
			//合作类型
			memberDto.setThirdpartyType(this.thirdpartyType);
		}
		if(this.getMemberVerfyList()!=null) {
			List<MemberVerfyDto> verfyDtoList = new ArrayList<MemberVerfyDto>();
			for(MemberVerfy verfy:this.getMemberVerfyList()){
				verfyDtoList.add(verfy.toDto());
			}
			memberDto.setMemberVerfyList(verfyDtoList);
		}

		memberDto.setChannels(getChannels());
        memberDto.setActiveTag(activeTag);
        memberDto.setmCustomerId(mCustomerId);
		return memberDto;
	}
	// 判断后者的卡级别是否比当前级别高
	public boolean isCardTypeHigher(String currentType,String otherType) {
        //TODO:是否应排除非电商的卡？
		if (NewCardType.SILVER_CARD.getCode().equals(currentType)) {
			if (NewCardType.SILVER_CARD.getCode().equals(otherType)) {
				return false;
			}else{
				return true;
			}
		} else if(NewCardType.GOLD_CARD.getCode().equals(currentType)){
			if (NewCardType.SILVER_CARD.getCode().equals(otherType)) {
				return false;
			} else if (NewCardType.GOLD_CARD.getCode().equals(otherType)) {
				return true;
			} else if (NewCardType.PLATINUM_CARD.getCode().equals(otherType)) {
				return true;
			} else if (NewCardType.BLACK_CARD.getCode().equals(otherType)) {
				return true;
			} 
		} else if(NewCardType.PLATINUM_CARD.getCode().equals(currentType)){
			if (NewCardType.SILVER_CARD.getCode().equals(otherType)) {
				return false;
			} else if (NewCardType.GOLD_CARD.getCode().equals(otherType)) {
				return false;
			} else if (NewCardType.PLATINUM_CARD.getCode().equals(otherType)) {
				return true;
			} else if (NewCardType.BLACK_CARD.getCode().equals(otherType)) {
				return true;
			} 
		} else if (NewCardType.BLACK_CARD.getCode().equals(currentType)) {
			return false;
		}
		return false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MemberMemCard> getCardList() {
		return cardList;
	}

	public void setCardList(List<MemberMemCard> cardList) {
		this.cardList = cardList;
	}

	public List<MemberVerfy> getMemberVerfyList() {
		return memberVerfyList;
	}

	public void setMemberVerfyList(List<MemberVerfy> memberVerfyList) {
		this.memberVerfyList = memberVerfyList;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityNo() {
		return StringUtils.upperCase(identityNo);
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public Date getLastUpd() {
		return lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}

	public String getActivateCode() {
		return activateCode;
	}

	public void setActivateCode(String activateCode) {
		this.activateCode = activateCode;
	}


	public void setIsWebMember(boolean isWebMember) {
		this.isWebMember = isWebMember;
	}

	public String getMcMemberCode() {
		return mcMemberCode;
	}

	public void setMcMemberCode(String mcMemberCode) {
		this.mcMemberCode = mcMemberCode;
	}

	public String getMemType() {
		return memType;
	}

	public void setMemType(String memType) {
		this.memType = memType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public String getActiveChannel() {
		return activeChannel;
	}

	public void setActiveChannel(String activeChannel) {
		this.activeChannel = activeChannel;
	}

	public String getThirdpartyType() {
		return thirdpartyType;
	}

	public void setThirdpartyType(String thirdpartyType) {
		this.thirdpartyType = thirdpartyType;
	}

    public String getmCustomerId() {
        return mCustomerId;
    }

    public void setmCustomerId(String mCustomerId) {
        this.mCustomerId = mCustomerId;
    }
    
    public  static  String  getOptye(String memberCardType, String cardName) {
		String optype = null;
//		if ("JJ Card".equals(memberCardType)) {
//			if ("J Benefit Card".equals(cardName)) {
//				optype = "Buy J";
//			} else if ("J2 Benefit Card".equals(cardName)) {
//				optype = "Buy J2";
//			} else if ("J8 Benefit Card".equals(cardName)) {
//				optype = "Invite To J8";
//			}
//
//		} else if ("J Benefit Card".equals(memberCardType)) {
//			if ("J2 Benefit Card".equals(cardName)) {
//				optype = "Upgrade J2";
//			} else if ("J8 Benefit Card".equals(cardName)) {
//				optype = "Invite To J8";
//			}
//
//		} else if ("J2 Benefit Card".equals(memberCardType)) {
//			if ("J8 Benefit Card".equals(cardName)) {
//				optype = "Invite To J8";
//			} else if ("J2 Benefit Card".equals(cardName)) {
//				optype = "Recharge J2";
//			}
//		}
		
		if (NewCardType.SILVER_CARD.name().equals(memberCardType)) {
			if (NewCardType.GOLD_CARD.name().equals(cardName)) {
				optype = "BuyGold";
			} else if (NewCardType.PLATINUM_CARD.name().equals(cardName)) {
				optype = "BuyPlatinum";
			} else if (NewCardType.BLACK_CARD.name().equals(cardName)) {
				optype = "Invite To Black";
			}
		} else if(NewCardType.GOLD_CARD.name().equals(memberCardType)){
			if (NewCardType.PLATINUM_CARD.name().equals(cardName)) {
				optype = "BuyPlatinum";
			} else if (NewCardType.BLACK_CARD.name().equals(cardName)) {
				optype = "Invite To Black";
			} else if (NewCardType.GOLD_CARD.name().equals(cardName)) {
                optype = "RechargeGold";
            }
		} else if(NewCardType.PLATINUM_CARD.name().equals(memberCardType)){
			if (NewCardType.BLACK_CARD.name().equals(cardName)) {
				optype = "Invite To Black";
			} else if(NewCardType.PLATINUM_CARD.name().equals(cardName)) {
                optype = "RechargePlatinum";
            }
		}
		return optype;
	}

	@SuppressWarnings("unchecked")
	public static List<MemberDto> toDtos(List<Member> members) {
		if (CollectionUtils.isEmpty(members)) {
			return ListUtils.EMPTY_LIST;
		}
		List<MemberDto> memberDtos = new ArrayList<MemberDto>();
		for (Member member : members) {
			memberDtos.add(member.toDto());
		}
		return memberDtos;
	}

	public String getNewMemberHierarchy() {
		return newMemberHierarchy;
	}

	public void setNewMemberHierarchy(String newMemberHierarchy) {
		this.newMemberHierarchy = newMemberHierarchy;
	}

	public String getHotelChannel() {
		return hotelChannel;
	}

	public void setHotelChannel(String hotelChannel) {
		this.hotelChannel = hotelChannel;
	}
	
	
}
