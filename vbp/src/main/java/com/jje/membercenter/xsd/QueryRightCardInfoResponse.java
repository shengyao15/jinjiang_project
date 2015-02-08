//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-146 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.01 at 02:44:56 PM CST 
//


package com.jje.membercenter.xsd;

import java.util.ArrayList;
import java.util.List;
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
 *                   &lt;element name="transcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="reqtime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="systype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="retcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
 *                   &lt;element name="rightcard" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="membid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="memcardno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="jjmemcardno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="membcdtype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="startdate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="enddate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="membcdsour" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="membcdstat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="listofhistory">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="viphistory" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="cdoptype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="cdbuydt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="cdsalechnl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="salesamount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
public class QueryRightCardInfoResponse {

    @XmlElement(required = true)
    protected QueryRightCardInfoResponse.Head head;
    @XmlElement(required = true)
    protected QueryRightCardInfoResponse.Body body;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link QueryRightCardInfoResponse.Head }
     *     
     */
    public QueryRightCardInfoResponse.Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryRightCardInfoResponse.Head }
     *     
     */
    public void setHead(QueryRightCardInfoResponse.Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link QueryRightCardInfoResponse.Body }
     *     
     */
    public QueryRightCardInfoResponse.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryRightCardInfoResponse.Body }
     *     
     */
    public void setBody(QueryRightCardInfoResponse.Body value) {
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
     *         &lt;element name="rightcard" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="membid" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="memcardno" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="jjmemcardno" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="membcdtype" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="startdate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="enddate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="membcdsour" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="membcdstat" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="listofhistory">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="viphistory" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="cdoptype" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="cdbuydt" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="cdsalechnl" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="salesamount" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
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
        "rightcard"
    })
    public static class Body {

        protected List<QueryRightCardInfoResponse.Body.Rightcard> rightcard;

        /**
         * Gets the value of the rightcard property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the rightcard property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRightcard().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link QueryRightCardInfoResponse.Body.Rightcard }
         * 
         * 
         */
        public List<QueryRightCardInfoResponse.Body.Rightcard> getRightcard() {
            if (rightcard == null) {
                rightcard = new ArrayList<QueryRightCardInfoResponse.Body.Rightcard>();
            }
            return this.rightcard;
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
         *         &lt;element name="memcardno" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="jjmemcardno" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="membcdtype" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="startdate" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="enddate" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="membcdsour" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="membcdstat" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="listofhistory">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="viphistory" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="cdoptype" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="cdbuydt" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="cdsalechnl" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="salesamount" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
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
            "membid",
            "memcardno",
            "jjmemcardno",
            "membcdtype",
            "startdate",
            "enddate",
            "membcdsour",
            "membcdstat",
            "listofhistory"
        })
        public static class Rightcard {

            @XmlElement(required = true)
            protected String membid;
            @XmlElement(required = true)
            protected String memcardno;
            @XmlElement(required = true)
            protected String jjmemcardno;
            @XmlElement(required = true)
            protected String membcdtype;
            @XmlElement(required = true)
            protected String startdate;
            @XmlElement(required = true)
            protected String enddate;
            @XmlElement(required = true)
            protected String membcdsour;
            @XmlElement(required = true)
            protected String membcdstat;
            @XmlElement(required = true)
            protected QueryRightCardInfoResponse.Body.Rightcard.Listofhistory listofhistory;

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
             * Gets the value of the memcardno property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMemcardno() {
                return memcardno;
            }

            /**
             * Sets the value of the memcardno property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMemcardno(String value) {
                this.memcardno = value;
            }

            /**
             * Gets the value of the jjmemcardno property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getJjmemcardno() {
                return jjmemcardno;
            }

            /**
             * Sets the value of the jjmemcardno property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setJjmemcardno(String value) {
                this.jjmemcardno = value;
            }

            /**
             * Gets the value of the membcdtype property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMembcdtype() {
                return membcdtype;
            }

            /**
             * Sets the value of the membcdtype property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMembcdtype(String value) {
                this.membcdtype = value;
            }

            /**
             * Gets the value of the startdate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStartdate() {
                return startdate;
            }

            /**
             * Sets the value of the startdate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStartdate(String value) {
                this.startdate = value;
            }

            /**
             * Gets the value of the enddate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEnddate() {
                return enddate;
            }

            /**
             * Sets the value of the enddate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEnddate(String value) {
                this.enddate = value;
            }

            /**
             * Gets the value of the membcdsour property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMembcdsour() {
                return membcdsour;
            }

            /**
             * Sets the value of the membcdsour property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMembcdsour(String value) {
                this.membcdsour = value;
            }

            /**
             * Gets the value of the membcdstat property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMembcdstat() {
                return membcdstat;
            }

            /**
             * Sets the value of the membcdstat property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMembcdstat(String value) {
                this.membcdstat = value;
            }

            /**
             * Gets the value of the listofhistory property.
             * 
             * @return
             *     possible object is
             *     {@link QueryRightCardInfoResponse.Body.Rightcard.Listofhistory }
             *     
             */
            public QueryRightCardInfoResponse.Body.Rightcard.Listofhistory getListofhistory() {
                return listofhistory;
            }

            /**
             * Sets the value of the listofhistory property.
             * 
             * @param value
             *     allowed object is
             *     {@link QueryRightCardInfoResponse.Body.Rightcard.Listofhistory }
             *     
             */
            public void setListofhistory(QueryRightCardInfoResponse.Body.Rightcard.Listofhistory value) {
                this.listofhistory = value;
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
             *         &lt;element name="viphistory" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="cdoptype" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="cdbuydt" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="cdsalechnl" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="salesamount" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "viphistory"
            })
            public static class Listofhistory {

                protected List<QueryRightCardInfoResponse.Body.Rightcard.Listofhistory.Viphistory> viphistory;

                /**
                 * Gets the value of the viphistory property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the viphistory property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getViphistory().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link QueryRightCardInfoResponse.Body.Rightcard.Listofhistory.Viphistory }
                 * 
                 * 
                 */
                public List<QueryRightCardInfoResponse.Body.Rightcard.Listofhistory.Viphistory> getViphistory() {
                    if (viphistory == null) {
                        viphistory = new ArrayList<QueryRightCardInfoResponse.Body.Rightcard.Listofhistory.Viphistory>();
                    }
                    return this.viphistory;
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
                 *         &lt;element name="cdoptype" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="cdbuydt" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="cdsalechnl" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="salesamount" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                    "cdoptype",
                    "cdbuydt",
                    "cdsalechnl",
                    "salesamount"
                })
                public static class Viphistory {

                    @XmlElement(required = true)
                    protected String cdoptype;
                    @XmlElement(required = true)
                    protected String cdbuydt;
                    @XmlElement(required = true)
                    protected String cdsalechnl;
                    @XmlElement(required = true)
                    protected String salesamount;

                    /**
                     * Gets the value of the cdoptype property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCdoptype() {
                        return cdoptype;
                    }

                    /**
                     * Sets the value of the cdoptype property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCdoptype(String value) {
                        this.cdoptype = value;
                    }

                    /**
                     * Gets the value of the cdbuydt property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCdbuydt() {
                        return cdbuydt;
                    }

                    /**
                     * Sets the value of the cdbuydt property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCdbuydt(String value) {
                        this.cdbuydt = value;
                    }

                    /**
                     * Gets the value of the cdsalechnl property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCdsalechnl() {
                        return cdsalechnl;
                    }

                    /**
                     * Sets the value of the cdsalechnl property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCdsalechnl(String value) {
                        this.cdsalechnl = value;
                    }

                    /**
                     * Gets the value of the salesamount property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSalesamount() {
                        return salesamount;
                    }

                    /**
                     * Sets the value of the salesamount property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSalesamount(String value) {
                        this.salesamount = value;
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
     *         &lt;element name="transcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="reqtime" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="systype" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="retcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "reqtime",
        "systype",
        "retcode",
        "retmsg"
    })
    public static class Head {

        @XmlElement(required = true)
        protected String transcode;
        @XmlElement(required = true)
        protected String reqtime;
        @XmlElement(required = true)
        protected String systype;
        @XmlElement(required = true)
        protected String retcode;
        @XmlElement(required = true)
        protected String retmsg;

        /**
         * Gets the value of the transcode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTranscode() {
            return transcode;
        }

        /**
         * Sets the value of the transcode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTranscode(String value) {
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

        /**
         * Gets the value of the retcode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRetcode() {
            return retcode;
        }

        /**
         * Sets the value of the retcode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRetcode(String value) {
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