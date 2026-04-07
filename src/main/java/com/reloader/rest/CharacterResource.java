package com.reloader.rest;

import com.reloader.services.CharacterService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("characters")
public class CharacterResource {

    private final CharacterService service = new CharacterService();

    @GET
    @Path("screen/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharacterScreen(@PathParam("userId") long userId) {
        try {
            JSONObject response = service.getCharacterScreen(userId);
            return Response.ok(response.toString()).build();

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            error.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error.toString()).build();
        }
    }

    @POST
    @Path("lapis-config")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveLapisConfig(String body) {
        try {
            JSONObject json = new JSONObject(body);
            long characterId = json.getLong("characterId");
            JSONArray config = json.getJSONArray("config");

            JSONObject response = service.saveLapisConfig(characterId, config);
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
