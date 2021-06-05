package com.lear.service.impl;

import com.github.pagehelper.PageHelper;
import com.lear.dao.IOrdersDao;
import com.lear.domain.Orders;
import com.lear.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao ordersDao;

    @Override
    public List<Orders> findAll(int page,int size) throws Exception {
        //参数pageNum是页码值，参数pageSize代表是每页显示条数
        PageHelper.startPage(page,size);//必须写在调用查询函数的前一句，前两句都不行
        return ordersDao.findAll();
    }
    public Orders findById(String ordersId) throws Exception{
        return ordersDao.findById(ordersId);
    }
}
