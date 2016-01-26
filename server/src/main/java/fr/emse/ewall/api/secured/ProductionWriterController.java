package fr.emse.ewall.api.secured;

import static fr.emse.ewall.conf.EWallCacheConfig.CACHE_PRODUCTION;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.ewall.api.dto.ProductionDto;
import fr.emse.ewall.conf.EWallCacheConfig;
import fr.emse.ewall.security.CurrentUser;
import fr.emse.ewall.model.FlatView;
import fr.emse.ewall.model.Production;
import fr.emse.ewall.model.ProductionDetailView;
import fr.emse.ewall.model.Role;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.ProductionRepository;
import fr.emse.ewall.repository.UserRepository;
import fr.emse.ewall.security.Authenticated;
import fr.emse.ewall.security.NeedsRole;
import fr.emse.ewall.service.production.ProductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    @Value("${ewall.list.maxsize}")
    private Integer listMaxSize;

    @Autowired
    private ProductionService productionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping(value = "/production/{page}", method = RequestMethod.PUT)
    @ApiOperation(value = "Return all the productions", httpMethod = "GET")
    @NeedsRole(Role.ADMIN)
    @Authenticated
    public Page<ProductionDto> findAll(
            @ApiParam(name = "page", value = "Current page") @PathVariable(value = "page") Integer page,
            @ApiParam(name = "production", value = "Production") @RequestBody ProductionDto filter) {

        Page<Production> productions = productionService.filterProductions(page, listMaxSize, filter);

        return new PageImpl<>(
                productions.getContent().stream().map(p -> ProductionDto.build(p)).collect(Collectors.toList()),
                new PageRequest(productions.getNumber(), productions.getSize(), productions.getSort()),
                productions.getTotalElements()
        );
    }


    @RequestMapping(value = "/production/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete one production", httpMethod = "DELETE")
    public ResponseEntity delete(@ApiParam(name = "id", value = "Production Id") @PathVariable(value = "id") Long id) {
        productionService.deleteProduction(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/production/myself")
    @ApiOperation(value = "Return my productions", httpMethod = "GET")
    @Authenticated
    @JsonView(FlatView.class)
    public List<Production> findMyProductions(HttpServletRequest request) {
        Optional<User> user = applicationContext.getBean(CurrentUser.class).getCredentials();
        return productionRepository.findByUserId(userRepository.findByEsmeid(user.get().getEsmeid()).getId());
    }

    @RequestMapping(value = "/category/{idCategorie}/production", method = RequestMethod.POST)
    @ApiOperation(value = "Create or update a production. For a new one a link with a QR codes is added", httpMethod = "POST")
    @JsonView(ProductionDetailView.class)
    @Authenticated
    public ResponseEntity<Production> save(
            @ApiParam(name = "idCategorie", value = "Category Id") @PathVariable(value = "idCategorie") Long idCategorie,
            @ApiParam(name = "production", value = "Production") @RequestBody Production production,
            HttpServletRequest request) {

        Optional<User> user = applicationContext.getBean(CurrentUser.class).getCredentials();
        return ResponseEntity.ok().body(productionService.saveMyProduction(idCategorie, production, userRepository.findByEsmeid(user.get().getEsmeid())));
    }

}