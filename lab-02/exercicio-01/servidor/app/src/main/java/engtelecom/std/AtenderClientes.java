package engtelecom.std;

import module java.base;

public record AtenderClientes(Socket clientSocket) implements Runnable {

    @Override
    public void run() {
        // protocolo, while, sair...

        try {
            // Obtém os dados do cliente
            var enderecoCliente = clientSocket.getInetAddress().getHostAddress();
            var portaCliente = clientSocket.getPort();
            System.out.printf("Cliente conectado: %s: %d%n", enderecoCliente, portaCliente);

            // Estabelecimentos dos fluxos de entrada e saída
            var reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8)
            );
            var writer = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8)
            );

            // Protocolo de comunicação
            String mensagem = "";

            while (!mensagem.toLowerCase().equals("sair")) {
                mensagem = reader.readLine();

                if (mensagem == null) {
                    break;
                }

                System.out.printf("[%s:%d] -> %s%n", enderecoCliente, portaCliente, mensagem);

                writer.write(mensagem.toUpperCase());
                writer.newLine();
                writer.flush();
            }

            IO.println("Cliente desconectado!");
            reader.close();
            writer.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
