import app.core.AssetType;
import app.core.common.Provider;
import app.core.common.ProviderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tests {

    public static void main(String[] args){
        URL url;
        String matches;

        try {
            url = new URL("https://www.leboncoin.fr/ventes_immobilieres/1388631331.htm?ca=12_s");
            Pattern pattern = Pattern.compile("/[^/]+/(?<Id>[0-9]+)\\.htm");
            Matcher m = pattern.matcher(url.getPath());
            m.matches();
            matches = m.group();
            System.out.println(matches);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



    }
}
