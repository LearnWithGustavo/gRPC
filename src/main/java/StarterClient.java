import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import service.VeiculoService;
import dev.gu_roodri.veiculo.VeiculoServiceGrpc;
import dev.gu_roodri.veiculo.VeiculoRequest;
import dev.gu_roodri.veiculo.VeiculoResponse;

import java.util.logging.Logger;

public class StarterClient {

    private final static Logger logger = Logger.getLogger(StarterClient.class.getName());

    public static void main(String[] args) {
        //Defino um channel para executar o gRPC
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        //Defino o tipo de stub que vamos usar
        VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channel);

        //Infomamos a request
        VeiculoRequest request = VeiculoRequest.newBuilder()
                .setPlaca("ABC-1234")
                .build();

        //Retornamos uma response com o retorno de nosso service
        VeiculoResponse response = stub.consultarVeiculo(request);

        //Mostramos o resultado da requesição
        logger.info(response.toString());

        //Fechamos o channel
        channel.shutdown();
    }
}
