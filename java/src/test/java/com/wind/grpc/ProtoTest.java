package com.wind.grpc;

import com.google.protobuf.DescriptorProtos;
import com.grpc.service.helloworld.GreeterGrpc;
import com.wolf.business.huibao.grpcservice.HomeIndexGrpc;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author wind
 */
public class ProtoTest {

    @Test
    public void parseProto(){

        GreeterGrpc greeterGrpc;

        try {
            Class<?> clazz = Class.forName("com.grpc.service.helloworld.GreeterGrpc$GreeterBlockingStub");
            clazz.getDeclaringClass();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Method[] methods = GreeterGrpc.GreeterBlockingStub.class.getDeclaredMethods();

        Arrays.stream(methods).forEach(m -> {
            String name = m.getName();
            if(!"build".equals(name)){
                System.out.println(name);
                Class type = m.getReturnType();
                System.out.println(type.getName());
                System.out.println(type.getPackage());
                Class[] args = m.getParameterTypes();
                Arrays.stream(args).forEach(System.out::println);
            }
        });

        System.out.println(System.getProperty("user.dir"));

        File directory = new File("");//参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(courseFile);


    }
}
