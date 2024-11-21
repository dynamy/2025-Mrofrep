package com.dtcookie.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public interface Http {

    public String GET(String url, Map<String,String> headers);

    public String POST(String url, String body);

    public static Http JDK = new Http() {
        @Override
        public String GET(String url, Map<String,String> headers) {
            URL u = null;
            try {
                u = new URL(url);
            } catch (Throwable t) {
                return Throwables.toString(t);
            }
            try (InputStream in = u.openStream()) {
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    Streams.copy(in, baos);
                    return baos.toString(StandardCharsets.UTF_8);
                }
            } catch (Throwable t) {
                return Throwables.toString(t);
            }
        }

        @Override
        public String POST(String url, String body) {
            URL u = null;
            try {
                u = new URL(url);
            } catch (Throwable t) {
                return Throwables.toString(t);
            }
            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) u.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);

                try (OutputStream out = con.getOutputStream()) {
                    Streams.copy(body.getBytes(StandardCharsets.UTF_8), out);
                }

                try (InputStream in = u.openStream()) {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        Streams.copy(in, baos);
                        return baos.toString(StandardCharsets.UTF_8);
                    }
                } catch (Throwable t) {
                    return Throwables.toString(t);
                }
            } catch (Throwable t) {
                return Throwables.toString(t);
            }
        }
    };

    public static Http Jodd = new Http() {
        @Override
        public String GET(String url, Map<String,String> headers) {
            try {
                HttpRequest httpRequest = HttpRequest.get(url);
                if (headers != null) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        httpRequest.header(entry.getKey(), entry.getValue());
                    }
                }                
                HttpResponse response = httpRequest.send();
                return response.bodyText();
            } catch (Throwable t) {
                return Throwables.toString(t);
            }
        }

        @Override
        public String POST(String url, String body) {
            try {
                HttpRequest httpRequest = HttpRequest.post(url);
                HttpResponse response = httpRequest.send();
                return response.bodyText();
            } catch (Throwable t) {
                return Throwables.toString(t);
            }
        }
    };

    public static void serve(String name, int port, final String context, final HttpGetter handler) throws Exception {
        serve(name, port, handler(context, handler));
    }

    public static void serve(String name, int port, final HttpHandlers handlers) throws Exception {
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setDisplayName(name);
        server.setHandler(context);

        for (Map.Entry<String, HttpGetter> entry : handlers.handlers.entrySet()) {
            context.addServlet(new ServletHolder(new DoGetHandler(entry.getValue())), entry.getKey());
        }

        server.start();
    }

    public static HttpHandlers handler(String context, HttpGetter handler) {
        return new HttpHandlers(context, handler);
    }

    public static class HttpHandlers {
        
        private final Map<String, HttpGetter> handlers = new HashMap<>();

        private HttpHandlers(String context, HttpGetter handler) {
            this.add(context, handler);
        }

        public HttpHandlers add(String context, HttpGetter handler) {
            this.handlers.put(context, handler);
            return this;
        }

    }

}
