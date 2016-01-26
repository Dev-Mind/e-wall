package fr.emse.ewall.api.free;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonView;
import fr.emse.ewall.model.FlatView;
import fr.emse.ewall.model.QrCode;
import fr.emse.ewall.repository.CategoryRepository;
import fr.emse.ewall.repository.QrCodeRepository;
import fr.emse.ewall.service.category.CategoryService;
import fr.emse.ewall.service.qrcode.QrCodeFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "QrCode", description = "This API helps to load the different QrCode")
@RestController
@RequestMapping("/api/public/qrcode")
public class QrCodeController {

    @Autowired
    private QrCodeFileService qrCodeFileService;

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @RequestMapping(value = "{category}/{name:.+}")
    @ApiOperation(value = "Return a QR code", httpMethod = "GET")
    public void findOne(@ApiParam(name = "category", value = "Category id") @PathVariable(value = "category") String category,
                        @ApiParam(name = "name", value = "resource name (without extension)") @PathVariable(value = "name") String name,
                        HttpServletResponse response) throws IOException {

        response.setContentType("image/svg+xml");
        response.getOutputStream().write(qrCodeFileService.getQrCode(String.format("/%s/%s", category, name)));
    }

    @RequestMapping(value = "/{id}")
    @ApiOperation(value = "Return one QRCode", httpMethod = "GET")
    @JsonView(FlatView.class)
    public QrCode findOne(@ApiParam(name = "id", value = "QR code Id") @PathVariable(value = "id") Long id) {
        return qrCodeRepository.findById(id);
    }

    @RequestMapping(value = "/random")
    @ApiOperation(value = "Return image for random QR code", httpMethod = "GET")
    @JsonView(FlatView.class)
    public void findRandom(HttpServletResponse response) throws IOException {
        response.setContentType("image/svg+xml");
        response.getOutputStream().write(qrCodeFileService.generateRandomyQRCode());
    }

}   