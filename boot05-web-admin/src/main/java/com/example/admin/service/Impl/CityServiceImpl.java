package com.example.admin.service.Impl;

import com.example.admin.bean.City;
import com.example.admin.mapper.CityMappper;
import com.example.admin.service.CityService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityMappper cityMappper;

    Counter counter;

    public CityServiceImpl(MeterRegistry meterRegistry){
         counter = meterRegistry.counter("cityService.saveCity.count");
    }

    public City getCityById(int id){
        return cityMappper.getCityById(id);
    }

    public void insert(City city){
        counter.increment();
        cityMappper.insert(city);
    }
}
