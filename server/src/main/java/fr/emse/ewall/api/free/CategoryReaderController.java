package fr.emse.ewall.api.free;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.ewall.model.Category;
import fr.emse.ewall.model.CategoryDetailView;
import fr.emse.ewall.model.FlatView;
import fr.emse.ewall.repository.CategoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Category", description = "Texts can be linked to a category. We have one big QR code by category compounded by several " +
        "smaller QR codes. Each small QR code is a link to a text")
@RestController
@RequestMapping("/api/public/category")
public class CategoryReaderController {

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping
    @ApiOperation(value = "Return all the categories", httpMethod = "GET")
    @JsonView(FlatView.class)
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }


    @RequestMapping(value = "/with-qrcode")
    @ApiOperation(value = "Return all the categories with data to generate QR code", httpMethod = "GET")
    @JsonView(CategoryDetailView.class)
    public Iterable<Category> findAllWithQrcode() {
        return categoryRepository.findAllithQrCode();
    }

    @RequestMapping(value = "/{id}")
    @ApiOperation(value = "Return one category", httpMethod = "GET")
    @JsonView(CategoryDetailView.class)
    public Category findOne(@ApiParam(name = "id", value = "Category Id") @PathVariable(value = "id") Long id) {
        return categoryRepository.findByIdFetchMode(id);
    }

}