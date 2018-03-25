
package hello;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

	List<Order> findByCustomer(Customer customer);
}
