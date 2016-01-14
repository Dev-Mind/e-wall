package fr.emse.ewall.api.secured;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.ewall.model.Category;
import fr.emse.ewall.model.CategoryDetailView;
import fr.emse.ewall.model.Role;
import fr.emse.ewall.repository.CategoryRepository;
import fr.emse.ewall.security.NeedsRole;
import fr.emse.ewall.service.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/api/secured/category")
public class CategoryWriterController {

    @Value("${ewall.qrcode.unlocked}")
    private Boolean unlocked;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/check/{code}")
    @ApiOperation(value = "Check if code is used by another category", httpMethod = "GET")
    public ResponseEntity<Void> checkCode(
            @ApiParam(name = "id", required = false, value = "Category Id") @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "code", required = true, value = "Code to check") @PathVariable(value = "code") String code) {


        Category category = categoryRepository.findByCode(code);

        if (category == null || (id != null && category.getId().equals(id))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete one category (be careful all the QR codes linked to this category will be deleted!)", httpMethod = "DELETE")
    @NeedsRole(Role.ADMIN)
    public ResponseEntity delete(@ApiParam(name = "id", value = "Category Id") @PathVariable(value = "id") Long id, HttpServletRequest request) {
        if (Boolean.TRUE.equals(unlocked)) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create or update a category. For a new one a set of QR codes are generated", httpMethod = "POST")
    @NeedsRole(Role.ADMIN)
    @JsonView(CategoryDetailView.class)
    public ResponseEntity<Category> save(@ApiParam(name = "category", value = "Category") @RequestBody Category category, HttpServletRequest request) {
        if (Boolean.TRUE.equals(unlocked)) {
            return ResponseEntity.ok().body(categoryService.save(category));
        }
        else {
            return ResponseEntity.status(HttpStatus.LOCKED).body(null);
        }
    }
}