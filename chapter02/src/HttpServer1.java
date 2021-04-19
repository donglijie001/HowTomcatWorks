import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/18/21 7:45 PM
 */
public class HttpServer1 {
    //关闭命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown = false;
    public static void main(String[] args) {
        HttpServer1 server = new HttpServer1();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        // 监听端口
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Loop waiting for a request
        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                // 接收到请求后创建一个socket
                socket = serverSocket.accept();
                // 获取输入流和输出流
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // create Request object and parse
                Request request = new Request(input);
                request.parse();

                // create Response object
                Response response = new Response(output);
                response.setRequest(request);
                // 判断是请求静态资源还是sevlet,分别进行处理
                if (request.getUri().startsWith("/servlet")){
                    ServletProcessor1 servletProcessor1=new ServletProcessor1();
                    servletProcessor1.process(request,response);
                }else {
                    StaticResourceProcessor staticResourceProcessor=new StaticResourceProcessor();
                    staticResourceProcessor.process(request,response);
                }

                // Close the socket
                socket.close();

                //check if the previous URI is a shutdown command
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
