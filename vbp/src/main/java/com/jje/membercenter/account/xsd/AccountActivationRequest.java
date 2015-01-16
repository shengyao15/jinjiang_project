//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-146 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.17 at 02:32:08 下午 CST 
//


package com.jje.membercenter.account.xsd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="head">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="transcode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="reqtime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="systype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="body">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="membrowid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="passwd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="pwdquestion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="pwdanswer" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "head",
    "body"
})
@XmlRootElement(name = "request")
public class AccountActivationRequest {

    @XmlElement(required = true)
    protected AccountActivationRequest.Head head;
    @XmlElement(required = true)
    protected AccountActivationRequest.Body body;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link AccountActivationRequest.Head }
     *     
     */
    public AccountActivationRequest.Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountActivationRequest.Head }
     *     
     */
    public void setHead(AccountActivationRequest.Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link AccountActivationRequest.Body }
     *     
     */
    public AccountActivationRequest.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountActivationRequest.Body }
     *     
     */
    public void setBody(AccountActivationRequest.Body value) {
        this.body = value;
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
     *         &lt;element name="membrowid" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="passwd" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="pwdquestion" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="pwdanswer" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "membrowid",
        "passwd",
        "pwdquestion",
        "pwdanswer"
    })
    public static class Body {

        @XmlElement(required = true)
        protected String membrowid;
        @XmlElement(required = true)
        protected String passwd;
        @XmlElement(required = true)
        protected String pwdquestion;
        @XmlElement(required = true)
        protected String pwdanswer;

        /**
         * Gets the value of the membrowid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMembrowid() {
            return membrowid;
        }

        /**
         * Sets the value of the membrowid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMembrowid(String value) {
            this.membrowid = value;
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
     *         &lt;element name="reqtime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
        protected BigInteger reqtime;
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
         *     {@link BigInteger }
         *     
         */
        public BigInteger getReqtime() {
            return reqtime;
        }

        /**
         * Sets the value of the reqtime property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setReqtime(BigInteger value) {
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
