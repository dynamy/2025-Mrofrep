package com.dtcookie.util;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DoGetHandler extends HttpServlet {
    private final HttpGetter delegate;

    public DoGetHandler(HttpGetter delegate) {
        this.delegate = delegate;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int statusCode = 200;
        Object response = "";
        try {
            response = delegate.doGet(req);
        } catch (Throwable t) {
            response = Throwables.toString(t);
            statusCode = 500;        
        }
        resp.setHeader("Content-Type", "text/plain");
        // exchange.getResponseHeaders().putIfAbsent("Content-Type", Collections.singletonList("text/plain"));
        if (response == null) {
            response = "";
        }
        byte[] responseBytes = response.toString().getBytes();
        try {
            resp.setStatus(statusCode);
            resp.setContentLength(responseBytes.length);
            ServletOutputStream sos = resp.getOutputStream();
            Streams.copy(responseBytes, sos);
            sos.flush();
            sos.close();
        } catch (IOException e) {
            //ignore
        } finally {
            resp.flushBuffer();
        }
    }

}