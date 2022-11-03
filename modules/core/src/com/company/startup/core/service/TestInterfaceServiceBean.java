package com.company.startup.core.service;

import org.springframework.stereotype.Service;


@Service(TestInterfaceService.NAME)
public class TestInterfaceServiceBean implements TestInterfaceService {

    @Override
    public String doTest() {



        return null;
    }
}
