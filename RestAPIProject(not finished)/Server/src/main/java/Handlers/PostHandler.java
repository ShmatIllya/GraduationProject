package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class PostHandler extends BaseHandler {
    @Override
    protected void makeHandle(HttpExchange output) {
        try {
            String requestMethod = output.getRequestMethod();
            if (requestMethod.equalsIgnoreCase("POST")) {
                InputStreamReader isr = new InputStreamReader(output.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                int code = handlePostInput(sb.toString());
                output.sendResponseHeaders(code, -1);
                System.out.println(code);
                br.close();
                isr.close();
            } else {
                output.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected abstract int handlePostInput(String output);
}
