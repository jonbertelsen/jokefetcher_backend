package utils;

import com.google.gson.Gson;
import dtos.ChuckDTO;
import dtos.CombinedDTO;
import dtos.DadDTO;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpUtils {

    private static Gson gson = new Gson();

    public static CombinedDTO fetchDataParallel() throws IOException, MalformedURLException, ExecutionException, InterruptedException
    {
        ExecutorService es = Executors.newCachedThreadPool();
        Future<ChuckDTO> chuckDTOFuture =  es.submit(
                () -> gson.fromJson(HttpUtils.fetchData("https://api.chucknorris.io/jokes/random"),ChuckDTO.class)
        );
        Future<DadDTO> dadDTOFuture = es.submit(
                () -> gson.fromJson(HttpUtils.fetchData("https://icanhazdadjoke.com"), DadDTO.class)
        );
        ChuckDTO chuckDTO = chuckDTOFuture.get();
        DadDTO dadDTO = dadDTOFuture.get();
        CombinedDTO combinedDTO = new CombinedDTO(chuckDTO, dadDTO);

        return combinedDTO;
    }


    public static CombinedDTO fetchDataSequential() throws IOException
    {
        String chuck = HttpUtils.fetchData("https://api.chucknorris.io/jokes/random");
        String dad = HttpUtils.fetchData("https://icanhazdadjoke.com");

        ChuckDTO chuckDTO = gson.fromJson(chuck, ChuckDTO.class);
        DadDTO dadDTO = gson.fromJson(dad, DadDTO.class);

        CombinedDTO combinedDTO = new CombinedDTO(chuckDTO,dadDTO);

        return combinedDTO;
    }


    public static String fetchData(String _url) throws MalformedURLException, IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        //con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("User-Agent", "server");

        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }
}
