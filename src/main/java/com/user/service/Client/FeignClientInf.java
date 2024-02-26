package com.user.service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.service.entity.Hotel;

@FeignClient("HOTELSERVICE")
public interface FeignClientInf {

	@GetMapping("/api/hotel/{userId}")
	Hotel getHotelId(@PathVariable("userId") String userId);

}
