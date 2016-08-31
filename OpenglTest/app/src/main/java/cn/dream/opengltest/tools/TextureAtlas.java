package cn.dream.opengltest.tools;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


/**
 * Created by lingu on 2016/8/25.
 */
public class TextureAtlas {
    static final String[] tuple = new String[4];
    private Context context;
    private String fileName;

    public TextureAtlas(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public void parseFile() {
     try {
         InputStream in = context.getResources().getAssets().open(fileName);
         BufferedReader reader = new BufferedReader(new InputStreamReader(in));
         String read;
         while ((read=reader.readLine())!=null){
             Log.e("lgx","read="+read);
         }
         in.close();
         reader.close();
     }catch (IOException e){
         e.printStackTrace();
     }
    }

    /**
     * Returns the number of tuple values read (1, 2 or 4).
     */
    static int readTuple(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int colon = line.indexOf(':');
        if (colon == -1) throw new RuntimeException("Invalid line: " + line);
        int i = 0, lastMatch = colon + 1;
        for (i = 0; i < 3; i++) {
            int comma = line.indexOf(',', lastMatch);
            if (comma == -1) break;
            tuple[i] = line.substring(lastMatch, comma).trim();
            lastMatch = comma + 1;
        }
        tuple[i] = line.substring(lastMatch).trim();
        return i + 1;
    }
}
