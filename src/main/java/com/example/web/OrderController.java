package com.example.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.data.model.Customer;
import com.example.data.model.Order;
import com.example.data.repository.CustomerRepository;
import com.example.data.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public String getAllOrders(Model model){
        Iterable<Order> orderIterable = this.orderRepository.findAll();
        List<OrderModel> orders = new ArrayList<>();
        orderIterable.forEach(oi ->{
           OrderModel order = new OrderModel();
           order.setOrderId(oi.getId());
           order.setCustomerId(oi.getCustomerId());
           Optional<Customer> oc = this.customerRepository.findById(oi.getCustomerId());
           order.setCustomer(oc.get().getName());
           order.setOrderDetails(oi.getOrderInfo());
           orders.add(order);
        });
        model.addAttribute("orders", orders);
        model.addAttribute("module", "orders");
        return "orders";
    }
}
