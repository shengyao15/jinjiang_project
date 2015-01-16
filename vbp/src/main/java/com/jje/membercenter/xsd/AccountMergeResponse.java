//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-146 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.17 at 09:16:35 AM CST 
//


package com.jje.membercenter.xsd;

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
 *                   &lt;element name="resptime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="systype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="retcode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="retmsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
 *                   &lt;element name="membid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="recode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="remsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlRootElement(name = "response")
public class AccountMergeResponse {

    @XmlElement(required = true)
    protected AccountMergeResponse.Head head;
    @XmlElement(required = true)
    protected AccountMergeResponse.Body body;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link AccountMergeResponse.Head }
     *     
     */
    public AccountMergeResponse.Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountMergeResponse.Head }
     *     
     */
    public void setHead(AccountMergeResponse.Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link AccountMergeResponse.Body }
     *     
     */
    public AccountMergeResponse.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountMergeResponse.Body }
     *     
     */
    public void setBody(AccountMergeResponse.Body value) {
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
     *         &lt;element name="membid" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="recode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="remsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "membid",
        "recode",
        "remsg"
    })
    public static class Body {

        @XmlElement(required = true)
        protected String membid;
        @XmlElement(required = true)
        protected BigInteger recode;
        @XmlElement(required = true)
        protected String remsg;

        /**
         * Gets the value of the membid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMembid() {
            return membid;
        }

        /**
         * Sets the value of the membid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMembid(String value) {
            this.membid = value;
        }

        /**
         * Gets the value of the recode property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getRecode() {
            return recode;
        }

        /**
         * Sets the value of the recode property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setRecode(BigInteger value) {
            this.recode = value;
        }

        /**
         * Gets the value of the remsg property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRemsg() {
            return remsg;
        }

        /**
         * Sets the value of the remsg property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRemsg(String value) {
            this.remsg = value;
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
     *         &lt;element name="resptime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="systype" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="retcode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="retmsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "resptime",
        "systype",
        "retcode",
        "retmsg"
    })
    public static class Head {

        @XmlElement(required = true)
        protected BigInteger transcode;
        @XmlElement(required = true)
        protected BigInteger resptime;
        @XmlElement(required = true)
        protected String systype;
        @XmlElement(required = true)
        protected BigInteger retcode;
        @XmlElement(required = true)
        protected String retmsg;

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
         * Gets the value of the resptime property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getResptime() {
            return resptime;
        }

        /**
         * Sets the value of the resptime property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setResptime(BigInteger value) {
            this.resptime = value;
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

        /**
         * Gets the value of the retcode property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getRetcode() {
            return retcode;
        }

        /**
         * Sets the value of the retcode property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setRetcode(BigInteger value) {
            this.retcode = value;
        }

        /**
         * Gets the value of the retmsg property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRetmsg() {
            return retmsg;
        }

        /**
         * Sets the value of the retmsg property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRetmsg(String value) {
            this.retmsg = value;
        }

    }

}
