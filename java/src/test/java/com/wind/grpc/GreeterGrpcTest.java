package com.wind.grpc;

import com.grpc.service.helloworld.GreeterGrpc;
import com.grpc.service.helloworld.HelloReply;
import com.grpc.service.helloworld.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author wind
 */
public class GreeterGrpcTest {

    private final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

    private ManagedChannel channel;
    private GreeterGrpc.GreeterBlockingStub blockingStub;

    private final String host = "192.168.1.44";

    private final int port = 50051;

    @Before
    public void init(){
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    @Test
    public void testSayHello(){
        HelloRequest request = HelloRequest.newBuilder().setName("").build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response);
    }

    @After
    public void shutdown(){
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
