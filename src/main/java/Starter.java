import server.GrpcServer;
import service.VeiculoService;

public class Starter {

    //Inicia o servidor gRPC e informando o serviço que queremos utilizar nesse servidor
    public static void main(String[] args) {
        GrpcServer.create(6565,
                builder -> {builder.addService(new VeiculoService());
        }).start().await();
    }
}
