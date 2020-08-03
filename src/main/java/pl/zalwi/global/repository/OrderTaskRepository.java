package pl.zalwi.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.zalwi.global.model.OrderTask;

@Repository
public interface OrderTaskRepository extends JpaRepository<OrderTask, Long>, JpaSpecificationExecutor<OrderTask> {
}
