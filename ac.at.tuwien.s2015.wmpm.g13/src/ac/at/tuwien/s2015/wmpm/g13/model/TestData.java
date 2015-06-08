package ac.at.tuwien.s2015.wmpm.g13.model;

import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;
import ac.at.tuwien.s2015.wmpm.g13.model.person.NaturalPerson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josef on 5/22/2015.
 */
public class TestData {

    public static NaturalPerson getCustomer() {
        NaturalPerson naturalPerson = new NaturalPerson();
        naturalPerson.setAddress("Street 23, Vienna 1050, Austria");
        naturalPerson.setEmail("max.muster@mail.com");
        naturalPerson.setFirstname("Maxi");
        naturalPerson.setLastname("Mustermanni");
        naturalPerson.setPersonId("123");
        return naturalPerson;
    }

    public static LegalPerson getSupplier() {
        LegalPerson legalPerson = new LegalPerson();
        legalPerson.setCountryCourt("AnotherCountry");
        legalPerson.setName("BestSupplier");
        legalPerson.setRegistrationNumber("123");
        return legalPerson;
    }

    public static LegalPerson getCompany() {
        LegalPerson legalPerson = new LegalPerson();
        legalPerson.setCountryCourt("ThisCountry");
        legalPerson.setName("PowerMaterials");
        legalPerson.setRegistrationNumber("234");
        return legalPerson;
    }

    public static List<OrderItem> getOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(new Product("123", "ProductOne", 20), 20));
        orderItems.add(new OrderItem(new Product("231", "ProductTwo", 30), 30));
        orderItems
                .add(new OrderItem(new Product("312", "ProductThree", 40), 40));
        return orderItems;
    }
}
