package actest.member;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.remote.vo.Contact.Listofpersonaladdress;
import com.jje.membercenter.remote.vo.Personaladdress;

public class ContactObjectTest extends DataPrepareFramework  {
    @Test
    public void ContactObjTest() {
        com.jje.membercenter.remote.vo.Contact contact = new com.jje.membercenter.remote.vo.Contact();
        contact.setBirthday("3999-01-01");
        contact.setCdno("cdno");
        contact.setCdtype("cdtype");
        contact.setCell("cell");
        contact.setCmpname("cmpname");
        contact.setConpriflag("conpriflag");
        contact.setCusttype("custtype");
        contact.setEdulevl("edulevl");
        contact.setEmail("email@email.com");
        contact.setHometown("三体");
        contact.setLifecycle("lifecycle");
        contact.setSex("F");
        contact.setRace("race");
        contact.setTitle("title");
        contact.setNation("吐槽星人");
        contact.setUnit("unit");
        contact.setMarried("Y");
        contact.setPosition("p");
        contact.setUnit("unit");
        Listofpersonaladdress listofpersonaladdress = new Listofpersonaladdress();
        contact.setListofpersonaladdress(listofpersonaladdress);
        Personaladdress personaladdress = new Personaladdress();
        personaladdress = new Personaladdress(
                "addpriflag",
                "addrtype",
                "address",
                "province",
                "city",
                "area",
                "streetaddr",
                "postcode",
                "nation");
        personaladdress.setAddpriflag("addpriflag");
        personaladdress.setAddress("address");
        personaladdress.setAddrtype("addrtype");
        personaladdress.setArea("area");
        personaladdress.setCity("city");
        personaladdress.setNation("nation");
        personaladdress.setPostcode("postcode");
        personaladdress.setProvince("province");
        personaladdress.setStreetaddr("streetaddr");

        List<Personaladdress> list = new ArrayList<Personaladdress>();
        listofpersonaladdress.setPersonaladdress(list);

        Assert.assertEquals("3999-01-01", contact.getBirthday());
        Assert.assertEquals("cdno", contact.getCdno());
        Assert.assertEquals("cdtype", contact.getCdtype() );
        Assert.assertEquals("cell", contact.getCell());
        Assert.assertEquals("email@email.com", contact.getEmail());
        Assert.assertEquals("cmpname", contact.getCmpname());
        Assert.assertEquals("conpriflag", contact.getConpriflag());
        Assert.assertEquals("custtype", contact.getCusttype());
        Assert.assertEquals("edulevl", contact.getEdulevl());
        Assert.assertEquals("三体", contact.getHometown());
        Assert.assertEquals("lifecycle", contact.getLifecycle());
        Assert.assertEquals("F", contact.getSex());
        Assert.assertEquals("race", contact.getRace());
        Assert.assertEquals("title", contact.getTitle());
        Assert.assertEquals("吐槽星人", contact.getNation());
        Assert.assertEquals("unit", contact.getUnit());
        Assert.assertEquals("Y", contact.getMarried());
        Assert.assertEquals("Y", contact.getMarried());
        Assert.assertEquals(list, listofpersonaladdress.getPersonaladdress());
        Assert.assertEquals(listofpersonaladdress, contact.getListofpersonaladdress());
        Assert.assertEquals("unit", contact.getUnit());
        Assert.assertEquals("addpriflag", personaladdress.getAddpriflag());
        Assert.assertEquals("address", personaladdress.getAddress());
        Assert.assertEquals("addrtype", personaladdress.getAddrtype());
        Assert.assertEquals("area", personaladdress.getArea());
        Assert.assertEquals("city", personaladdress.getCity());
        Assert.assertEquals("nation", personaladdress.getNation());
        Assert.assertEquals("postcode", personaladdress.getPostcode());
        Assert.assertEquals("province", personaladdress.getProvince());
        Assert.assertEquals("streetaddr", personaladdress.getStreetaddr());
    }
}
