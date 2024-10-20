package com.example.demo.address.Service;

import com.example.demo.common.Entity.Address;

import java.util.List;

public interface AddressService {
    public int addAddress(Address address);

    public Address getAddressById(int id);

    public void deleteAddressById(int id);

    public List<Address> getAddressByUserId(int userId);

}
