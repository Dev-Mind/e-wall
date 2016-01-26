package fr.emse.ewall.api.secured;

import fr.emse.ewall.conf.EWallCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secured/cache")
public class CacheController {

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(value = "/clear")
    public void clearCache() {
        cacheManager.getCache(EWallCacheConfig.CACHE_PRODUCTION).clear();
    }
}