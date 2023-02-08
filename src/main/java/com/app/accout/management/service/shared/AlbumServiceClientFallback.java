package com.app.accout.management.service.shared;

import com.app.accout.management.service.data.AlbumServiceClient;
import com.app.accout.management.service.model.AlbumResponseModel;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class AlbumServiceClientFallback implements AlbumServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumServiceClientFallback.class);
    private final Throwable cause;
    public AlbumServiceClientFallback(Throwable cause){
        this.cause = cause;
    }

    @Override
    public List<AlbumResponseModel> getAlbums(String id) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404){
            LOGGER.error("404 error occur while fetching album details of user-id :" +id+". " +"Error message" +cause.getLocalizedMessage());
        }else {
            LOGGER.error("other error took place : " + cause.getLocalizedMessage());
        }

        return Collections.emptyList();
    }
}
