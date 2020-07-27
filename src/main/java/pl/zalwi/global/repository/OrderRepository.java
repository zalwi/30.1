package pl.zalwi.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.zalwi.global.model.ServiceOrder;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ServiceOrder, Long>, JpaSpecificationExecutor<ServiceOrder> {
    List<ServiceOrder> findTop3ByEndDateIsNullOrderByDeadlineDateAsc();
}
