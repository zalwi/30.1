package pl.zalwi.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.zalwi.global.model.OrderTask;
import pl.zalwi.global.model.ServiceOrder;
import pl.zalwi.global.repository.OrderRepository;
import pl.zalwi.global.repository.OrderTaskRepository;
import pl.zalwi.orders.model.OrdersFilters;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceOrderService {

    OrderRepository orderRepository;
    OrderTaskRepository orderTaskRepository;

    @Autowired
    public ServiceOrderService(OrderRepository orderRepository, OrderTaskRepository orderTaskRepository) {
        this.orderRepository = orderRepository;
        this.orderTaskRepository = orderTaskRepository;
    }

    public List<ServiceOrder> getMostPriorityServiceOrders(){
        return orderRepository.findTop3ByEndDateIsNullOrderByDeadlineDateAsc();
    }

    public List<ServiceOrder> getAllServiceOrders(){
        return orderRepository.findAll();
    }

    public Page<ServiceOrder> findAllForFiltersAndSort(OrdersFilters ordersFilters, PageRequest pageable, Boolean finished) {
        Specification<ServiceOrder> specification = Specification.where(null);
        specification = addSpecification(specification, "producer", ordersFilters.getProducer());
        specification = addSpecification(specification, "model", ordersFilters.getModel());
        specification = addSpecification(specification, "registrationNumber", ordersFilters.getRegistrationNumber());
        specification = addBooleanSpecification(specification, "finished", finished);
        return orderRepository.findAll(specification, pageable);
    }

    private Specification<ServiceOrder> addSpecification(Specification<ServiceOrder> specification, String fieldName, String fieldValue) {
        if (!StringUtils.isEmpty(fieldValue)) {
            Specification<ServiceOrder> secondSpecification = specification.and(
                    (Specification<ServiceOrder>) (root, query, criteriaBuilder)
                            -> criteriaBuilder.like(   criteriaBuilder.upper(root.get(fieldName)),
                            "%" + fieldValue.toUpperCase() + "%"));
            return specification.and(secondSpecification);
        }else{
            return specification;
        }
    }

    private Specification<ServiceOrder> addBooleanSpecification(Specification<ServiceOrder> specification, String fieldName, Boolean fieldValue) {
        if (!StringUtils.isEmpty(fieldValue)) {
            Specification<ServiceOrder> secondSpecification = specification.and(
                    (Specification<ServiceOrder>) (root, query, criteriaBuilder)
                            -> criteriaBuilder.equal(   criteriaBuilder.upper(root.get(fieldName)),
                            fieldValue));
            return specification.and(secondSpecification);
        }else{
            return specification;
        }
    }

    public void addNewServiceOrder(ServiceOrder serviceOrder) {
        orderRepository.save(serviceOrder);
    }

    public void addNewTaskToServiceOrder(Long id, OrderTask orderTask){
        Optional<ServiceOrder> serviceOrderById = orderRepository.findById(id);
        ServiceOrder tmpServiceOrder;
        if(serviceOrderById.isPresent())
        {
            tmpServiceOrder = serviceOrderById.get();
            tmpServiceOrder.addNewTaskToList(orderTask);
            orderTaskRepository.save(orderTask);
            orderRepository.save(tmpServiceOrder);
        }
    }



    public Optional<ServiceOrder> getServiceOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void modifyServiceOrder(ServiceOrder serviceOrderWithNewValues) {
        Optional<ServiceOrder> OptionalServiceOrder = orderRepository.findById(serviceOrderWithNewValues.getId());
        ServiceOrder modifiedServiceOrder;
        if(OptionalServiceOrder.isPresent())
        {
            modifiedServiceOrder = OptionalServiceOrder.get();
            modifiedServiceOrder.setProducer(               serviceOrderWithNewValues.getProducer());
            modifiedServiceOrder.setModel(                  serviceOrderWithNewValues.getModel());
            modifiedServiceOrder.setRegistrationNumber(     serviceOrderWithNewValues.getRegistrationNumber());
            modifiedServiceOrder.setVinNumber(              serviceOrderWithNewValues.getVinNumber());
            modifiedServiceOrder.setFirstDateRegistration(  serviceOrderWithNewValues.getFirstDateRegistration());
            modifiedServiceOrder.setEngineCapacity(         serviceOrderWithNewValues.getEngineCapacity());
            modifiedServiceOrder.setFuelType(               serviceOrderWithNewValues.getFuelType());
            modifiedServiceOrder.setCreationDate(           serviceOrderWithNewValues.getCreationDate());
            modifiedServiceOrder.setDeadlineDate(           serviceOrderWithNewValues.getDeadlineDate());
            orderRepository.save(modifiedServiceOrder);
        }
    }

    public void deleteServiceOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
