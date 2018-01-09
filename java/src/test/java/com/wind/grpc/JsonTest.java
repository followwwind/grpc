package com.wind.grpc;

import com.googlecode.protobuf.format.JsonFormat;
import com.grpc.service.helloworld.HelloRequest;
import org.junit.Test;

/**
 * @author wind
 */
public class JsonTest {

    @Test
    public void toJson(){
        HelloRequest request = HelloRequest.newBuilder().setName("123").build();

        String str = JsonFormat.printToString(request);
        System.out.println(str);

    }

    @Test
    public void msgToJson(){

    }
}
