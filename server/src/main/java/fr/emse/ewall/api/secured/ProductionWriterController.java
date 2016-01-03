package fr.emse.ewall.api.secured;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.ewall.model.FlatView;
import fr.emse.ewall.model.Production;
import fr.emse.ewall.model.Role;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.ProductionRepository;
import fr.emse.ewall.repository.UserRepository;
import fr.emse.ewall.security.CheckUserRole;
import fr.emse.ewall.service.production.ProductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Production", description = "Secured service to add or update the production of content for a category")
@RestController
@RequestMapping("/api/secured")
public class ProductionWriterController {

    @Autowired
    private ProductionService productionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private CheckUserRole checkUserRole;


    @RequestMapping(value = "/production")
    @ApiOperation(value = "Return all the productions", httpMethod = "GET")
    @JsonView(FlatView.class)
    public Iterable<Production> findAll(HttpServletRequest request) {
        checkUserRole.checkRole(request, Role.ADMIN);
        return productionRepository.findAll();
    }


    @RequestMapping(value = "/production/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete one production", httpMethod = "DELETE")
    public ResponseEntity delete(@ApiParam(name = "id", value = "Production Id") @PathVariable(value = "id") Long id) {
        productionService.deleteProduction(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{idCategorie}/production", method = RequestMethod.POST)
    @ApiOperation(value = "Create or update a production. For a new one a link with a QR codes is added", httpMethod = "POST")
    public ResponseEntity<Production> save(
            @ApiParam(name = "idCategorie", value = "Category Id") @PathVariable(value = "idCategorie") Long idCategorie,
            @ApiParam(name = "production", value = "Production") @RequestBody Production production,
            HttpServletRequest request) {

        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        return ResponseEntity.ok().body(productionService.saveMyProduction(idCategorie, production, userRepository.findByEsmeid(principal.getName()), false));
    }

    @RequestMapping(value = "/production/myself")
    @ApiOperation(value = "Return my productions", httpMethod = "GET")
    @JsonView(FlatView.class)
    public List<Production> findMyProductions(HttpServletRequest request) {
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        return productionRepository.findByUserId(userRepository.findByEsmeid(principal.getName()).getId());
    }
}