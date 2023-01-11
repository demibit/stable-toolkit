package it.demib.stabletoolkitback.utility;

public interface TextParser<OUT, IN> {

  OUT parse(IN in);
}
