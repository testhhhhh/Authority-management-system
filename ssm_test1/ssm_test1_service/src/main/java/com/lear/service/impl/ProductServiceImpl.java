package com.lear.service.impl;

import com.lear.dao.IProductDao;
import com.lear.domain.Product;
import com.lear.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired(required = true)
    private IProductDao productDao;

    @Override
    public List<Product> findAll() throws Exception {
        return productDao.findAll();
    }

    public void  save(Product product) throws Exception {
        productDao.save(product);
    }
}
