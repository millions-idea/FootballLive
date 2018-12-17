package com.management.admin.biz.impl;

import com.management.admin.biz.IInformationService;
import com.management.admin.entity.db.Information;
import com.management.admin.repository.InformationMapper;

public class InformationServiceImpl implements IInformationService {

    private InformationMapper informationMapper;

    public InformationServiceImpl(InformationMapper informationMapper){
        this.informationMapper = informationMapper;
    }



    /**
     * 添加情报
     *
     * @param information
     * @return
     */
    @Override
    public Integer insertInformation(Information information) {
        return informationMapper.insertInformation(information);
    }
}
