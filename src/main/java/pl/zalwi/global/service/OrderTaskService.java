package pl.zalwi.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.zalwi.global.model.OrderTask;
import pl.zalwi.global.model.TaskStatus;
import pl.zalwi.global.repository.OrderTaskRepository;

@Service
public class OrderTaskService {

    OrderTaskRepository orderTaskRepository;

    @Autowired
    public OrderTaskService(OrderTaskRepository orderTaskRepository) {
        this.orderTaskRepository = orderTaskRepository;
    }

    public Page<OrderTask> findAllForStatusAndSort(TaskStatus taskStatus, PageRequest pageable) {
        Specification<OrderTask> specification = Specification.where(null);
        if (taskStatus != null) {
            specification = addSpecification(specification, "taskStatus", taskStatus);
        }
        return orderTaskRepository.findAll(specification, pageable);
    }

    private Specification<OrderTask> addSpecification(Specification<OrderTask> specification, String fieldName, TaskStatus fieldValue) {
        if (!StringUtils.isEmpty(fieldValue)) {
            Specification<OrderTask> secondSpecification = specification.and(
                    (Specification<OrderTask>) (root, query, criteriaBuilder)
                            -> criteriaBuilder.equal(criteriaBuilder.upper(root.get(fieldName)),
                            fieldValue));
            return specification.and(secondSpecification);
        } else {
            return specification;
        }
    }
}
