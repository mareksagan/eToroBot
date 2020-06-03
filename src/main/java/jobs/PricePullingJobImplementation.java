package jobs;

import DTOs.NLS;
import DTOs.NasdaqApiFrame;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PricePullingJobImplementation implements PricePullingJob {
    private String nasdaqApiUrl = "http://api.nasdaq.com/api/quote/";
    private String nasdaqApiRTSuffix = "/realtime-trades?&limit=1";

    private Logger logger = LoggerFactory.getLogger(PricePullingJobImplementation.class);

    private String getSymbolFromProps() throws IOException {
        String stockSymbol = null;
        InputStream is = null;

        try {
            Properties props = new Properties();
            is = this.getClass().getClassLoader().getResourceAsStream("bot.properties");

            props.load(is);

            stockSymbol = props.getProperty("symbol");
        } catch (IOException e) {
            logger.error("Could not read symbol from properties file");
        } finally {
            is.close();
        }

        return stockSymbol;
    }

    private void pullNasdaqPrice() {
        try {
            String symbol = getSymbolFromProps();

            String url = nasdaqApiUrl + symbol + nasdaqApiRTSuffix;

            HttpGet getRequest = new HttpGet(url);

            CloseableHttpClient httpClient = HttpClients.createDefault();

            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpClient.execute(getRequest, responseHandler);

            ObjectMapper mapper = new ObjectMapper();

            NasdaqApiFrame nasdaqApiFrame = mapper.readValue(responseBody, NasdaqApiFrame.class);

            for (NLS elem : nasdaqApiFrame.getData().getRows()) {
                String parsedNumber = elem.getNlsPrice().replaceAll("\\$ ", "");
                double price = Double.parseDouble(parsedNumber);
                logger.info("Current " + symbol + " price - " + price);
            }

            httpClient.close();

            checkTrend();
        } catch (IOException e) {
            logger.error("Could not access prices API - " + e.getMessage());
        }

    }

    public static void checkTrend() {

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        pullNasdaqPrice();
    }
}
