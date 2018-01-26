package mafoe.brand.repository;

import mafoe.brand.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Repository
public class BrandRepositoryImpl implements BrandRepository {

    private static final String KEY = "brand";
    private RedisTemplate<String, Brand> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public BrandRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<String, Brand> findAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public int count() {
        return hashOperations.entries(KEY).size();
    }

    @Override
    public void add(Brand brand) {
        hashOperations.put(KEY, brand.getName(), brand);
    }

    @Override
    public void delete(String brand) {
        hashOperations.delete(KEY, brand);
    }

    @Override
    public Optional<Brand> findOne(String brand) {
        return Optional.ofNullable((Brand) hashOperations.get(KEY, brand));
    }
}
