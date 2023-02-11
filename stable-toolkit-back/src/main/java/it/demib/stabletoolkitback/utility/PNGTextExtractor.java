package it.demib.stabletoolkitback.utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.InflaterInputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PNGTextExtractor {

  /**
   * PNG signature constant
   */
  public static final long SIGNATURE = 0x89504E470D0A1A0AL;
  /** PNG Chunk type constants, 4 Critical chunks */
  /**
   * Image header
   */
  private static final int IHDR = 0x49484452;   // "IHDR"
  /**
   * Image trailer
   */
  private static final int IEND = 0x49454E44;   // "IEND"

  /**
   * Textual data
   */
  private static final int tEXt = 0x74455874;   // "tEXt"
  /**
   * International textual data
   */
  private static final int iTXt = 0x69545874;   // "iTXt"

  public String[] showText(InputStream is) throws Exception {
    int data_len = 0;
    int chunk_type = 0;
    byte[] buf = null;

    long signature = readLong(is);

    if (signature != SIGNATURE || (readInt(is) != 13) || (readInt(is) != IHDR)) {
      log.info("--- NOT A PNG IMAGE ---");
      return null;
    }

    buf = new byte[13 + 4];
    is.read(buf, 0, 17);

    while (true) {
      data_len = readInt(is);
      chunk_type = readInt(is);

      if (chunk_type == IEND) {
        break;
      }

      switch (chunk_type) {
        case tEXt: {
          buf = new byte[data_len];
          is.read(buf);
          int keyword_len = 0;
          while (buf[keyword_len] != 0) {
            keyword_len++;
          }
          is.skip(4);
          String lines = new String(buf, keyword_len + 1, data_len - keyword_len - 1, "UTF-8");
          return lines.split("\n");
        }

        case iTXt: {
          /**
           * Keyword:             1-79 bytes (character string)
           * Null separator:      1 byte
           * Compression flag:    1 byte
           * Compression method:  1 byte
           * Language tag:        0 or more bytes (character string)
           * Null separator:      1 byte
           * Translated keyword:  0 or more bytes
           * Null separator:      1 byte
           * Text:                0 or more bytes
           */
          buf = new byte[data_len];
          is.read(buf);
          int keyword_len = 0;
          int trans_keyword_len = 0;
          int lang_flg_len = 0;
          boolean compr = false;
          while (buf[keyword_len] != 0) {
            keyword_len++;
          }
          if (buf[++keyword_len] == 1) {
            compr = true;
          }
          keyword_len++;
          while (buf[++keyword_len] != 0) {
            lang_flg_len++;
          }

          while (buf[++keyword_len] != 0) {
            trans_keyword_len++;
          }

          String[] lines = null;

          if (compr) {
            InflaterInputStream ii = new InflaterInputStream(
                new ByteArrayInputStream(buf, keyword_len + 1, data_len - keyword_len - 1));
            InputStreamReader ir = new InputStreamReader(ii, "UTF-8");
            BufferedReader br = new BufferedReader(ir);
            String read = null;
            while ((read = br.readLine()) != null) {
              log.info(read);
            }
          } else {
            lines = new String(buf, keyword_len + 1, data_len - keyword_len - 1, "UTF-8").split(
                "\n");
          }
          is.skip(4);

          return lines;
        }

        default: {
          buf = new byte[data_len + 4];
          is.read(buf, 0, data_len + 4);
          break;
        }
      }
    }
    is.close();
    return null;
  }

  private static int readInt(InputStream is) throws Exception {
    byte[] buf = new byte[4];
    is.read(buf, 0, 4);
    return (((buf[0] & 0xff) << 24) | ((buf[1] & 0xff) << 16) |
        ((buf[2] & 0xff) << 8) | (buf[3] & 0xff));
  }

  private static long readLong(InputStream is) throws Exception {
    byte[] buf = new byte[8];
    is.read(buf, 0, 8);
    return (((buf[0] & 0xffL) << 56) | ((buf[1] & 0xffL) << 48) |
        ((buf[2] & 0xffL) << 40) | ((buf[3] & 0xffL) << 32) | ((buf[4] & 0xffL) << 24) |
        ((buf[5] & 0xffL) << 16) | ((buf[6] & 0xffL) << 8) | (buf[7] & 0xffL));
  }


}