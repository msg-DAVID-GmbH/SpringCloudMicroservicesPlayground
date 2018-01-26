package mafoe.repository;

import mafoe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.MANDATORY;

/**
 * Spring Data JPA repository for Author entities.
 */
@Transactional(MANDATORY)
public interface OrderRepository extends JpaRepository<Order, Long> {

}