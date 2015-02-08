//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-146 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.18 at 11:36:03 AM CST 
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
 *                   &lt;element name="membid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="srtype" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
public class ListAccountMergeRequest {

    @XmlElement(required = true)
    protected ListAccountMergeRequest.Head head;
    @XmlElement(required = true)
    protected ListAccountMergeRequest.Body body;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link ListAccountMergeRequest.Head }
     *     
     */
    public ListAccountMergeRequest.Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListAccountMergeRequest.Head }
     *     
     */
    public void setHead(ListAccountMergeRequest.Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link ListAccountMergeRequest.Body }
     *     
     */
    public ListAccountMergeRequest.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListAccountMergeRequest.Body }
     *     
     */
    public void setBody(ListAccountMergeRequest.Body value) {
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
     *         &lt;element name="srtype" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "srtype"
    })
    public static class Body {

        @XmlElement(required = true)
        protected String membid;
        @XmlElement(required = true)
        protected String srtype;

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
         * Gets the value of the srtype property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSrtype() {
            return srtype;
        }

        /**
         * Sets the value of the srtype property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSrtype(String value) {
            this.srtype = value;
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