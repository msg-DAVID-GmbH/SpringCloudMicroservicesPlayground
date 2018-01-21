package mafoe.brand.repository;

import mafoe.brand.model.Brand;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface BrandRepository {

    Map<String, Brand> findAll();

    int count();

    void add(Brand brand);

    void delete(String brand);

    Brand findOne(String brand);
}
