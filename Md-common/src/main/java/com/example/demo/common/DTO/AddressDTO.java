package com.example.demo.common.DTO;

import lombok.Data;

@Data
public class AddressDTO {
    private Integer addressId;
    private Integer userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
