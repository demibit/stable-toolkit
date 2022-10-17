package it.demib.stabletoolkitback.utils;

public interface TextParser<OUT, IN> {

  OUT parse(IN in);
}
