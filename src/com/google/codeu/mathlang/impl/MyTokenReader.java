// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;

import com.google.codeu.mathlang.core.tokens.*;
import com.google.codeu.mathlang.parsing.TokenReader;
import java.lang.Character;
import java.lang.Double;
// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {

  private final String source;
  private int initPos;

  public MyTokenReader(String source)
  {
    // Your token reader will only be given a string for input. The string will
    // contain the whole source (0 or more lines).
    this.source = source;
    initPos = 0;
  }

  private char readText() throws IOException
   {
     try
      {
        if(initPos < source.length())
          {
            return source.charAt(initPos++);
          }
        else
          {
            return '\0';
          }
      }
     catch(Exception exception)
      {
        throw new IOException();
      }
   }

  private Token isName(char curr)
    {
       String result = "" + curr;
       try
         {
            c = readText();
            while(!Character.isWhitespace(curr) && curr != ';' && curr != '\0')
              {
                result += curr;
                curr = readText();
              }
         }
       catch(IOException exception)
         {
           throw new IOException("Error in name read.");
         }
       return new NameToken(result);
    }

  private Token isNumber(char curr) throws IOException
   {
     String total = "";

     try
      {
        while(Character.isDigit(curr) && curr != '\0')
          {
              total += curr;
              curr = readText();
          }
        if(curr == '.')
          {
              total += curr;
              curr = readText();
          }
          while(Character.isDigit(curr) && curr != '\0')
          {
              total += curr;
              curr = readText();
          }
          if(total.length() != 0)
          {
              return new NumberToken(Double.parseDouble(total));
          }
      }
     catch(IOException exception)
      {
          throw new IOException("Error in number read.");
      }
         return '\0';
   }


  private Token isString(char curr) throws IOException
   {
     String result = "";
     try
     {
       curr = readText();
       while(curr != '\0' && curr != '\"')
       {
         result += curr;
         curr = readText();
       }
     }
     catch(IOException exception)
     {
       throw new IOException("Error in string read.");
     }
     return new StringToken(result);
   }

  private Token isSymbol(char curr)
  {
    if(curr == ';')
    {
      return new SymbolToken(';');
    }
    else if (curr == '=')
    {
      return new SymbolToken('=');
    }
    else if(curr == '+')
    {
      return new SymbolToken('+');
    }
    else
    {
      return new SymbolToken('-');
    }
  }

  @Override
  public Token next() throws IOException {
    // Most of your work will take place here. For every call to |next| you should
    // return a token until you reach the end. When there are no more tokens, you
    // should return |null| to signal the end of input.

    // If for any reason you detect an error in the input, you may throw an IOException
    // which will stop all execution.
    try
    {
      char curr = readText();
      while (curr == '\n' || curr == ' ')
        {
          curr = readText();
        }
      if (Character.isAlphabetic(curr))
      {
        return isName(curr);
      }
      if (Character.isDigit(curr))
      {
        return isNumber(curr);
      }
      if (curr == ';')
      {
        return isSymbol(';');
      }
      if (curr == '=')
      {
        return isSymbol('=');
      }
      if (curr == '+')
      {
        return isSymbol('+');
      }
      if (curr == '-')
      {
        return isSymbol('-');
      }
      if (curr == '\"')
      {
        return isString(curr);
      }
      if (curr == '\0')
      {
        return null;
      }
    }

    catch(IOException exception)
      {
        throw new IOException("Token not valid.");
      }

    return null;
  }

