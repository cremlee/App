package android.luna.Utils;
        import java.io.ByteArrayInputStream;
        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.io.PushbackInputStream;
        import java.nio.ByteBuffer;

public abstract class CharacterDecoder
{
    protected abstract int bytesPerAtom();

    protected abstract int bytesPerLine();

    protected void decodeBufferPrefix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream)
            throws IOException
    {
    }

    protected void decodeBufferSuffix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream)
            throws IOException
    {
    }

    protected int decodeLinePrefix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream)
            throws IOException
    {
        return bytesPerLine();
    }

    protected void decodeLineSuffix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream)
            throws IOException
    {
    }

    protected void decodeAtom(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream, int paramInt)
            throws IOException
    {
        throw new CEStreamExhausted();
    }

    protected int readFully(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
            throws IOException
    {
        for (int i = 0; i < paramInt2; i++)
        {
            int j;
            if ((
                    j = paramInputStream.read()) ==
                    -1) {
                if (i == 0) return -1; return i;
            }paramArrayOfByte[(i + paramInt1)] = ((byte)j);
        }
        return paramInt2;
    }

    public void decodeBuffer(InputStream paramInputStream, OutputStream paramOutputStream)
            throws IOException
    {
        int i;
        PushbackInputStream localPushbackInputStream = new PushbackInputStream(paramInputStream);
        decodeBufferPrefix(localPushbackInputStream, paramOutputStream);
        try
        {
            while (true)
            {
                int localInputStream = decodeLinePrefix(localPushbackInputStream, paramOutputStream);
                for (i = 0; i + bytesPerAtom() < localInputStream; i += bytesPerAtom())
                {
                    decodeAtom(localPushbackInputStream, paramOutputStream, bytesPerAtom());
                    bytesPerAtom();
                }
                if (i + bytesPerAtom() == localInputStream)
                {
                    decodeAtom(localPushbackInputStream, paramOutputStream, bytesPerAtom());
                    bytesPerAtom();
                }
                else {
                    decodeAtom(localPushbackInputStream, paramOutputStream, localInputStream - i);
                }

                decodeLineSuffix(localPushbackInputStream, paramOutputStream);
            }

        }
        catch (CEStreamExhausted localCEStreamExhausted)
        {
            decodeBufferSuffix(localPushbackInputStream, paramOutputStream);
        }
    }

    public byte[] decodeBuffer(String paramString)
            throws IOException
    {
        Object localObject = new byte[paramString.length()];

        paramString.getBytes(0, paramString.length(), (byte[])localObject, 0);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])localObject);
        localObject = new ByteArrayOutputStream();
        decodeBuffer(byteArrayInputStream, (OutputStream)localObject);
        return ((ByteArrayOutputStream)localObject).toByteArray();
    }

    public byte[] decodeBuffer(InputStream paramInputStream)
            throws IOException
    {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        decodeBuffer(paramInputStream, localByteArrayOutputStream);
        return localByteArrayOutputStream.toByteArray();
    }

    public ByteBuffer decodeBufferToByteBuffer(String paramString)
            throws IOException
    {
        return ByteBuffer.wrap(decodeBuffer(paramString));
    }

    public ByteBuffer decodeBufferToByteBuffer(InputStream paramInputStream)
            throws IOException
    {
        return ByteBuffer.wrap(decodeBuffer(paramInputStream));
    }
}