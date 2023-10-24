package test.io.smallrye.openapi.runtime.scanner.jakarta;

import jakarta.annotation.Priority;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Priority(2)
@APIResponse(
    responseCode = "500",
    description = "Priority 2 Internal Server Error"
)
public class PriorityTwoExceptionMapper implements ExceptionMapper<WebApplicationException> {
  @Override
  public Response toResponse(WebApplicationException exception) {
    return null;
  }
}
