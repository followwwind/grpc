package com.wind.grpc;

import com.googlecode.protobuf.format.JsonFormat;
import com.wolf.business.huibao.grpcservice.HomeIndexGrpc;
import com.wolf.business.huibao.grpcservice.HomeRequest;
import com.wolf.business.huibao.grpcservice.HomeResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.Test;

/**
 * @author wind
 */
public class GrpcTest {

    @Test
    public void test(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("192.168.1.44", 50051).usePlaintext(true).build();
        HomeIndexGrpc.HomeIndexBlockingStub blockingStub = HomeIndexGrpc.newBlockingStub(channel);
        HomeRequest request = HomeRequest.newBuilder().setMsg("").build();
        HomeResponse response;
        try {
            response = blockingStub.getHomeIndex(request);
            System.out.println(response);
            System.out.println(JsonFormat.printToString(response));
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }
}
