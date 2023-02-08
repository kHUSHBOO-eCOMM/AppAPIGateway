package com.app.accout.management.service.data;

import com.app.accout.management.service.model.AlbumResponseModel;
import com.app.accout.management.service.shared.AlbumServiceClientFallback;
import com.app.accout.management.service.shared.AlbumServiceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "albums-ws", fallbackFactory = AlbumServiceClientFallbackFactory.class)
public interface AlbumServiceClient {

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}
