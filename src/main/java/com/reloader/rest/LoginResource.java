package com.reloader.rest;

import com.reloader.services.LoginService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

@Path("auth")
public class LoginResource {

    private final LoginService service = new LoginService();

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String body) {
        try {
            JSONObject json = new JSONObject(body);
            String username = json.getString("username");
            String password = json.getString("password");

            JSONObject response = service.login(username, password);
            return Response.ok(response.toString()).build();

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            error.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error.toString()).build();
        }
    }
}
