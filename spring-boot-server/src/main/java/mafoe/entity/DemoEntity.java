package mafoe.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import static javax.persistence.GenerationType.AUTO;

/**
 * Superclass for all JPA entities.
 */
@MappedSuperclass
public class DemoEntity {

    @GeneratedValue(strategy = AUTO)
    @Id
    private Long id;

    @Version
    private Long optLock;

    public Long getId() {
        return id;
    }

    public Long getOptLock() {
        return optLock;
    }
}
