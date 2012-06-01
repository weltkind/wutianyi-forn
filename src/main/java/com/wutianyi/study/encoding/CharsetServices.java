package com.wutianyi.study.encoding;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class CharsetServices
{
    public static void main(String[] args) throws IOException
    {
        byte[] data = FileUtils.readFileToByteArray(new File(Main.class.getResource(
                "/com/wutianyi/study/encoding/gbk.txt").getFile()));
        translateEncoding(data, "utf-8");
        String s = "abc中国";
        byte[] utf8Bytes = s.getBytes(Charset.forName("utf-8"));
        byte[] gbkBytes = s.getBytes(Charset.forName("gbk"));
        CharArrayWriter writer = new CharArrayWriter();
        System.out.println(decode(Main.class.getResourceAsStream("/com/wutianyi/study/encoding/gbk.txt"), writer, Charset.forName("utf-8")));
        System.out.println(writer.toString());
        writer = new CharArrayWriter();
        System.out.println(decode(new ByteArrayInputStream(gbkBytes), writer, Charset.forName("gbk")));
        System.out.println(writer.toString());

    }

    public static byte[] translateEncoding(byte[] data, String destEncoding)
    {
        if (null == data || data.length == 0 || StringUtils.isBlank(destEncoding))
        {
            return data;
        }
        // 完成解析
        // UniversalDetector universalDetector = new UniversalDetector(null);
        // universalDetector.handleData(data, 0, data.length);
        // universalDetector.dataEnd();
        //
        // String srcEncoding = universalDetector.getDetectedCharset();
        // 识别不了，则原样返回
        // if(StringUtils.isBlank(srcEncoding))
        // {
        // return data;
        // }
        //
        // Charset charset = Charset.forName(srcEncoding);

        Charset charset = Charset.forName(destEncoding);
        CharsetDecoder charsetDecoder = charset.newDecoder();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(data);
        byteBuffer.flip();
        int len = data.length % 2 == 0 ? data.length / 2 : data.length / 2 + 1;
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.clear();
        charsetDecoder.onMalformedInput(CodingErrorAction.REPORT);
        charsetDecoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        CoderResult coderResult = charsetDecoder.decode(byteBuffer, charBuffer, false);

        StringBuilder builder = new StringBuilder();
        char[] chars = new char[len];

        boolean r = true;
        while (coderResult != CoderResult.UNDERFLOW)
        {
            if (coderResult == CoderResult.OVERFLOW)
            {
                charBuffer.flip();
                charBuffer.get(chars);
                charBuffer.clear();
                builder.append(chars);
                coderResult = charsetDecoder.decode(byteBuffer, charBuffer, true);
            }
            else
            {
                r = false;
                System.out.println("[Error]: " + coderResult);
                break;
            }
        }
        System.out.println(coderResult);
//        charsetDecoder.flush(charBuffer);
        byteBuffer.flip();
        builder.append(charBuffer.array());
        // builder.append(chars, 0, charBuffer.position());
        // charsetDecoder.decode(byteBuffer, charBuffer, true);

        System.out.println(builder.toString());
        return null;
    }

    public static boolean decode(InputStream input, Writer output, Charset charset) throws IOException
    {
        ReadableByteChannel channel = Channels.newChannel(input);
        CharsetDecoder decoder = charset.newDecoder();

        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        CharBuffer charBuffer = CharBuffer.allocate(1024);

        boolean endOfInput = false;
        boolean error = false;
        while (!endOfInput)
        {
            int n = channel.read(byteBuffer);
            byteBuffer.flip(); // flip so it can be drained

            endOfInput = (n == -1);
            CoderResult coderResult = decoder.decode(byteBuffer, charBuffer, endOfInput);
            error = drainCharBuffer(error, byteBuffer, charBuffer, coderResult, output);
            if (coderResult != CoderResult.UNDERFLOW)
            {
                while (coderResult != CoderResult.UNDERFLOW)
                {
                    coderResult = decoder.decode(byteBuffer, charBuffer, endOfInput);
                    error = drainCharBuffer(error, byteBuffer, charBuffer, coderResult, output);
                }
            }
            byteBuffer.compact(); // compact so it can be refilled
        }
        CoderResult coderResult;
        while ((coderResult = decoder.flush(charBuffer)) != CoderResult.UNDERFLOW)
        {
            error = drainCharBuffer(error, byteBuffer, charBuffer, coderResult, output);
        }
        error = drainCharBuffer(error, byteBuffer, charBuffer, coderResult, output);

        output.flush();
        return !error;
    }

    private static boolean drainCharBuffer(boolean error, ByteBuffer byteBuffer, CharBuffer charBuffer,
            CoderResult coderResult, Writer output) throws IOException
    {
        // write charBuffer to output
        charBuffer.flip();
        if (charBuffer.hasRemaining())
            output.write(charBuffer.toString());
        charBuffer.clear();

        if (coderResult.isError())
        {
            error = true;
            byteBuffer.position(byteBuffer.position() + coderResult.length()); // ignore
                                                                               // invalid
                                                                               // byte
                                                                               // sequence
        }
        return error;
    }
}
