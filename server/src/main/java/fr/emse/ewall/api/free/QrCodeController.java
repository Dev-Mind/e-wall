package fr.emse.ewall.api.free;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

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


    @RequestMapping(value = "{category}/{name:.+}")
    @ApiOperation(value = "Return a QR code", httpMethod = "GET")
    public void findOne(@ApiParam(name = "category", value = "Category id") @PathVariable(value = "category") String category,
                        @ApiParam(name = "name", value = "resource name (without extension)") @PathVariable(value = "name") String name,
                        HttpServletResponse response) throws IOException {

        response.setContentType("image/svg+xml");
        response.getOutputStream().write(qrCodeFileService.getQrCode(String.format("/%s/%s", category, name)));
    }

}   