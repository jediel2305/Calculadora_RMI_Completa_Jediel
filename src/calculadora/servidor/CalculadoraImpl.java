package calculadora.servidor;

import calculadora.comum.Calculadora;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraImpl extends UnicastRemoteObject implements Calculadora {

    private static final long serialVersionUID = 1L;

    public CalculadoraImpl() throws RemoteException {
        super(1099);
    }

    @Override
    public int somar(int a, int b) throws RemoteException {
        registrarOperacao("soma", a, "+", b);
        return a + b;
    }

    @Override
    public int subtrair(int a, int b) throws RemoteException {
        registrarOperacao("subtração", a, "-", b);
        return a - b;
    }

    @Override
    public int multiplicar(int a, int b) throws RemoteException {
        registrarOperacao("multiplicação", a, "*", b);
        return a * b;
    }

    @Override
    public double dividir(int a, int b) throws RemoteException {
        registrarOperacao("divisão", a, "/", b);

        if (b == 0) {
            throw new IllegalArgumentException("Não é possível dividir por zero.");
        }

        // A conversão antes da divisão preserva a parte decimal do resultado.
        return (double) a / b;
    }

    private void registrarOperacao(String operacao, int a, String simbolo, int b) {
        try {
            String ipCliente = RemoteServer.getClientHost();
            System.out.println("[" + ipCliente + "] Pedido de " + operacao
                    + " recebido: " + a + " " + simbolo + " " + b);
        } catch (ServerNotActiveException e) {
            System.out.println("[Local] Pedido de " + operacao
                    + " recebido: " + a + " " + simbolo + " " + b);
        }
    }
}
