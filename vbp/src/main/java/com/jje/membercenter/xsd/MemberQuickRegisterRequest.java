//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-146 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.02.20 at 02:50:23 PM CST 
//


package com.jje.membercenter.xsd;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberRegisterDto;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "head",
    "body"
})
@XmlRootElement(name = "request")
public class MemberQuickRegisterRequest {

    @XmlElement(required = true)
    protected MemberQuickRegisterRequest.Head head;
    @XmlElement(required = true)
    protected MemberQuickRegisterRequest.Body body;

    public MemberQuickRegisterRequest() {
    }

    public MemberQuickRegisterRequest(MemberRegisterDto registerDto) {
        MemberRegisterResponse response = new MemberRegisterResponse();
        MemberQuickRegisterRequest.Head head = getRegisterRequestHead();
        MemberQuickRegisterRequest.Body body = new MemberQuickRegisterRequest.Body();
        MemberQuickRegisterRequest.Body.Record record = getMemberRegisterRequestHead(registerDto);
        MemberQuickRegisterRequest.Body.Record.Listofcontact listofcontact = new MemberQuickRegisterRequest.Body.Record.Listofcontact();
        MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact contact = getMemberRegisterRequestContact(registerDto);
        listofcontact.setContact(contact);
        record.setListofcontact(listofcontact);
        body.setRecord(record);
        this.setHead(head);
        this.setBody(body);
    }

