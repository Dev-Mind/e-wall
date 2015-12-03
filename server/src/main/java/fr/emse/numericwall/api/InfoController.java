package fr.emse.numericwall.api;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Application", description = "Return info on the application")
@RestController
@RequestMapping("/api/about")
public class InfoController {

    @Value("${build.tag}")
    private String version;

    @Value("${project.name}")
    private String name;


    @RequestMapping
    @ApiOperation(value = "Return the app version", httpMethod = "GET")
    public ResponseEntity<Map<String, String>> getVersion() {
        Map<String, String> params = new HashMap<>();
        params.put("application", name);
        params.put("version", version);

        return ResponseEntity
                .ok()
                .body(params);
    }

}