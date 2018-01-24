package mafoe.brand.repository;

import mafoe.brand.model.Brand;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface BrandRepository {

    Map<String, Brand> findAll();

    int count();

    void add(Brand brand);

    void delete(String brand);

    Optional<Brand> findOne(String brand);
}
