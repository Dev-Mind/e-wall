package fr.emse.ewall.api.free;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.ewall.model.CategoryDetailView;
import fr.emse.ewall.model.FlatView;
import fr.emse.ewall.model.Production;
import fr.emse.ewall.model.ProductionDetailView;
import fr.emse.ewall.repository.ProductionRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Production", description = "Public service to find a production")
@RestController
@RequestMapping("/api/public/production")
public class ProductionReaderController {

    @Autowired
    private ProductionRepository productionRepository;

    @RequestMapping
    @ApiOperation(value = "Return all the productions", httpMethod = "GET")
    @JsonView(FlatView.class)
    public Iterable<Production> findAll() {
        return productionRepository.findAll();
    }


    @RequestMapping(value = "/{id}")
    @ApiOperation(value = "Return a production", httpMethod = "GET")
    @JsonView(ProductionDetailView.class)
    public Production findOne(@ApiParam(name = "id", value = "Production Id") @PathVariable(value = "id") Long id) {
        return productionRepository.findByIdFetchMode(id);
    }

}