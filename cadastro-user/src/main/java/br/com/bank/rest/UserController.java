package br.com.bank.rest;

import br.com.bank.persistence.dto.UserDto;
import br.com.bank.persistence.model.Users;
import br.com.bank.service.UserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/v1/users")
public class UserController {

    @Inject
    UserService service;


    @POST
    @Transactional
    public Response addUser(@Valid UserDto userData) {
        this.service.addUser(userData);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response getUsers(@PathParam("id") Long id){
        Users users = this.service.getUser(id).orElse(new Users());
        return Response.status(Response.Status.OK).entity(users).build();

    }

    @PUT
    @Path("/{id}")
    public Response editUser(@PathParam("id") Long id, @Valid UserDto data) {
        Users existingUser = this.service.getUser(id).orElseThrow(() -> new NotFoundException("ID não identificado: " + id));
        try {
            this.service.editUser(id, data);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao editar a conta").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUser(@PathParam("id")Long id){
        this.service.deleteUser(id);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    public Response listAllUsers() {
        List<Users> users = this.service.getUsers();
        return Response.status(Response.Status.OK).entity(users).build();
    }

}