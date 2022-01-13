package servidor;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import servidor.compilador.ControlInicial;

/**
 *
 * @author Jorge
 */
public class Servidor {

    public static final String RUTA = "C:\\archivos\\";

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(3700);
        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("Conectado");
                client.sendEvent("message", new Message(""));
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("Desconectado");
            }
        });
        server.addEventListener("send", Paquete.class, new DataListener<Paquete>() {

            @Override
            public void onData(SocketIOClient client, Paquete data, AckRequest ackSender) throws Exception {
                System.out.println("Peticion");
                if (data.guardar) {
                    if (guardar(data)) {
                        server.getBroadcastOperations().sendEvent("message", new Message("Exito"));
                    } else {
                        server.getBroadcastOperations().sendEvent("message", new Message("Errores"));
                    }
                } else {
                    //Message msg = new ControlInicial().generar(data);
                    //escribir(msg.getCodigo());
                    server.getBroadcastOperations().sendEvent("message", new ControlInicial().generar(data));
                }
            }
        });
        System.out.println("Iniciando...");
        server.start();
        System.out.println("Iniciado");
    }

    public static boolean guardar(Paquete paquete) {
        for (Archivo archivo : paquete.archivos) {
            try {
                String nombre = RUTA + paquete.carpeta + "\\" + archivo.nombre;
                File file = new File(nombre);
                file.createNewFile();
                try (FileWriter myWriter = new FileWriter(nombre)) {
                    myWriter.write(archivo.contenido);
                }
                System.out.println("guardado " + nombre);
            } catch (IOException ex) {
                return false;
            }
        }
        return true;
    }

}
