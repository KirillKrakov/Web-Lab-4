package kr_k.weblab4.rest;

import kr_k.weblab4.bean.AuthResultBean;
import kr_k.weblab4.bean.AuthorizationBean;
import kr_k.weblab4.bean.UserBean;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResourse {

    @EJB
    private AuthorizationBean authorizationBean;

    @POST
    @Path("/login")
    public Response login(UserBean user, @CookieParam("token") String token) {
        if(user == null) user = new UserBean();
        user.setToken(token);

        var result = authorizationBean.tryLogin(user);
        return makeResponse(result);
    }

    @POST
    @Path("/register")
    public Response register(UserBean user, @CookieParam("token") String token) {
        if(user == null) user = new UserBean();
        user.setToken(token);
        var result = authorizationBean.tryRegister(user);

        return makeResponse(result);
    }

    @POST
    @Path("/logout")
    public Response logout(@CookieParam("token") String token) {
        UserBean user = new UserBean();
        user.setToken(token);
        var result = authorizationBean.logout(user);

        return makeResponse(result);
    }

    @GET
    @Path("/")
    public Response checkAuth(@CookieParam("token") String token) {
        var result = authorizationBean.checkAuthorization(token);
        return makeResponse(result);
    }

    private Response makeResponse(AuthResultBean result) {
        Response.ResponseBuilder response;
        if(result.isSuccess()) response = Response.ok();
        else response = Response.status(Response.Status.BAD_REQUEST);

        System.out.println("setting token: " + result.getToken());
        var cookie = new NewCookie("token", result.getToken(), "/", null, null, 1209600, false);
        return response.cookie(cookie).entity(result).build();
    }

}