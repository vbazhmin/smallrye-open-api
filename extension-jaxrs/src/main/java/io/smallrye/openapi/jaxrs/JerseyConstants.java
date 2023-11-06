package io.smallrye.openapi.jaxrs;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jboss.jandex.DotName;

public class JerseyConstants {

  public static final String JERSEY_PACKAGE = "org.glassfish.jersey";

  // Jersey multipart annotations
  public static final DotName FORM_DATA_PARAM = DotName
      .createSimple("org.glassfish.jersey.media.multipart.FormDataParam");

  // Jersey multipart headers
  public static final DotName CONTENT_DISPOSITION = DotName
      .createSimple("org.glassfish.jersey.media.multipart.ContentDisposition");
  public static final DotName FORM_DATA_CONTENT_DISPOSITION = DotName
      .createSimple("org.glassfish.jersey.media.multipart.FormDataContentDisposition");

  // Jersey multipart input types
  public static final DotName BODY_PART = DotName
      .createSimple("org.glassfish.jersey.media.multipart.BodyPart");
  public static final DotName FORM_DATA_BODY_PART = DotName
      .createSimple("org.glassfish.jersey.media.multipart.FormDataBodyPart");
  public static final DotName FILE_DATA_BODY_PART = DotName
      .createSimple("org.glassfish.jersey.media.multipart.FileDataBodyPart");
  public static final DotName STREAM_DATA_BODY_PART = DotName
      .createSimple("org.glassfish.jersey.media.multipart.StreamDataBodyPart");

  public static final DotName MULTI_PART = DotName
      .createSimple("org.glassfish.jersey.media.multipart.MultiPart");
  public static final DotName FORM_DATA_MULTI_PART = DotName
      .createSimple("org.glassfish.jersey.media.multipart.FormDataMultiPart");

  public static final Set<DotName> MULTIPART_INPUTS = Collections.unmodifiableSet(
      new HashSet<>(
          Arrays.asList(
              CONTENT_DISPOSITION,
              FORM_DATA_CONTENT_DISPOSITION,
              BODY_PART,
              FORM_DATA_BODY_PART,
              FILE_DATA_BODY_PART,
              STREAM_DATA_BODY_PART,
              MULTI_PART,
              FORM_DATA_MULTI_PART
          )
      )
  );
}
