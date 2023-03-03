package com.adobe.prj.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.CustomerDao;
import com.adobe.prj.dao.OrderDao;
import com.adobe.prj.dao.ProductDao;
import com.adobe.prj.entity.Customer;
import com.adobe.prj.entity.Item;
import com.adobe.prj.entity.Order;
import com.adobe.prj.entity.Product;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private OrderDao orderDao;
	
	public void addCustomer(Customer c) {
		customerDao.save(c);
	}
	
	public Customer getCustomer(String email) {
		return customerDao.findById(email).get();
	}
	
	/* 
	  
	{
		"customer": {"email":"a@adobe.com"},
		"items": [
			{"product": {id:1}, quantity:2},
			{"product": {id:2}, quantity:1}
			]
		}
	}
	 */
	
	@Transactional
	public Order placeOrder(Order o) {
		double total = 0.0;
		List<Item> items = o.getItems();
		for(Item item : items) {
			Product p = productDao.findById(item.getProduct().getId()).get();
			if(p.getQuantity() == 0) {
				throw new RuntimeException("no stock!!!");
			}
			p.setQuantity(p.getQuantity() - item.getQuantity()); // dirty checking ==> update SQL
			item.setAmount(p.getPrice() * item.getQuantity());
			total += item.getAmount();
		}
		o.setTotal(total);
		return orderDao.save(o);
	}
	
	public List<Order> getOrders() {
		return orderDao.findAll();
	}
	
	public List<Product> getProducts() {
		return productDao.findAll();
	}
	
	public Product getProductById(int id) {
		Optional<Product> opt = productDao.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}
	
	public Product addProduct(Product p) {
		return productDao.save(p);
	}
	
	public List<Product> getByRange(double low, double high) {
		return productDao.getByRange(low, high);
	}
	
	@Transactional
	public Product updateProduct(int id, double price) {
		productDao.updateProduct(id, price);
		return this.getProductById(id);
	}
}