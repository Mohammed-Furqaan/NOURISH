package com.tap.DAO;

import java.util.List;
import com.tap.model.RestaurantAddress;

public interface RestaurantAddressDAO {

    // Insert
    int addAddress(RestaurantAddress address);

    // Fetch
    RestaurantAddress getAddressById(int addressId);

    List<RestaurantAddress> getAddressByRestaurantId(int restaurantId);

    List<RestaurantAddress> getAllAddresses();

    // Update
    int updateAddress(RestaurantAddress address);

    // Delete
    int deleteAddress(int addressId);

}