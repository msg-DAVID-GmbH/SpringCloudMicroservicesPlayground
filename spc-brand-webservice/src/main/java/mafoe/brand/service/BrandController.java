package mafoe.brand.service;

import com.google.common.collect.Lists;
import mafoe.brand.model.Brand;
import mafoe.brand.repository.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/brands")
@RestController
public class BrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    private final BrandRepository brandRepository;

    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Return all brands.
     */
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Brand> getBrands() {
        LOGGER.info("getBrands() was called");
        return Lists.newArrayList(brandRepository.findAll());
    }

    /**
     * @return whether a brand's data should be publicly visible.
     */
    @RequestMapping(path = "public", method = RequestMethod.GET, headers = "Accept=application/json")
    public boolean isPublic(@RequestParam("name") String brandName) {

        LOGGER.info("isPublic for brand {} was called", brandName);

        Brand brand = brandRepository.findOne(brandName);
        return brand != null && brand.isPublic();
    }

}
