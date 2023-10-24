package test.io.smallrye.openapi.runtime.scanner.jakarta;

import jakarta.annotation.Priority;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;


@APIResponse(
    responseCode = "500",
    description = "Parent Exception Mapper Internal Server Error"
)
@Priority(2)
public class ParentExceptionMapper implements ExceptionMapper<WebApplicationException> {
  @Override
  public Response toResponse(WebApplicationException exception) {
    return null;
  }
}
