package mafoe.brand.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/brands")
@RestController
public class BrandService {

    /**
     * Return all brands.
     */
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<BrandDto> getBrands() {
        return Arrays.asList(new BrandDto("Ford"), new BrandDto("Toyota"));
    }

}
