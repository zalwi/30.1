package pl.zalwi.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zalwi.global.model.ServiceOrder;
import pl.zalwi.global.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<ServiceOrder> getMostPriorityServiceOrders(){
        return orderRepository.findTop3ByEndDateIsNullOrderByDeadlineDateAsc();
    }
}
