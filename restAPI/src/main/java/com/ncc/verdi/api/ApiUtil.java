package com.ncc.verdi.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * ApiUtil class auto generated from swagger code gen.
 * For this project we only generate the api and model and ignore everything else.
 * This file falls under 'everything else' so we copy it over here so we don't need to generate it again.
 *
 * @author Swagger SpringCodegen
 */
public class ApiUtil {
    @Autowired
    static Gson gson;

    public static void setExampleResponse(NativeWebRequest req, String contentType, String example) {
        try {
            HttpServletResponse res = req.getNativeResponse(HttpServletResponse.class);
            res.setCharacterEncoding("UTF-8");
            res.addHeader("Content-Type", contentType);
            res.getWriter().print(example);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpStatus getResponseFromId(String id) {
        if (!id.equals("-1")) {
            return HttpStatus.CREATED;
        } else {
            return HttpStatus.NOT_MODIFIED;
        }
    }

    public static HttpStatus getResponseFromList(List genericList) {
        HttpStatus statusToReturn;
        if (genericList.size() > 0) {
            statusToReturn = HttpStatus.OK;
        } else {
            statusToReturn = HttpStatus.NO_CONTENT;
        }
        return statusToReturn;
    }
}
