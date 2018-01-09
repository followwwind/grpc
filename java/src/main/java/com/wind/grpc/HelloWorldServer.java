package com.wind.grpc;

import com.grpc.service.helloworld.GreeterGrpc;
import com.grpc.service.helloworld.HelloReply;
import com.grpc.service.helloworld.HelloRequest;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * @author wind
 */
public class HelloWorldServer {

    private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

    /**
     * The port on which the server should run
     */
    private int port = 50051;
    private Server server;

    private static List<BindableService> serviceList = new ArrayList<>();



    private void start() throws IOException {
        ServerBuilder<?> builder = ServerBuilder.forPort(port);
        serviceList.add(new GreeterImpl());
        serviceList.forEach(service -> builder.addService(service));
        server = builder.build().start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            HelloWorldServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloWorldServer server = new HelloWorldServer();
        server.start();
        server.blockUntilShutdown();
    }

    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        /** 原子Integer */
        public AtomicInteger count = new AtomicInteger(0);

        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            System.out.println("call sayHello");
            System.out.println(req.getName());
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            System.out.println(count.incrementAndGet() + Thread.currentThread().getName());
        }
    }
}
