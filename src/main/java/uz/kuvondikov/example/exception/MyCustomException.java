package uz.kuvondikov.example.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyCustomException extends RuntimeException implements
        ExceptionMapper<MyCustomException> {

    public MyCustomException() {
        super("OOPS! There is some problem !");
    }

    public MyCustomException(String reason) {
        super(reason);
    }

    @Override
    public Response toResponse(MyCustomException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type("text/plain").build();
    }

}
