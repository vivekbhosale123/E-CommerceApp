package com.vivek.Vivek.Ecommerce.project.Service.Interf;

import com.vivek.Vivek.Ecommerce.project.Dto.AddressDto;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;

public interface AddressService {

	Response saveAndUpdateAddress(AddressDto addressDto);
}
