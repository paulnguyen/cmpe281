package hello;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  // Default Query Derivation
  List<Customer> findByLastName(String lastName) ;

  // Default Query Derivation
  List<Customer> findByEmail(String email) ;

  // Manual Query Definition
  @Query("select c from Customer c where c.email = :email")
  List<Customer> findByEmail2( @Param("email") String email) ;

  // Manual Query (Alternate Format)
  @Query("select c from Customer c where c.email = ?1")
  List<Customer> findByEmail3(String email) ;

  // Query Derivation (Combined Attributes)
  List<Customer> findByEmailAndLastName(String email, String lastname) ;

  // Nested Traversal Query Derivation
  List<Customer> findByAddresses_Country(String country) ;

}

/*

public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> 
{
  <S extends T> save(S entity);  
  <S extends T> Iterable<S> save(Iterable<S> entities);  
  T findOne(ID id);  Iterable<T> findAll();  
  void delete(ID id);  
  void delete(T entity);  
  void deleteAll()
}

public interface PagingAndSortingRepository<T, ID extends Serializable> extends CrudRepository<T, ID> 
{
  Iterable<T> findAll(Sort sort);
  Page<T> findAll(Pageable pageable);
}

*/