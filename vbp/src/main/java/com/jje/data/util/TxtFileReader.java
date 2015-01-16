package com.jje.data.util;


import java.io.BufferedReader;
import java.io.FileReader;


public class TxtFileReader
{

    private static int NOT_OPENED = 0;
    private static int OPENED = 1;

    private BufferedReader reader;
    private int status = NOT_OPENED ;

    public TxtFileReader()
    {
    }

    public void open(String localfile)
    {
        try {
            reader = new BufferedReader( new FileReader( localfile ) );
            status = OPENED;
        }
        catch(Exception e)
        {
            reader = null;
            localfile = null;
            status = NOT_OPENED ;
        }
    }

    public String nextLine()
    {
        if( status == NOT_OPENED ) return null;

        try {
            String s = reader.readLine();
            return s;
        }
        catch(Exception e)
        {
            try {
                if( reader != null ) reader.close();
            }
            catch(Exception e2)
            {
            }
            reader = null;
            status = NOT_OPENED ;
            return null;
        }
    }

    public void close()
    {
        try {
            if( reader != null )
            {
                reader.close();
                reader = null;
            }
            status = NOT_OPENED;
        }
        catch(Exception e)
        {
            reader = null;
            status = NOT_OPENED ;
        }
    }

}
