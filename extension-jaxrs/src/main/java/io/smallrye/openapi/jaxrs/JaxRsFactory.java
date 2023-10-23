package io.smallrye.openapi.jaxrs;

import java.util.Comparator;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;

public class JaxRsFactory {

  public static Comparator<ClassInfo> createPriorityComparator() {
    return (first, second) -> {
      Integer firstPriorityValue = JaxRsConstants.PRIORITY_DEFAULT_VALUE;
      for (DotName dotName : JaxRsConstants.PRIORITY) {
        AnnotationInstance annotation = first.annotation(dotName);
        if (annotation == null) {
          continue;
        }

        firstPriorityValue = (Integer) annotation.value("value").value();
      }

      Integer secondPriorityValue = JaxRsConstants.PRIORITY_DEFAULT_VALUE;
      for (DotName dotName : JaxRsConstants.PRIORITY) {
        AnnotationInstance annotation = second.annotation(dotName);
        if (annotation == null) {
          continue;
        }
        secondPriorityValue = (Integer) annotation.value("value").value();
      }

      return firstPriorityValue.compareTo(secondPriorityValue);
    };
  }
}
