package com.utraveler.ws.launcher.comparator;

import java.lang.reflect.Method;

import javax.ws.rs.Path;

import org.apache.cxf.jaxrs.ext.ResourceComparator;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;

public class WebServiceComparator implements ResourceComparator {

    @Override
    public int compare(ClassResourceInfo arg0, ClassResourceInfo arg1, Message message) {

        UriInfoImpl uriInfo = new UriInfoImpl(message);

        String path = uriInfo.getPath();
        String[] pathArray = path.split("/");
        int value = -1;
        if (pathArray.length > 0) {
            String resourceUrlName = pathArray[1];
            value = 1;
            Method[] methods = arg0.getServiceClass().getInterfaces()[0].getMethods();
            String resource = null;
            for (Method method : methods) {

                Path annotationPath = method.getAnnotation(Path.class);
                if (annotationPath != null) {
                    String pathValue = annotationPath.value();
                    String[] parts = pathValue.split("/");
                    resource = parts[1];
                }

                if (resource == null || resourceUrlName.contains(resource)) {
                    value = -1;
                }

            }
        }
        return value;
    }


    @Override
    public int compare(OperationResourceInfo arg0, OperationResourceInfo arg1, Message arg2) {
        return 0;
    }

}
