package com.vivek.Vivek.Ecommerce.project.Service.Impl;

import org.springframework.stereotype.Service;

import com.vivek.Vivek.Ecommerce.project.Dto.AddressDto;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Entities.Address;
import com.vivek.Vivek.Ecommerce.project.Entities.User;
import com.vivek.Vivek.Ecommerce.project.Repository.AddressRepository;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.AddressService;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto) {
        User user = userService.getLoginUser();

        Address address = user.getAddress(); // Retrieve the existing address
        boolean isNewAddress = false;

        // Create a new Address if the user doesn't have one
        if (address == null) {
            address = new Address();
            address.setUser(user);
            isNewAddress = true;
        }

        // Update fields from AddressDto if not null
        if (addressDto.getStreet() != null) address.setStreet(addressDto.getStreet());
        if (addressDto.getCity() != null) address.setCity(addressDto.getCity());
        if (addressDto.getState() != null) address.setState(addressDto.getState());
        if (addressDto.getZipCode() != null) address.setZipCode(addressDto.getZipCode());
        if (addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());

        // Save the address in the repository
        addressRepository.save(address);

        // Build appropriate success message
        String message = isNewAddress ? "Address added successfully" : "Address updated successfully";

        return Response.builder()
                .status(200)
                .message(message)
                .build();
    }
}
