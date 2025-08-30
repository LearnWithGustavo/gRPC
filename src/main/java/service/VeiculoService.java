package service;

import dev.gu_roodri.veiculo.VeiculoRequest;
import dev.gu_roodri.veiculo.VeiculoResponse;
import dev.gu_roodri.veiculo.VeiculoServiceGrpc;
import io.grpc.stub.StreamObserver;

public class VeiculoService extends VeiculoServiceGrpc.VeiculoServiceImplBase{

    @Override
    public void consultarVeiculo(VeiculoRequest request, StreamObserver<VeiculoResponse> responseObserver) {
        //Monta o response
        var response = VeiculoResponse.newBuilder()
                .setPlaca(request.getPlaca())
                .setModelo("Gol")
                .setMarca("Volkswagen")
                .setAno(2016)
                .setDisponivel(true)
                .build();

        //Retorna o response para quem solicitou com os dados e completa a resposta
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
