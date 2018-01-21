package mafoe.brand.service;

import mafoe.brand.model.Brand;
import mafoe.brand.repository.BrandRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/brands")
@RestController
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Return all brands.
     */
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Brand> getBrands() {
        return new ArrayList<>(brandRepository.findAll().values());
    }

}