    private MemberQuickRegisterRequest.Head getRegisterRequestHead() {
        MemberQuickRegisterRequest.Head head = new MemberQuickRegisterRequest.Head();
        head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date()));
        head.setTranscode(BigInteger.valueOf(Long.parseLong("10008")));
        head.setSystype("JJ000");
        return head;
    }

    private MemberQuickRegisterRequest.Body.Record getMemberRegisterRequestHead(
            MemberRegisterDto dto) {
        MemberInfoDto info = dto.getMemberInfoDto();
        MemberQuickRegisterRequest.Body.Record record = new MemberQuickRegisterRequest.Body.Record();
        record.setPasswd(info.getPasssword());
        record.setPwdanswer(info.getRemindAnswer());
        record.setPwdquestion(info.getRemindQuestion());
        if (null != info.getRegisterSource())
            record.setRegichnl(info.getRegisterSource().name());
        record.setRegichnltag(dto.getRegisterTag());

        return record;
    }

    private MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact getMemberRegisterRequestContact(
            MemberRegisterDto dto) {
        MemberInfoDto info = dto.getMemberInfoDto();
        MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact contact = new MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact();
        contact.setConpriflag("Y");
        contact.setEmail(info.getEmail());

        contact.setMobile(info.getMobile());
        return contact;
    }



    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link MemberQuickRegisterRequest.Head }
     *     
     */
    public MemberQuickRegisterRequest.Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link MemberQuickRegisterRequest.Head }
     *     
     */
    public void setHead(MemberQuickRegisterRequest.Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link MemberQuickRegisterRequest.Body }
     *     
     */
    public MemberQuickRegisterRequest.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link MemberQuickRegisterRequest.Body }
     *     
     */
    public void setBody(MemberQuickRegisterRequest.Body value) {
        this.body = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "record"
    })
    public static class Body {

        @XmlElement(required = true)
        protected MemberQuickRegisterRequest.Body.Record record;

        /**
         * Gets the value of the record property.
         * 
         * @return
         *     possible object is
         *     {@link MemberQuickRegisterRequest.Body.Record }
         *     
         */
        public MemberQuickRegisterRequest.Body.Record getRecord() {
            return record;
        }

        /**
         * Sets the value of the record property.
         * 
         * @param value
         *     allowed object is
         *     {@link MemberQuickRegisterRequest.Body.Record }
         *     
         */
        public void setRecord(MemberQuickRegisterRequest.Body.Record value) {
            this.record = value;
        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "passwd",
            "pwdquestion",
            "pwdanswer",
            "regichnl",
            "listofcontact",
             "regichnltag",
             "cddesp"
        })
        public static class Record {
        	
            @XmlElement(required = true)
            protected String passwd;
            @XmlElement(required = true)
            protected String pwdquestion;
            @XmlElement(required = true)
            protected String pwdanswer;
            @XmlElement(required = false)
            protected String regichnl;
            @XmlElement(required = true)
            protected MemberQuickRegisterRequest.Body.Record.Listofcontact listofcontact;

            @XmlElement(required = false)
            protected String regichnltag;

            @XmlElement(required = false)
            private String cddesp;

            
            public String getCddesp() {
				return cddesp;
			}

			public void setCddesp(String cddesp) {
				this.cddesp = cddesp;
			}

            public String getRegichnltag() {
                return regichnltag;
            }

            public void setRegichnltag(String regichnltag) {
                this.regichnltag = regichnltag;
            }


            /**
             * Gets the value of the passwd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPasswd() {
                return passwd;
            }

            /**
             * Sets the value of the passwd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPasswd(String value) {
                this.passwd = value;
            }

            /**
             * Gets the value of the pwdquestion property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPwdquestion() {
                return pwdquestion;
            }

            /**
             * Sets the value of the pwdquestion property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPwdquestion(String value) {
                this.pwdquestion = value;
            }

            /**
             * Gets the value of the pwdanswer property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPwdanswer() {
                return pwdanswer;
            }

            /**
             * Sets the value of the pwdanswer property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPwdanswer(String value) {
                this.pwdanswer = value;
            }


			/**
             * Gets the value of the listofcontact property.
             * 
             * @return
             *     possible object is
             *     {@link MemberQuickRegisterRequest.Body.Record.Listofcontact }
             *     
             */
            public MemberQuickRegisterRequest.Body.Record.Listofcontact getListofcontact() {
                return listofcontact;
            }

            /**
             * Sets the value of the listofcontact property.
             * 
             * @param value
             *     allowed object is
             *     {@link MemberQuickRegisterRequest.Body.Record.Listofcontact }
             *     
             */
            public void setListofcontact(MemberQuickRegisterRequest.Body.Record.Listofcontact value) {
                this.listofcontact = value;
            }

            public String getRegichnl() {
                return regichnl;
            }

            public void setRegichnl(String regichnl) {
                this.regichnl = regichnl;
            }
            
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "contact"
            })
            public static class Listofcontact {

                @XmlElement(required = true)
                protected MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact contact;

                /**
                 * Gets the value of the contact property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact }
                 *     
                 */
                public MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact getContact() {
                    return contact;
                }

                /**
                 * Sets the value of the contact property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact }
                 *     
                 */
                public void setContact(MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact value) {
                    this.contact = value;
                }



                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "conpriflag",
                    "email",
                    "mobile"
                })
                public static class Contact {

                    @XmlElement(required = true)
                    protected String conpriflag;
                    @XmlElement(required = true)
                    protected String email;
                    @XmlElement(required = true)
                    protected String mobile;

                    /**
                     * Gets the value of the conpriflag property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getConpriflag() {
                        return conpriflag;
                    }

                    /**
                     * Sets the value of the conpriflag property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setConpriflag(String value) {
                        this.conpriflag = value;
                    }


                    /**
                     * Gets the value of the email property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEmail() {
                        return email;
                    }

                    /**
                     * Sets the value of the email property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEmail(String value) {
                        this.email = value;
                    }

                    /**
                     * Gets the value of the mobile property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMobile() {
                        return mobile;
                    }

                    /**
                     * Sets the value of the mobile property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMobile(String value) {
                        this.mobile = value;
                    }



                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="personaladdress" maxOccurs="unbounded">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="addpriflag" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="addrtype" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="province" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="streetaddr" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "personaladdress"
                    })
                    public static class Listofpersonaladdress {

                        @XmlElement(required = true)
                        protected List<MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress.Personaladdress> personaladdress;

                        /**
                         * Gets the value of the personaladdress property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the personaladdress property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getPersonaladdress().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress.Personaladdress }
                         * 
                         * 
                         */
                        public List<MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress.Personaladdress> getPersonaladdress() {
                            if (personaladdress == null) {
                                personaladdress = new ArrayList<MemberQuickRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress.Personaladdress>();
                            }
                            return this.personaladdress;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence>
                         *         &lt;element name="addpriflag" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="addrtype" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="province" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="streetaddr" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                         *       &lt;/sequence>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "addpriflag",
                            "addrtype",
                            "address",
                            "province",
                            "city",
                            "area",
                            "streetaddr",
                            "postcode"
                        })
                        public static class Personaladdress {

                            @XmlElement(required = true)
                            protected String addpriflag;
                            @XmlElement(required = true)
                            protected String addrtype;
                            @XmlElement(required = true)
                            protected String address;
                            @XmlElement(required = true)
                            protected String province;
                            @XmlElement(required = true)
                            protected String city;
                            @XmlElement(required = true)
                            protected String area;
                            @XmlElement(required = true)
                            protected String streetaddr;
                            @XmlElement(required = true)
                            protected BigInteger postcode;

                            /**
                             * Gets the value of the addpriflag property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getAddpriflag() {
                                return addpriflag;
                            }

                            /**
                             * Sets the value of the addpriflag property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setAddpriflag(String value) {
                                this.addpriflag = value;
                            }

                            /**
                             * Gets the value of the addrtype property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getAddrtype() {
                                return addrtype;
                            }

                            /**
                             * Sets the value of the addrtype property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setAddrtype(String value) {
                                this.addrtype = value;
                            }

                            /**
                             * Gets the value of the address property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getAddress() {
                                return address;
                            }

                            /**
                             * Sets the value of the address property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setAddress(String value) {
                                this.address = value;
                            }

                            /**
                             * Gets the value of the province property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getProvince() {
                                return province;
                            }

                            /**
                             * Sets the value of the province property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setProvince(String value) {
                                this.province = value;
                            }

                            /**
                             * Gets the value of the city property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getCity() {
                                return city;
                            }

                            /**
                             * Sets the value of the city property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setCity(String value) {
                                this.city = value;
                            }

                            /**
                             * Gets the value of the area property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getArea() {
                                return area;
                            }

                            /**
                             * Sets the value of the area property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setArea(String value) {
                                this.area = value;
                            }

                            /**
                             * Gets the value of the streetaddr property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getStreetaddr() {
                                return streetaddr;
                            }

                            /**
                             * Sets the value of the streetaddr property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setStreetaddr(String value) {
                                this.streetaddr = value;
                            }

                            /**
                             * Gets the value of the postcode property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigInteger }
                             *     
                             */
                            public BigInteger getPostcode() {
                                return postcode;
                            }

                            /**
                             * Sets the value of the postcode property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigInteger }
                             *     
                             */
                            public void setPostcode(BigInteger value) {
                                this.postcode = value;
                            }

                        }

                    }

                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="transcode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="reqtime" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="systype" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "transcode",
        "reqtime",
        "systype"
    })
    public static class Head {

        @XmlElement(required = true)
        protected BigInteger transcode;
        @XmlElement(required = true)
        protected String reqtime;
        @XmlElement(required = true)
        protected String systype;

        /**
         * Gets the value of the transcode property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTranscode() {
            return transcode;
        }

        /**
         * Sets the value of the transcode property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTranscode(BigInteger value) {
            this.transcode = value;
        }

        /**
         * Gets the value of the reqtime property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReqtime() {
            return reqtime;
        }

        /**
         * Sets the value of the reqtime property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReqtime(String value) {
            this.reqtime = value;
        }

        /**
         * Gets the value of the systype property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSystype() {
            return systype;
        }

        /**
         * Sets the value of the systype property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSystype(String value) {
            this.systype = value;
        }

    }

}