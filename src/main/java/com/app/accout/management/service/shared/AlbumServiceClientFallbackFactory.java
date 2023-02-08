package com.app.accout.management.service.shared;

import com.app.accout.management.service.data.AlbumServiceClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AlbumServiceClientFallbackFactory implements FallbackFactory<AlbumServiceClient> {
    @Override
    public AlbumServiceClient create(Throwable cause) {
        return new AlbumServiceClientFallback(cause);
    }
}
