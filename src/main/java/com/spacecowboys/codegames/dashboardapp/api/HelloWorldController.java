package com.spacecowboys.codegames.dashboardapp.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/hello")
@Api(value = "/hello", description = "Says hello", tags = "hello")
public class HelloWorldController {

	@GET
	@Produces("text/plain")
	@ApiOperation(value = "get hello", notes = "my notes", response = String.class)
	public Response doGet() {
		return Response.ok("Hello from WildFly Swarm!").build();
	}
}