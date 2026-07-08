package com.tap.DAO;

import java.util.List;
import com.tap.model.UserAddress;

public interface AddressDAO {

    int addAddress(UserAddress address);

    UserAddress getAddressById(int addressId);

    List<UserAddress> getAddressesByUserId(int userId);

    List<UserAddress> getAllAddresses();

    int updateAddress(UserAddress address);

    int deleteAddress(int addressId);

}