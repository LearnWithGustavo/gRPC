package server;

import io.grpc.*;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Consumer;

/*
* A classe GrpcServer encapusla toda a logica para criar, iniciar, aguardar e interromper um
* servidor gRPC utilizando o Netty como transporte subjacente.
* */

public class GrpcServer {

    /*
    * Logger para exibição de mensagens de log
    * */
    private static final Logger log = LoggerFactory.getLogger(GrpcServer.class);

    //Instancia interna do servidor gRPC
    private final Server server;

    /*
    * Construtor privado que recebe uma instancia de {@link Server}.
    * @Param server Instancia de servidor gRPC
     */
    private GrpcServer(Server server){
        this.server = server;
    }

    /*
    * Cria uma instancia de {@code GrpcServer} na porta padrão (6565),
    * adicionando os serviços gRPC fornecidos
    *
    * @param services Um ou mais serviços que implementam (@link BindableService).
    * @return Uma instancia configurada de {@code GrpcServer}.
    */
    public static GrpcServer create(BindableService... services){
        return create(6565, services);
    }

    /*
     * Cria uma instancia de {@code GrpcServer} na porta padrão (6565),
     * adicionando os serviços gRPC fornecidos
     *
     * @param port Porta para escutar requisições.
     * @param services Um ou mais serviços que implementam (@link BindableService).
     * @return Uma instancia configurada de {@code GrpcServer}.
     */
    public static GrpcServer create(int port, BindableService... services){
        return create(port, builder -> {
            Arrays.asList(services).forEach(builder::addService);
        });
    }

    /*
     * Cria uma instancia de {@code GrpcServer} em uma porta especifica,
     * utilizando um {@link Consumer} de {@link NettyServerBuilder} para
     * personalizar a construção do servidor (adicionar interceptors, SSL etc)
     * adicionando os serviços gRPC fornecidos
     *
     * @param port Porta para escutar requisições.
     * @param consumer Função que recebe o {@code NettyServerBuilder} para configuração personalizada.
     * @return Uma instancia configurada de {@code GrpcServer}.
     */
    public static GrpcServer create(int port, Consumer<NettyServerBuilder> consumer){
        var builder = ServerBuilder.forPort(port);
        consumer.accept((NettyServerBuilder) builder);
        return new GrpcServer(builder.build());
    }

    public GrpcServer start(){
        var services = server.getServices()
                .stream()
                .map(ServerServiceDefinition::getServiceDescriptor)
                .map(ServiceDescriptor::getName)
                .toList();
        try {
            server.start();
            log.info("server started. listening on port {}. services: {}", server.getPort(), services);
            return this;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /*
    * Bloqueia a thread atual ate o servidor seja interrompido.
    * Caso ocorra uma exceção, será lançada uma {@link RuntimeException}
    * */
    public void await(){
        try{
            server.awaitTermination();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /*
    * Força a interrupção imediata do servidor, encerrando todas as conexões em andamento
    * e liberando recursos
    * */
    public void stop(){
        server.shutdownNow();
        log.info("server stopped");
    }
}
