package com.tap.test;

import com.tap.DAO.AddressDAO;
import com.tap.DAOImpl.AddressDAOImpl;
import com.tap.model.UserAddress;

public class AddressDAOTest {

    public static void main(String[] args) {

        AddressDAO dao = new AddressDAOImpl();

        UserAddress address = new UserAddress(

                1,
                "HOME",
                "12A",
                "MG Road",
                "Indiranagar",
                "Near Metro Station",
                "Bangalore",
                "Karnataka",
                "560038",
                12.9716,
                77.5946,
                true

        );

        int result = dao.addAddress(address);

        if(result > 0) {
            System.out.println("Address Added Successfully...");
        }
        else {
            System.out.println("Failed to Add Address...");
        }

    }

}