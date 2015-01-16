package com.jje.data.util;
import java.io.FileWriter;
import java.io.PrintWriter;


public class TxtFileWriter
{

    private static int NOT_OPENED = 0;
    private static int OPENED = 1;

    private PrintWriter writer;
    private int status = NOT_OPENED ;

    private long writeCount = 0;

    private long maxToFlush = 10000;
    private boolean flushMode = true;

    public TxtFileWriter()
    {
    }

    public boolean isOpen()
    {
        return (status == OPENED);
    }

    public void open(String localfile,boolean isappend)
    {
        try {
            writer = new PrintWriter( new FileWriter( localfile,isappend ) );
            status = OPENED;
            writeCount = 0;
        }
        catch(Exception e)
        {
            writer = null;
            localfile = null;
            status = NOT_OPENED ;
        }
    }

    public void open(String localfile)
    {
        open(localfile,false);
    }

    public void writeLine(String s)
    {
        if( status == NOT_OPENED ) return;

        try {
            writer.println(s);

            if( flushMode )
                writer.flush();
            else {
                writeCount++;
                if( writeCount >= maxToFlush ) { flush(); writeCount=0; }
            }
        }
        catch(Exception e)
        {
            try {
                if( writer != null ) writer.close();
            }
            catch(Exception e2)
            {
            }
            writer = null;
            status = NOT_OPENED ;
            return;
        }
    }
    public void write(String s)
    {
        if( status == NOT_OPENED ) return;

        try {
            writer.print(s);

            if( flushMode )
                writer.flush();
            else {
                writeCount++;
                if( writeCount >= maxToFlush ) { flush(); writeCount=0; }
            }
        }
        catch(Exception e)
        {
            try {
                if( writer != null ) writer.close();
            }
            catch(Exception e2)
            {
            }
            writer = null;
            status = NOT_OPENED ;
            return;
        }
    }

    public void flush()
    {
        if( status == NOT_OPENED ) return;

        try {
            writer.flush();
        }
        catch(Exception e)
        {
            try {
                if( writer != null ) writer.close();
            }
            catch(Exception e2)
            {
            }
            writer = null;
            status = NOT_OPENED ;
            return;
        }
    }

    public void close()
    {
        flush();

        try {
            if( writer != null )
            {
                writer.close();
                writer = null;
            }
            status = NOT_OPENED;
        }
        catch(Exception e)
        {
            writer = null;
            status = NOT_OPENED ;
        }
    }

    public long getMaxToFlush() {
        return maxToFlush;
    }

    public void setMaxToFlush(long maxToFlush) {
        this.maxToFlush = maxToFlush;
    }

    public boolean isFlushMode() {
        return flushMode;
    }

    public void setFlushMode(boolean flushMode) {
        this.flushMode = flushMode;
    }

}
