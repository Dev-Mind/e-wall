package fr.emse.ewall.api.free;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.ewall.model.FlatView;
import fr.emse.ewall.model.Production;
import fr.emse.ewall.model.ProductionDetailView;
import fr.emse.ewall.repository.ProductionRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Production", description = "Public service to find a production")
@RestController
@RequestMapping("/api/public/production")
public class ProductionReaderController {

    @Value("${ewall.qrcode.url}")
    private String qrCodePrefixUrl;

    @Autowired
    private ProductionRepository productionRepository;

    @RequestMapping
    @ApiOperation(value = "Return all the productions", httpMethod = "GET")
    @JsonView(FlatView.class)
    public Iterable<Production> findAll() {
        return productionRepository.findAllValidated();
    }


    @RequestMapping(value = "/{id}")
    @ApiOperation(value = "Return a production", httpMethod = "GET")
    @JsonView(ProductionDetailView.class)
    public Production findOne(@ApiParam(name = "id", value = "Production Id") @PathVariable(value = "id") Long id) {
        return productionRepository.findByIdFetchMode(id);
    }


    @RequestMapping(value = "/{category}/{num}")
    @ApiOperation(value = "Return a production", httpMethod = "GET")
    @JsonView(ProductionDetailView.class)
    public Production findOne(
            @ApiParam(name = "category", value = "Category code") @PathVariable(value = "category") String category,
            @ApiParam(name = "id", value = "QR code numd") @PathVariable(value = "num") String num) {

        if ("random".equals(category) || "r".equals(category)) {
            //We choose a random production
            List<Production> productions = productionRepository.findAllValidated();
            Collections.shuffle(productions);
            return productions.stream().findAny().orElse(null);
        }

        String qrcodeUrl = !num.equals("general") ? String.format("%s/%s/%s", qrCodePrefixUrl, category, num) : String.format("%s/%s", qrCodePrefixUrl, category);
        return productionRepository.findByUrl(qrcodeUrl);
    }

    @RequestMapping(value = "/search/{text}")
    @JsonView(FlatView.class)
    public List<Long> findRandom(@PathVariable(value = "text") String text) throws IOException {
        List<Production> productions = productionRepository.findAllValidated();

        return productions
                .stream()
                .filter(p -> p.getContent()!=null && p.getContent().toLowerCase().contains(text.toLowerCase()))
                .map(p -> p.getQrcode().getId())
                .collect(Collectors.toList());

    }


}