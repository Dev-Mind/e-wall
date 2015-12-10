package fr.emse.numericwall.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.numericwall.model.Category;
import fr.emse.numericwall.model.FlatView;
import fr.emse.numericwall.repository.CategoryRepository;
import fr.emse.numericwall.service.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Category", description = "Texts can be linked to a category. We have one big QR code by category compounded by several " +
        "smaller QR codes. Each small QR code is a link to a text")
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/check/{code}")
    @ApiOperation(value = "Check if code is used by another category", httpMethod = "GET")
    public ResponseEntity<Void> checkCode(
            @ApiParam(name = "id", required = false, value = "Category Id") @RequestParam(value = "id", required = false) String id,
            @ApiParam(name = "code", required = true, value = "Code to check") @PathVariable(value = "code") String code) {

        Category category = categoryRepository.findByCode("code");

        if (category == null || !category.getId().equals(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }

    @RequestMapping
    @ApiOperation(value = "Return all the categories", httpMethod = "GET")
    @JsonView(FlatView.class)
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }


    @RequestMapping(value = "/{id}")
    @ApiOperation(value = "Return one category", httpMethod = "GET")
    public Category findOne(@ApiParam(name = "id", value = "Category Id") @PathVariable(value = "id") Long id) {
        return categoryRepository.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete one category (be careful all the QR codes linked to this category will be deleted!)", httpMethod = "DELETE")
    public ResponseEntity delete(@ApiParam(name = "id", value = "Category Id") @PathVariable(value = "id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create or update a category. For a new one a set of QR codes are generated", httpMethod = "POST")
    public Category save(@ApiParam(name = "category", value = "Category") @RequestBody Category category) {
        return categoryService.save(category);
    }
}