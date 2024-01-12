package uz.kuvondikov.example.resources;

import uz.kuvondikov.example.dto.AuthUserDTO;
import uz.kuvondikov.example.dto.CreateDTO;
import uz.kuvondikov.example.dto.UpdateDTO;
import uz.kuvondikov.example.exception.MyCustomException;
import uz.kuvondikov.example.service.AuthUserService;
import uz.kuvondikov.example.service.impl.AuthUserServiceImpl;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/auth")
public class AuthUserController {

    private final AuthUserService service;

    public AuthUserController() {
        this.service = new AuthUserServiceImpl();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid CreateDTO createDTO) {
        try {
            Long createdId = service.create(createDTO);
            return Response.ok().entity(createdId).build();
        } catch (MyCustomException exception) {
            return handleException(exception);
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            List<AuthUserDTO> allUsers = service.getAll();
            return Response.ok().entity(allUsers).build();
        } catch (MyCustomException exception) {
            return handleException(exception);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        try {
            AuthUserDTO byId = service.getById(id);
            return Response.ok().entity(byId).build();
        } catch (MyCustomException exception) {
            return handleException(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, @Valid UpdateDTO updateDTO) {
        try {
            Long updatedId = service.update(id, updateDTO);
            return Response.ok().entity(updatedId).build();
        } catch (MyCustomException exception) {
            return handleException(exception);
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        try {
            Long deletedId = service.delete(id);
            return Response.ok().entity(deletedId).build();
        } catch (MyCustomException myCustomException) {
            return handleException(myCustomException);
        }
    }

    private Response handleException(MyCustomException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
