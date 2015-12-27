package fr.emse.ewall.api.secured;

import fr.emse.ewall.model.Production;
import fr.emse.ewall.service.production.ProductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Production", description = "Secured service to add or update the production of content for a category")
@RestController
@RequestMapping("/api/secured/production")
public class ProductionWriterController {

    @Autowired
    private ProductionService productionService;

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete one production", httpMethod = "DELETE")
    public ResponseEntity delete(@ApiParam(name = "id", value = "Production Id") @PathVariable(value = "id") Long id) {
        productionService.deleteProduction(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create or update a production. For a new one a link with a QR codes is added", httpMethod = "POST")
    public ResponseEntity<Production> save(@ApiParam(name = "production", value = "Production") @RequestBody Production production) {
        return ResponseEntity.ok().body(productionService.save(production));
    }
}