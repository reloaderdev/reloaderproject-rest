package com.reloader.rest;

import com.reloader.services.NotificationService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

@Path("notifications")
public class NotificationResource {

    private final NotificationService service = new NotificationService();

    @POST
    @Path("device-token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upsertDeviceToken(String body) {
        try {
            JSONObject json = new JSONObject(body);
            long userId = json.getLong("userId");
            String platform = json.getString("platform").trim().toUpperCase();
            String deviceToken = json.getString("deviceToken").trim();

            JSONObject response = service.upsertDeviceToken(userId, platform, deviceToken);
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
