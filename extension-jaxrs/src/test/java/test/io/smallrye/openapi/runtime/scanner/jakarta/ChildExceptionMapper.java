package test.io.smallrye.openapi.runtime.scanner.jakarta;

import jakarta.annotation.Priority;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@APIResponse(
    responseCode = "500",
    description = "Child Exception Mapper Internal Server Error"
)
@Priority(1)
public class ChildExceptionMapper extends ParentExceptionMapper {

  @Override
  public Response toResponse(WebApplicationException exception) {
    return super.toResponse(exception);
  }
}
