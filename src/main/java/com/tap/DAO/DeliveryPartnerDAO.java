package com.tap.DAO;

import java.util.List;
import com.tap.model.DeliveryPartner;

public interface DeliveryPartnerDAO {

    int addPartner(DeliveryPartner partner);

    DeliveryPartner getPartnerById(int id);

    List<DeliveryPartner> getAllPartners();

    List<DeliveryPartner> getAvailablePartners();

    int updatePartner(DeliveryPartner partner);

    int deletePartner(int id);
} 