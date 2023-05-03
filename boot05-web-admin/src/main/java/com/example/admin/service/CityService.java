package com.example.admin.service;

import com.example.admin.bean.City;
import com.example.admin.mapper.CityMappper;

public interface CityService {

    public City getCityById(int id);

    public void insert(City city);
}
